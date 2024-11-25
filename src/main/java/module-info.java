module com.example.trainticketbooking {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.rmi;
    requires java.sql;
    requires jfoenix;

    exports com.example.trainticketbooking.management; // Thêm dòng này
    opens com.example.trainticketbooking.management to javafx.fxml; // Cần thiết để FXML truy cập
    opens com.example.trainticketbooking to javafx.fxml;
    exports com.example.trainticketbooking;
    exports comp.Rmi.rmi to java.rmi;
}