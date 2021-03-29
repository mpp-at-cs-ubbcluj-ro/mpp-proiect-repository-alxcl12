///*
// *  @author albua
// *  created on 11/03/2021
// */
//
//import model.Admin;
//import model.Booking;
//import model.Trip;
//import validators.AdminValidator;
//import validators.BookingValidator;
//import validators.TripValidator;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import repository.RepositoryAdmin;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Properties;
//import java.util.Random;
//import java.util.stream.StreamSupport;
//
//public class RepositoryTest {
//
//    @Test
//    public void adminRepoTest() throws NoSuchAlgorithmException {
//        Properties props = new Properties();
//        try {
//            props.load(new FileReader("bd.config"));
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+ e);
//        }
//
//        AdminValidator validator = new AdminValidator();
//        RepositoryAdmin adminRepo=new RepositoryAdmin(props, validator);
//        Random random = new Random();
//
//        int number = random.nextInt();
//
//        String unHashedPasswd = "TestPasswordHash" + number;
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(unHashedPasswd.getBytes());
//
//        byte[] hashedPasswd = md.digest();
//
//
//        Admin testAdmin = new Admin("TestAdminUsername" + number, hashedPasswd);
//
//        adminRepo.save(testAdmin);
//
//        Iterable<Admin> admins = adminRepo.findAll();
//
//        Assertions.assertNotEquals(0, StreamSupport.stream(admins.spliterator(), false).count());
//
//        Admin recover = adminRepo.findOne(1L);
//
//        Assertions.assertEquals("TestAdminUsername-919258746", recover.getUsername());
//    }
//
//
////    @Test
////    public void tripRepoTest(){
////        Properties props = new Properties();
////        try {
////            props.load(new FileReader("bd.config"));
////        } catch (IOException e) {
////            System.out.println("Cannot find bd.config "+ e);
////        }
////
////        TripValidator validator = new TripValidator();
////        RepositoryTrip tripRepo = new RepositoryTrip(props, validator);
////        Random random = new Random();
////
////        int number = random.nextInt();
////
////        Trip testTrip = new Trip("TestSource" + number, "TestDestination" + number,
////                LocalDateTime.now(), 5);
////
////        tripRepo.save(testTrip);
////
////        Iterable<Trip> trips = tripRepo.findAll();
////
////        Assertions.assertNotEquals(0, StreamSupport.stream(trips.spliterator(), false).count());
////
////        Trip recover = tripRepo.findOne(1L);
////
////        Assertions.assertEquals("TestSource-206989128", recover.getSource());
////    }
//
////    @Test
////    public void bookingRepoTest(){
////        Properties props = new Properties();
////        try {
////            props.load(new FileReader("bd.config"));
////        } catch (IOException e) {
////            System.out.println("Cannot find bd.config "+ e);
////        }
////
////        BookingValidator validator = new BookingValidator();
////        RepositoryBooking bookingRepo = new RepositoryBooking(props, validator);
////
////        Trip testTrip = new Trip("tst", "test", LocalDateTime.now(), 5);
////        testTrip.setID(1L);
////
////        Booking testBooking = new Booking(testTrip, 3,"nn","nn");
////
////        bookingRepo.save(testBooking);
////
////        Iterable<Booking> bookings = bookingRepo.findAll();
////
////        Assertions.assertNotEquals(0, StreamSupport.stream(bookings.spliterator(), false).count());
////
////        Booking recover = bookingRepo.findOne(5L);
////
////        Assertions.assertEquals(3, recover.getNrSeats());
////    }
//}
