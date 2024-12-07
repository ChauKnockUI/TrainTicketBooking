package comp.Rmi.rmi;

import comp.trainticketserver.DAO.AdminDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
public class StatictisServiceImpl extends UnicastRemoteObject implements StatictisService {
    private AdminDAO adminDAO;
    public StatictisServiceImpl() throws RemoteException {
        adminDAO = new AdminDAO();
    }
    @Override
    public Map<Integer, Float> getDoanhThuTheoThang(int nam) throws RemoteException {
        return adminDAO.getDoanhThuTheoThang(nam);
    }
    @Override
    public Map<Integer, Float> getDoanhThuTheoQuy(int nam) throws RemoteException {
        return adminDAO.getDoanhThuTheoQuy(nam);
    }
    @Override
    public Map<Integer, Float> getDoanhThuTheoNam(int soNam) throws RemoteException {
        return adminDAO.getDoanhThuTheoNam(soNam);
    }
}
