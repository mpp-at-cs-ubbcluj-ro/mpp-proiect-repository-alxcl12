package repository;/*
 *  @author albua
 *  created on 28/02/2021
 */

import model.Trip;
import model.validators.TripValidator;

public class RepositoryTrip implements TripRepoInterface{
    TripValidator Validator;

    @Override
    public Trip FindOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Trip> FindAll() {
        return null;
    }

    @Override
    public Trip Save(Trip entity) {
        return null;
    }

    @Override
    public Trip Delete(Long aLong) {
        return null;
    }

    @Override
    public Trip Update(Trip entity) {
        return null;
    }
}
