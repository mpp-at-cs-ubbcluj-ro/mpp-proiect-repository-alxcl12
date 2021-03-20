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
import repository.AdminRepoInterface;
import service.Service;

import java.io.IOException;

public class GUI extends Application {
    AdminRepoInterface userRepo;

    @Override
    public void start(Stage primaryStage) throws IOException {
//        Validator<User> userValidator = new UserValidator();
//        FriendshipValidator friendshipValidator = new FriendshipValidator();
//        FriendRequestValidator friendRequestValidator = new FriendRequestValidator();
//        MessageValidator messageValidator = new MessageValidator();
//        EventValidator eventValidator = new EventValidator();
//
//        userRepo = new UserDatabase(
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password"),
//                userValidator);
//
//        FriendshipDatabase databaseFriendship = new FriendshipDatabase(
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password"),
//                friendshipValidator);
//
//        FriendRequestDatabase databaseFriendRequest = new FriendRequestDatabase(
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password"),
//                friendRequestValidator);
//
//        MessagesDatabase databaseMessages = new MessagesDatabase(
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password"),
//                messageValidator);
//        EventDatabase databaseEvent = new EventDatabase(
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username"),
//                ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password"),
//                eventValidator
//        );
//
//
//        Service service = new Service(userRepo, databaseFriendship, databaseFriendRequest, databaseMessages, databaseEvent);
//        service.setPageSizeNonFriends(25);
//        service.setPageSizeFriends(25);
//        service.setPageSizeMes(25);
//        service.setPageSizeReq(25);
//
//        //service.bigPopulateForA();

        Scene scene = new Scene(new StackPane());
        Service service = null;

        LoginManager loginManager = new LoginManager(scene, userRepo, service);
        loginManager.showLoginScreen();

        primaryStage.setScene(scene);
        primaryStage.setTitle("socialnect");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
