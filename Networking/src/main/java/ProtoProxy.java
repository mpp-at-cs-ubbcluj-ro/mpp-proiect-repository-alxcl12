/*
 *  @author albua
 *  created on 17/04/2021
 */

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProxy implements Services{
    private String host;
    private int port;

    private Observer client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<Protobufs.ChatResponse> responses;

    private volatile boolean finished;

    public ProtoProxy(String host, int port){
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
    }

    @Override
    public void login(Admin admin, Observer client) throws ServiceException {
        initializeConnection();
        sendRequest(ProtoUtils.createLoginRequest(admin));
        Protobufs.ChatResponse response=readResponse();
        if (response.getType()== Protobufs.ChatResponse.Type.Ok){
            this.client=client;
            return;
        }
        if (response.getType()== Protobufs.ChatResponse.Type.Error){
            String err=ProtoUtils.getError(response);
            closeConnection();
            throw new ServiceException(err);
        }
    }

    @Override
    public void logout(String username) throws ServiceException {
        byte[] nd = null;
        Admin ad = new Admin(username, nd);
        sendRequest(ProtoUtils.createLoginRequest(ad));
        Protobufs.ChatResponse response = readResponse();
        if (response.getType()== Protobufs.ChatResponse.Type.Ok){
            closeConnection();
            return;
        }
        if (response.getType()== Protobufs.ChatResponse.Type.Error){
            String err=ProtoUtils.getError(response);
            closeConnection();
            throw new ServiceException(err);
        }
    }

    @Override
    public Trip[] getTrips() throws ServiceException {
        sendRequest(ProtoUtils.createFindAllTripsRequest());
        Protobufs.ChatResponse response = readResponse();

        if (response.getType()== Protobufs.ChatResponse.Type.Error){
            String err=ProtoUtils.getError(response);
            throw new ServiceException(err);
        }

        return ProtoUtils.getTrips(response);
    }

    @Override
    public Booking[] getBookings(Trip trip) throws ServiceException {
        sendRequest(ProtoUtils.createFindBookingsTripRequest(trip));

        Protobufs.ChatResponse response = readResponse();
        if (response.getType()== Protobufs.ChatResponse.Type.Error){
            String err=ProtoUtils.getError(response);
            throw new ServiceException(err);
        }
        return ProtoUtils.getBookings(response);
    }

    @Override
    public Trip[] getTripsFiltered(Trip trip) throws ServiceException {
        sendRequest(ProtoUtils.createFindTripFilterRequest(trip));

        Protobufs.ChatResponse response = readResponse();

        if (response.getType()== Protobufs.ChatResponse.Type.Error){
            String err=ProtoUtils.getError(response);
            throw new ServiceException(err);
        }
        return ProtoUtils.getTrips(response);
    }

    @Override
    public void addBooking(Long tripId, String firstName, String lastName, int seats) throws ServiceException {
        Trip trip = new Trip("a", "a", LocalDateTime.now(), 18);
        trip.setID(tripId);

        Booking booking = new Booking(trip, seats, firstName, lastName);
        sendRequest(ProtoUtils.createAddBookingRequest(booking));
        Protobufs.ChatResponse response = readResponse();

        if(response.getType() == Protobufs.ChatResponse.Type.Error){
            String err = ProtoUtils.getError(response);
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

    private void sendRequest(Protobufs.ChatRequest request)throws ServiceException {
        try {
            //output.writeObject(request);
            request.writeDelimitedTo(output);
            output.flush();
        }
        catch (IOException e) {
            throw new ServiceException("Error sending object "+e);
        }

    }

    private Protobufs.ChatResponse readResponse() throws ServiceException {
        Protobufs.ChatResponse response = null;
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
            output = connection.getOutputStream();
            //output.flush();
            input = connection.getInputStream();
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

    private void handleUpdate(Protobufs.ChatResponse response){
        Trip[] result = ProtoUtils.getTrips(response);
        try{
            client.newTrips(result);
        }
        catch (ServiceException e){
            e.printStackTrace();
        }

    }

    private boolean isUpdate(Protobufs.ChatResponse.Type response){
        return response == Protobufs.ChatResponse.Type.Refresh;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Protobufs.ChatResponse response = Protobufs.ChatResponse.parseDelimitedFrom(input);
                    System.out.println("response received "+response);

                    if (isUpdate(response.getType())){
                        handleUpdate(response);
                    }
                    else{
                        try {
                            responses.put(response);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
