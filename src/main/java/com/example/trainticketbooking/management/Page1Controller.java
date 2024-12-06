package com.example.trainticketbooking.management;

import com.example.trainticketbooking.TrainListController;
import comp.Rmi.model.Station;
import comp.Rmi.model.Tuyen;
import comp.Rmi.rmi.StationService;
import comp.Rmi.rmi.TuyenService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Page1Controller {
    @FXML
    private ComboBox<String> routeIdComboBox;

    @FXML
    private ComboBox<String> departureStationComboBox;
    @FXML
    private ComboBox<String> arrivalTimeComboBox;

    @FXML
    private ComboBox<String> arrivalStationComboBox;
    @FXML
    private ComboBox<String> departureTimeComboBox;
    @FXML
    private DatePicker departureDatePicker;
   @FXML
    private DatePicker arrivalDatePicker;
    @FXML
    public void initialize() {
        getTuyen();
        getGaDiVaGaDen();
        themGioVaoComboBox();
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
                routeIdComboBox.getItems().add(tuyen.getTen()); // Sử dụng phương thức getTuyenName() hoặc tên khác
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
                String stationName = station.getTen(); // Giả sử Station có phương thức getStationName()
                departureStationComboBox.getItems().add(stationName);
                arrivalStationComboBox.getItems().add(stationName);
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
    @FXML
    public void handleAddTrainButton(ActionEvent event) {
        // Lấy ngày từ DatePicker
        LocalDate departureDate = departureDatePicker.getValue();
        LocalDate arrivalDay = arrivalDatePicker.getValue();

        // Lấy giờ từ ComboBox
        String departureTime = departureTimeComboBox.getValue();
        String arrivalTime = arrivalTimeComboBox.getValue();

        // Chuyển ngày và giờ sang định dạng "yyyy-MM-dd HH:mm:ss"
        String departureDateTime = convertDateTime(departureDate, departureTime);
        String arrivalDateTime = convertDateTime(arrivalDay, arrivalTime); // Giả sử cùng ngày

        // In ra kết quả (có thể thay đổi logic để lưu vào cơ sở dữ liệu)
        System.out.println("Departure DateTime: " + departureDateTime);
        System.out.println("Arrival DateTime: " + arrivalDateTime);
    }
}
