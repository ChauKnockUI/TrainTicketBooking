package com.example.trainticketbooking.management;


import com.example.trainticketbooking.TrainListController;
import comp.Rmi.model.RevenueEntry;
import comp.Rmi.rmi.StatictisService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.application.Platform;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Page3Controller {
    @FXML private ComboBox<Integer> monthlyYearComboBox;
    @FXML private ComboBox<Integer> quarterlyYearComboBox;
    @FXML private ComboBox<Integer> yearlyRangeComboBox;

    @FXML private BarChart<String, Number> monthlyChart;
    @FXML private PieChart quarterlyChart;
    @FXML private LineChart<String, Number> yearlyChart;

    @FXML private TableView<RevenueEntry> monthlyTable;
    @FXML private TableView<RevenueEntry> quarterlyTable;
    @FXML private TableView<RevenueEntry> yearlyTable;

    @FXML private TableColumn<RevenueEntry, String> monthColumn;
    @FXML private TableColumn<RevenueEntry, Number> monthRevenueColumn;
    @FXML private TableColumn<RevenueEntry, String> quarterColumn;
    @FXML private TableColumn<RevenueEntry, Number> quarterRevenueColumn;
    @FXML private TableColumn<RevenueEntry, Number> yearColumn;
    @FXML private TableColumn<RevenueEntry, Number> yearRevenueColumn;

    private StatictisService statictisService;

    @FXML
    public void initialize() {
        initializeRMIConnection();
        setupComboBoxes();
        setupTableColumns();
        setupEventListeners();
        updateMonthlyData();
        updateQuarterlyData();
        updateYearlyData();
    }

    private void initializeRMIConnection() {
        try {
            Registry registry = LocateRegistry.getRegistry(TrainListController.GlobalConfig.serverIP, 1099);
            statictisService = (StatictisService) registry.lookup("StatictisService");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("RMI Connection Error", "Failed to connect to the server. Please check your network connection and try again.");
        }
    }

    private void setupComboBoxes() {
        int currentYear = java.time.Year.now().getValue();
        monthlyYearComboBox.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(currentYear - 5, currentYear).boxed().collect(Collectors.toList())
        ));
        monthlyYearComboBox.setValue(currentYear);

        quarterlyYearComboBox.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(currentYear - 5, currentYear).boxed().collect(Collectors.toList())
        ));
        quarterlyYearComboBox.setValue(currentYear);

        yearlyRangeComboBox.setItems(FXCollections.observableArrayList(1, 3, 5, 10));
        yearlyRangeComboBox.setValue(5);
    }

    private void setupTableColumns() {
        monthColumn.setCellValueFactory(cellData -> cellData.getValue().periodProperty());
        monthRevenueColumn.setCellValueFactory(cellData -> cellData.getValue().revenueProperty());
        quarterColumn.setCellValueFactory(cellData -> cellData.getValue().periodProperty());
        quarterRevenueColumn.setCellValueFactory(cellData -> cellData.getValue().revenueProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        yearRevenueColumn.setCellValueFactory(cellData -> cellData.getValue().revenueProperty());
    }

    private void setupEventListeners() {
        monthlyYearComboBox.setOnAction(e -> updateMonthlyData());
        quarterlyYearComboBox.setOnAction(e -> updateQuarterlyData());
        yearlyRangeComboBox.setOnAction(e -> updateYearlyData());
    }

    private void updateMonthlyData() {
        int selectedYear = monthlyYearComboBox.getValue();
        try {
            Map<Integer, Float> monthlyRevenue = statictisService.getDoanhThuTheoThang(selectedYear);
            Platform.runLater(() -> {
                updateMonthlyChart(monthlyRevenue);
                updateMonthlyTable(monthlyRevenue);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            showAlert("Data Retrieval Error", "Failed to retrieve monthly revenue data. Please try again.");
        }
    }

    private void updateQuarterlyData() {
        int selectedYear = quarterlyYearComboBox.getValue();
        try {
            Map<Integer, Float> quarterlyRevenue = statictisService.getDoanhThuTheoQuy(selectedYear);
            Platform.runLater(() -> {
                updateQuarterlyChart(quarterlyRevenue);
                updateQuarterlyTable(quarterlyRevenue);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            showAlert("Data Retrieval Error", "Failed to retrieve quarterly revenue data. Please try again.");
        }
    }

    private void updateYearlyData() {
        int years = yearlyRangeComboBox.getValue();
        try {
            Map<Integer, Float> yearlyRevenue = statictisService.getDoanhThuTheoNam(years);
            Platform.runLater(() -> {
                updateYearlyChart(yearlyRevenue);
                updateYearlyTable(yearlyRevenue);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            showAlert("Data Retrieval Error", "Failed to retrieve yearly revenue data. Please try again.");
        }
    }

    private void updateMonthlyChart(Map<Integer, Float> monthlyRevenue) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Revenue");
        monthlyRevenue.forEach((month, revenue) ->
                series.getData().add(new XYChart.Data<>(getMonthName(month), revenue))
        );
        monthlyChart.getData().clear();
        monthlyChart.getData().add(series);
    }

    private void updateMonthlyTable(Map<Integer, Float> monthlyRevenue) {
        monthlyTable.setItems(FXCollections.observableArrayList(
                monthlyRevenue.entrySet().stream()
                        .map(entry -> new RevenueEntry(getMonthName(entry.getKey()), entry.getValue()))
                        .collect(Collectors.toList())
        ));
    }

    private void updateQuarterlyChart(Map<Integer, Float> quarterlyRevenue) {
        quarterlyChart.getData().clear();
        quarterlyRevenue.forEach((quarter, revenue) -> {
            PieChart.Data slice = new PieChart.Data("Q" + quarter, revenue);
            quarterlyChart.getData().add(slice);
            slice.getNode().setStyle("-fx-pie-color: " + getQuarterColor(quarter) + ";");
        });
    }

    private void updateQuarterlyTable(Map<Integer, Float> quarterlyRevenue) {
        quarterlyTable.setItems(FXCollections.observableArrayList(
                quarterlyRevenue.entrySet().stream()
                        .map(entry -> new RevenueEntry("Qúy" + entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList())
        ));
    }

    private void updateYearlyChart(Map<Integer, Float> yearlyRevenue) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Yearly Revenue");
        yearlyRevenue.forEach((year, revenue) ->
                series.getData().add(new XYChart.Data<>(year.toString(), revenue))
        );
        yearlyChart.getData().clear();
        yearlyChart.getData().add(series);
    }

    private void updateYearlyTable(Map<Integer, Float> yearlyRevenue) {
        yearlyTable.setItems(FXCollections.observableArrayList(
                yearlyRevenue.entrySet().stream()
                        .map(entry -> new RevenueEntry(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList())
        ));
    }

    private String getMonthName(int month) {
        return java.time.Month.of(month).name();
    }

    private String getQuarterColor(int quarter) {
        switch (quarter) {
            case 1: return "#E53935";
            case 2: return "#43A047";
            case 3: return "#FDD835";
            case 4: return "#1E88E5";
            default: return "#2196F3";
        }
    }

    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
