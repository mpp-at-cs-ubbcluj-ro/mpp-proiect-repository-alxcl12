/*
 *  @author albua
 *  created on 15/05/2021
 */
package starter;

import models.Trip;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class TripClient {
    public static final String URL = "http://localhost:8080/trips";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) { // server down, resource exception
            throw new RuntimeException(e);
        }
    }

    public Trip[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Trip[].class));
    }

    public Trip getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Trip.class));
    }

    public Trip create(Trip trip) {
        return execute(() -> restTemplate.postForObject(URL, trip, Trip.class));
    }

    public void update(Trip trip) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, trip.getID()), trip);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
