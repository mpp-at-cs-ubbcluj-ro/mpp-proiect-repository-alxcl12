/*
 *  @author albua
 *  created on 19/03/2021
 */

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Booking;
import models.Trip;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class MainViewController implements ObserverNormal {
    private MainServiceClient service;

    private ObservableList<Trip> modelTrip = FXCollections.observableArrayList();
    private ObservableList<Booking> modelBooking = FXCollections.observableArrayList();

    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @FXML
    private Button logoutButton;
    @FXML
    private Button filterButton;

    @FXML
    private TextField sourceTextField;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField seatsField;

    @FXML
    private TableColumn<Trip, String> tableColumnSource;
    @FXML
    private TableColumn<Trip, String> tableColumnDestination;
    @FXML
    private TableColumn<Trip, String> tableColumnDate;
    @FXML
    private TableColumn<Trip, String> tableColumnFreeSeats;

    @FXML
    private TableColumn<Booking, Integer> tableColumnSeatNumber;
    @FXML
    private TableColumn<Booking, String> tableColumnName;

    @FXML
    private TableView<Trip> tableViewTrips;
    @FXML
    private TableView<Booking> tableViewPassenger;


    public void setService(MainServiceClient service) throws ServiceException {
        this.service = service;
        //initModel();
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
                    if (rowIndex <= row.getTableView().getItems().size()) {
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

                Trip tripId = tableViewTrips.getSelectionModel().getSelectedItem();
                Booking[] bookings = null;
                bookings = (Booking[]) service.getBookings(tripId);
                for(int i=0;i<bookings.length;i++){
                    System.out.println(bookings[i]);
                }

                int k = 1;

                for(int i=0;i<18;i++){
                    if(i >= bookings.length){
                        while(k<=18) {
                            Booking book = new Booking(null, 0, "-", null);
                            book.setClientDisplayName("-");
                            k++;
                            modelBooking.add(book);
                        }
                        break;
                    }
                    else {
                        for (int j = 0; j < bookings[i].getNrSeats(); j++) {
                            modelBooking.add(bookings[i]);
                            k++;
                        }
                    }
                }
            }
        });

        tableViewPassenger.setItems(modelBooking);
    }

    public void initModel() throws ServiceException {
        Iterable<Trip> trips = (Iterable<Trip>) service.getAllTrips();

        List<Trip> tripList = StreamSupport.stream(trips.spliterator(), false)
                .collect(Collectors.toList());

        modelTrip.setAll(tripList);
    }

    public void handleBook() throws ServiceException {
        Trip tripId = tableViewTrips.getSelectionModel().getSelectedItem();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String nrSeatsStr = seatsField.getText();

        int nrSeats = Integer.parseInt(nrSeatsStr);

        service.addBooking(tripId, firstName, lastName, nrSeats);
    }

    public void handleFilter() throws ServiceException {
        String source = sourceTextField.getText();
        LocalDate date = datePicker.getValue();

        Trip trip = new Trip(source, "a", date.atStartOfDay(), 2);
        Trip[] result = (Trip[]) service.getTripsFiltered(trip);

        modelTrip.setAll(Arrays.asList(result));

        initModel();
    }

    public void handleReset() throws ServiceException {
        initModel();
    }

    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    void logout() {
        service.logout(adminId);

    }

    @Override
    public void update(Object arg) {
        Platform.runLater(()->{
            try {
                initModel();
            }
            catch (ServiceException e){
                e.printStackTrace();
            }
        });

    }
}
