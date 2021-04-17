/**
 * Interface for a repository used to store trips
 */
public interface TripRepoInterface extends Repository<Long, Trip> {

    Iterable<Booking> findBookingsForTrip(Long tripId);

    Iterable<Trip> findTripsBySource(String source);
}
