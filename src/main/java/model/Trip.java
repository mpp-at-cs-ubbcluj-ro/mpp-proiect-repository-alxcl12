/*
 *  @author albua
 *  created on 28/02/2021
 */
package model;
import java.time.LocalDateTime;

/**
 * Class used to model a trip
 */
public class Trip extends Entity<Long> {
    String source;
    String destination;
    LocalDateTime departureTime;
    Integer freeSeats;

    public static Integer defaultFreeSeats = 18;

    public Trip(String source, String destination, LocalDateTime departureTime, Integer freeSeats) {
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.freeSeats = freeSeats;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }
}
