/*
 *  @author albua
 *  created on 04/03/2021
 */
package repository;

import model.Booking;

/**
 * Repository used to store bookings
 */
public class RepositoryBooking implements BookingRepoInterface{
    @Override
    public Booking findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Booking> findAll() {
        return null;
    }

    @Override
    public void save(Booking entity) {
        return;
    }

    @Override
    public void delete(Long aLong) {
        return;
    }

    @Override
    public void update(Booking entity) {
        return;
    }
}
