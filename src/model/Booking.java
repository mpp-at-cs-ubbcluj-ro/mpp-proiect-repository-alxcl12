/*
 *  @author albua
 *  created on 04/03/2021
 */
package model;

public class Booking extends Entity<Long> {
    Long clientID;
    Long tripID;
    Integer nrSeats;

    public Booking(Long Id, Long clientID, Long tripID, Integer nrSeats) {
        this.ID = Id;
        this.clientID = clientID;
        this.tripID = tripID;
        this.nrSeats = nrSeats;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public Long getTripID() {
        return tripID;
    }

    public void setTripID(Long tripID) {
        this.tripID = tripID;
    }

    public Integer getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(Integer nrSeats) {
        this.nrSeats = nrSeats;
    }
}
