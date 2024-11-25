package comp.trainticketserver.DAO;

import comp.Rmi.model.Carriage;
import comp.Rmi.model.Train;
import comp.Rmi.model.TrainCarriage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToaDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection(); // Phương thức để lấy kết nối cơ sở dữ liệu
    }

    public List<TrainCarriage> getCarriageByTrainID(int trainID) {
        List<TrainCarriage> trainCarriages = new ArrayList<>();

        String sql = "SELECT tt.TauToaID, t.TauID, t.TenTau, c.ToaID, c.TenToa " +
                "FROM tautoa tt " +
                "JOIN tau t ON tt.TauID = t.TauID " +
                "JOIN toa c ON tt.ToaID = c.ToaID " +
                "WHERE tt.TauID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, trainID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TrainCarriage trainCarriage = new TrainCarriage();

                    // Thiết lập TrainCarriage
                    trainCarriage.setTauToaID(rs.getInt("TauToaID"));

                    // Thiết lập Train
                    Train train = new Train();
                    train.setTauID(rs.getInt("TauID"));
                    train.setTenTau(rs.getString("TenTau"));
                    trainCarriage.setTrain(train);

                    // Thiết lập Carriage
                    Carriage carriage = new Carriage();
                    carriage.setToaID(rs.getInt("ToaID"));
                    carriage.setTenToa(rs.getString("TenToa"));
                    trainCarriage.setCarriage(carriage);

                    trainCarriages.add(trainCarriage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainCarriages;
    }


}
