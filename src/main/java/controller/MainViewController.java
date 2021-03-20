/*
 *  @author albua
 *  created on 19/03/2021
 */
package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import service.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainViewController {

    Long currentId;
    Service service;

    public void initSessionID(final LoginManager loginManager, Long sessionID) {
        this.currentId = sessionID;
//        logoutButton.setOnAction(event -> {
//            try {
//                loginManager.logout();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public void setService(Service service) {
        this.service = service;
//        service.addObserver(this);
//        initModel();


    }
}
