package comp.Rmi.rmi;

import comp.trainticketserver.DAO.TuyenDAO;
import comp.Rmi.model.Tuyen;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TuyenServiceImpl extends UnicastRemoteObject implements TuyenService {

    private TuyenDAO tuyenDAO;

    public TuyenServiceImpl() throws RemoteException {
        super();
        tuyenDAO = new TuyenDAO();
    }

    @Override
    public List<Tuyen> getAllTuyen() throws RemoteException {
        return tuyenDAO.getAllTuyen();
    }
}
