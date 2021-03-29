/*
 *  @author albua
 *  created on 25/03/2021
 */

import utils.AbstractServer;
import utils.ServerException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/server.proprieties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties " + e);
            return;
        }
        AdminValidator adminValidator = new AdminValidator();
        TripValidator tripValidator = new TripValidator();
        BookingValidator bookingValidator = new BookingValidator();


        AdminRepoInterface adminRepo = new RepositoryAdmin(serverProps, adminValidator);
        TripRepoInterface tripRepo = new RepositoryTrip(serverProps, tripValidator);
        BookingRepoInterface bookingRepo = new RepositoryBooking(serverProps, bookingValidator, tripRepo);

        Service service = new Service(tripRepo, adminRepo, bookingRepo);
//
//        byte[] digest = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            String st = "parola";
//            md.update(st.getBytes());
//
//            digest = md.digest();
//        }
//        catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }
//
//        Admin ad = new Admin("toor", digest);
//        adminRepo.save(ad);

        Services serverImpl = new ServicesImplem(service);
        int serverPort = defaultPort;

        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }
        catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new RpcConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
