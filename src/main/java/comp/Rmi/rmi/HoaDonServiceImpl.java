package comp.Rmi.rmi;

import comp.Rmi.model.CTHDDetailsDTO;
import comp.Rmi.model.HoaDon;
import comp.trainticketserver.DAO.HoaDonDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class HoaDonServiceImpl extends UnicastRemoteObject implements HoaDonService {
    private final HoaDonDAO hoaDonDAO;

    public HoaDonServiceImpl() throws RemoteException {
        hoaDonDAO = new HoaDonDAO();
    }

    @Override
    public List<HoaDon> showAllHoaDon() throws RemoteException {
        return hoaDonDAO.showAllHoaDon();
    }

    @Override
    public HoaDon findHoaDonByID(int hoadonID) throws RemoteException {
        return hoaDonDAO.findHoaDonByID(hoadonID);
    }

    @Override
    public List<CTHDDetailsDTO> getHoaDonDetailsByNhanVienID(int nhanVienID) throws RemoteException {
        return hoaDonDAO.getHoaDonDetailsByNhanVienID(nhanVienID);
    }
}
