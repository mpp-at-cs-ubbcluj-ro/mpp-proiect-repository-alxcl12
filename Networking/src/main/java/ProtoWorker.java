/*
 *  @author albua
 *  created on 17/04/2021
 */

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProtoWorker implements Runnable, Observer{
    private Services server;
    private Socket connection;


    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;

    public ProtoWorker(Services server, Socket connection){
        this.server = server;
        this.connection = connection;

        try {
            output = connection.getOutputStream();

            input = connection.getInputStream();
            connected = true;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(connected){
            try {
                Protobufs.ChatRequest request = Protobufs.ChatRequest.parseDelimitedFrom(input);
                Protobufs.ChatResponse response = handleRequest(request);

                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }


    private Protobufs.ChatResponse handleRequest(Protobufs.ChatRequest request){
        Protobufs.ChatResponse response = null;
        switch (request.getType()){
            case Login -> {
                Admin admin = ProtoUtils.getAdmin(request);
                try {
                    server.login(admin, this);
                    return ProtoUtils.createOkResponse();
                }
                catch (ServiceException e){
                    connected = false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Logout -> {
                Admin admin = ProtoUtils.getAdmin(request);
                try {
                    server.logout(admin.getUsername());
                    return ProtoUtils.createOkResponse();
                } catch (ServiceException e) {
                    connected=false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Add_Booking -> {
                try {
                    Booking booking = ProtoUtils.getBooking(request);
                    server.addBooking(booking.getTrip().getID(), booking.getClientFirstName(), booking.getClientLastName(), booking.getNrSeats());

                    return ProtoUtils.createOkResponse();
                } catch (ServiceException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Find_All_Trips -> {
                try {
                    Trip[] friends=server.getTrips();
                    return ProtoUtils.createGetTripsResponse(friends);
                } catch (ServiceException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Find_Bookings_Trip -> {
                Trip c = ProtoUtils.getTrip(request);
                try {
                    Booking[] friends=server.getBookings(c);
                    return ProtoUtils.createGetBookingsResponse(friends);
                } catch (ServiceException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Find_Trip_Filter -> {
                Trip c = ProtoUtils.getTrip(request);
                try {
                    Trip[] friends=server.getTripsFiltered(c);
                    return ProtoUtils.createGetTripsFilterResponse(friends);
                } catch (ServiceException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
        }
        return response;
    }



    private void sendResponse(Protobufs.ChatResponse response) throws IOException{
        System.out.println("sending response "+response);
        response.writeDelimitedTo(output);
        output.flush();
    }


    @Override
    public void newTrips(Trip[] trips) throws ServiceException {
        try {
            sendResponse(ProtoUtils.createRefreshResponse(trips));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
