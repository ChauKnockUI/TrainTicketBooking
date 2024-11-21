package comp.trainticketserver.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVienDAO {

    public boolean login(String email, String password) {
        String sql = "SELECT * FROM nhanvien WHERE Email = ? AND Password = ?";
        boolean isAuthenticated = false;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                // If ResultSet has data, login is successful
                if (rs.next()) {
                    isAuthenticated = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
}
