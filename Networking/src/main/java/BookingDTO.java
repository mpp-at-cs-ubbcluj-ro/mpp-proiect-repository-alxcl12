/*
 *  @author albua
 *  created on 25/03/2021
 */

import java.io.Serializable;

public class BookingDTO implements Serializable {
    private String firstName;
    private String lastName;
    private int nrSeats;
    private Long tripId;

    public BookingDTO(String firstName, String lastName, int nrSeats, Long tripId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nrSeats = nrSeats;
        this.tripId = tripId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(int nrSeats) {
        this.nrSeats = nrSeats;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nrSeats=" + nrSeats +
                ", tripId=" + tripId +
                '}';
    }
}
