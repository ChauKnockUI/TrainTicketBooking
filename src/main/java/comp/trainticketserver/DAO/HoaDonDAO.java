package comp.trainticketserver.DAO;

import comp.Rmi.model.CTHDDetailsDTO;
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

    public List<CTHDDetailsDTO> getHoaDonDetailsByNhanVienID(int nhanVienID) {
        List<CTHDDetailsDTO> detailsList = new ArrayList<>();

        String sql = "SELECT " +
                "h.HoaDonID, nv.Ten AS TenNhanVien, h.TenKH, g.GheID, t.TauID, t.TenTau, " +
                "lg.Ten AS LoaiGhe, cthd.NgayKhoiHanh, " +
                "gd.Ten AS GaDi, gd2.Ten AS GaDen " +
                "FROM hoadon h " +
                "JOIN chitiethoadon cthd ON h.HoaDonID = cthd.HoaDonID " +
                "JOIN nhanvien nv ON h.NhanVienID = nv.NhanVienID " +
                "JOIN ghe g ON cthd.GheID = g.GheID " +
                "JOIN loaighe lg ON g.LoaiGheID = lg.LoaiGheID " +
                "JOIN tau t ON cthd.TauID = t.TauID " +
                "JOIN ga gd ON cthd.GaDi = gd.GaID " +
                "JOIN ga gd2 ON cthd.GaDen = gd2.GaID " +
                "WHERE h.NhanVienID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, nhanVienID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CTHDDetailsDTO details = new CTHDDetailsDTO();
                    details.setHoadonID(rs.getInt("HoaDonID"));
                    details.setTenNhanVien(rs.getString("TenNhanVien"));
                    details.setTenKH(rs.getString("TenKH"));
                    details.setSoGhe(rs.getInt("GheID"));
                    details.setTauID(rs.getInt("TauID"));
                    details.setTenTau(rs.getString("TenTau"));
                    details.setLoaiGhe(rs.getString("LoaiGhe"));
                    details.setNgayKhoiHanh(rs.getDate("NgayKhoiHanh"));
                    details.setTenGaDi(rs.getString("GaDi"));
                    details.setTenGaDen(rs.getString("GaDen"));

                    detailsList.add(details);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detailsList;
    }


}
