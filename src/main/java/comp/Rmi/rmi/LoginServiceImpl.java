package comp.Rmi.rmi;

import comp.Rmi.model.NhanVien;
import comp.trainticketserver.DAO.NhanVienDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginServiceImpl extends UnicastRemoteObject implements LoginService {
    private final NhanVienDAO nhanVienDAO;

    public LoginServiceImpl() throws RemoteException {
        nhanVienDAO = new NhanVienDAO();
    }

    @Override
    public NhanVien login(String email, String password) throws RemoteException {
        return nhanVienDAO.login(email, password);
    }
}
