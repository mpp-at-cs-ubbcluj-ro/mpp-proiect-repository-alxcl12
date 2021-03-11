/*
 *  @author albua
 *  created on 28/02/2021
 */
package model.validators;
import model.Trip;

/**
 * Class used to validate Trip
 */
public class TripValidator implements Validator<Trip> {
    @Override
    public void validate(Trip entity) throws ValidationException {
        if (entity.getFreeSeats() > Trip.defaultFreeSeats){
            throw new ValidationException("Trip has too many empty seats!");
        }
    }
}
