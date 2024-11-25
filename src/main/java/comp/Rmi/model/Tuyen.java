package comp.Rmi.model;

import java.io.Serializable;

public class Tuyen implements Serializable {
    private int tuyenID;
    private String ten;
    private String huong;

    // Constructor mặc định
    public Tuyen() {
    }

    // Getters và Setters
    public int getTuyenID() {
        return tuyenID;
    }

    public void setTuyenID(int tuyenID) {
        this.tuyenID = tuyenID;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHuong() {
        return huong;
    }

    public void setHuong(String huong) {
        this.huong = huong;
    }
}
