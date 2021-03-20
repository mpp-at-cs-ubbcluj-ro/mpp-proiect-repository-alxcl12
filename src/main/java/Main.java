/*
 *  @author albua
 *  created on 19/03/2021
 */

import model.Admin;
import model.Trip;
import model.validators.AdminValidator;
import model.validators.TripValidator;
import repository.AdminRepoInterface;
import repository.RepositoryAdmin;
import repository.RepositoryTrip;
import repository.TripRepoInterface;
import ui.GUI;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//
//        String passwd = "parola";
//        md.update(passwd.getBytes());
//        byte[] digest = md.digest();
//
//        Admin admin = new Admin("root", digest);
//        TripValidator validator = new TripValidator();
//
//        Properties props = new Properties();
//        try {
//            props.load(new FileReader("bd.config"));
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+ e);
//        }
//
//
//        TripRepoInterface repo = new RepositoryTrip(props, validator);
//
//        Trip trip = new Trip("Bucuresti", "Atena", LocalDateTime.of(2021,8,16,5,40), Trip.defaultFreeSeats);
//
//        repo.save(trip);
//
//        trip = new Trip("Istanbul", "Varsovia", LocalDateTime.of(2022,2,10,7,20), Trip.defaultFreeSeats);
//        repo.save(trip);
//        trip = new Trip("Cluj-Napoca", "Paris", LocalDateTime.of(2021,5,20,4,30), Trip.defaultFreeSeats);
//        repo.save(trip);
//        trip = new Trip("Berlin", "Praga", LocalDateTime.of(2022,7,10,10,55), Trip.defaultFreeSeats);
//        repo.save(trip);

        GUI.main(args);
    }
}
