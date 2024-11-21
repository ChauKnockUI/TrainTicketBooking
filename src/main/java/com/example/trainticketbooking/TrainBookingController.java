package com.example.trainticketbooking;

import comp.Rmi.model.Carriage;
import comp.Rmi.model.Seat;
import comp.Rmi.model.Train;
import comp.Rmi.rmi.SeatService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainBookingController {

    @FXML
    private Label trainIdNameLabel;
    @FXML
    private Label carriageLabel;
    @FXML
    private Label trainDateDiLabel;
    @FXML
    private Label trainTimeDiLabel;
    @FXML
    private Label trainGaDiLabel;
    @FXML
    private Label trainDateDenLabel;
    @FXML
    private Label trainTimeDenLabel;
    @FXML
    private Label trainGaDenLabel;
    @FXML
    private Label soLuongGhe;
    @FXML
    private Label tongTien;
    @FXML
    private GridPane seatGridPane;
    private Map<String, ToggleButton> seatButtonMap = new HashMap<>();

    public void initialize() {
        // Gắn các ToggleButtons trong GridPane vào seatButtonMap theo ID
        for (Node node : seatGridPane.getChildren()) {
            if (node instanceof ToggleButton toggleButton) {
                String seatID = toggleButton.getId(); // Giữ lại dưới dạng chuỗi
                seatButtonMap.put(seatID, toggleButton);
            }
        }
        System.out.println("Seat IDs in seatButtons: " + seatButtonMap.keySet());
    }

    public void updateSeatStatus(List<Seat> seatsFromDB) {
        for (Seat seat : seatsFromDB) {
            String soGhe = String.valueOf(seat.getSoGhe()); // Đảm bảo soGhe là chuỗi
            int status = seat.getStatus();

            ToggleButton toggleButton = seatButtonMap.get(soGhe);

            if (toggleButton != null) {
                if (status == 1) {
                    // Nếu ghế đã được đặt, đổi màu và vô hiệu hóa click
                    toggleButton.setStyle("-fx-background-color: #8596B3;");
                    toggleButton.setDisable(true); // Vô hiệu hóa ghế đã đặt
                } else {
                    // Nếu ghế trống, đặt màu nền và thêm sự kiện click
                    toggleButton.setStyle("-fx-background-color: #E9EFFF;");
                    toggleButton.setDisable(false); // Cho phép click

                    // Thêm sự kiện click để in ra seatID
                    toggleButton.setOnAction(event -> {
                        System.out.println("Ghế đã chọn: " + seat.getGheID());
                    });
                }
                toggleButton.setUserData(seat); // Đính kèm đối tượng Seat vào ToggleButton
            } else {
                System.out.println("Không tìm thấy ToggleButton cho mã ghế: " + soGhe);
            }
        }
    }





    public void setTrainAndCarriage(Train train, Carriage carriage) throws RemoteException {
        if (trainIdNameLabel != null) {
            trainIdNameLabel.setText("ID: " + train.getTauID() + " - Train Name: " + train.getTenTau());
            carriageLabel.setText("Class " + carriage.getToaID() + " & Carriage: " + carriage.getTenToa());

            String[] dateTimeParts = DateTimeUtil.splitDateTime(train.getGioDi().toString());
            String[] dateTimeParts2 = DateTimeUtil.splitDateTime(train.getGioDen().toString());

            trainDateDiLabel.setText(dateTimeParts[0]);
            trainTimeDiLabel.setText(dateTimeParts[1]);
            trainGaDiLabel.setText(train.getGaDiTen());
            trainDateDenLabel.setText(dateTimeParts2[0]);
            trainTimeDenLabel.setText(dateTimeParts2[1]);

            try {
                Registry registry = LocateRegistry.getRegistry("172.20.10.4", 1099);
                SeatService seatService = (SeatService) registry.lookup("SeatService");
                List<Seat> seats = seatService.getAllSeats(train.getTauID(), carriage.getToaID());

                if (!seats.isEmpty()) {
                    updateSeatStatus(seats);
                } else {
                    System.out.println("Không lấy được dữ liệu ghế.");
                }
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("trainIdLabel is null!");
        }
    }
}
