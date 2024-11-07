package comp.Rmi.model;

import java.io.Serializable;

public class Carriage implements Serializable {
    private int ToaID;
    private String TenToa;

    public int getToaID() {
        return ToaID;
    }

    public void setToaID(int toaID) {
        ToaID = toaID;
    }

    public String getTenToa() {
        return TenToa;
    }

    public void setTenToa(String tenToa) {
        TenToa = tenToa;
    }
}
