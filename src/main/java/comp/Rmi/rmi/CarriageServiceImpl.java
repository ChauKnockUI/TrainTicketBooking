package comp.Rmi.rmi;

import comp.Rmi.model.TrainCarriage;
import comp.trainticketserver.DAO.ToaDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CarriageServiceImpl extends UnicastRemoteObject implements CarriageService {
    private ToaDAO toaDAO;

    public CarriageServiceImpl() throws RemoteException {
        toaDAO = new ToaDAO();
    }

    @Override
    public List<TrainCarriage> getCarriageByTrainID(int trainID ) throws RemoteException {
        return toaDAO.getCarriageByTrainID(trainID);
    }
}
