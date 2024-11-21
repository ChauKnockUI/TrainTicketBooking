package comp.trainticketserver.DAO;

import comp.Rmi.model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    // Phương thức để lấy tất cả ghế và thiết lập trạng thái
    public List<Seat> getAllSeats(int tauID, int toaID) {
        List<Seat> seats = new ArrayList<>();

        // Câu lệnh SQL để lấy tất cả ghế từ bảng ghe theo TauID và ToaID
        String sql = "SELECT g.GheID, g.SoGhe, g.LoaiGheID, g.ToaID " +
                "FROM ghe g " +
                "JOIN tautoa tt ON g.ToaID = tt.ToaID " +
                "WHERE tt.TauID = ? AND g.ToaID = ?";

        try (Connection conn = getConnection(); // Đảm bảo rằng bạn có phương thức để lấy kết nối
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Đặt giá trị cho tham số TauID
            ps.setInt(1, tauID);
            // Đặt giá trị cho tham số ToaID
            ps.setInt(2, toaID);

            try (ResultSet rs = ps.executeQuery()) {
                // Duyệt qua từng ghế
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setGheID(rs.getInt("GheID"));
                    seat.setSoGhe(rs.getInt("SoGhe"));
                    seat.setLoaiGheID(rs.getInt("LoaiGheID"));
                    seat.setToaID(rs.getInt("ToaID"));

                    // Kiểm tra trạng thái của ghế
                    seat.setStatus(isSeatBooked(seat.getGheID())); // Kiểm tra ghế đã được đặt chưa

                    // Thêm ghế vào danh sách
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    // Phương thức để kiểm tra ghế đã được đặt hay chưa
    private int isSeatBooked(int gheID) {
        // Kiểm tra trạng thái của ghế dựa vào bảng đặt chỗ (ví dụ chitiethoadon hoặc bảng khác)
        String sql = "SELECT COUNT(*) FROM chitiethoadon WHERE GheID = ?";
        try (Connection conn = getConnection(); // Lấy kết nối
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, gheID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0 ? 1 : 0; // Nếu có số lượng lớn hơn 0 thì ghế đã được đặt
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Nếu không có lỗi thì ghế chưa được đặt
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
