package comp.Rmi.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginService extends Remote {
    public boolean login(String email, String password) throws RemoteException;
}
