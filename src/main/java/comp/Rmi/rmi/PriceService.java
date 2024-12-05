package comp.Rmi.rmi;

import comp.Rmi.model.Price;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface PriceService extends Remote {
    public List<Price> getUniquePrices(int tauID, int gaDi, int gaDen) throws RemoteException;
    public boolean themGiaTien(int tauID, List<Map.Entry<Integer, Double>> toaPriceList, int gaDi, int gaDen) throws RemoteException;
}