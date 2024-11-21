package comp.trainticketserver.DAO;

import comp.Rmi.model.Tuyen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TuyenDAO {

    public List<Tuyen> getAllTuyen() {
        List<Tuyen> tuyens = new ArrayList<>();
        String sql = "SELECT * FROM tuyen";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Tuyen tuyen = new Tuyen();
                tuyen.setTuyenID(resultSet.getInt("tuyenID"));
                tuyen.setTen(resultSet.getString("ten"));
                tuyen.setHuong(resultSet.getString("huong"));
                tuyens.add(tuyen);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tuyens;
    }

    public boolean addTuyen(Tuyen tuyen) {
        String sql = "INSERT INTO tuyen (ten, huong) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tuyen.getTen());
            preparedStatement.setString(2, tuyen.getHuong());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTuyen(Tuyen tuyen) {
        String sql = "UPDATE tuyen SET ten = ?, huong = ? WHERE tuyenID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tuyen.getTen());
            preparedStatement.setString(2, tuyen.getHuong());
            preparedStatement.setInt(3, tuyen.getTuyenID());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTuyen(int tuyenID) {
        String sql = "DELETE FROM tuyen WHERE tuyenID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, tuyenID);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
