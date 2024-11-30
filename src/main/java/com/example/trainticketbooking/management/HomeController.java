package com.example.trainticketbooking.management;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.trainticketbooking.HoaDonListCell;
import com.example.trainticketbooking.Session;
import com.example.trainticketbooking.TrainListController;
import comp.Rmi.model.HoaDon;
import comp.Rmi.model.NhanVien;
import comp.Rmi.rmi.HoaDonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class HomeController {
    private Parent originalCenter; // Lưu trạng thái gốc

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    private ListView<HoaDon> HomeSVListView;

    @FXML
    public void initialize() {
        originalCenter = (Parent) bp.getCenter(); // Lưu trạng thái gốc của BorderPane Center
        loadHomePage();
    }


    @FXML
    void home(MouseEvent event) {
        if (bp.getCenter() != originalCenter) {
            bp.setCenter(originalCenter); // Khôi phục giao diện Home
        }
        loadHomePage(); // Tải lại danh sách hóa đơn
    }


    @FXML
    void page1(MouseEvent event) {
        loadPage("page1");
    }

    @FXML
    void page2(MouseEvent event) {
        loadPage("page2");
    }

    @FXML
    void page3(MouseEvent event) {
        loadPage("page3");
    }

    @FXML
    void page4(MouseEvent event) {
        loadPage("page4");
    }

    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/trainticketbooking/management/pages/" + page + ".fxml"));
            bp.setCenter(root); // Thay thế nội dung của center
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHomePage() {
        try {
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            HoaDonService hoadonService = (HoaDonService) registry.lookup("HoaDonService");

            List<HoaDon> hoaDons = hoadonService.showAllHoaDon();
            ObservableList<HoaDon> observableList = FXCollections.observableArrayList(hoaDons);

            HomeSVListView.setItems(observableList); // Cập nhật ListView
            HomeSVListView.setCellFactory(listView -> new HoaDonListCell());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
