import models.Trip;

public interface Observer {
    void newTrips(Trip[] trips) throws ServiceException;
}
