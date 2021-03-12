/*
 *  @author albua
 *  created on 11/03/2021
 */

import model.Admin;
import model.Booking;
import model.Client;
import model.Trip;
import model.validators.AdminValidator;
import model.validators.BookingValidator;
import model.validators.ClientValidator;
import model.validators.TripValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.RepositoryAdmin;
import repository.RepositoryBooking;
import repository.RepositoryClient;
import repository.RepositoryTrip;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.stream.StreamSupport;

public class RepositoryTest {

    @Test
    public void adminRepoTest(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+ e);
        }

        AdminValidator validator = new AdminValidator();
        RepositoryAdmin adminRepo=new RepositoryAdmin(props, validator);
        Random random = new Random();

        int number = random.nextInt();

        Admin testAdmin = new Admin("TestAdminUsername" + number, "TestPasswordHash" + number);

        adminRepo.save(testAdmin);

        Iterable<Admin> admins = adminRepo.findAll();

        Assertions.assertNotEquals(0, StreamSupport.stream(admins.spliterator(), false).count());

        Admin recover = adminRepo.findOne(1L);

        Assertions.assertEquals("TestAdminUsername-919258746", recover.getUsername());
    }

    @Test
    public void clientRepoTest(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+ e);
        }

        ClientValidator validator = new ClientValidator();
        RepositoryClient clientRepo = new RepositoryClient(props, validator);
        Random random = new Random();

        int number = random.nextInt();

        Client testClient = new Client("TestClientFirstName" + number, "TestClientLastName" + number);

        clientRepo.save(testClient);

        Iterable<Client> clients = clientRepo.findAll();

        Assertions.assertNotEquals(0, StreamSupport.stream(clients.spliterator(), false).count());

        Client recover = clientRepo.findOne(1L);

        Assertions.assertEquals("TestClientFirstName-2045517797", recover.getFirstName());
    }

    @Test
    public void tripRepoTest(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+ e);
        }

        TripValidator validator = new TripValidator();
        RepositoryTrip tripRepo = new RepositoryTrip(props, validator);
        Random random = new Random();

        int number = random.nextInt();

        Trip testTrip = new Trip("TestSource" + number, "TestDestination" + number,
                LocalDateTime.now(), 5);

        tripRepo.save(testTrip);

        Iterable<Trip> trips = tripRepo.findAll();

        Assertions.assertNotEquals(0, StreamSupport.stream(trips.spliterator(), false).count());

        Trip recover = tripRepo.findOne(1L);

        Assertions.assertEquals("TestSource-206989128", recover.getSource());
    }

    @Test
    public void bookingRepoTest(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+ e);
        }

        BookingValidator validator = new BookingValidator();
        RepositoryBooking bookingRepo = new RepositoryBooking(props, validator);

        Client testClient = new Client("tst", "test");
        testClient.setID(1L);

        Trip testTrip = new Trip("tst", "test", LocalDateTime.now(), 5);
        testTrip.setID(1L);

        Booking testBooking = new Booking(testClient, testTrip, 3);

        bookingRepo.save(testBooking);

        Iterable<Booking> bookings = bookingRepo.findAll();

        Assertions.assertNotEquals(0, StreamSupport.stream(bookings.spliterator(), false).count());

        Booking recover = bookingRepo.findOne(1L);

        Assertions.assertEquals(3, recover.getNrSeats());
    }
}
