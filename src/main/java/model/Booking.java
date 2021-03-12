/*
 *  @author albua
 *  created on 04/03/2021
 */
package model;

/**
 * Classed used to model a booking for one client and one trip
 */
public class Booking extends Entity<Long> {
    private Client client;
    private Trip trip;
    private Integer nrSeats;

    public Booking(Client client, Trip trip, Integer nrSeats) {
        this.client = client;
        this.trip = trip;
        this.nrSeats = nrSeats;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    @Override
    public String toString() {
        return "Booking{" +
                "client=" + client +
                ", trip=" + trip +
                ", nrSeats=" + nrSeats +
                '}';
    }
}
