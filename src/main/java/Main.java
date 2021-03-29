///*
// *  @author albua
// *  created on 19/03/2021
// */
//
//import model.Admin;
//import model.Trip;
//import validators.AdminValidator;
//import validators.TripValidator;
//import ui.GUI;
//
//import java.security.NoSuchAlgorithmException;
//
//public class Main {
//    public static void main(String[] args) throws NoSuchAlgorithmException {
////        MessageDigest md = MessageDigest.getInstance("MD5");
////
////        String passwd = "parola";
////        md.update(passwd.getBytes());
////        byte[] digest = md.digest();
////
////        Admin admin = new Admin("root", digest);
////        TripValidator validator = new TripValidator();
////
////        Properties props = new Properties();
////        try {
////            props.load(new FileReader("bd.config"));
////        } catch (IOException e) {
////            System.out.println("Cannot find bd.config "+ e);
////        }
////
////
////        TripRepoInterface repo = new RepositoryTrip(props, validator);
////
////        Trip trip = new Trip("Praga", "Cluj-Napoca", LocalDateTime.of(2021,9,25,7,25), Trip.defaultFreeSeats);
////
////        repo.save(trip);
//
//
//        GUI.main(args);
//    }
//}
