package comp.trainticketserver.DAO;

import comp.Rmi.model.NhanVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVienDAO {

    public NhanVien login(String email, String password) {
        String sql = "SELECT * FROM nhanvien WHERE Email = ? AND Password = ?";
        NhanVien nhanVien = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Tạo đối tượng NhanVien từ kết quả ResultSet
                    nhanVien = new NhanVien();
                    nhanVien.setNhanVienID(rs.getInt("NhanVienID"));
                    nhanVien.setTen(rs.getString("Ten"));
                    nhanVien.setSdt(rs.getString("SDT"));
                    nhanVien.setEmail(rs.getString("Email"));
                    nhanVien.setPassword(rs.getString("Password"));
                    nhanVien.setRole(rs.getString("Role")); // Nếu đã thêm cột Role
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nhanVien;
    }
}
