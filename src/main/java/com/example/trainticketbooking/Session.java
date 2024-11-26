package com.example.trainticketbooking;

import comp.Rmi.model.NhanVien;

public class Session {
    // Biến static lưu instance duy nhất
    private static Session instance;

    // Thuộc tính để lưu thông tin NhanVien
    private NhanVien nhanVien;

    // Constructor private để ngăn tạo đối tượng từ bên ngoài
    private Session() {}

    // Phương thức để lấy instance duy nhất
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Getter và Setter cho NhanVien
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    // Phương thức xóa dữ liệu khi logout
    public void clearSession() {
        nhanVien = null;
    }
}
