package comp.Rmi.rmi;

import comp.Rmi.model.Train;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

public interface TrainService extends Remote {
    List<Train> searchTrains(Date departureDate, int departureStationId, int destinationStationId) throws RemoteException;
    List<Train> getAllTrains() throws RemoteException;
}
