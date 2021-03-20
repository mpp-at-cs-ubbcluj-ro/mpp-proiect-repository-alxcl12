/*
 *  @author albua
 *  created on 28/02/2021
 */
package service;
import model.Admin;
import model.Booking;
import model.Trip;
import repository.*;
import utils.Observable;
import utils.Observer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Service class for the application
 */
public class Service implements Observable {
    RepositoryTrip repoTrip;
    RepositoryAdmin repoAdmins;
    RepositoryBooking repoBooking;

    List<Admin> LoggedInAdmins;

    public Service(RepositoryTrip repoTrip, RepositoryAdmin repoAdmins, RepositoryBooking
            repoBooking, List<Admin> loggedInAdmins) {
        this.repoTrip = repoTrip;
        this.repoAdmins = repoAdmins;
        this.repoBooking = repoBooking;
        LoggedInAdmins = loggedInAdmins;
    }

    public Iterable<Trip> getAllTrips(){

        return repoTrip.findAll();
    }

    public Iterable<Booking> getBookingsForTrip(Long tripId){

        return repoTrip.findBookingsForTrip(tripId);
    }

    public void addBooking(Long tripId, String firstName, String lastName, int seats){
        Trip trip = repoTrip.findOne(tripId);

        Booking booking = new Booking(trip, seats, firstName, lastName);

        repoBooking.save(booking);
        trip.setFreeSeats(trip.getFreeSeats() - seats);
        repoTrip.update(trip);
    }

    @Override
    public void addObserver(Observer e) {

    }

    @Override
    public void removeObserver(Observer e) {

    }

    @Override
    public void notifyObservers() {

    }
}
