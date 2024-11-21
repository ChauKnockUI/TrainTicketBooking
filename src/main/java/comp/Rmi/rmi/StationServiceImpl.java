package comp.Rmi.rmi;

import comp.Rmi.model.Station;
import comp.trainticketserver.DAO.StationDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class StationServiceImpl extends UnicastRemoteObject implements StationService {
    private StationDAO stationDAO;

    public StationServiceImpl() throws RemoteException {
        stationDAO = new StationDAO();
    }

    @Override
    public List<Station> getAllStations() throws RemoteException {
        return stationDAO.getAllStations();
    }
}
