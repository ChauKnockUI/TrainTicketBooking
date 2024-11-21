package com.example.trainticketbooking;

import comp.Rmi.model.Carriage;
import comp.Rmi.model.TrainCarriage;
import comp.Rmi.rmi.CarriageService;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import comp.Rmi.model.Train;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TrainListCell extends ListCell<Train> {
    private final HBox content;
    private final VBox infoBox;
    private final Label name;
    private final Label dateTime;
    private final HBox priceButtons;
    private final Button price3AButton;
    private final Button price2AButton;
    private final Button price1AButton;

    public TrainListCell() {
        super();
        name = new Label();
        name.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        dateTime = new Label();
        dateTime.setFont(Font.font("Arial", 14));

        infoBox = new VBox(5, name, dateTime);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        price3AButton = createPriceButton("3A");
        price2AButton = createPriceButton("2A");
        price1AButton = createPriceButton("1A");

        priceButtons = new HBox(10, price3AButton, price2AButton, price1AButton);
        priceButtons.setAlignment(Pos.CENTER_RIGHT);

        content = new HBox(20, infoBox, priceButtons);
        content.setPadding(new Insets(15, 20, 15, 20));

        content.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
        HBox.setHgrow(infoBox, Priority.ALWAYS);
        setStyle("-fx-background-color: transparent; -fx-padding: 5 0 5 0;");
    }

    private Button createPriceButton(String classType) {
        Button button = new Button(classType);
        button.setStyle("-fx-background-radius: 15; -fx-min-width: 80; -fx-min-height: 30;");
        button.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        return button;
    }

    @Override
    protected void updateItem(Train train, boolean empty) {
        super.updateItem(train, empty);
        if (train != null && !empty) {
            name.setText(train.getTenTau());
            dateTime.setText(train.getGioDi().toString());
            List<TrainCarriage> carriage;

            try {
                Registry registry = LocateRegistry.getRegistry("172.20.10.4", 1099);
                CarriageService carriageService = (CarriageService) registry.lookup("CarriageService");
                carriage = carriageService.getCarriageByTrainID(train.getTauID());
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
                carriage = null; // Thiết lập null nếu xảy ra lỗi
            }

            // Kiểm tra và cập nhật các nút giá dựa trên kích thước của danh sách carriage
            if (carriage != null && carriage.size() > 0) {
                updatePriceButton(price3AButton, carriage.get(0).getCarriage().getTenToa() + ": ₹100", "#90EE90", "#000", train, carriage.get(0).getCarriage());
            } else {
                price3AButton.setDisable(true); // Vô hiệu hóa nếu không có dữ liệu
            }
            if (carriage != null && carriage.size() > 1) {
                updatePriceButton(price2AButton, carriage.get(1).getCarriage().getTenToa() + ": ₹150", "#FFA500", "#000", train, carriage.get(1).getCarriage());
            } else {
                price2AButton.setDisable(true); // Vô hiệu hóa nếu không có dữ liệu
            }
            if (carriage != null && carriage.size() > 2) {
                updatePriceButton(price1AButton, carriage.get(2).getCarriage().getTenToa() + ": ₹200", "#FFB6C1", "#000", train, carriage.get(2).getCarriage());
            } else {
                price1AButton.setDisable(true); // Vô hiệu hóa nếu không có dữ liệu
            }

            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }

    private void updatePriceButton(Button button, String text, String bgColor, String textColor, Train train, Carriage classType) {
        button.setText(text);
        button.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: " + textColor + ";" +
                "-fx-background-radius: 15; -fx-min-width: 80; -fx-min-height: 30;");
        button.setOnAction(event -> onPriceButtonClicked(train, classType));
    }

    private void onPriceButtonClicked(Train train, Carriage classType) {
        System.out.println("Clicked " + classType + " for train: " + train.getTenTau());
        // Implement navigation to booking screen here
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trainticketbooking/TrainBooking.fxml"));

            Parent root = loader.load();

            // Get the controller to set the train ID and carriage ID
            TrainBookingController controller = loader.getController();
            controller.setTrainAndCarriage(train, classType);

            // Create a new scene and display it
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
