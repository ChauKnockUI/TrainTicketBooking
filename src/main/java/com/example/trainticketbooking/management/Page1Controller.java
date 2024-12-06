package com.example.trainticketbooking.management;

import com.example.trainticketbooking.TrainListController;
import comp.Rmi.model.Station;
import comp.Rmi.model.Tuyen;
import comp.Rmi.rmi.StationService;
import comp.Rmi.rmi.TuyenService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


public class Page1Controller {
    @FXML
    private ComboBox<String> routeIdComboBox;

    @FXML
    private ComboBox<String> departureStationComboBox;

    @FXML
    private ComboBox<String> arrivalStationComboBox;

    @FXML
    public void initialize() {
        getTuyen();
        getGaDiVaGaDen();
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
    @FXML
    public void handleAddTrainButton(ActionEvent event) {
        // Xử lý sự kiện ở đây
    }
}
