package comp.Rmi.model;

import java.io.Serializable;

public class TrainCarriage implements Serializable {
    private int TauToaID;
    private Train train;
    private Carriage carriage;

    public int getTauToaID() {
        return TauToaID;
    }

    public void setTauToaID(int tauToaID) {
        TauToaID = tauToaID;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }
}
