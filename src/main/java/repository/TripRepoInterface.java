/*
 *  @author albua
 *  created on 04/03/2021
 */
package repository;

import model.Booking;
import model.Trip;

/**
 * Interface for a repository used to store trips
 */
public interface TripRepoInterface extends Repository<Long, Trip> {

    Iterable<Booking> findBookingsForTrip(Long tripId);
}
