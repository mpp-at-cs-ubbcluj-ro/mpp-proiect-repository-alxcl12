/*
 *  @author albua
 *  created on 28/02/2021
 */

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class for the application
 */
public class Service{
    private TripRepoInterface repoTrip;
    private AdminRepoInterface repoAdmins;
    private BookingRepoInterface repoBooking;

    public Service(TripRepoInterface repoTrip, AdminRepoInterface repoAdmins, BookingRepoInterface
            repoBooking) {
        this.repoTrip = repoTrip;
        this.repoAdmins = repoAdmins;
        this.repoBooking = repoBooking;
    }

    public Iterable<Trip> getAllTrips(){

        return repoTrip.findAll();
    }

    public Iterable<Booking> getBookingsForTrip(Long tripId){

        return repoTrip.findBookingsForTrip(tripId);
    }

    public void addBooking(Long tripId, String firstName, String lastName, int seats){
        Trip trip = repoTrip.findOne(tripId);

        Booking booking = new Booking(trip, seats, firstName, lastName);

        repoBooking.save(booking);
        trip.setFreeSeats(trip.getFreeSeats() - seats);
        repoTrip.update(trip);
    }

    public Iterable<Trip> getTripsBySourceAndDate(String source, LocalDateTime date){
        Iterable<Trip> trips = repoTrip.findTripsBySource(source);

        HashSet<Trip> result = new HashSet<>();
        trips.forEach(x->{
            if(date.getDayOfMonth() == x.getDepartureTime().getDayOfMonth() && date.getMonth() == x.getDepartureTime().getMonth()
            && date.getYear() == x.getDepartureTime().getYear()){
                result.add(x);
            }
        });

        return result;
    }

    public boolean loginHandler(String username, String password){
        try {
            return repoAdmins.authenticateAdmin(username, password) != null;
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return false;
    }

    public Admin findOneAdminUsername(String username){
        Iterable<Admin> admins = repoAdmins.findAll();

        List<Admin> lAdmin = StreamSupport.stream(admins.spliterator(), false).collect(Collectors.toList());

        for (Admin admin : lAdmin) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }

        return null;
    }
}
