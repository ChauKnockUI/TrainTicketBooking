package comp.trainticketserver.DAO;

import comp.Rmi.model.Price;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PriceDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public List<Price> getUniquePrices(int tauID, int gaDi, int gaDen) {
        List<Price> uniquePrices = new ArrayList<>();
        String sql = "SELECT * FROM banggia AS bg "
                + "WHERE bg.BangGiaID = ("
                + "    SELECT MIN(BangGiaID) "
                + "    FROM banggia "
                + "    WHERE TauID = bg.TauID AND GaDi = bg.GaDi AND GaDen = bg.GaDen AND GiaTien = bg.GiaTien"
                + ") AND bg.TauID = ? AND bg.GaDi = ? AND bg.GaDen = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tauID);
            ps.setInt(2, gaDi);
            ps.setInt(3, gaDen);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Price price = new Price();
                    price.setBangGiaID(rs.getInt("BangGiaID"));
                    price.setTauID(rs.getInt("TauID"));
                    price.setGheID(rs.getInt("GheID"));
                    price.setGaDi(rs.getInt("GaDi"));
                    price.setGaDen(rs.getInt("GaDen"));
                    price.setGiaTien(rs.getInt("GiaTien"));
                    uniquePrices.add(price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uniquePrices;
    }

}
