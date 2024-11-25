package comp.Rmi.rmi;

import comp.Rmi.model.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginService extends Remote {
    NhanVien login(String email, String password) throws RemoteException;
}
