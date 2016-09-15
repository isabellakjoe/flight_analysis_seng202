package seng202.group8.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Flight;
import seng202.group8.Model.Objects.Waypoint;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by esa46 on 15/09/16.
 */
public class FlightViewController implements Initializable {

    private MainController mainController;
    @FXML
    private GridPane flightViewPane;
    @FXML
    private Button flightViewBackButton;
    @FXML
    private TableView<Waypoint> flightTable;
    @FXML
    private TableColumn<Waypoint, String> waypointName;
    @FXML
    private TableColumn<Waypoint, String> waypointAltitude;
    @FXML
    private TableColumn<Waypoint, String> waypointLatitude;
    @FXML
    private TableColumn<Waypoint, String> waypointLongitude;
    @FXML
    private TableColumn<Waypoint, String> waypointType;
    @FXML
    private Text titleString;
    @FXML
    private Text aName;
    @FXML
    private Text bName;
    @FXML
    private Text aLatitude;
    @FXML
    private Text bLatitude;
    @FXML
    private Text aLongitude;
    @FXML
    private Text bLongitude;
    @FXML
    private Text aAltitude;
    @FXML
    private Text bAltitude;

    @FXML
    private void exitFlightView(ActionEvent e) {
        flightViewPane.setVisible(false);
        mainController.backToTableView(e);
    }

    public void setUpFlightView(Flight flight) {

        flightViewPane.setVisible(true);
        //Set source airport information
        Airport source = flight.getSourceAirport();
        String sourceName = source.getName();
        double sourceAltitude = source.getAltitude();
        double sourceLatitude = source.getLatitude();
        double sourceLongitude = source.getLongitude();
        aName.setText(sourceName);
        int srcAltitudeInt = ((int) Math.ceil(sourceAltitude));
        aAltitude.setText(Integer.toString(srcAltitudeInt));
        aLatitude.setText(Double.toString(sourceLatitude));
        aLongitude.setText(Double.toString(sourceLongitude));

        //Set destination airport information
        Airport destination = flight.getDestinationAirport();
        String destinationName = destination.getName();
        double destinationAltitude = destination.getAltitude();
        double destinationLatitude = destination.getLatitude();
        double destinationLongitude = destination.getLongitude();
        bName.setText(destinationName);
        int destAltitudeInt = ((int) Math.ceil(destinationAltitude));
        bAltitude.setText(Integer.toString(destAltitudeInt));
        bLatitude.setText(Double.toString(destinationLatitude));
        bLongitude.setText(Double.toString(destinationLongitude));

        //Create header string
        String headerString = "Flight from " + sourceName + " to " + destinationName;
        titleString.setText(headerString);

        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        flightTable.setItems(waypoints);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("FlightViewController init");

        waypointName.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("name"));
        waypointType.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("type"));
        waypointAltitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("altitude"));
        waypointLatitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("latitude"));
        waypointLongitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("longitude"));
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
