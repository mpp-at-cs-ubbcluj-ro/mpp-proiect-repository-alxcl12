/*
 *  @author albua
 *  created on 25/03/2021
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.StreamSupport;

public class DTOUtils {
    public static Admin getFromDTO(AdminDTO adminDTO){
        String username = adminDTO.getUsername();
        //            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(adminDTO.getPassword().getBytes());
//
//            byte[] digest = md.digest();
        byte[] nt = new byte[23];

        Admin ad = new Admin(username, nt);
        ad.setPasswordString(adminDTO.getPassword());

        return ad;
    }

    public static AdminDTO getDTO(Admin admin){
        return new AdminDTO(admin.getUsername(), admin.getPasswordString());
    }

    public static Booking getFromDTO(BookingDTO bookingDTO){
        String firstName = bookingDTO.getFirstName();
        String lastName = bookingDTO.getLastName();
        int nrSeats = bookingDTO.getNrSeats();
        Long tripId = bookingDTO.getTripId();

        Trip trip = new Trip("a", "a", LocalDateTime.now(), 18);
        trip.setID(tripId);

        return new Booking(trip, nrSeats, firstName, lastName);
    }

    public static BookingDTO getDTO(Booking booking){
        return new BookingDTO(booking.getClientFirstName(), booking.getClientLastName(), booking.getNrSeats(), booking.getTrip().getID());
    }

    public static Trip getFromDTO(TripDTO trip){
        Trip tripG = new Trip(trip.getSource(), trip.getDestination(), LocalDateTime.parse(trip.getDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")), trip.getFreeSeats());
        tripG.setID(trip.getId());
        return tripG;
    }

    public static TripDTO getDTO(Trip trip){
        return new TripDTO(trip.getID(), trip.getSource(), trip.getDestination(), trip.getDateFormat(), trip.getFreeSeats());
    }

    public static TripDTO[] getDTO(Trip[] trips){
        TripDTO[] tripDTO = new TripDTO[trips.length];

        for(int i=0; i<trips.length; i++)
            tripDTO[i]=getDTO(trips[i]);

        return tripDTO;
    }

    public static Trip[] getFromDTO(TripDTO[] trips){
        Trip[] tripsStd=new Trip[trips.length];

        for(int i=0; i<trips.length; i++){
            tripsStd[i]=getFromDTO(trips[i]);
        }
        return tripsStd;
    }

    public static BookingDTO[] getDTO(Booking[] bookings){
        BookingDTO[] bookingDTO = new BookingDTO[bookings.length];

        for(int i=0; i<bookings.length; i++)
            bookingDTO[i]=getDTO(bookings[i]);

        return bookingDTO;
    }

    public static Booking[] getFromDTO(BookingDTO[] bookings){
        Booking[] bookingsStd=new Booking[bookings.length];

        for(int i=0; i<bookings.length; i++){
            bookingsStd[i]=getFromDTO(bookings[i]);
        }
        return bookingsStd;
    }


}
