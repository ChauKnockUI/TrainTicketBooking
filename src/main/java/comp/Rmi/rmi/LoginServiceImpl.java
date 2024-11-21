package comp.Rmi.rmi;

import comp.trainticketserver.DAO.NhanVienDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginServiceImpl extends UnicastRemoteObject implements LoginService {
    private NhanVienDAO nhanVienDAO;

    public LoginServiceImpl() throws RemoteException {
        nhanVienDAO = new NhanVienDAO();
    }

    @Override
    public boolean login(String email, String password) throws RemoteException {
        return nhanVienDAO.login(email, password);
    }
}
