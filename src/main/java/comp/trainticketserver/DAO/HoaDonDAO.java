package comp.trainticketserver.DAO;

import comp.Rmi.model.HoaDon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    // Method to show all invoices
    public List<HoaDon> showAllHoaDon() {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT * FROM hoadon";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setHoadonID(rs.getInt("HoaDonID"));
                hoaDon.setTenKH(rs.getString("TenKH"));
                hoaDon.setDiaChi(rs.getString("DiaChi"));
                hoaDon.setSdt(rs.getString("SDT"));
                hoaDon.setThoiGian(rs.getDate("ThoiGian"));
                hoaDon.setNhanvienID(rs.getInt("NhanVienID"));
                hoaDon.setSoTien(rs.getFloat("SoTien"));
                hoaDons.add(hoaDon);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDons;
    }

    // Method to find an invoice by ID
    public HoaDon findHoaDonByID(int hoadonID) {
        HoaDon hoaDon = null;
        String sql = "SELECT * FROM hoadon WHERE HoaDonID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, hoadonID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hoaDon = new HoaDon();
                    hoaDon.setHoadonID(rs.getInt("HoaDonID"));
                    hoaDon.setTenKH(rs.getString("TenKH"));
                    hoaDon.setDiaChi(rs.getString("DiaChi"));
                    hoaDon.setSdt(rs.getString("SDT"));
                    hoaDon.setThoiGian(rs.getDate("ThoiGian"));
                    hoaDon.setNhanvienID(rs.getInt("NhanVienID"));
                    hoaDon.setSoTien(rs.getFloat("SoTien"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDon;
    }
}
