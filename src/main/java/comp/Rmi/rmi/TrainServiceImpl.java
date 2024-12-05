package comp.Rmi.rmi;

import comp.trainticketserver.DAO.AdminDAO;
import comp.trainticketserver.DAO.TrainDAO;
import comp.Rmi.model.Train;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;

public class TrainServiceImpl extends UnicastRemoteObject implements TrainService {
    private TrainDAO trainDAO;
    private AdminDAO adminDAO;

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

    @Override
    public boolean themTauVaGioTau(int tuyenID, String tenTau, int gaDi, int gaDen, String gioDi, String gioDen) throws RemoteException {
        return adminDAO.themTauVaGioTau(tuyenID, tenTau, gaDi, gaDen, gioDi, gioDen);
    }

}