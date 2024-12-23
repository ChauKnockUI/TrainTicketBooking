package com.example.trainticketbooking;

import comp.Rmi.model.*;
import comp.Rmi.model.Train;
import comp.Rmi.rmi.SeatService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import comp.Rmi.rmi.TicketService;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

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
    @FXML
    private Button bookNow;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField nameCus;
    @FXML
    private TextField addressCus;
    @FXML
    private TextField phoneCus;
    @FXML
    private  Label clerkName;
    @FXML
    private Button 	cancleButton;
    @FXML
    private Button changeBoardButton;
    private Map<String, ToggleButton> seatButtonMap = new HashMap<>();
    private int selectedSeatsCount = 0; // Số ghế đã chọn
    public int nvienID = Session.getInstance().getNhanVien().getNhanVienID();
    private Train trainM;
    private Carriage carriageM;
    public void initialize() {
        NhanVien currentNhanVien = Session.getInstance().getNhanVien();

        if (currentNhanVien != null) {
            // Hiển thị tên nhân viên trong giao diện
            nameLabel.setText(currentNhanVien.getTen());
            clerkName.setText(currentNhanVien.getTen());
        } else {
            // Nếu không có thông tin, điều hướng về trang đăng nhập
            System.out.println("Không có nhân viên trong phiên. Điều hướng về trang đăng nhập.");
        }
        // Gắn các ToggleButtons trong GridPane vào seatButtonMap theo ID
        for (Node node : seatGridPane.getChildren()) {
            if (node instanceof ToggleButton toggleButton) {
                String seatID = toggleButton.getId(); // Giữ lại dưới dạng chuỗi
                seatButtonMap.put(seatID, toggleButton);

                // Thêm sự kiện theo dõi trạng thái chọn/deselect
                toggleButton.setOnAction(event -> {
                    updateSelectedSeatsCount(toggleButton.isSelected());
                });
            }
        }
        System.out.println("Seat IDs in seatButtons: " + seatButtonMap.keySet());

        // Thêm sự kiện cho nút Book Now
        bookNow.setOnAction(event -> {
            // Lấy thông tin từ các TextField (hoặc những nơi người dùng nhập thông tin)
            String tenKH = nameCus.getText(); // Thay bằng thông tin từ giao diện
            String diaChi = addressCus.getText(); // Thay bằng thông tin từ giao diện
            String sdt = phoneCus.getText(); // Thay bằng thông tin từ giao diện
            int nhanVienID = nvienID; // Thay bằng thông tin từ giao diện, ví dụ ID nhân viên phục vụ
            // In thông tin từ client
         //   printClientInfo(tenKH, diaChi, sdt, nhanVienID, trainM, carriageM, seatButtonMap);
            // Gọi phương thức bookTickets để đặt vé
            bookTickets(trainM, carriageM, tenKH, diaChi, sdt, nhanVienID);
        });
        cancleButton.setOnAction(event -> {
            // Thoát khỏi màn hình hiện tại
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
        changeBoardButton.setOnAction(event -> {
            // Thoát khỏi màn hình hiện tại
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
    }
    private void updateSelectedSeatsCount(boolean isSelected) {
        if (isSelected) {
            selectedSeatsCount++;
        } else {
            selectedSeatsCount--;
        }

        // Cập nhật Label soLuongGhe
        soLuongGhe.setText(String.valueOf(selectedSeatsCount));

        // Cập nhật Label tongTien (giả định mỗi ghế giá 100)
        int totalPrice = selectedSeatsCount * 100;
        tongTien.setText(String.valueOf(totalPrice));
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
                }
                toggleButton.setUserData(seat); // Đính kèm đối tượng Seat vào ToggleButton
            } else {
                System.out.println("Không tìm thấy ToggleButton cho mã ghế: " + soGhe);
            }
        }
    }

    public void setTrainAndCarriage(Train train, Carriage carriage) throws RemoteException {
        trainM=train;
        carriageM=carriage;
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
                Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
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

    public void bookTickets(Train train, Carriage carriage, String tenKH, String diaChi, String sdt, int nhanVienID) {
        try {
            // Kết nối tới RMI để sử dụng TicketService
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            TicketService ticketService = (TicketService) registry.lookup("TicketService");

            // Biến đếm số ghế đặt thành công
            int successCount = 0;

            // Duyệt qua các ToggleButton để tìm ghế đã chọn
            for (Map.Entry<String, ToggleButton> entry : seatButtonMap.entrySet()) {
                ToggleButton toggleButton = entry.getValue();

                if (toggleButton.isSelected()) {
                    Seat seat = (Seat) toggleButton.getUserData(); // Lấy dữ liệu ghế từ UserData

                    if (seat != null) {
                        try {
                            // Tính giá tiền (giả định mỗi ghế giá 100)
                            float giaTien = 100.0f;

                            // Gọi TicketService để đặt vé
                            CTHD cthd = ticketService.bookTicket(
                                    seat.getGheID(),
                                    train.getTauID(),
                                    train.getGaDiID(),
                                    2,
                                    tenKH,
                                    diaChi,
                                    sdt,
                                    giaTien,
                                    nhanVienID
                            );

                            if (cthd != null) {
                                System.out.println("Đặt vé thành công cho ghế: " + seat.getSoGhe());
                                // Cập nhật trạng thái ghế sau khi đặt
                                toggleButton.setStyle("-fx-background-color: #8596B3;");
                                toggleButton.setDisable(true);
                                toggleButton.setSelected(false);
                                successCount++;
                            } else {
                                System.out.println("Không thể đặt vé cho ghế: " + seat.getSoGhe());
                            }
                        } catch (RemoteException e) {
                            System.out.println("Lỗi kết nối tới TicketService.");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Không thể lấy dữ liệu ghế từ ToggleButton: " + toggleButton.getId());
                        continue;
                    }
                }
            }

            // Sau khi đặt vé, kiểm tra nếu không có ghế nào được đặt thành công
            if (successCount > 0) {
                themVeThanhCongAlert.showSuccessAlert();
            } else {
                trungVeAlert.showSuccessAlert();
            }

            // Sau khi đặt vé, đặt lại số lượng ghế được chọn và tổng tiền
            selectedSeatsCount = 0;
            soLuongGhe.setText("0");
            tongTien.setText("0");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối tới TicketService.");
            JOptionPane.showMessageDialog(null, "Lỗi kết nối tới hệ thống. Vui lòng thử lại sau.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public class themVeThanhCongAlert {

        public static void showSuccessAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đặt vé thành công!", ButtonType.OK);
            alert.setTitle("Thông báo");
            alert.initStyle(StageStyle.UNDECORATED); // Loại bỏ viền cửa sổ
            alert.setHeaderText("Thành công!");
            alert.getDialogPane().setStyle(
                    "-fx-background-color: #f6fafd; " +
                            "-fx-border-color: #189AEC; " +
                            "-fx-border-width: 2px; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-family: 'Arial';"
            );

            // Đổi màu nút OK
            alert.getDialogPane().lookupButton(ButtonType.OK).setStyle(
                    "-fx-background-color: #189AEC; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold;"
            );

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true); // Đảm bảo hiển thị lên trước
            alert.showAndWait();
        }
    }
    public class trungVeAlert {

        public static void showSuccessAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ghế đã được hành khách khác chọn trước", ButtonType.OK);
            alert.setTitle("Thông báo");
            alert.initStyle(StageStyle.UNDECORATED); // Loại bỏ viền cửa sổ
            alert.setHeaderText("Thành công!");
            alert.getDialogPane().setStyle(
                    "-fx-background-color: #f6fafd; " +
                            "-fx-border-color: #189AEC; " +
                            "-fx-border-width: 2px; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-family: 'Arial';"
            );

            // Đổi màu nút OK
            alert.getDialogPane().lookupButton(ButtonType.OK).setStyle(
                    "-fx-background-color: RED; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold;"
            );

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true); // Đảm bảo hiển thị lên trước
            alert.showAndWait();
        }
    }
}
