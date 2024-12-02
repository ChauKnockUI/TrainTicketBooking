package com.example.trainticketbooking;

import comp.Rmi.model.HoaDon;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HoaDonListCell extends ListCell<HoaDon> {
    private HBox content;
    private VBox detailsBox;
    private Text hoadonID;
    private Text tenKH;
    private Text diaChi;
    private Text sdt;
    private Text thoiGian;
    private Text nhanVienID;
    private Text soTienText;
    private StackPane soTienPane;

    public HoaDonListCell() {
        super();
        // Tạo các thành phần Text
        hoadonID = new Text();
        hoadonID.setFont(Font.font("Arial", 14));
        hoadonID.setFill(Color.web("#2C3E50"));

        tenKH = new Text();
        tenKH.setFont(Font.font("Arial", 14));
        tenKH.setFill(Color.web("#34495E"));

        diaChi = new Text();
        diaChi.setFont(Font.font("Arial", 12));
        diaChi.setFill(Color.web("#7F8C8D"));

        sdt = new Text();
        sdt.setFont(Font.font("Arial", 12));
        sdt.setFill(Color.web("#7F8C8D"));

        thoiGian = new Text();
        thoiGian.setFont(Font.font("Arial", 12));
        thoiGian.setFill(Color.web("#7F8C8D"));

        nhanVienID = new Text();
        nhanVienID.setFont(Font.font("Arial", 12));
        nhanVienID.setFill(Color.web("#7F8C8D"));

        // Tổng tiền với nền màu
        soTienText = new Text();
        soTienText.setFont(Font.font("Arial", 16));
        soTienText.setFill(Color.WHITE);
        soTienText.setTextAlignment(TextAlignment.CENTER);

        soTienPane = new StackPane(soTienText);
        soTienPane.setPadding(new Insets(5, 10, 5, 10));
        soTienPane.setBackground(new Background(new BackgroundFill(Color.web("#2ECC71"), new CornerRadii(5), Insets.EMPTY)));

        // Chi tiết hóa đơn
        detailsBox = new VBox(5, hoadonID, tenKH, diaChi, sdt, thoiGian, nhanVienID);
        detailsBox.setPadding(new Insets(10, 0, 0, 10));

        // Tổng tiền
        Region spacer = new Region();
        spacer.setPrefWidth(20);
        content = new HBox(15, detailsBox, spacer, soTienPane);
        content.setPadding(new Insets(10));
        content.setStyle("-fx-background-color: #ECF0F1; -fx-border-color: #BDC3C7; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    @Override
    protected void updateItem(HoaDon hoaDon, boolean empty) {
        super.updateItem(hoaDon, empty);

        if (empty || hoaDon == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Gán dữ liệu vào các thành phần
            hoadonID.setText("ID Hóa Đơn: " + hoaDon.getHoadonID());
            tenKH.setText("Tên KH: " + hoaDon.getTenKH());
            diaChi.setText("Địa Chỉ: " + hoaDon.getDiaChi());
            sdt.setText("SĐT: " + hoaDon.getSdt());
            thoiGian.setText("Thời Gian: " + hoaDon.getThoiGian());
            nhanVienID.setText("Nhân Viên: " + hoaDon.getNhanvienID());
            soTienText.setText(hoaDon.getSoTien() + " VND");
            setGraphic(content);
        }
    }
}
