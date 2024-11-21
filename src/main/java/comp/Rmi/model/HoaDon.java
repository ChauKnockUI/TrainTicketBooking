package comp.Rmi.model;

import java.io.Serializable;
import java.sql.Date;

public class HoaDon implements Serializable {
    public int hoadonID;
    public String tenKH;
    public String diaChi;
    public String sdt;
    public Date thoiGian;
    public int nhanvienID;
    public float SoTien;

    public int getHoadonID() {
        return hoadonID;
    }

    public void setHoadonID(int hoadonID) {
        this.hoadonID = hoadonID;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getNhanvienID() {
        return nhanvienID;
    }

    public void setNhanvienID(int nhanvienID) {
        this.nhanvienID = nhanvienID;
    }

    public float getSoTien() {
        return SoTien;
    }

    public void setSoTien(float soTien) {
        SoTien = soTien;
    }
}
