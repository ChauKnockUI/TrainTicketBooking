package com.example.trainticketbooking.management;

import com.example.trainticketbooking.TrainListController;
import comp.Rmi.model.Tuyen;
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
    public void initialize() {
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
    @FXML
    public void handleAddTrainButton(ActionEvent event) {
        // Xử lý sự kiện ở đây
    }
}
