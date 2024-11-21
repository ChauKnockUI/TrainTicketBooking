package comp.Rmi.rmi;

import comp.Rmi.model.CTHD;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;

public interface TicketService extends Remote {
    public CTHD bookTicket(int gheID, int tauID, int gaDi, int gaDen, String tenKH, String diaChi, String sdt, float giaTien, int nhanVienID) throws RemoteException;
    public boolean cancelTicket(int hoaDonID, int gheID) throws RemoteException;
    public CTHD modifyTicket(int cthdID, int newGheID, int newTauID, int newGaDi, int newGaDen, Date newNgayKhoiHanh,
                             String newTenKH, String newDiaChi, String newSDT, float newGiaTien, int newNhanVienID) throws RemoteException;
}
