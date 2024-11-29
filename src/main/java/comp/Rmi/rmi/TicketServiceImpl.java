package comp.Rmi.rmi;

import comp.Rmi.model.CTHD;
import comp.trainticketserver.DAO.TicketDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;

public class TicketServiceImpl extends UnicastRemoteObject implements TicketService {

    private final TicketDAO ticketDAO;

    public TicketServiceImpl() throws RemoteException {
        ticketDAO = new TicketDAO();
    }

    @Override
    public CTHD bookTicket(int gheID, int tauID, int gaDi, int gaDen, String tenKH, String diaChi, String sdt, float giaTien, int nhanVienID) throws RemoteException {
        return ticketDAO.bookTicket(gheID, tauID, gaDi, gaDen, tenKH, diaChi, sdt, giaTien, nhanVienID);
    }

    @Override
    public boolean cancelTicket(int hoaDonID, int gheID) throws RemoteException {
        return ticketDAO.cancelTicket(hoaDonID, gheID);
    }

    @Override
    public boolean modifyTicket(int cthdID, int newGheID, String newTenKH, String newDiaChi, String newSDT, float newGiaTien, int newNhanVienID) throws RemoteException {
        return ticketDAO.modifyTicket(cthdID, newGheID,  newTenKH, newDiaChi, newSDT, newGiaTien, newNhanVienID);
    }


}
