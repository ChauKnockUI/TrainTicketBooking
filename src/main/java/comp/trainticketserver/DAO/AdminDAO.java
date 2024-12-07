package comp.trainticketserver.DAO;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AdminDAO {
    public boolean themTauVaGioTau(int tuyenID, String tenTau, int gaDi, int gaDen, String gioDi, String gioDen) {
        // Bắt đầu giao dịch
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            // 1. Thêm tàu vào bảng 'tau'
            String sqlTau = "INSERT INTO tau (TuyenID, TenTau) VALUES (?, ?)";
            try (PreparedStatement stmtTau = connection.prepareStatement(sqlTau, Statement.RETURN_GENERATED_KEYS)) {
                stmtTau.setInt(1, tuyenID);
                stmtTau.setString(2, tenTau);
                stmtTau.executeUpdate();
                int tauID = -1;
                try (var rs = stmtTau.getGeneratedKeys()) {
                    if (rs.next()) {
                        tauID = rs.getInt(1);
                    }
                }
                // 2. Chuyển đổi giờ tàu từ String sang Timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestampGioDi = new Timestamp(sdf.parse(gioDi).getTime());
                Timestamp timestampGioDen = new Timestamp(sdf.parse(gioDen).getTime());
                // 3. Thêm giờ tàu vào bảng 'giotau'
                String sqlGioTau = "INSERT INTO giotau (TauID, GaID, GaDenID, GioDi, GioDen) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmtGioTau = connection.prepareStatement(sqlGioTau)) {
                    stmtGioTau.setInt(1, tauID);
                    stmtGioTau.setInt(2, gaDi);  // Giả sử 'GaDi' là tên ga
                    stmtGioTau.setInt(3, gaDen);  // Giả sử 'GaDen' là tên ga
                    stmtGioTau.setTimestamp(4, timestampGioDi);  // Gán giá trị Timestamp
                    stmtGioTau.setTimestamp(5, timestampGioDen);  // Gán giá trị Timestamp
                    stmtGioTau.executeUpdate();
                }
                // 4. Truy vấn các ToaID liên kết với tuyến (hoặc tàu) từ bảng 'toa'
                List<Integer> toaIDs = new ArrayList<>();
                String sqlToa = "SELECT ToaID FROM toa";
                try (PreparedStatement stmtToa = connection.prepareStatement(sqlToa)) {
                    try (var rsToa = stmtToa.executeQuery()) {
                        while (rsToa.next()) {
                            toaIDs.add(rsToa.getInt("ToaID"));  // Lưu các ToaID vào danh sách
                        }
                    }
                }
                // 5. Thêm liên kết giữa tàu và các toa vào bảng 'tautoa'
                String sqlTaoToa = "INSERT INTO tautoa (TauID, ToaID) VALUES (?, ?)";
                try (PreparedStatement stmtTaoToa = connection.prepareStatement(sqlTaoToa)) {
                    for (int toaID : toaIDs) {
                        stmtTaoToa.setInt(1, tauID);  // Sử dụng TauID từ tàu vừa thêm
                        stmtTaoToa.setInt(2, toaID);  // ToaID của toa
                        stmtTaoToa.addBatch();  // Thêm vào batch để thực hiện cùng lúc
                    }
                    stmtTaoToa.executeBatch();  // Thực thi tất cả câu lệnh thêm
                }
            } catch (ParseException | SQLException e) {
                // Xử lý lỗi và rollback nếu có lỗi
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException rollbackEx) {
                        System.err.println("Có lỗi khi rollback: " + rollbackEx.getMessage());
                    }
                }
                System.err.println("Có lỗi trong quá trình xử lý: " + e.getMessage());
                return false;  // Trả về false nếu có lỗi
            }
            // Commit giao dịch nếu tất cả thành công
            connection.commit();
            return true;  // Trả về true khi mọi thứ thành công
        } catch (SQLException e) {
            // Xử lý khi không thể kết nối cơ sở dữ liệu
            System.err.println("Có lỗi khi kết nối CSDL: " + e.getMessage());
            return false;  // Trả về false nếu không thể kết nối
        } finally {
            // Đảm bảo luôn bật lại chế độ tự động commit sau khi thực hiện xong
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Có lỗi khi bật lại autoCommit: " + e.getMessage());
                }
            }
        }
    }
    public boolean themGiaTien(int tauID, List<Map.Entry<Integer, Double>> toaPriceList, int gaDi, int gaDen) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);  // Bắt đầu giao dịch
            // 1. Duyệt qua từng ToaID và giá tiền tương ứng
            String sqlGhe = "SELECT GheID FROM ghe WHERE ToaID = ?";  // Truy vấn các ghế của toa
            String sqlBangGia = "INSERT INTO banggia (TauID, GheID, GaDi, GaDen, GiaTien) VALUES (?, ?, ?, ?, ?)"; // Câu lệnh chèn giá
            for (Map.Entry<Integer, Double> entry : toaPriceList) {
                int toaID = entry.getKey();
                double giaTien = entry.getValue();
                // 2. Truy vấn danh sách ghe của toa
                try (PreparedStatement stmtGhe = connection.prepareStatement(sqlGhe)) {
                    stmtGhe.setInt(1, toaID);  // Truy vấn theo ToaID
                    try (ResultSet rsGhe = stmtGhe.executeQuery()) {
                        // 3. Duyệt qua tất cả các ghế trong toa và chèn vào bảng 'banggia'
                        try (PreparedStatement stmtBangGia = connection.prepareStatement(sqlBangGia)) {
                            while (rsGhe.next()) {
                                int gheID = rsGhe.getInt("GheID");  // Lấy GheID
                                // Thực hiện chèn vào bảng 'banggia'
                                stmtBangGia.setInt(1, tauID);      // Sử dụng TauID từ tàu
                                stmtBangGia.setInt(2, gheID);      // GheID của ghế
                                stmtBangGia.setInt(3, gaDi);       // GaDi (ID ga đi)
                                stmtBangGia.setInt(4, gaDen);      // GaDen (ID ga đến)
                                stmtBangGia.setDouble(5, giaTien); // Giá tiền của ghế
                                stmtBangGia.addBatch();  // Thêm vào batch
                            }
                            stmtBangGia.executeBatch();  // Thực thi batch chèn tất cả
                        }
                    }
                }
            }
            // Commit giao dịch nếu tất cả thành công
            connection.commit();
            return true;  // Trả về true khi mọi thứ thành công
        } catch (SQLException e) {
            // Rollback nếu có lỗi
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Có lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Có lỗi trong quá trình xử lý: " + e.getMessage());
            return false;  // Trả về false nếu có lỗi xảy ra
        } finally {
            // Đảm bảo luôn bật lại chế độ tự động commit sau khi thực hiện xong
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Có lỗi khi bật lại autoCommit: " + e.getMessage());
                }
            }
        }
    }
    public Map<Integer, Float> getDoanhThuTheoThang(int nam) {
        Map<Integer, Float> doanhThuThang = new HashMap<>();
        String query = "SELECT MONTH(ThoiGian) AS Thang, SUM(SoTien) AS DoanhThu " +
                "FROM hoadon " +
                "WHERE YEAR(ThoiGian) = ? " +
                "GROUP BY MONTH(ThoiGian)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nam);
            ResultSet rs = stmt.executeQuery();
            // Khởi tạo tất cả 12 tháng với giá trị ban đầu là 0
            for (int i = 1; i <= 12; i++) {
                doanhThuThang.put(i, 0f);
            }
            while (rs.next()) {
                int thang = rs.getInt("Thang");
                float doanhThu = rs.getFloat("DoanhThu");
                doanhThuThang.put(thang, doanhThu);  // Cập nhật doanh thu cho tháng tương ứng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doanhThuThang;
    }
    public Map<Integer, Float> getDoanhThuTheoQuy(int nam) {
        Map<Integer, Float> doanhThuQuy = new HashMap<>();
        String query = "SELECT QUARTER(ThoiGian) AS Quy, SUM(SoTien) AS DoanhThu " +
                "FROM hoadon " +
                "WHERE YEAR(ThoiGian) = ? " +
                "GROUP BY QUARTER(ThoiGian)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nam);
            ResultSet rs = stmt.executeQuery();
            // Khởi tạo tất cả 4 quý với giá trị ban đầu là 0
            for (int i = 1; i <= 4; i++) {
                doanhThuQuy.put(i, 0f);
            }
            while (rs.next()) {
                int quy = rs.getInt("Quy");
                float doanhThu = rs.getFloat("DoanhThu");
                doanhThuQuy.put(quy, doanhThu);  // Cập nhật doanh thu cho quý tương ứng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doanhThuQuy;
    }
    public Map<Integer, Float> getDoanhThuTheoNam(int soNam) {
        Map<Integer, Float> doanhThuNam = new HashMap<>();
        String query = "SELECT YEAR(ThoiGian) AS Nam, SUM(SoTien) AS DoanhThu " +
                "FROM hoadon " +
                "WHERE YEAR(ThoiGian) >= ? " +
                "GROUP BY YEAR(ThoiGian)";
        // Tính năm bắt đầu (5 năm trước)
        int namHienTai = LocalDate.now().getYear();
        int namBatDau = namHienTai - soNam + 1;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, namBatDau);
            ResultSet rs = stmt.executeQuery();
            // Khởi tạo tất cả các năm với giá trị ban đầu là 0
            for (int i = namBatDau; i <= namHienTai; i++) {
                doanhThuNam.put(i, 0f);
            }
            while (rs.next()) {
                int nam = rs.getInt("Nam");
                float doanhThu = rs.getFloat("DoanhThu");
                doanhThuNam.put(nam, doanhThu);  // Cập nhật doanh thu cho năm tương ứng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doanhThuNam;
    }
}