package repo;/*
 *  @author albua
 *  created on 04/03/2021
 */


import models.Booking;
import models.Trip;

/**
 * Interface for a repository used to store trips
 */
public interface TripRepoInterface extends Repository<Long, Trip> {

    Iterable<Booking> findBookingsForTrip(Long tripId);

    Iterable<Trip> findTripsBySource(String source);
}
