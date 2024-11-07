package comp.Rmi.rmi;

import comp.Rmi.model.Price;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PriceService extends Remote {
    public List<Price> getUniquePrices(int tauID, int gaDi, int gaDen) throws RemoteException;
}
