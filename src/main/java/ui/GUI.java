/*
 *  @author albua
 *  created on 19/03/2021
 */
package ui;

import controller.LoginManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Admin;
import model.validators.AdminValidator;
import model.validators.BookingValidator;
import model.validators.TripValidator;
import repository.*;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GUI extends Application {
    RepositoryAdmin adminRepo;

    @Override
    public void start(Stage primaryStage) throws IOException {
        AdminValidator adminValidator = new AdminValidator();
        TripValidator tripValidator = new TripValidator();
        BookingValidator bookingValidator = new BookingValidator();


        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+ e);
        }

        adminRepo = new RepositoryAdmin(props, adminValidator);

        RepositoryTrip tripRepository = new RepositoryTrip(props, tripValidator);

        RepositoryBooking bookingRepo = new RepositoryBooking(props, bookingValidator, tripRepository);


        List<Admin> admins = new ArrayList<Admin>();


        Service service = new Service(tripRepository, adminRepo, bookingRepo, admins);

        Scene scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(scene, adminRepo, service);
        loginManager.showLoginScreen();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
