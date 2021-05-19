package models;/*
 *  @author albua
 *  created on 28/02/2021
 */

import org.springframework.stereotype.Component;

/**
 * Class used to validate models.Trip
 */
@Component
public class TripValidator implements Validator<Trip> {
    @Override
    public void validate(Trip entity) throws ValidationException {
        if (entity.getFreeSeats() > Trip.defaultFreeSeats){
            throw new ValidationException("models.Trip has too many empty seats!");
        }
    }
}
