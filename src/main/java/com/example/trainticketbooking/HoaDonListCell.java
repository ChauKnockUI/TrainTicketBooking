package com.example.trainticketbooking;

import comp.Rmi.model.HoaDon;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private VBox soTienBox;

    public HoaDonListCell() {
        super();

        // Tạo các thành phần Text với thiết kế hiện đại
        hoadonID = createText(14, "#2C3E50", true);
        tenKH = createText(14, "#34495E", true);
        diaChi = createText(12, "#7F8C8D", false);
        sdt = createText(12, "#7F8C8D", false);
        thoiGian = createText(12, "#7F8C8D", false);
        nhanVienID = createText(12, "#7F8C8D", false);

        // Tổng tiền với thiết kế nổi bật
        soTienText = new Text();
        soTienText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        soTienText.setFill(Color.web("#2ECC71"));
        soTienText.setTextAlignment(TextAlignment.RIGHT);

        Text labelSoTien = new Text("Tổng tiền:");
        labelSoTien.setFont(Font.font("Arial", 12));
        labelSoTien.setFill(Color.web("#95A5A6"));

        soTienBox = new VBox(3, labelSoTien, soTienText);
        soTienBox.setPadding(new Insets(8));
        soTienBox.setBackground(new Background(new BackgroundFill(Color.web("#F4F6F7"), new CornerRadii(8), Insets.EMPTY)));
        soTienBox.setBorder(new Border(new BorderStroke(Color.web("#D5DBDB"), BorderStrokeStyle.SOLID, new CornerRadii(8), BorderWidths.DEFAULT)));

        // Chi tiết hóa đơn với khoảng cách và căn chỉnh tốt hơn
        detailsBox = new VBox(6, hoadonID, tenKH, diaChi, sdt, thoiGian, nhanVienID);
        detailsBox.setPadding(new Insets(10, 0, 0, 15));

        // Tổng tiền và khoảng cách
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        content = new HBox(20, detailsBox, spacer, soTienBox);
        content.setPadding(new Insets(12));
        content.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);" +
                        "-fx-border-color: #E0E0E0;" +
                        "-fx-border-radius: 12;"
        );
    }

    private Text createText(int fontSize, String color, boolean isBold) {
        Text text = new Text();
        text.setFont(Font.font("Arial", isBold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));
        text.setFill(Color.web(color));
        return text;
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