/*
 *  @author albua
 *  created on 27/03/2021
 */

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainServiceClient implements Observer{
    private Services server;

    public MainServiceClient(Services server) {
        this.server = server;
    }


    public boolean loginHandler(String text, String text1) {
        Admin admin = new Admin(text, text1.getBytes(StandardCharsets.UTF_8));
        admin.setPasswordString(text1);
        try {
            server.login(admin,this);
            return true;
        } catch (ServiceException e) {
            return false;
        }

    }

    public Object getAllTrips() {
        try {
            Trip[] c = server.getTrips();
            List<Trip> rez = new ArrayList<>();
            Collections.addAll(rez, c);
            return rez;
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getTripsFiltered(Trip c){
        try {
            Trip[] l = server.getTripsFiltered(c);
            return l;
        }
        catch (ServiceException e){
            e.printStackTrace();
        }
        return null;
    }

    public Object getBookings(Trip c) {
        try {
            Booking[] l = server.getBookings(c);
            return l;
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBooking(Trip idCursa, String text, String text1, int value) {
        try {
            server.addBooking(idCursa.getID(), text,text1,value);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void logout(String adminUsername){
        try{
            server.logout(adminUsername);
        }
        catch (ServiceException e){
            e.printStackTrace();
        }
    }




    @Override
    public void newTrips(Trip[] trips) throws ServiceException {
        List<Trip> rez = new ArrayList<>(Arrays.asList(trips));

    }
}
