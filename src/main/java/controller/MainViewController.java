/*
 *  @author albua
 *  created on 19/03/2021
 */
package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Booking;
import model.Trip;
import service.Service;
import utils.Observer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class MainViewController implements Observer {
    Long currentId;
    Service service;

    ObservableList<Trip> modelTrip = FXCollections.observableArrayList();
    ObservableList<Booking> modelBooking = FXCollections.observableArrayList();

    @FXML
    Button logoutButton;
    @FXML
    Button filterButton;

    @FXML
    TextField sourceTextField;
    @FXML
    DatePicker datePicker;

    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField seatsField;

    @FXML
    TableColumn<Trip, String> tableColumnSource;
    @FXML
    TableColumn<Trip, String> tableColumnDestination;
    @FXML
    TableColumn<Trip, String> tableColumnDate;
    @FXML
    TableColumn<Trip, String> tableColumnFreeSeats;

    @FXML
    TableColumn<Booking, Integer> tableColumnSeatNumber;
    @FXML
    TableColumn<Booking, String> tableColumnName;

    @FXML
    TableView<Trip> tableViewTrips;
    @FXML
    TableView<Booking> tableViewPassenger;
    
    public void initSessionID(final LoginManager loginManager, Long sessionID) {
        this.currentId = sessionID;
        logoutButton.setOnAction(event -> {
            try {
                loginManager.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    public void initialize(){
        tableColumnSource.setCellValueFactory(new PropertyValueFactory<>("source"));
        tableColumnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        tableColumnFreeSeats.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("dateFormat"));
        tableViewTrips.setItems(modelTrip);

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("clientDisplayName"));

        tableColumnSeatNumber.setCellFactory(col ->{
            TableCell<Booking, Integer> indexCell = new TableCell<>();
            ReadOnlyObjectProperty<TableRow<Booking>> rowProperty = indexCell.tableRowProperty();
            ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                TableRow<Booking> row = rowProperty.get();
                if (row != null) {
                    int rowIndex = row.getIndex() + 1;
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });

        tableViewTrips.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tableViewPassenger.getItems().clear();
                modelBooking.clear();

                Long tripId = tableViewTrips.getSelectionModel().getSelectedItem().getID();
                Iterable<Booking> bookings = service.getBookingsForTrip(tripId);

                List<Booking> bookingList = StreamSupport.stream(bookings.spliterator(), false)
                        .collect(Collectors.toList());

                for(int i=0;i<18;i++){
                    if(i>=bookingList.size()){
                        Booking book = new Booking(null, 0, "-", null);
                        book.setClientDisplayName("-");

                        modelBooking.add(book);
                    }
                    else {
                        for (int j = 0; j < bookingList.get(i).getNrSeats(); j++) {
                            modelBooking.add(bookingList.get(i));
                        }
                    }
                }
            }
        });

        tableViewPassenger.setItems(modelBooking);
    }

    public void initModel(){
        Iterable<Trip> trips = service.getAllTrips();

        List<Trip> tripList = StreamSupport.stream(trips.spliterator(), false)
                .collect(Collectors.toList());

        modelTrip.setAll(tripList);
    }

    public void handleBook(){
        Long tripId = tableViewTrips.getSelectionModel().getSelectedItem().getID();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String nrSeatsStr = seatsField.getText();

        int nrSeats = Integer.parseInt(nrSeatsStr);

        service.addBooking(tripId, firstName, lastName, nrSeats);
        initModel();
    }

    public void handleFilter(){

    }

    @Override
    public void update() {
        initModel();
    }
}
