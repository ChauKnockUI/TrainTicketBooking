package comp.trainticketserver.DAO;

import comp.Rmi.model.CTHD;

import java.sql.*;

public class TicketDAO {

    public CTHD bookTicket(int gheID, int tauID, int gaDi, int gaDen, String tenKH, String diaChi, String sdt, float giaTien, int nhanVienID) {
        Connection connection = null;
        CTHD cthd = null;

        try {
            // Kết nối với cơ sở dữ liệu
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Bắt đầu giao dịch

            // 1. Khóa dòng ghế đang đặt bằng câu lệnh SELECT FOR UPDATE
            String sqlLockGhe = "SELECT GheID FROM ghe WHERE GheID = ? FOR UPDATE";
            try (PreparedStatement psLock = connection.prepareStatement(sqlLockGhe)) {
                psLock.setInt(1, gheID);
                ResultSet rs = psLock.executeQuery();

                if (!rs.next()) {
                    // Nếu không tìm thấy ghế, không thể đặt vé
                    connection.rollback();
                    return null;
                }

                // 2. Thêm hóa đơn vào bảng hoadon
                String sqlInsertHoaDon = "INSERT INTO hoadon (TenKH, DiaChi, SDT, NhanVienID, SoTien) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psHoaDon = connection.prepareStatement(sqlInsertHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                    psHoaDon.setString(1, tenKH);
                    psHoaDon.setString(2, diaChi);
                    psHoaDon.setString(3, sdt);
                    psHoaDon.setInt(4, nhanVienID);
                    psHoaDon.setFloat(5, giaTien);

                    int affectedRows = psHoaDon.executeUpdate();

                    // Kiểm tra nếu không thêm được hóa đơn
                    if (affectedRows == 0) {
                        connection.rollback();
                        return null;
                    }

                    // Lấy ID của hóa đơn vừa thêm
                    try (ResultSet rsHoaDon = psHoaDon.getGeneratedKeys()) {
                        int hoaDonID = 0;
                        if (rsHoaDon.next()) {
                            hoaDonID = rsHoaDon.getInt(1);

                            // 3. Thêm chi tiết hóa đơn vào bảng chitiethoadon
                            String sqlInsertCTHD = "INSERT INTO chitiethoadon (GheID, TauID, GaDi, GaDen, HoaDonID, NgayKhoiHanh) VALUES (?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement psCTHD = connection.prepareStatement(sqlInsertCTHD, Statement.RETURN_GENERATED_KEYS)) {
                                psCTHD.setInt(1, gheID);
                                psCTHD.setInt(2, tauID);
                                psCTHD.setInt(3, gaDi);
                                psCTHD.setInt(4, gaDen);
                                psCTHD.setInt(5, hoaDonID);
                                psCTHD.setTimestamp(6, new Timestamp(System.currentTimeMillis())); // Ngày giờ hiện tại

                                affectedRows = psCTHD.executeUpdate();

                                // Kiểm tra nếu không thêm được chi tiết hóa đơn
                                if (affectedRows == 0) {
                                    connection.rollback();
                                    return null;
                                }

                                // Lấy ID của chi tiết hóa đơn vừa thêm và tạo đối tượng CTHD
                                try (ResultSet rsCTHD = psCTHD.getGeneratedKeys()) {
                                    if (rsCTHD.next()) {
                                        int cthdID = rsCTHD.getInt(1);
                                        cthd = new CTHD();
                                        cthd.setCthdID(cthdID);
                                        cthd.setGheID(gheID);
                                        cthd.setTauID(tauID);
                                        cthd.setGadiID(gaDi);
                                        cthd.setGadenID(gaDen);
                                        cthd.setHoadonID(hoaDonID);
                                        cthd.setNgayKhoiHanh(new Date(System.currentTimeMillis()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Nếu tất cả các thao tác thành công, commit giao dịch
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý rollback trong trường hợp có lỗi
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            // Đảm bảo đóng kết nối
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cthd;
    }

    public boolean cancelTicket(int hoaDonID, int gheID) {
        boolean isCanceled = false;
        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Lock the ticket details in chitiethoadon for update
            String sqlLockCTHD = "SELECT CTHDID FROM chitiethoadon WHERE HoaDonID = ? AND GheID = ? FOR UPDATE";
            try (PreparedStatement psLockCTHD = connection.prepareStatement(sqlLockCTHD)) {
                psLockCTHD.setInt(1, hoaDonID);
                psLockCTHD.setInt(2, gheID);
                ResultSet rs = psLockCTHD.executeQuery();

                if (!rs.next()) {
                    connection.rollback();
                    return false; // Ticket not found
                }

                // 2. Delete from chitiethoadon
                String sqlDeleteCTHD = "DELETE FROM chitiethoadon WHERE HoaDonID = ? AND GheID = ?";
                try (PreparedStatement psDeleteCTHD = connection.prepareStatement(sqlDeleteCTHD)) {
                    psDeleteCTHD.setInt(1, hoaDonID);
                    psDeleteCTHD.setInt(2, gheID);
                    int affectedRows = psDeleteCTHD.executeUpdate();

                    if (affectedRows == 0) {
                        connection.rollback();
                        return false;
                    }
                }

                // 3. Delete from hoadon if there are no remaining ticket details linked to it
                String sqlCheckCTHD = "SELECT CTHDID FROM chitiethoadon WHERE HoaDonID = ?";
                try (PreparedStatement psCheckCTHD = connection.prepareStatement(sqlCheckCTHD)) {
                    psCheckCTHD.setInt(1, hoaDonID);
                    rs = psCheckCTHD.executeQuery();

                    if (!rs.next()) { // No remaining entries in chitiethoadon for this HoaDonID
                        String sqlDeleteHoaDon = "DELETE FROM hoadon WHERE HoaDonID = ?";
                        try (PreparedStatement psDeleteHoaDon = connection.prepareStatement(sqlDeleteHoaDon)) {
                            psDeleteHoaDon.setInt(1, hoaDonID);
                            psDeleteHoaDon.executeUpdate();
                        }
                    }
                }
            }

            connection.commit();
            isCanceled = true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isCanceled;
    }

    public CTHD modifyTicket(int cthdID, int newGheID, int newTauID, int newGaDi, int newGaDen, Date newNgayKhoiHanh,
                             String newTenKH, String newDiaChi, String newSDT, float newGiaTien, int newNhanVienID) {
        Connection connection = null;
        CTHD updatedCTHD = null;

        try {
            // Kết nối với cơ sở dữ liệu
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Bắt đầu giao dịch

            // 1. Khóa dòng chi tiết hóa đơn bằng SELECT FOR UPDATE
            String sqlLockCTHD = "SELECT HoaDonID FROM chitiethoadon WHERE CTHDID = ? FOR UPDATE";
            try (PreparedStatement psLock = connection.prepareStatement(sqlLockCTHD)) {
                psLock.setInt(1, cthdID);
                ResultSet rs = psLock.executeQuery();

                if (!rs.next()) {
                    // Nếu không tìm thấy chi tiết hóa đơn, không thể sửa vé
                    connection.rollback();
                    return null;
                }

                // Lấy ID của hóa đơn liên quan để cập nhật
                int hoaDonID = rs.getInt("HoaDonID");

                // 2. Cập nhật hóa đơn trong bảng hoadon
                String sqlUpdateHoaDon = "UPDATE hoadon SET TenKH = ?, DiaChi = ?, SDT = ?, NhanVienID = ?, SoTien = ? WHERE HoaDonID = ?";
                try (PreparedStatement psUpdateHoaDon = connection.prepareStatement(sqlUpdateHoaDon)) {
                    psUpdateHoaDon.setString(1, newTenKH);
                    psUpdateHoaDon.setString(2, newDiaChi);
                    psUpdateHoaDon.setString(3, newSDT);
                    psUpdateHoaDon.setInt(4, newNhanVienID);
                    psUpdateHoaDon.setFloat(5, newGiaTien);
                    psUpdateHoaDon.setInt(6, hoaDonID);

                    int affectedRows = psUpdateHoaDon.executeUpdate();

                    // Kiểm tra nếu không cập nhật được hóa đơn
                    if (affectedRows == 0) {
                        connection.rollback();
                        return null;
                    }
                }

                String sqlUpdateCTHD = "UPDATE chitiethoadon SET GheID = ?, TauID = ?, GaDi = ?, GaDen = ?, NgayKhoiHanh = ? WHERE CTHDID = ?";
                int affectedRows = 0;  // Declare the variable here

                try (PreparedStatement psUpdateCTHD = connection.prepareStatement(sqlUpdateCTHD)) {
                    psUpdateCTHD.setInt(1, newGheID);
                    psUpdateCTHD.setInt(2, newTauID);
                    psUpdateCTHD.setInt(3, newGaDi);
                    psUpdateCTHD.setInt(4, newGaDen);

                    // Kiểm tra nếu ngày khởi hành là null
                    if (newNgayKhoiHanh != null) {
                        psUpdateCTHD.setDate(5, newNgayKhoiHanh);
                    } else {
                        psUpdateCTHD.setNull(5, Types.DATE);
                    }

                    psUpdateCTHD.setInt(6, cthdID);

                    affectedRows = psUpdateCTHD.executeUpdate(); // Now we can use affectedRows

                    // Kiểm tra nếu không cập nhật được chi tiết hóa đơn
                    if (affectedRows == 0) {
                        connection.rollback();
                        System.out.println("Không tìm thấy hoặc không cập nhật được chi tiết hóa đơn với ID: " + cthdID);
                        return null;  // Returning null if no rows were updated
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    connection.rollback();
                    throw new SQLException("Lỗi khi cập nhật chi tiết hóa đơn: " + e.getMessage());
                }

                // 4. Tạo đối tượng CTHD với các thông tin đã cập nhật
                updatedCTHD = new CTHD();
                updatedCTHD.setCthdID(cthdID);
                updatedCTHD.setGheID(newGheID);
                updatedCTHD.setTauID(newTauID);
                updatedCTHD.setGadiID(newGaDi);
                updatedCTHD.setGadenID(newGaDen);
                updatedCTHD.setHoadonID(hoaDonID);
                updatedCTHD.setNgayKhoiHanh(newNgayKhoiHanh);
            }

            // Nếu tất cả các thao tác thành công, commit giao dịch
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý rollback trong trường hợp có lỗi
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            // Đảm bảo đóng kết nối
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return updatedCTHD;
    }
}
