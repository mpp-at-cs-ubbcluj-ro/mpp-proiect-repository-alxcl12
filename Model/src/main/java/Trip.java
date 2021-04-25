/*
 *  @author albua
 *  created on 28/02/2021
 */

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class used to model a trip
 */
public class Trip extends BaseEntity<Long> implements Serializable {
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private Integer freeSeats;
    private String dateFormat;

    public static Integer defaultFreeSeats = 18;

    public Trip(String source, String destination, LocalDateTime departureTime, Integer freeSeats) {
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.freeSeats = freeSeats;

        dateFormat = departureTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm"));
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
        dateFormat = departureTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm"));
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
