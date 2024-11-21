package comp.Rmi.model;

import java.io.Serializable;
import java.sql.Date;

public class CTHD implements Serializable {
    public int cthdID;
    public int gheID;
    public int tauID;
    public  int gadiID;
    public int gadenID;
    public int hoadonID;
    public Date ngayKhoiHanh;

    public int getCthdID() {
        return cthdID;
    }

    public void setCthdID(int cthdID) {
        this.cthdID = cthdID;
    }

    public int getGheID() {
        return gheID;
    }

    public void setGheID(int gheID) {
        this.gheID = gheID;
    }

    public int getTauID() {
        return tauID;
    }

    public void setTauID(int tauID) {
        this.tauID = tauID;
    }

    public int getGadiID() {
        return gadiID;
    }

    public void setGadiID(int gadiID) {
        this.gadiID = gadiID;
    }

    public int getGadenID() {
        return gadenID;
    }

    public void setGadenID(int gadenID) {
        this.gadenID = gadenID;
    }

    public int getHoadonID() {
        return hoadonID;
    }

    public void setHoadonID(int hoadonID) {
        this.hoadonID = hoadonID;
    }

    public Date getNgayKhoiHanh() {
        return ngayKhoiHanh;
    }

    public void setNgayKhoiHanh(Date ngayKhoiHanh) {
        this.ngayKhoiHanh = ngayKhoiHanh;
    }
}
