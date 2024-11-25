package comp.Rmi.rmi;

import comp.Rmi.model.CTHD;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;

public interface TicketService extends Remote {
    CTHD bookTicket(int gheID, int tauID, int gaDi, int gaDen, String tenKH, String diaChi, String sdt, float giaTien, int nhanVienID) throws RemoteException;

    boolean cancelTicket(int hoaDonID, int gheID) throws RemoteException;

    CTHD modifyTicket(int cthdID, int newGheID, int newTauID, int newGaDi, int newGaDen, Date newNgayKhoiHanh,
                      String newTenKH, String newDiaChi, String newSDT, float newGiaTien, int newNhanVienID) throws RemoteException;
}
