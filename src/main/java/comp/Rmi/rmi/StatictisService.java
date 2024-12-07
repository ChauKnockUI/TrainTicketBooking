package comp.Rmi.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
public interface StatictisService extends Remote {
    public Map<Integer, Float> getDoanhThuTheoThang(int nam) throws RemoteException;
    public Map<Integer, Float> getDoanhThuTheoQuy(int nam) throws RemoteException;
    public Map<Integer, Float> getDoanhThuTheoNam(int soNam) throws RemoteException;
}
