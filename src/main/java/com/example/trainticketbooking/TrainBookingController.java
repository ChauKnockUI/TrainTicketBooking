package com.example.trainticketbooking;

import comp.Rmi.model.Carriage;
import comp.Rmi.model.Seat;
import comp.Rmi.model.Train;
import comp.Rmi.rmi.SeatService;
import javafx.fxml.FXML;
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
    private GridPane seatGridPane; // Make sure this matches the fx:id in FXML
    private Map<String, ToggleButton> seatButtons = new HashMap<>();

    @FXML
    public void initialize() {
        // Initialize ToggleButtons dynamically from seatGridPane children
        for (int rowIndex = 2; rowIndex <= 6; rowIndex++) {
            final int finalRowIndex = rowIndex; // Make rowIndex effectively final
            for (int columnIndex = 0; columnIndex <= 4; columnIndex++) {
                final int finalColumnIndex = columnIndex; // Make columnIndex effectively final
                if (columnIndex == 2) {
                    // Skip the middle column for labels
                    continue;
                }
                String rowLabel = String.valueOf((char) ('A' + (rowIndex - 2)));
                String seatLabel = rowLabel + (columnIndex < 2 ? (columnIndex + 1) : (columnIndex - 1));
                ToggleButton button = (ToggleButton) seatGridPane.getChildren().stream()
                        .filter(node -> {
                            Integer nodeRowIndex = GridPane.getRowIndex(node);
                            Integer nodeColumnIndex = GridPane.getColumnIndex(node);
                            return nodeRowIndex != null && nodeColumnIndex != null &&
                                    nodeRowIndex == finalRowIndex && nodeColumnIndex == finalColumnIndex;
                        })
                        .findFirst().orElse(null);
                if (button != null) {
                    seatButtons.put(seatLabel, button);
                    // Add event handler to print seat ID to console when clicked
                    button.setOnAction(event -> {
                        System.out.println("Clicked seat: " + seatLabel);
                    });
                }
            }
        }
    }

    public void updateSeatStatus(List<Seat> seats) {
        for (Seat seat : seats) {
            String seatID = convertSeatID(seat.getGheID()); // Phương thức chuyển đổi seatID thành tên dạng "A1", "B2",...
            ToggleButton button = seatButtons.get(seatID);
            if (button != null) {
                // Cập nhật trạng thái ghế và màu sắc
                if (seat.getStatus() == 1) { // Ghế đã được đặt
                    button.setSelected(true);
                    button.setDisable(true);
                    button.setStyle("-fx-background-color: #8596B3;");
                } else if (seat.getStatus() == 0) { // Ghế chưa được đặt
                    button.setSelected(false);
                    button.setDisable(false);
                    button.setStyle("-fx-background-color: #E9EFFF;");
                }
            }
        }
    }

    private String convertSeatID(int seatID) {
        int row = (seatID - 1) / 4;
        int col = (seatID - 1) % 4;
        char rowLabel = (char) ('A' + row);
        int seatNumber = col + 1;
        return rowLabel + String.valueOf(seatNumber);
    }

    public void setTrainAndCarriage(Train train, Carriage carriage) throws RemoteException {
        if (trainIdNameLabel != null) {
            trainIdNameLabel.setText("ID: " + train.getTauID() + "- Train Name: " + train.getTenTau());
            carriageLabel.setText("Class " + carriage.getToaID() + " & Carriage: " + carriage.getTenToa());

            String[] dateTimeParts = DateTimeUtil.splitDateTime(train.getGioDi().toString());
            String[] dateTimeParts2 = DateTimeUtil.splitDateTime(train.getGioDen().toString());

            trainDateDiLabel.setText(dateTimeParts[0]);
            trainTimeDiLabel.setText(dateTimeParts[1]);
            trainGaDiLabel.setText(train.getGaDiTen());
            trainDateDenLabel.setText(dateTimeParts2[0]);
            trainTimeDenLabel.setText(dateTimeParts2[1]);

            try {
                Registry registry = LocateRegistry.getRegistry("172.20.10.5", 1099);
                SeatService seatService = (SeatService) registry.lookup("SeatService");
                List<Seat> seats = seatService.getAllSeats(train.getTauID(), carriage.getToaID());
                System.out.println("Total seats in grid: " + seatGridPane.getChildren().size());
                if (seats.size() > 0) {
                    System.out.println("Total seats in grid: " + seatGridPane.getChildren().size());
                } else {
                    System.out.println("khong lay dươc");
                }

                updateSeatStatus(seats);

            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("trainIdLabel is null!");
        }
    }
}
