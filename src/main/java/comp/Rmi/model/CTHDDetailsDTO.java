package comp.Rmi.model;

import java.io.Serializable;
import java.util.Date;

public class CTHDDetailsDTO implements Serializable {
    private int hoadonID;
    private String tenNhanVien;
    private String tenKH;
    private int soGhe;
    private int tauID;
    private String tenTau;
    private String loaiGhe;
    private Date ngayKhoiHanh;
    private String tenGaDi;
    private String tenGaDen;

    public int getHoadonID() {
        return hoadonID;
    }

    public void setHoadonID(int hoadonID) {
        this.hoadonID = hoadonID;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public int getTauID() {
        return tauID;
    }

    public void setTauID(int tauID) {
        this.tauID = tauID;
    }

    public String getTenTau() {
        return tenTau;
    }

    public void setTenTau(String tenTau) {
        this.tenTau = tenTau;
    }

    public String getLoaiGhe() {
        return loaiGhe;
    }

    public void setLoaiGhe(String loaiGhe) {
        this.loaiGhe = loaiGhe;
    }

    public Date getNgayKhoiHanh() {
        return ngayKhoiHanh;
    }

    public void setNgayKhoiHanh(Date ngayKhoiHanh) {
        this.ngayKhoiHanh = ngayKhoiHanh;
    }

    public String getTenGaDi() {
        return tenGaDi;
    }

    public void setTenGaDi(String tenGaDi) {
        this.tenGaDi = tenGaDi;
    }

    public String getTenGaDen() {
        return tenGaDen;
    }

    public void setTenGaDen(String tenGaDen) {
        this.tenGaDen = tenGaDen;
    }
}
