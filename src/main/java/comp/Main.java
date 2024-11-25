package comp;

import comp.Rmi.rmi.*;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "172.20.10.4");
            LocateRegistry.createRegistry(1099);

            TrainService trainService = new TrainServiceImpl();
            CarriageService carriageService = new CarriageServiceImpl();
            PriceService priceService = new PriceServiceImpl();
            SeatService seatService = new SeatServiceImpl();
            StationService stationService = new StationServiceImpl();
            HoaDonService hoaDonService = new HoaDonServiceImpl();
            LoginService loginService = new LoginServiceImpl();
            TicketService ticketService = new TicketServiceImpl();

            Naming.rebind("rmi://172.20.10.4:1099/TrainService", trainService);
            Naming.rebind("rmi://172.20.10.4:1099/CarriageService", carriageService);
            Naming.rebind("rmi://172.20.10.4:1099/PriceService", priceService);
            Naming.rebind("rmi://172.20.10.4:1099/SeatService", seatService);
            Naming.rebind("rmi://172.20.10.4:1099/StationService", stationService);
            Naming.rebind("rmi://172.20.10.4:1099/HoaDonService", hoaDonService);
            Naming.rebind("rmi://172.20.10.4:1099/LoginService", loginService);
            Naming.rebind("rmi://172.20.10.4:1099/TicketService", ticketService);


            System.out.println("Service is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
