module com.example.trainticketbooking {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.rmi;
    requires java.sql;

    opens com.example.trainticketbooking to javafx.fxml;
    exports com.example.trainticketbooking;
}