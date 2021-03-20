/*
 *  @author albua
 *  created on 28/02/2021
 */
package service;
import model.Admin;
import model.Booking;
import model.Client;
import model.Trip;
import repository.*;
import utils.Observable;
import utils.Observer;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for the application
 */
public class Service implements Observable {
    RepositoryTrip repoTrip;
    RepositoryAdmin repoAdmins;
    RepositoryBooking repoBooking;
    RepositoryClient repoClient;

    List<Admin> LoggedInAdmins;

    public Service(RepositoryTrip repoTrip, RepositoryAdmin repoAdmins, RepositoryBooking
            repoBooking, RepositoryClient repoClient, List<Admin> loggedInAdmins) {
        this.repoTrip = repoTrip;
        this.repoAdmins = repoAdmins;
        this.repoBooking = repoBooking;
        this.repoClient = repoClient;
        LoggedInAdmins = loggedInAdmins;
    }

    public void Login(String Username, String Password){

    }

    public Trip Search(LocalDateTime DepTime, String Destination){

        return null;
    }

    public void Book(String FirstName, String LastName, Integer NrOfSeats){

    }

    public void Logout(){

    }

    @Override
    public void AddObserver(Observer e) {

    }

    @Override
    public void RemoveObserver(Observer e) {

    }

    @Override
    public void NotifyObservers() {

    }
}
