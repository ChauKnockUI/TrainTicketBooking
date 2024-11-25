package comp.Rmi.rmi;

import comp.Rmi.model.Seat;
import comp.trainticketserver.DAO.SeatDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SeatServiceImpl extends UnicastRemoteObject implements SeatService {
    private final SeatDAO seatDAO;

    public SeatServiceImpl() throws RemoteException {
        seatDAO = new SeatDAO();
    }

    @Override
    public List<Seat> getAllSeats(int tauID, int toaID) throws RemoteException {
        return seatDAO.getAllSeats(tauID, toaID);
    }


}
