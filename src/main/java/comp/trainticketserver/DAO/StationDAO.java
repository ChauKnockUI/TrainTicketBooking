package comp.trainticketserver.DAO;

import comp.Rmi.model.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Thêm một nhà ga mới
    public void addStation(Station station) {
        String sql = "INSERT INTO ga (Ten, DiaChi, SDT) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, station.getTen());
            ps.setString(2, station.getDiaChi());
            ps.setString(3, station.getSDT());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy thông tin tất cả các nhà ga
    public List<Station> getAllStations() {
        List<Station> stationList = new ArrayList<>();
        String sql = "SELECT * FROM ga";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Station station = new Station();
                station.setGaID(rs.getInt("GaID"));
                station.setTen(rs.getString("Ten"));
                station.setDiaChi(rs.getString("DiaChi"));
                station.setSDT(rs.getString("SDT"));
                stationList.add(station);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stationList;
    }

    // Lấy thông tin nhà ga theo ID
    public Station getStationById(int gaID) {
        Station station = null;
        String sql = "SELECT * FROM ga WHERE GaID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, gaID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    station = new Station();
                    station.setGaID(rs.getInt("GaID"));
                    station.setTen(rs.getString("Ten"));
                    station.setDiaChi(rs.getString("DiaChi"));
                    station.setSDT(rs.getString("SDT"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return station;
    }

    // Cập nhật thông tin nhà ga
    public void updateStation(Station station) {
        String sql = "UPDATE ga SET Ten = ?, DiaChi = ?, SDT = ? WHERE GaID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, station.getTen());
            ps.setString(2, station.getDiaChi());
            ps.setString(3, station.getSDT());
            ps.setInt(4, station.getGaID());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa nhà ga theo ID
    public void deleteStation(int gaID) {
        String sql = "DELETE FROM ga WHERE GaID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, gaID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

