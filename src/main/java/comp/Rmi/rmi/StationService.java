package comp.Rmi.rmi;

import comp.Rmi.model.Station;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StationService extends Remote {
    List<Station> getAllStations() throws RemoteException;
}
