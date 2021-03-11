/*
 *  @author albua
 *  created on 28/02/2021
 */
package repository;
import model.Trip;
import model.validators.TripValidator;

/**
 * Repository used to store trips
 */
public class RepositoryTrip implements TripRepoInterface{
    TripValidator Validator;

    @Override
    public Trip findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Trip> findAll() {
        return null;
    }

    @Override
    public void save(Trip entity) {
        return;
    }

    @Override
    public void delete(Long aLong) {
        return;
    }

    @Override
    public void update(Trip entity) {
        return;
    }
}
