import java.time.LocalDateTime;

public interface Services {
    void login(Admin admin, Observer client) throws ServiceException;

    Trip[] getTrips() throws ServiceException;

    Booking[] getBookings(Trip trip) throws ServiceException;

    Trip[] getTripsFiltered(Trip trip) throws ServiceException;

    void addBooking(Long idTrip, String firstName, String lastName, int seats) throws ServiceException;

    void logout(String username) throws ServiceException;
}
