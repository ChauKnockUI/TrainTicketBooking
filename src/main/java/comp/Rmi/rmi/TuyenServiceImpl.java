package comp.Rmi.rmi;

import comp.Rmi.model.Tuyen;
import comp.trainticketserver.DAO.TuyenDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TuyenServiceImpl extends UnicastRemoteObject implements TuyenService {

    private final TuyenDAO tuyenDAO;

    public TuyenServiceImpl() throws RemoteException {
        super();
        tuyenDAO = new TuyenDAO();
    }

    @Override
    public List<Tuyen> getAllTuyen() throws RemoteException {
        return tuyenDAO.getAllTuyen();
    }
}
