package com.example.trainticketbooking.management;

import com.example.trainticketbooking.TrainListController;
import comp.Rmi.model.Station;
import comp.Rmi.model.Train;
import comp.Rmi.model.Tuyen;
import comp.Rmi.rmi.PriceService;
import comp.Rmi.rmi.StationService;
import comp.Rmi.rmi.TrainService;
import comp.Rmi.rmi.TuyenService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Page1Controller {
    @FXML
    private ComboBox<Tuyen> routeIdComboBox;

    @FXML
    private ComboBox<Station> departureStationComboBox;
    @FXML
    private ComboBox<String> arrivalTimeComboBox;

    @FXML
    private ComboBox<Station> arrivalStationComboBox;
    @FXML
    private ComboBox<String> departureTimeComboBox;
    @FXML
    private DatePicker departureDatePicker;
   @FXML
    private DatePicker arrivalDatePicker;
   @FXML
   private ComboBox<Integer> trainIdComboBox;
    @FXML
    private TextField car1PriceField;
    @FXML
    private TextField car2PriceField;
    @FXML
    private TextField car3PriceField;
    @FXML
    private TextField trainNameField;
    @FXML
    public void initialize() {
        getTuyen();
        getGaDiVaGaDen();
        themGioVaoComboBox();
        getTrainID();
    }

    public void getTuyen(){
        try {
            // Kết nối tới RMI Registry
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            TuyenService tuyenService = (TuyenService) registry.lookup("TuyenService");

            // Lấy danh sách các tuyến
            List<Tuyen> tuyenList = tuyenService.getAllTuyen();

            // Thêm các tuyến vào ComboBox
            for (Tuyen tuyen : tuyenList) {
                Tuyen routeItem= new Tuyen(tuyen.getTuyenID(),tuyen.getTen(), tuyen.getHuong());
                routeIdComboBox.getItems().add(routeItem); // Sử dụng phương thức getTuyenName() hoặc tên khác
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getGaDiVaGaDen(){
        try {
            // Kết nối tới RMI Registry
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            StationService stationService = (StationService) registry.lookup("StationService");

            // Gọi phương thức getAllStations() để lấy danh sách các ga
            List<Station> stations = stationService.getAllStations();

            // Thêm danh sách ga vào hai ComboBox
            for (Station station : stations) {
                Station stationItem = new Station( station.getTen(), station.getGaID()); // Giả sử Station có phương thức getStationName()
                departureStationComboBox.getItems().add(stationItem);
                arrivalStationComboBox.getItems().add(stationItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void themGioVaoComboBox(){
        // Thêm danh sách giờ (ví dụ từ 00:00 đến 23:59)
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                String time = String.format("%02d:%02d", hour, minute);
                departureTimeComboBox.getItems().add(time);
                arrivalTimeComboBox.getItems().add(time);
            }
        }
    }
    public String convertDateTime(LocalDate date, String time) {
        try {
            // Tách giờ và phút từ chuỗi time
            String[] timeParts = time.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            // Tạo LocalDateTime từ ngày và giờ
            LocalTime localTime = LocalTime.of(hour, minute);
            LocalDateTime localDateTime = LocalDateTime.of(date, localTime);

            // Định dạng LocalDateTime thành chuỗi "yyyy-MM-dd HH:mm:ss"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return localDateTime.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
    }
    public void getTrainID(){
        try {
            // Kết nối tới RMI Registry
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            TrainService trainService = (TrainService) registry.lookup("TrainService");

            // Lấy danh sách các tuyến
            List<Train> trainList = trainService.getAllTrains();

            // Thêm các tuyến vào ComboBox
            for (Train train : trainList) {
                trainIdComboBox.getItems().add(train.getTauID()); // Sử dụng phương thức getTuyenName() hoặc tên khác
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleAddTrainButton(ActionEvent event) throws java.io.NotSerializableException {
        // Lấy dữ liệu từ giao diện
        LocalDate departureDate = departureDatePicker.getValue();
        LocalDate arrivalDate = arrivalDatePicker.getValue();
        String departureTime = departureTimeComboBox.getValue();
        String arrivalTime = arrivalTimeComboBox.getValue();
        Tuyen tuyen = routeIdComboBox.getValue();
        String trainName= trainNameField.getText();
        Station departureStation = departureStationComboBox.getValue();
        Station arrivalStation = arrivalStationComboBox.getValue();
        int trainID = trainIdComboBox.getValue(); // ComboBox chứa trainID

        // Chuyển đổi ngày giờ sang định dạng "yyyy-MM-dd HH:mm:ss"
        String departureDateTime = convertDateTime(departureDate, departureTime);
        String arrivalDateTime = convertDateTime(arrivalDate, arrivalTime);
        if (tuyen == null) {
            System.out.println("Vui lòng chọn một tuyến!");
            return;
        }
        if (departureStation == null || arrivalStation == null || trainName.isEmpty()) {
            System.out.println("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // In ra chi tiết lỗi
        try {
            // Lấy giá từ các TextField cho từng toa
            double car1Price = Double.parseDouble(car1PriceField.getText());
            double car2Price = Double.parseDouble(car2PriceField.getText());
            double car3Price = Double.parseDouble(car3PriceField.getText());
            List<Map.Entry<Integer, Double>> toaPriceList = new ArrayList<>();
            toaPriceList.add(new AbstractMap.SimpleEntry<>(1, car1Price));
            toaPriceList.add(new AbstractMap.SimpleEntry<>(2, car2Price));
            toaPriceList.add(new AbstractMap.SimpleEntry<>(3, car3Price));


            // Kết nối tới RMI Registry
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            TrainService trainService = (TrainService) registry.lookup("TrainService");

            // Gọi phương thức thêm tàu và giờ tàu
            boolean successAddTrain = trainService.themTauVaGioTau(
                    tuyen.getTuyenID(),
                    trainName, // Tên tàu, có thể lấy từ giao diện nếu cần
                    departureStation.getGaID(), arrivalStation.getGaID(),
//                    getStationID(departureStation),
//                    getStationID(arrivalStation),
                    departureDateTime,
                    arrivalDateTime
            );

            if (!successAddTrain) {
                System.err.println("Thêm tàu thất bại!");
                return;
            }
            System.out.println("Thêm tàu thành công!");

            PriceService priceService = (PriceService) registry.lookup("PriceService");
            // Gọi phương thức thêm giá tiền
            boolean successAddPrice = priceService.themGiaTien(
                    trainID,
                    toaPriceList,
                    departureStation.getGaID(), arrivalStation.getGaID()
            );

            if (!successAddPrice) {
                System.err.println("Thêm giá tiền thất bại!");
                return;
            }
            System.out.println("Thêm giá tiền thành công!");

        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập giá tiền hợp lệ cho các toa!");
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
