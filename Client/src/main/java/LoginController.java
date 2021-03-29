/*
 *  @author albua
 *  created on 19/03/2021
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class LoginController {
    private MainServiceClient server;

    private MainViewController mainController;

    Parent mainViewParent;

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    public void initialize(){}


    /**
     * Sets parameters for login controller
     * @param userRepo database repository for users
     */
    public void setService(MainServiceClient userRepo){
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

        boolean result = server.loginHandler(username, password);

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
}
