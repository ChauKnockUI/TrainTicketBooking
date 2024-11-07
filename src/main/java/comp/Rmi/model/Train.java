package comp.Rmi.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Train implements Serializable {
    private int TauID;
    private String TenTau;
    private int GaDiID;
    private String GaDiTen;
    private Timestamp GioDi;
    private Timestamp GioDen;


    public int getTauID() {
        return TauID;
    }

    public void setTauID(int tauID) {
        TauID = tauID;
    }

    public String getTenTau() {
        return TenTau;
    }

    public void setTenTau(String tenTau) {
        TenTau = tenTau;
    }

    public int getGaDiID() {
        return GaDiID;
    }

    public void setGaDiID(int gaDiID) {
        GaDiID = gaDiID;
    }

    public String getGaDiTen() {
        return GaDiTen;
    }

    public void setGaDiTen(String gaDiTen) {
        GaDiTen = gaDiTen;
    }

    public Timestamp getGioDi() {
        return GioDi;
    }

    public void setGioDi(Timestamp gioDi) {
        GioDi = gioDi;
    }

    public Timestamp getGioDen() {
        return GioDen;
    }

    public void setGioDen(Timestamp gioDen) {
        GioDen = gioDen;
    }
}
