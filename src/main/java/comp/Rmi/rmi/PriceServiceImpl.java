package comp.Rmi.rmi;

import comp.Rmi.model.Price;
import comp.trainticketserver.DAO.AdminDAO;
import comp.trainticketserver.DAO.PriceDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class PriceServiceImpl extends UnicastRemoteObject implements PriceService {
    private PriceDAO priceDAO;
    private AdminDAO adminDAO;

    public PriceServiceImpl() throws RemoteException {
        priceDAO = new PriceDAO();
        adminDAO = new AdminDAO();
    }

    @Override
    public List<Price> getUniquePrices(int tauID, int gaDi, int gaDen) throws RemoteException {
        return priceDAO.getUniquePrices(tauID, gaDi, gaDen);
    }

    @Override
    public boolean themGiaTien(int tauID, List<Map.Entry<Integer, Double>> toaPriceList, int gaDi, int gaDen) throws RemoteException {
        return adminDAO.themGiaTien(tauID, toaPriceList, gaDi, gaDen);
    }

}