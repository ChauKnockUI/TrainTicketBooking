package comp.Rmi.rmi;

import comp.Rmi.model.Seat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SeatService extends Remote {
    List<Seat> getAllSeats(int tauID, int toaID) throws RemoteException;
}
