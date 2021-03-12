/*
 *  @author albua
 *  created on 04/03/2021
 */
package repository;

import model.Booking;
import model.Client;
import model.Trip;
import model.validators.BookingValidator;
import model.validators.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Properties;

/**
 * Repository used to store bookings
 */
public class RepositoryBooking implements BookingRepoInterface{
    BookingValidator validator;

    private JdbcUtil dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepositoryBooking(Properties props, BookingValidator validator){
        logger.info("Initializing BookingRepository with properties: {} ", props);
        dbUtils = new JdbcUtil(props);
        this.validator = validator;
    }


    @Override
    public Booking findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Booking booking = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Bookings where ID=?")){
            preStmt.setLong(1, aLong);
            try (ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    Long id = result.getLong("ID");
                    Client client = findOneClient(result.getLong("clientId"));
                    Trip trip = findOneTrip(result.getLong("tripId"));
                    Integer nrSeats = result.getInt("numberSeats");

                    booking = new Booking(client, trip, nrSeats);
                    booking.setID(id);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();
        return booking;
    }

    @Override
    public Iterable<Booking> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        HashSet<Booking> bookings = new HashSet<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Bookings")){
            try (ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    Long id = result.getLong("ID");
                    Client client = findOneClient(result.getLong("clientId"));
                    Trip trip = findOneTrip(result.getLong("tripId"));
                    Integer nrSeats = result.getInt("numberSeats");

                    Booking booking = new Booking(client, trip, nrSeats);
                    booking.setID(id);

                    bookings.add(booking);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();

        return bookings;
    }

    @Override
    public void save(Booking entity) {
        logger.traceEntry("saving booking {}", entity);
        Connection con = dbUtils.getConnection();

        try {
            validator.validate(entity);
        }
        catch (ValidationException e){
            logger.error(e);
            return;
        }

        try(PreparedStatement preStmt = con.prepareStatement("insert into Bookings (clientId, tripId, numberSeats)" +
                " values (?,?,?)")){

            preStmt.setLong(1, entity.getClient().getID());
            preStmt.setLong(2, entity.getTrip().getID());
            preStmt.setInt(3, entity.getNrSeats());

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
        logger.traceEntry("deleting booking with id {}", aLong);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("delete from Bookings where ID=?")){
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
    public void update(Booking entity) {
        logger.traceEntry("updating booking with id {}", entity.getID());
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("update Bookings set numberSeats = ? where" +
                " ID=?")){

            preStmt.setInt(1, entity.getNrSeats());
            preStmt.setLong(2, entity.getID());

            int result = preStmt.executeUpdate();
            logger.trace("updated {} instances", result);
        }
        catch (SQLException e){
            logger.error(e);
        }

        logger.traceExit();
    }

    @Override
    public Trip findOneTrip(Long tripId) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Trip trip = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Trips where ID=?")){
            preStmt.setLong(1, tripId);
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
    public Client findOneClient(Long clientId) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Client client = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Clients where ID=?")){
            preStmt.setLong(1, clientId);
            try (ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    Long id = result.getLong("ID");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");

                    client = new Client(firstName, lastName);
                    client.setID(id);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        logger.traceExit();
        return client;
    }
}
