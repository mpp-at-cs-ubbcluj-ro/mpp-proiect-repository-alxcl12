/*
 *  @author albua
 *  created on 28/02/2021
 */
package repository;
import model.Trip;
import model.validators.TripValidator;
import model.validators.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Properties;

/**
 * Repository used to store trips
 */
public class RepositoryTrip implements TripRepoInterface{
    TripValidator validator;

    private JdbcUtil dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepositoryTrip(Properties props, TripValidator validator){
        logger.info("Initializing TripRepository with properties: {} ", props);
        dbUtils = new JdbcUtil(props);
        this.validator = validator;
    }


    @Override
    public Trip findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Trip trip = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Trips where ID=?")){
            preStmt.setLong(1, aLong);
            try (ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    Long id = result.getLong("ID");
                    String source = result.getString("sourceCity");
                    String destination = result.getString("destinationCity");
                    LocalDateTime time = result.getTimestamp(4).toLocalDateTime();
                    Integer freeSeats = result.getInt("freeSeats");

                    trip = new Trip(source, destination, time, freeSeats);
                    trip.setID(id);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();
        return trip;
    }

    @Override
    public Iterable<Trip> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        HashSet<Trip> trips = new HashSet<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Trips")){
            try (ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    Long id = result.getLong("ID");
                    String source = result.getString("sourceCity");
                    String destination = result.getString("destinationCity");
                    LocalDateTime time = result.getTimestamp(4).toLocalDateTime();
                    Integer freeSeats = result.getInt("freeSeats");

                    Trip trip = new Trip(source, destination, time, freeSeats);
                    trip.setID(id);
                    trips.add(trip);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();

        return trips;
    }

    @Override
    public void save(Trip entity) {
        logger.traceEntry("saving trip {}", entity);
        Connection con = dbUtils.getConnection();

        try {
            validator.validate(entity);
        }
        catch (ValidationException e){
            logger.error(e);
            return;
        }

        try(PreparedStatement preStmt = con.prepareStatement("insert into Trips (sourceCity, destinationCity, " +
                "departureTime, freeSeats) values (?,?,?,?)")){

            preStmt.setString(1, entity.getSource());
            preStmt.setString(2, entity.getDestination());
            preStmt.setTimestamp(3, Timestamp.valueOf(entity.getDepartureTime()));
            preStmt.setInt(4, entity.getFreeSeats());

            int result = preStmt.executeUpdate();

            logger.trace("saved {} instances", result);
        }
        catch (SQLException e){
            logger.error(e);
        }

        logger.traceExit();
    }

    @Override
    public void delete(Long aLong) {
        logger.traceEntry("deleting trip with id {}", aLong);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("delete from Trips where ID=?")){
            preStmt.setLong(1, aLong);

            int result = preStmt.executeUpdate();
            logger.trace("deleted {} instances with ID", result);
        }
        catch (SQLException e){
            logger.error(e);
        }

        logger.traceExit();
    }

    @Override
    public void update(Trip entity) {
        logger.traceEntry("updating trip with id {}", entity.getID());
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("update Trips set departureTime = ?, freeSeats = ? where" +
                " ID=?")){

            preStmt.setTimestamp(1, Timestamp.valueOf(entity.getDepartureTime()));
            preStmt.setInt(2, entity.getFreeSeats());
            preStmt.setLong(3, entity.getID());

            int result = preStmt.executeUpdate();
            logger.trace("updated {} instances", result);
        }
        catch (SQLException e){
            logger.error(e);
        }

        logger.traceExit();
    }
}
