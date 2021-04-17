/*
 *  @author albua
 *  created on 25/03/2021
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class StartRpcClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    public void start(Stage primaryStage) throws Exception {
//        System.out.println("In start");
//        Properties clientProps = new Properties();
//        try {
//            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
//            System.out.println("Client properties set. ");
//            clientProps.list(System.out);
//        }
//        catch (IOException e) {
//            System.err.println("Cannot find client.properties " + e);
//            return;
//        }
//
//        String serverIP = clientProps.getProperty("server.host", defaultServer);
//        int serverPort = defaultChatPort;
//
//        try {
//            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
//        }
//        catch (NumberFormatException ex) {
//            System.err.println("Wrong port number " + ex.getMessage());
//            System.out.println("Using default port: " + defaultChatPort);
//        }
//        System.out.println("Using server IP " + serverIP);
//        System.out.println("Using server port " + serverPort);
//
//        Services server = new ServicesRpcProxy(serverIP, serverPort);
//        MainServiceClient clSrv = new MainServiceClient(server);
//
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(StartRpcClient.class.getResource("loginView.fxml"));
//        System.setProperty("javafx.sg.warn", "true");
//        Parent root = loader.load();
//
//
//        LoginController loginController = loader.<LoginController>getController();
//        loginController.setService(clSrv);
//
//
//        FXMLLoader mLoader = new FXMLLoader();
//        mLoader.setLocation(StartRpcClient.class.getResource("mainView.fxml"));
//        System.setProperty("javafx.sg.warn", "true");
//        Parent mRoot = mLoader.load();
//
//
//        MainViewController mainController =
//                mLoader.<MainViewController>getController();
//        mainController.setService(clSrv);
//
//        loginController.setMainController(mainController);
//        loginController.setParent(mRoot);
//
//        clSrv.addObserver(mainController);
//        primaryStage.setScene(new Scene(root, 600, 600));
//        primaryStage.show();

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        Services server = (Services) factory.getBean("service");
        System.out.println("Got service reference");


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartRpcClient.class.getResource("loginView.fxml"));
        System.setProperty("javafx.sg.warn", "true");
        Parent root = loader.load();


        LoginController loginController = loader.<LoginController>getController();
        loginController.setService(server);


        FXMLLoader mLoader = new FXMLLoader();
        mLoader.setLocation(StartRpcClient.class.getResource("mainView.fxml"));
        System.setProperty("javafx.sg.warn", "true");
        Parent mRoot = mLoader.load();


        MainViewController mainController =
                mLoader.<MainViewController>getController();
        mainController.setService(server);

        loginController.setMainController(mainController);
        loginController.setParent(mRoot);

        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
}
