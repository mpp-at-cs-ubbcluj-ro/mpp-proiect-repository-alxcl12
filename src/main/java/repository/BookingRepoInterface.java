/*
 *  @author albua
 *  created on 04/03/2021
 */
package repository;

import model.Booking;
import model.Client;
import model.Trip;

/**
 * Interface for a repository used to store bookings
 */
public interface BookingRepoInterface extends Repository<Long, Booking> {
    Trip findOneTrip(Long tripId);

    Client findOneClient(Long clientId);
}
