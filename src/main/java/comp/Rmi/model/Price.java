package comp.Rmi.model;

import java.io.Serializable;

public class Price implements Serializable {
    private int BangGiaID;
    private int TauID;
    private int GheID;
    private int GaDi;
    private int GaDen;
    private int GiaTien;

    public int getBangGiaID() {
        return BangGiaID;
    }

    public void setBangGiaID(int bangGiaID) {
        BangGiaID = bangGiaID;
    }

    public int getTauID() {
        return TauID;
    }

    public void setTauID(int tauID) {
        TauID = tauID;
    }

    public int getGheID() {
        return GheID;
    }

    public void setGheID(int gheID) {
        GheID = gheID;
    }

    public int getGaDi() {
        return GaDi;
    }

    public void setGaDi(int gaDi) {
        GaDi = gaDi;
    }

    public int getGaDen() {
        return GaDen;
    }

    public void setGaDen(int gaDen) {
        GaDen = gaDen;
    }

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
        GiaTien = giaTien;
    }
}
