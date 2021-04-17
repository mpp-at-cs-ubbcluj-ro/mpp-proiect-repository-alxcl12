/*
 *  @author albua
 *  created on 04/03/2021
 */

import java.io.Serializable;

/**
 * Classed used to model a booking for one client and one trip
 */
public class Booking extends Entity<Long> implements Serializable {
    private Trip trip;
    private Integer nrSeats;
    private String clientFirstName;
    private String clientLastName;
    private String clientDisplayName;

    public Booking(Trip trip, Integer nrSeats, String firstName, String lastName) {
        this.trip = trip;
        this.nrSeats = nrSeats;
        this.clientFirstName = firstName;
        this.clientLastName = lastName;
        this.clientDisplayName = firstName + " " + lastName;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Integer getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(Integer nrSeats) {
        this.nrSeats = nrSeats;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientDisplayName() {
        return clientDisplayName;
    }

    public void setClientDisplayName(String clientDisplayName) {
        this.clientDisplayName = clientDisplayName;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "trip=" + trip +
                ", nrSeats=" + nrSeats +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", clientDisplayName='" + clientDisplayName + '\'' +
                '}';
    }
}
