package com.example.trainticketbooking;

import com.jfoenix.controls.JFXButton;
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

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void handleLogin(ActionEvent event) throws Exception {
        // Lấy thông tin người dùng
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Kiểm tra thông tin đăng nhập
        if (username.equals("admin") && password.equals("admin")) { // Điều kiện đăng nhập đơn giản
            // Nếu đăng nhập thành công, chuyển đến màn hình search-trains.fxml
            System.out.println("Login successful!");

            // Chuyển màn hình
            FXMLLoader loader = new FXMLLoader(getClass().getResource("search-trains.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            // Nếu thông tin đăng nhập sai, in ra thông báo đăng nhập thất bại trong console
            System.out.println("Invalid username or password.");

            // Hiển thị thông báo lỗi
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid username or password");
            alert.showAndWait();
        }
    }


}
