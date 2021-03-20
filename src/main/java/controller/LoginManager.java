/*
 *  @author albua
 *  created on 19/03/2021
 */
package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import repository.AdminRepoInterface;
import service.Service;

import java.io.IOException;

public class LoginManager {
    //current scene
    private Scene scene;

    //user database
    private AdminRepoInterface userRepo;

    //service
    private Service service;

    public LoginManager(Scene scene, AdminRepoInterface userRepo, Service service) {
        this.scene = scene;
        this.userRepo = userRepo;
        this.service = service;
    }

    /**
     * Method called in order to notify that user has logged in
     * Will show the main application screen.
     * @param sessionID ID of logged user
     */
    public void authenticated(Long sessionID) throws IOException {
        showMainView(sessionID);
        scene.getWindow().sizeToScene();
    }

    /**
     * Method called in order to notify that user has logged out
     * Will show the login application screen.
     */
    public void logout() throws IOException {
        showLoginScreen();
        scene.getWindow().sizeToScene();
    }

    /**
     * Loads FXML file for login screen and displays it
     */
    public void showLoginScreen(){
        try {
            FXMLLoader loginLoader = new FXMLLoader();
            loginLoader.setLocation(getClass().getResource("/views/loginScreenView.fxml"));

            scene.setRoot((Parent) loginLoader.load());

            LoginController loginController = loginLoader.getController();
            loginController.setService(userRepo);
            loginController.initManager(this);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads FXML file for main screen and displays it
     * @param sessionID ID of logged in user
     */
    private void showMainView(Long sessionID){
        try {
            FXMLLoader mainLoader = new FXMLLoader();
            mainLoader.setLocation(getClass().getResource("/views/mainWindowView.fxml"));

            scene.setRoot((Parent) mainLoader.load());

            MainViewController mainViewController = mainLoader.getController();
            mainViewController.initSessionID(this, sessionID);
            mainViewController.setService(service);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
