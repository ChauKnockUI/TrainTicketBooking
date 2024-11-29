package com.example.trainticketbooking;

import comp.Rmi.model.Carriage;
import comp.Rmi.model.Seat;
import comp.Rmi.model.Train;
import comp.Rmi.rmi.SeatService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateTrainBookingController {

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
    @FXML
    private TextField nameCus;
    @FXML
    private TextField addressCus;
    @FXML
    private TextField phoneCus;

    private Map<String, ToggleButton> seatButtonMap = new HashMap<>();
    private int selectedSeatsCount = 0; // Số ghế đã chọn
    private int hoadonID;
    private String tenNhanVien;
    private String tenKH;
    private int soGhe; // ID ghế đã chọn
    private int tauID;
    private String tenTau;
    private String loaiGhe;
    private Date ngayKhoiHanh;
    private String tenGaDi;
    private String tenGaDen;

    // Khởi tạo ban đầu
    @FXML
    public void initialize() {
        // Gắn các ToggleButtons trong GridPane vào seatButtonMap theo ID
        for (Node node : seatGridPane.getChildren()) {
            if (node instanceof ToggleButton toggleButton) {
                String seatID = toggleButton.getId(); // Lấy ID ToggleButton
                seatButtonMap.put(seatID, toggleButton);

                // Sự kiện click
                toggleButton.setOnAction(event -> {
                    updateSelectedSeatsCount(toggleButton.isSelected());
                });
            }
        }
    }

    private void updateSelectedSeatsCount(boolean isSelected) {
        if (isSelected) {
            selectedSeatsCount++;
        } else {
            selectedSeatsCount--;
        }

        // Cập nhật số lượng ghế và tổng tiền
        soLuongGhe.setText(String.valueOf(selectedSeatsCount));
        int totalPrice = selectedSeatsCount * 100; // Giả định giá mỗi ghế là 100
        tongTien.setText(String.valueOf(totalPrice));
    }

    public void updateSeatStatus(List<Seat> seatsFromDB) {
        for (Seat seat : seatsFromDB) {
            String seatID = String.valueOf(seat.getSoGhe()); // ID ghế
            int status = seat.getStatus(); // Trạng thái: 0 - trống, 1 - đã đặt

            ToggleButton toggleButton = seatButtonMap.get(seatID);

            if (toggleButton != null) {
                if (status == 1) {
                    // Ghế đã đặt
                    toggleButton.setStyle("-fx-background-color: #8596B3;");
                    toggleButton.setDisable(true);
                } else {
                    // Ghế trống
                    toggleButton.setStyle("-fx-background-color: #E9EFFF;");
                    toggleButton.setDisable(false);

                    // Nếu là ghế được truyền từ màn hình trước, đặt selected
                    if (seat.getSoGhe() == soGhe) {
                        toggleButton.setSelected(true);
                        toggleButton.setStyle("-fx-background-color: #8FE388;");
                    }
                }
                toggleButton.setUserData(seat);
            }
        }
    }

    public void setBookingDetails(int hoadonID, String tenNhanVien, String tenKH, int soGhe, int tauID,
                                  String tenTau, String loaiGhe, Date ngayKhoiHanh, String tenGaDi, String tenGaDen) {
        this.hoadonID = hoadonID;
        this.tenNhanVien = tenNhanVien;
        this.tenKH = tenKH;
        this.soGhe = soGhe;
        this.tauID = tauID;
        this.tenTau = tenTau;
        this.loaiGhe = loaiGhe;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.tenGaDi = tenGaDi;
        this.tenGaDen = tenGaDen;

        // Hiển thị thông tin lên giao diện
        trainIdNameLabel.setText("Train: " + tenTau + " (ID: " + tauID + ")");
        carriageLabel.setText("Carriage: " + loaiGhe);
        trainDateDiLabel.setText(ngayKhoiHanh.toString());
        trainGaDiLabel.setText("Departure: " + tenGaDi);
        trainGaDenLabel.setText("Arrival: " + tenGaDen);
        nameCus.setText(tenKH);

        // Lấy dữ liệu ghế từ RMI
        try {
            Registry registry = LocateRegistry.getRegistry("172.20.10.4", 1099);
            SeatService seatService = (SeatService) registry.lookup("SeatService");
            List<Seat> seats = seatService.getAllSeats(tauID, 1); // Giả định ToaID là 1

            if (!seats.isEmpty()) {
                updateSeatStatus(seats);
            } else {
                System.out.println("Không lấy được dữ liệu ghế.");
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
