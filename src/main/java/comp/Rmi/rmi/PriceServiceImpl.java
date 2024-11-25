package comp.Rmi.rmi;

import comp.Rmi.model.Price;
import comp.trainticketserver.DAO.PriceDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PriceServiceImpl extends UnicastRemoteObject implements PriceService {
    private final PriceDAO priceDAO;

    public PriceServiceImpl() throws RemoteException {
        priceDAO = new PriceDAO();
    }

    @Override
    public List<Price> getUniquePrices(int tauID, int gaDi, int gaDen) throws RemoteException {
        return priceDAO.getUniquePrices(tauID, gaDi, gaDen);
    }


}
