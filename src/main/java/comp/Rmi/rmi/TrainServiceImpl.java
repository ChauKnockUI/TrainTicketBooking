package comp.Rmi.rmi;

import comp.Rmi.model.Train;
import comp.trainticketserver.DAO.TrainDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;

public class TrainServiceImpl extends UnicastRemoteObject implements TrainService {
    private final TrainDAO trainDAO;

    public TrainServiceImpl() throws RemoteException {
        trainDAO = new TrainDAO();
    }

    @Override
    public List<Train> searchTrains(Date departureDate, int departureStationId, int destinationStationId) throws RemoteException {
        return trainDAO.searchTrains(departureDate, departureStationId, destinationStationId);
    }

    @Override
    public List<Train> getAllTrains() throws RemoteException {
        return trainDAO.getAllTrains();
    }


}
