package comp.trainticketserver.DAO;

import comp.Rmi.model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    // Method to connect to the database
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Method to search for trains based on departure time, departure station, and destination station
    public List<Train> searchTrains(Date departureDate, int departureStationID, int destinationStationID) {
        List<Train> trainList = new ArrayList<>();

        String sql = "SELECT t.TauID, t.TenTau, g1.GaID AS GaDiID, g1.Ten AS GaDiTen, gt.GioDi, gt.GioDen, g2.GaID AS GaDenID, g2.Ten AS GaDenTen "
                + "FROM giotau gt "
                + "JOIN tau t ON gt.TauID = t.TauID "
                + "JOIN ga g1 ON gt.GaID = g1.GaID "
                + "JOIN ga g2 ON gt.GaDenID = g2.GaID "
                + "WHERE DATE(gt.GioDi) = ? AND g1.GaID = ? AND g2.GaID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, departureDate);
            ps.setInt(2, departureStationID);
            ps.setInt(3, destinationStationID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Train train = new Train();
                    train.setTauID(rs.getInt("TauID"));
                    train.setTenTau(rs.getString("TenTau"));
                    train.setGaDiID(rs.getInt("GaDiID"));
                    train.setGaDiTen(rs.getString("GaDiTen"));
                    train.setGioDi(rs.getTimestamp("GioDi"));
                    train.setGioDen(rs.getTimestamp("GioDen"));
                    trainList.add(train);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainList;
    }
    // Method to get all trains
    public List<Train> getAllTrains() {
        List<Train> trainList = new ArrayList<>();

        String sql = "SELECT t.TauID, t.TenTau, gt.GioDi, gt.GioDen, g1.GaID AS GaDiID, g1.Ten AS GaDiTen "
                + "FROM tau t "
                + "JOIN giotau gt ON t.TauID = gt.TauID "
                + "JOIN ga g1 ON gt.GaID = g1.GaID";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Train train = new Train();
                train.setTauID(rs.getInt("TauID"));
                train.setTenTau(rs.getString("TenTau"));
                train.setGaDiID(rs.getInt("GaDiID"));
                train.setGaDiTen(rs.getString("GaDiTen"));
                train.setGioDi(rs.getTimestamp("GioDi"));
                train.setGioDen(rs.getTimestamp("GioDen"));
                trainList.add(train);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainList;
    }

    // Method to add a new train
    public boolean addTrain(Train train) {
        String sql = "INSERT INTO tau (TenTau) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, train.getTenTau());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        train.setTauID(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
