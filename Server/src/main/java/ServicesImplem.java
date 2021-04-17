/*
 *  @author albua
 *  created on 25/03/2021
 */
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServicesImplem implements Services {

    private Map<Long, Observer> loggedClients;
    private Service service;
    private final int defaultThreadsNo=5;

    public ServicesImplem(Service service) {
        this.service = service;
        loggedClients=new ConcurrentHashMap<>();;
    }


    public synchronized void login(Admin user, Observer client) throws ServiceException {
        boolean found = false;
        Admin foundA = null;

        found = service.loginHandler(user.getUsername(), user.getPasswordString());
        foundA = service.findOneAdminUsername(user.getUsername());

        if (found){
            if(loggedClients.get(foundA.getID()) != null) {
                throw new ServiceException("Admin already logged in.");
            }

            loggedClients.put(foundA.getID(), client);
        }
        else{
            throw new ServiceException("Authentication failed.");
        }
    }

    @Override
    public Trip[] getTrips() throws ServiceException {
        List<Trip> rez = StreamSupport.stream(service.getAllTrips().spliterator(), false).collect(Collectors.toList());

        Trip[] destiTOSend = new Trip[rez.size()];

        final int[] i = {0};

        rez.forEach(x->{
            destiTOSend[i[0]] = x;
            i[0]++;
        });

        return destiTOSend;
    }

    @Override
    public Trip[] getTripsFiltered(Trip trip) throws ServiceException {
        List<Trip> rez = StreamSupport.stream(service.getTripsBySourceAndDate(trip.getSource(), trip.getDepartureTime()).spliterator(), false).collect(Collectors.toList());

        Trip[] destiTOSend = new Trip[rez.size()];

        final int[] i = {0};

        rez.forEach(x->{
            destiTOSend[i[0]] = x;
            i[0]++;
        });

        return destiTOSend;
    }

    private void notifyNewBooking() {
        List<Trip> curse = StreamSupport.stream(service.getAllTrips().spliterator(), false).collect(Collectors.toList());
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        loggedClients.keySet().forEach(x->{
            Observer obs = loggedClients.get(x);
            executor.execute(()->{
                try {
                    Trip[] curserez = new Trip[curse.size()];
                    final int[] i = {0};
                    curse.forEach(y->{
                        curserez[i[0]]=y;
                        i[0]++;
                    });
                    obs.newTrips(curserez);
                } catch (ServiceException| RemoteException e) {
                    e.printStackTrace();
                }
            });
        });
        executor.shutdown();
    }


    @Override
    public Booking[] getBookings(Trip trip) throws ServiceException {
        List<Booking> rez = StreamSupport.stream(service.getBookingsForTrip(trip.getID()).spliterator(), false).collect(Collectors.toList());

        Booking[] destiTOSend = new Booking[rez.size()];

        final int[] i = {0};

        rez.forEach(x->{
            destiTOSend[i[0]] = x;
            i[0]++;
        });

        return destiTOSend;
    }

    @Override
    public void addBooking(Long idTrip, String firstName, String lastName, int seats) throws ServiceException {
        service.addBooking(idTrip,firstName,lastName,seats);
        notifyNewBooking();

    }

    @Override
    public void logout(String username) throws ServiceException {
        Admin ad = service.findOneAdminUsername(username);
        loggedClients.remove(ad.getID());
    }
}