package com.example.trainticketbooking;

import comp.Rmi.model.CTHDDetailsDTO;
import comp.Rmi.model.HoaDon;
import comp.Rmi.model.NhanVien;
import comp.Rmi.rmi.HoaDonService;
import comp.Rmi.rmi.TicketService;
import comp.Rmi.rmi.TrainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class MyBookingController {
    @FXML
    private Label nameLabel;
    @FXML
    private ListView<CTHDDetailsDTO> myBookingListView;
    // Nút Sửa
    javafx.scene.control.Button editButton = new javafx.scene.control.Button("Sửa");
    // Các phương thức khác để xử lý logic của MyBooking.fxml

    @FXML
    public void initialize() {
        // Logic khởi tạo nếu cần
        loadMyBookings();
    }
    public void loadMyBookings() {
        try {
            NhanVien currentNhanVien = Session.getInstance().getNhanVien();

            if (currentNhanVien != null) {
                // Hiển thị tên nhân viên trong giao diện
                nameLabel.setText(currentNhanVien.getTen());
            } else {
                // Nếu không có thông tin, điều hướng về trang đăng nhập
                System.out.println("Không có nhân viên trong phiên. Điều hướng về trang đăng nhập.");
            }
            // Lấy ID nhân viên từ Session
            int nhanVienID = Session.getInstance().getNhanVien().getNhanVienID();

            // Kết nối tới RMI registry
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            HoaDonService hoadonService = (HoaDonService) registry.lookup("HoaDonService");

            // Gọi phương thức từ server để lấy danh sách hóa đơn
            List<CTHDDetailsDTO> bookings = hoadonService.getHoaDonDetailsByNhanVienID(nhanVienID);

            // Đổ dữ liệu vào ListView
            ObservableList<CTHDDetailsDTO> observableList = FXCollections.observableArrayList(bookings);
            myBookingListView.setItems(observableList);

            // Cài đặt hiển thị tùy chỉnh cho ListView
            myBookingListView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(CTHDDetailsDTO item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Tạo giao diện tùy chỉnh
                        VBox vbox = new VBox();
                        vbox.setSpacing(5); // Khoảng cách giữa các dòng
                        vbox.setStyle("-fx-background-color: #E6F2FF; -fx-padding: 10; -fx-border-color: #CCCCCC; -fx-border-radius: 5; -fx-background-radius: 5;");

                        // Invoice ID và Ticket clerk
                        Label invoiceLabel = new Label("Invoice ID: " + item.getHoadonID());
                        Label ticketClerkLabel = new Label("Ticket clerk: " + item.getTenNhanVien());

                        // Customer và Seat
                        Label customerLabel = new Label("Customer: " + item.getTenKH());
                        Label seatLabel = new Label("Seat: " + item.getSoGhe());

                        // Train và Class
                        Label trainLabel = new Label(item.getTenTau() + " - " + item.getLoaiGhe());
                        trainLabel.setStyle("-fx-font-weight: bold;");

                        // Date và Stations
                        Label dateLabel = new Label("Date: " + item.getNgayKhoiHanh());
                        Label routeLabel = new Label(item.getTenGaDi() + " -> " + item.getTenGaDen());

                        // Thêm các nút Sửa và Xóa
                        HBox buttonBox = new HBox();
                        buttonBox.setSpacing(10); // Khoảng cách giữa các nút
                        buttonBox.setStyle("-fx-alignment: center-right;");


                        editButton.setOnAction(event -> {
                            // Xử lý logic sửa hóa đơn
                            handleEditBooking(item);
                        });

                        // Nút Xóa
                        javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Xóa");
                        deleteButton.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: #333333;");
                        deleteButton.setOnAction(event -> {
                            // Xác nhận và xóa hóa đơn
                            handleDeleteBooking(item);
                        });

                        // Thêm các nút vào HBox
                        buttonBox.getChildren().addAll(editButton, deleteButton);

                        // Thêm tất cả các thành phần vào VBox
                        vbox.getChildren().addAll(invoiceLabel, ticketClerkLabel, customerLabel, seatLabel, trainLabel, dateLabel, routeLabel, buttonBox);

                        // Đặt VBox làm giao diện hiển thị
                        setGraphic(vbox);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            // Hiển thị thông báo lỗi nếu kết nối RMI thất bại
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load bookings");
            alert.setContentText("Could not connect to the server. Please try again later.");
            alert.showAndWait();
        }
    }

    // Hàm xử lý sự kiện sửa hóa đơn
    private void handleEditBooking(CTHDDetailsDTO item) {

        // Thực hiện logic sửa ở đây (ví dụ mở form sửa)
        try{
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            HoaDonService hoadonService = (HoaDonService) registry.lookup("HoaDonService");
            HoaDon hoaDon= hoadonService.findHoaDonByID(item.getHoadonID());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateTrainBooking.fxml"));
            Parent root = loader.load();

            UpdateTrainBookingController controller = loader.getController();
            controller.setBookingDetails(
                    item.getHoadonID(), item.getTenNhanVien(), item.getTenKH(), item.getSoGhe(), item.getTauID(), item.getTenTau(),
                    item.getLoaiGhe(), Date.valueOf(String.valueOf(item.getNgayKhoiHanh())), item.getTenGaDi(), item.getTenGaDen(), hoaDon.diaChi, hoaDon.sdt, hoaDon.SoTien
            );


            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    // Hàm xử lý sự kiện xóa hóa đơn
    // Hàm xử lý sự kiện xóa hóa đơn
    private void handleDeleteBooking(CTHDDetailsDTO item) {
        // Hiển thị hộp thoại xác nhận
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Booking");
        confirmation.setHeaderText("Are you sure you want to delete this booking?");
        confirmation.setContentText("Invoice ID: " + item.getHoadonID());

        confirmation.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    // Gọi hàm xóa booking qua RMI
                    if (deleteBookingFromServer(item)) {
                        // Nếu xóa thành công, hiển thị thông báo và tải lại danh sách
                        showAlert(Alert.AlertType.INFORMATION, "Success",
                                "Booking canceled successfully",
                                "The booking has been successfully canceled.");
                        loadMyBookings();
                    } else {
                        // Nếu xóa thất bại
                        showAlert(Alert.AlertType.WARNING, "Failure",
                                "Cancellation failed",
                                "Unable to cancel the booking. Please try again.");
                    }
                } catch (Exception e) {
                    // Xử lý lỗi khi kết nối hoặc thực thi RMI
                    e.printStackTrace();
                    System.out.println("Error: " + e.getMessage());
                    Throwable cause = e.getCause();
                    if (cause != null) {
                        System.out.println("Cause: " + cause.getMessage());
                        cause.printStackTrace();
                    }
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Cancellation error",
                            "An error occurred while canceling the booking. Please try again.");
                }
            }
        });
    }

    // Hàm gọi RMI để xóa booking
    private boolean deleteBookingFromServer(CTHDDetailsDTO item) throws Exception {

        // Kết nối tới RMI registry
        Registry registry1 = LocateRegistry.getRegistry("172.20.10.4", 1099);
        // Kiểm tra các dịch vụ đã được đăng ký
        String[] services = registry1.list();
        System.out.println("Services registered in registry:");
        for (String service : services) {
            System.out.println(service);
        }
        if (!Arrays.asList(services).contains("TicketService")) {
            System.err.println("Error: TicketService is not registered in the registry.");
            return false;
        }
        TicketService ticketService = (TicketService) registry1.lookup("TicketService");

        // Gọi phương thức hủy vé trên server
        System.out.println("Attempting to cancel ticket: Invoice ID " + item.getHoadonID() +
                ", Seat " + item.getSoGhe());
        return ticketService.cancelTicket(item.getHoadonID(), item.getSoGhe());
    }

    // Hàm hiển thị thông báo tiện ích
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }






}
