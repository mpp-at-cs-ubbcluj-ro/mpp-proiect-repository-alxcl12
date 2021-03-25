/*
 *  @author albua
 *  created on 19/03/2021
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Admin;
import repository.AdminRepoInterface;
import service.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    AdminRepoInterface userRepo;

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;

    @FXML
    public void initialize(){}

    /**
     * Bind login button to action
     * Call login manager and authenticate user if credentials are right
     * @param loginManager login manager
     */
    public void initManager(final LoginManager loginManager){
        loginButton.setOnAction(event -> {
            String username = emailTextField.getText();
            String passwd = passwordTextField.getText();

            Admin admin = null;
            try {
                admin = userRepo.authenticateAdmin(username, passwd);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if(admin == null){
                return;
            }

            try {
                //tell login manager to open main window for this user and close login window
                loginManager.authenticated(admin.getID());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Sets parameters for login controller
     * @param userRepo database repository for users
     */
    public void setService(AdminRepoInterface userRepo){
        this.userRepo = userRepo;
    }

}
