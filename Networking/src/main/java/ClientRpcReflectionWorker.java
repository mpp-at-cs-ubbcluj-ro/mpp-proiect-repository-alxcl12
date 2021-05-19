/*
 *  @author albua
 *  created on 25/03/2021
 */

import models.Admin;
import models.Booking;
import models.Trip;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientRpcReflectionWorker implements Runnable, Observer{
    private Services server;
    private Socket connection;


    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientRpcReflectionWorker(Services server, Socket connection){
        this.server = server;
        this.connection = connection;

        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();

            input = new ObjectInputStream(connection.getInputStream());
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
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
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

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
    //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        AdminDTO adDT=(AdminDTO) request.data();
        Admin user= DTOUtils.getFromDTO(adDT);
        try {
            server.login(user, this);
            return okResponse;
        } catch (ServiceException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
        //return null;
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request..."+request.type());
        AdminDTO adDT=(AdminDTO) request.data();
        Admin user= DTOUtils.getFromDTO(adDT);
        try {
            server.logout(user.getUsername());
            return okResponse;
        } catch (ServiceException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFIND_ALL_TRIPS(Request request)
    {
        System.out.println("GetCUrse Request ...");
        try {
            Trip[] friends=server.getTrips();
            TripDTO[] frDTO=DTOUtils.getDTO(friends);
            for(int i=0; i<friends.length; i++){
                System.out.println(friends[i]);
            }
            return new Response.Builder().type(ResponseType.GET_TRIPS).data(frDTO).build();
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFIND_BOOKINGS_TRIP(Request request)
    {
        TripDTO trDt=(TripDTO) request.data();
        Trip c= DTOUtils.getFromDTO(trDt);
        System.out.println("Get locuri Request ...");
        try {
            Booking[] friends=server.getBookings(c);
            BookingDTO[] frDTO=DTOUtils.getDTO(friends);

            return new Response.Builder().type(ResponseType.GET_BOOKINGS).data(frDTO).build();
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFIND_TRIP_FILTER(Request request)
    {
        TripDTO trDt=(TripDTO) request.data();
        Trip c= DTOUtils.getFromDTO(trDt);
        System.out.println("Get trip filter Request ...");
        try {
            Trip[] friends=server.getTripsFiltered(c);
            TripDTO[] frDTO=DTOUtils.getDTO(friends);

            return new Response.Builder().type(ResponseType.GET_TRIPS_FILTER).data(frDTO).build();
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_BOOKING(Request request) {
        try {
            BookingDTO bookDto=(BookingDTO) request.data();
            server.addBooking(bookDto.getTripId(), bookDto.getFirstName(), bookDto.getLastName(), bookDto.getNrSeats());
            return new Response.Builder().type(ResponseType.OK).data(null).build();
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }


    @Override
    public void newTrips(Trip[] trips) throws ServiceException {
        TripDTO[] rez = DTOUtils.getDTO(trips);
        Response response = new Response.Builder().type(ResponseType.REFRESH).data(rez).build();
        try {
            sendResponse(response);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
