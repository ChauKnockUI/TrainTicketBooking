package comp.Rmi.model;

import java.io.Serializable;

public class Station implements Serializable {
    private int GaID;
    private String ten;
    private String diaChi;
    private String SDT;

    public int getGaID() {
        return GaID;
    }

    public void setGaID(int gaID) {
        this.GaID = gaID;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
