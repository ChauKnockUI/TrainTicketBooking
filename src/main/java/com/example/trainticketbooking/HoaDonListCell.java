package com.example.trainticketbooking;

import comp.Rmi.model.HoaDon;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class HoaDonListCell extends ListCell<HoaDon> {
    private HBox content;
    private Text hoadonID;
    private Text tenKH;
    private Text soTien;

    public HoaDonListCell() {
        super();
        hoadonID = new Text();
        tenKH = new Text();
        soTien = new Text();

        content = new HBox(15, hoadonID, tenKH, soTien);
        content.setStyle("-fx-padding: 10; -fx-background-color: #FFFFFF; -fx-border-color: #D3D3D3; -fx-border-radius: 5;");
    }

    @Override
    protected void updateItem(HoaDon hoaDon, boolean empty) {
        super.updateItem(hoaDon, empty);

        if (empty || hoaDon == null) {
            setText(null);
            setGraphic(null);
        } else {
            hoadonID.setText("ID: " + hoaDon.getHoadonID());
            tenKH.setText("Tên: " + hoaDon.getTenKH());
            soTien.setText("Tổng tiền: " + hoaDon.getSoTien() + " VND");
            setGraphic(content);
        }
    }
}
