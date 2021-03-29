/*
 *  @author albua
 *  created on 25/03/2021
 */

import java.io.Serializable;

public class TripDTO implements Serializable {
    private Long id;
    private String source;
    private String destination;
    private String date;
    private Integer freeSeats;

    public TripDTO(Long id, String source, String destination, String date, Integer freeSeats) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
