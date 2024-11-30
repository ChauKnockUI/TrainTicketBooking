package com.example.trainticketbooking;

import comp.Rmi.model.HoaDon;
import comp.Rmi.rmi.HoaDonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class HomeSVController {
    @FXML
    private ListView<HoaDon> HomeSVListView;

    public void initialize() {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            HoaDonService hoadonService = (HoaDonService) registry.lookup("HoaDonService");

            // Gọi phương thức RMI để lấy danh sách hóa đơn
            List<HoaDon> hoaDons = hoadonService.showAllHoaDon();

            // Chuyển đổi danh sách thành ObservableList để hiển thị trên ListView
            ObservableList<HoaDon> observableList = FXCollections.observableArrayList(hoaDons);
            HomeSVListView.setItems(observableList);

            // Định dạng hiển thị bằng cách sử dụng Custom Cell Factory
            HomeSVListView.setCellFactory(listView -> new HoaDonListCell());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
