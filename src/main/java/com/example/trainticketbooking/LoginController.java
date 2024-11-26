package com.example.trainticketbooking;

import com.jfoenix.controls.JFXButton;
import comp.Rmi.model.NhanVien;
import comp.Rmi.rmi.LoginService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    public class GlobalConfig {
        public static final String serverIP = "172.20.10.4"; // Địa chỉ IP của server RMI
    }
    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            // Lấy thông tin từ giao diện
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Kết nối đến Registry trên server RMI
            String serverIP = GlobalConfig.serverIP; // Sử dụng biến cấu hình GlobalConfig
            int port = 1099; // Cổng mặc định
            Registry registry = LocateRegistry.getRegistry(serverIP, port);

            // Tra cứu dịch vụ LoginService
            LoginService loginService = (LoginService) registry.lookup("LoginService");

            // Gọi phương thức đăng nhập trên server và nhận đối tượng NhanVien
            NhanVien nhanVien = loginService.login(username, password);

            if (nhanVien == null) {
                // Nếu đăng nhập thất bại
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid username or password");
                alert.showAndWait();
            } else {
                Session.getInstance().setNhanVien(nhanVien);
                // Xử lý theo vai trò của nhân viên
                String role = nhanVien.getRole(); // Lấy role từ đối tượng NhanVien

                // Chuyển đến màn hình tương ứng dựa trên vai trò
                String fxmlFile = role != null && role.equalsIgnoreCase("staff") ?
                        "/com/example/trainticketbooking/search-trains.fxml" :
                        "/com/example/trainticketbooking/management/pages/Home.fxml";

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while connecting to the server.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
