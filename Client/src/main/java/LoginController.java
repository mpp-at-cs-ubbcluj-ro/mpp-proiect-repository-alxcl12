/*
 *  @author albua
 *  created on 19/03/2021
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class LoginController extends UnicastRemoteObject implements Observer, Serializable {
    private Services server;

    private MainViewController mainController;

    Parent mainViewParent;

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    public LoginController() throws RemoteException {
    }

    @FXML
    public void initialize(){}


    /**
     * Sets parameters for login controller
     * @param userRepo database repository for users
     */
    public void setService(Services userRepo){
        this.server = userRepo;
    }

    public void setMainController(MainViewController mainViewController){
        this.mainController = mainViewController;
    }

    public void setParent(Parent p){
        mainViewParent=p;
    }

    public void handleLogin(ActionEvent actionEvent){
        String username = emailTextField.getText();
        String password = passwordTextField.getText();

        byte[] nd = null;
        Admin admin = new Admin(username, nd);
        admin.setPasswordString(password);
        boolean result = false;
        try {
            server.login(admin, this);
            result = true;
        }
        catch (ServiceException e){
            return;
        }

        if(result) {
            Stage stage = new Stage();
            stage.setTitle("Window for " + username);
            stage.setScene(new Scene(mainViewParent));
            mainController.setAdminId(username);
            try {
                mainController.initModel();
            }
            catch (ServiceException e){
                e.printStackTrace();
            }

            try {
                server.logout(admin.getUsername());
            }
            catch (ServiceException e){
                e.printStackTrace();
            }

            mainController.logToServer(admin);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    mainController.logout();
                    System.exit(0);
                }
            });

            stage.show();
            ((javafx.scene.Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    @Override
    public void newTrips(Trip[] trips) throws ServiceException, RemoteException {

    }
}
