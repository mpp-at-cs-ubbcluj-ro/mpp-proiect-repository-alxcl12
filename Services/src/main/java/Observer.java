import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void newTrips(Trip[] trips) throws ServiceException, RemoteException;
}
