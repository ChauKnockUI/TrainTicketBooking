package com.example.trainticketbooking;

import comp.Rmi.model.NhanVien;
import comp.Rmi.model.Station;
import comp.Rmi.model.Train;
import comp.Rmi.rmi.StationService;
import comp.Rmi.rmi.TrainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.List;


public class TrainListController {

    @FXML
    private ListView<Train> trainListView;
    @FXML
    private ComboBox<Station> fromComboBox;
    @FXML
    private ComboBox<Station> toComboBox;
    @FXML
    private Label nameLabel;
    @FXML
    private Button myBookingUIButton;
    public class GlobalConfig {

        // Biến IP toàn cục
        public static String serverIP = "172.20.10.4";
    }


    @FXML
    // Hàm để log ra thông tin các chuyến tàu
    private void logTrainListInfo(ObservableList<Train> trainList) {
        System.out.println("Number of trains: " + trainList.size());
//        for (Train train : trainList) {
//            System.out.println("Train Name: " + train.getName());
//            System.out.println("Departure Time: " + train.getTime());
//            System.out.println("Price: ₹" + train.getPrice1A());
//            System.out.println("AC Fare: ₹" + train.getPrice2A());
//            System.out.println("Sleeper Fare: ₹" + train.getPrice3A());
//            System.out.println("-------------------------");
//        }
    }

    public void initialize() {
        System.out.println("Initialize method called!");
        ObservableList<Train> trainList;
        NhanVien currentNhanVien = Session.getInstance().getNhanVien();

        if (currentNhanVien != null) {
            // Hiển thị tên nhân viên trong giao diện
            nameLabel.setText(currentNhanVien.getTen());
        } else {
            // Nếu không có thông tin, điều hướng về trang đăng nhập
            System.out.println("Không có nhân viên trong phiên. Điều hướng về trang đăng nhập.");
        }
        try {
            Registry registry = LocateRegistry.getRegistry(GlobalConfig.serverIP, 1099);
            TrainService trainService = (TrainService) registry.lookup("TrainService");
            // Tạo danh sách chuyến tàu

            List<Train> trains = trainService.getAllTrains();
            // Chuyển danh sách thành ObservableList và gán vào ListView
            trainList = FXCollections.observableArrayList(trains

            );
            trainListView.setItems(trainList);


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }


        trainListView.setStyle("-fx-background-color: #f0f0f0;"); // Màu xám nhạt
        trainListView.setCellFactory(lv -> new TrainListCell());
        trainListView.setFixedCellSize(100); // Điều chỉnh kích thước này để tăng/giảm khoảng cách giữa các item
        // Gán dữ liệu vào ListView
        trainListView.setItems(trainList);

        // Sử dụng custom cell để hiển thị thông tin chi tiết
        trainListView.setCellFactory(listView -> new TrainListCell());
        try {
            // Kết nối tới server RMI
            Registry registry = LocateRegistry.getRegistry(GlobalConfig.serverIP, 1099);
            StationService stationService = (StationService) registry.lookup("StationService");

            // Gọi getAllStations() để lấy danh sách trạm
            List<Station> stations = stationService.getAllStations();

            // Chuyển đổi danh sách thành ObservableList
            ObservableList<Station> stationList = FXCollections.observableArrayList(stations);

            // Gán danh sách vào fromComboBox
            fromComboBox.setItems(stationList);
            toComboBox.setItems(stationList); // Gán danh sách vào toComboBox

            // Thiết lập để hiển thị tên trạm cho fromComboBox
            fromComboBox.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(Station station, boolean empty) {
                    super.updateItem(station, empty);
                    if (station != null && !empty) {
                        setText(station.getTen());
                    } else {
                        setText(null);
                    }
                }
            });

            // Thiết lập để hiển thị tên trạm cho toComboBox
            toComboBox.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(Station station, boolean empty) {
                    super.updateItem(station, empty);
                    if (station != null && !empty) {
                        setText(station.getTen());
                    } else {
                        setText(null);
                    }
                }
            });

            // Cài đặt `Converter` cho fromComboBox
            fromComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Station station) {
                    return station != null ? station.getTen() : "";
                }

                @Override
                public Station fromString(String s) {
                    return null;
                }
            });

            // Cài đặt `Converter` cho toComboBox
            toComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Station station) {
                    return station != null ? station.getTen() : "";
                }

                @Override
                public Station fromString(String s) {
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onSearchButtonClick(ActionEvent event) {
        try {
            // Kết nối tới server RMI
            Registry registry = LocateRegistry.getRegistry(GlobalConfig.serverIP, 1099);
            TrainService trainService = (TrainService) registry.lookup("TrainService");

            // Lấy thông tin từ các trường đầu vào (giả sử đã có các trường `departureDate`, `departureStationId`, `destinationStationId`)
            // Định dạng ngày từ chuỗi

            Date departureDate = Date.valueOf(("2024-09-18"));
            // Lấy ID của trạm khởi hành và trạm đến từ các ComboBox
            Station departureStation = fromComboBox.getSelectionModel().getSelectedItem();
            Station destinationStation = toComboBox.getSelectionModel().getSelectedItem();

            // Kiểm tra nếu người dùng đã chọn trạm khởi hành và trạm đến
            if (departureStation == null || destinationStation == null) {
                System.out.println("Vui lòng chọn cả trạm khởi hành và trạm đến.");
                return; // Thoát nếu chưa chọn đủ
            }

            int departureStationId = departureStation.getGaID();
            int destinationStationId = destinationStation.getGaID();
            System.out.println("Danh sách các chuyến tàu tìm được:" + departureStationId + "-" + destinationStationId);

            // Thực hiện tìm kiếm
            List<Train> trains = trainService.searchTrains(departureDate, departureStationId, destinationStationId);

            // Chuyển danh sách thành ObservableList và gán vào ListView
            ObservableList<Train> trainList = FXCollections.observableArrayList(trains);
            trainListView.setItems(trainList);
            // In danh sách các ga tìm kiếm được ra terminal
            if (trains.isEmpty()) {
                System.out.println("Không tìm thấy chuyến tàu nào.");
            } else {
                System.out.println("Danh sách các chuyến tàu tìm được:");
                for (Train train : trains) {
                    System.out.println("Tàu: " + train.getTenTau());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML

    private void onMyBookingButtonClick(ActionEvent event) {
        System.out.println("Button My Booking clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MyBooking.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) myBookingUIButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
