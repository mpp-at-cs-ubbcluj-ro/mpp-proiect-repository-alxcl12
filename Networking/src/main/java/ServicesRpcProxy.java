/*
 *  @author albua
 *  created on 25/03/2021
 */

import utils.ServerException;

import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesRpcProxy implements Services{
    private String host;
    private int port;

    private Observer client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> responses;

    private volatile boolean finished;

    public ServicesRpcProxy(String host, int port){
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
    }

    @Override
    public void login(Admin admin, Observer client) throws ServiceException {
        initializeConnection();
        AdminDTO adto= DTOUtils.getDTO(admin);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(adto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new ServiceException(err);
        }
    }

    @Override
    public void logout(String username) throws ServiceException {
        AdminDTO adto= new AdminDTO(username, "a");
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(adto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            closeConnection();
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new ServiceException(err);
        }
    }

    @Override
    public Trip[] getTrips() throws ServiceException {
        Request req = new Request.Builder().type(RequestType.FIND_ALL_TRIPS).data(null).build();
        sendRequest(req);
        Response response = readResponse();

        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ServiceException(err);
        }
        TripDTO[] trDto=(TripDTO[])response.data();
        return DTOUtils.getFromDTO(trDto);
    }

    @Override
    public Booking[] getBookings(Trip trip) throws ServiceException {
        TripDTO tripDTO = DTOUtils.getDTO(trip);
        Request req=new Request.Builder().type(RequestType.FIND_BOOKINGS_TRIP).data(tripDTO).build();
        sendRequest(req);
        Response response=readResponse();

        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ServiceException(err);
        }
        BookingDTO[] bookDTO=(BookingDTO[])response.data();
        return DTOUtils.getFromDTO(bookDTO);
    }

    @Override
    public Trip[] getTripsFiltered(Trip trip) throws ServiceException {
        TripDTO tripDTO = DTOUtils.getDTO(trip);
        Request req=new Request.Builder().type(RequestType.FIND_TRIP_FILTER).data(tripDTO).build();
        sendRequest(req);
        Response response=readResponse();

        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ServiceException(err);
        }
        TripDTO[] tripDTOa = (TripDTO[])response.data();
        return DTOUtils.getFromDTO(tripDTOa);
    }

    @Override
    public void addBooking(Long tripId, String firstName, String lastName, int seats) throws ServiceException{
        BookingDTO bookingDTO = new BookingDTO(firstName, lastName, seats, tripId);

        Request req = new Request.Builder().type(RequestType.ADD_BOOKING).data(bookingDTO).build();
        sendRequest(req);

        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new ServiceException(err);
        }
    }


    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws ServiceException {
        try {
            output.writeObject(request);
            output.flush();
        }
        catch (IOException e) {
            throw new ServiceException("Error sending object "+e);
        }

    }

    private Response readResponse() throws ServiceException {
        Response response=null;

        try{

            response=responses.take();

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws ServiceException {
        try {
            connection = new Socket(host,port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if (response.type()== ResponseType.REFRESH){
            TripDTO[] trips = (TripDTO[]) response.data();
            Trip[] result = DTOUtils.getFromDTO(trips);

            try{
                client.newTrips(result);
            }
            catch (ServiceException e){
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.REFRESH;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("response received "+response);

                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }
                    else{
                        try {
                            responses.put((Response)response);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
