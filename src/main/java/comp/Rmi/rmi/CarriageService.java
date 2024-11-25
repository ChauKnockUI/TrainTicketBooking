package comp.Rmi.rmi;

import comp.Rmi.model.TrainCarriage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CarriageService extends Remote {
    List<TrainCarriage> getCarriageByTrainID(int trainID) throws RemoteException;
}
