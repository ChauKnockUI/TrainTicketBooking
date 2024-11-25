package com.example.trainticketbooking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TrainTicketApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(TrainTicketApplication.class.getResource("Login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(TrainTicketApplication.class.getResource("/com/example/trainticketbooking/management/pages/Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 550);
        stage.setTitle("Train Ticket Booking");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
