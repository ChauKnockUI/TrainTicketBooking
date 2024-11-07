package comp.Rmi.model;

import java.io.Serializable;

public class Seat implements Serializable {
    private int gheID;
    private int soGhe;
    private int loaiGheID;
    private  int toaID;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGheID() {
        return gheID;
    }

    public void setGheID(int gheID) {
        this.gheID = gheID;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public int getLoaiGheID() {
        return loaiGheID;
    }

    public void setLoaiGheID(int loaiGheID) {
        this.loaiGheID = loaiGheID;
    }

    public int getToaID() {
        return toaID;
    }

    public void setToaID(int toaID) {
        this.toaID = toaID;
    }
}
