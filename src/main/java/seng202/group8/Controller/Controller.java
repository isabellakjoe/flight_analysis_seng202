package seng202.group8.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group8.Model.*;


import java.io.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by esa46 on 19/08/16.
 */
public class Controller implements Initializable {

    @FXML
    private MenuItem viewAirportData;

    @FXML
    private MenuItem addAirportData;

    @FXML
    private MenuItem addAirlineData;

    @FXML
    private MenuItem addRouteData;

    @FXML
    private MenuItem addFlightData;


    @FXML
    private Pane tableView;

    @FXML
    private Pane flightView;

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


    /* Method to open up a file chooser for the user to select the Airport Data file  with error handling*/
    public void addAirportData(ActionEvent e){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airport datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airport data file
                load.getFile(br);
                load.readData(br);
                load.buildAirports();
            }
        } catch(FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    /* Method to open up a file chooser for the user to select the Airline Data file with error handling */
    public void addAirlineData(ActionEvent e){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airline datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                //Use imported methods from FileLoader to process the airline data file
                FileLoader load = new FileLoader(br);
                load.getFile(br);
                load.readData(br);
                load.buildAirlines();
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    /* Method to open up a file chooser for the user to select the Route Data file with error handling */
    public void addRouteData(ActionEvent e) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Route datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the route data file
                load.getFile(br);
                load.readData(br);
                load.buildRoute();
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }
    @FXML
    /* Method to open up a file chooser for the user to select the Flight Data file  with error handling*/
    public void addFlightData(ActionEvent e){
        tableView.setVisible(false);
        flightView.setVisible(true);
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open flight datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                Flight flight = load.buildFlight();
                flightViewSetUp(flight);
                tableView.setVisible(false);
                flightView.setVisible(true);
            }
        } catch(FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    /**
     * Initializing flight column names
     */
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

    /**
     * Populate the flightView table with waypoints
     */
    private void flightViewSetUp(Flight flight){

        Airport source = flight.getSourceAirport();
        String sourceName = source.getName();
        int sourceAltitude = source.getAltitude();
        double sourceLatitude = source.getLatitude();
        double sourceLongitude = source.getLongitude();
        aName.setText(sourceName);
        aAltitude.setText(Integer.toString(sourceAltitude));
        aLatitude.setText(Double.toString(sourceLatitude));
        aLongitude.setText(Double.toString(sourceLongitude));

        Airport destination = flight.getDestinationAirport();
        String destinationName = destination.getName();
        int destinationAltitude = destination.getAltitude();
        double destinationLatitude = destination.getLatitude();
        double destinationLongitude = destination.getLongitude();
        bName.setText(destinationName);
        bAltitude.setText(Integer.toString(destinationAltitude));
        bLatitude.setText(Double.toString(destinationLatitude));
        bLongitude.setText(Double.toString(destinationLongitude));

        String headerString = "Flight from " + sourceName + " to " + destinationName;
        System.out.println(headerString);
        titleString.setText(headerString);
        ArrayList<Waypoint> waypoints = flight.getWaypoints();
        for(int i = 0; i < waypoints.size(); i++){
            Waypoint waypoint = waypoints.get(i);
            flightTable.getItems().add(waypoint);
        }

    }


    /**
     * Initializing airline column names
     */
    @FXML
    private TableView<Airline> airlineTable;
    @FXML
    private TableColumn<Airline, String> airlineID;
    @FXML
    private TableColumn<Airline, String> airlineName;
    @FXML
    private TableColumn<Airline, String> alias;
    @FXML
    private TableColumn<Airline, String> IATA;
    @FXML
    private TableColumn<Airline, String> ICAO;
    @FXML
    private TableColumn<Airline, String> callsign;
    @FXML
    private TableColumn<Airline, String> country;
    @FXML
    private TableColumn<Airline, String> active;


    /**
     * Initializing airport column names
     */
    @FXML
    private TableView<Airport> airportTable;
    @FXML
    private TableColumn<Airport, String> airportID;
    @FXML
    private TableColumn<Airport, String> airportName;
    @FXML
    private TableColumn<Airport, String> city;
    @FXML
    private TableColumn<Airport, String> airportCountry;
    @FXML
    private TableColumn<Airport, String> FAA;
    @FXML
    private TableColumn<Airport, String> airportIATA;
    @FXML
    private TableColumn<Airport, String> airportICAO;
    @FXML
    private TableColumn<Airport, String> latitude;
    @FXML
    private TableColumn<Airport, String> longitude;
    @FXML
    private TableColumn<Airport, String> altitude;
    @FXML
    private TableColumn<Airport, String> timezone;
    @FXML
    private TableColumn<Airport, String> DST;


    /**
     * Test Airline to be put in table. Needs to eventually read airlinedata already in database
     * Could be used as a test case
     */
    private String airlineString = "324,All Nippon Airways,ANA All Nippon Airways,NH,ANA,ALL NIPPON,Japan,Y";
    AirlineParser airlineParser = new AirlineParser();
    Airline airline = airlineParser.createSingleAirline(airlineString);


    /**
     * Test Airport to be put in table. Needs to eventually read airport data already in database
     * Could be used as a test case
     */
    private String airportString = "2006,Auckland Intl,Auckland,New Zealand,AKL,NZAA,-37.008056,174.791667,23,12,Z,Pacific/Auckland";
    AirportParser airportParser = new AirportParser();
    Airport airport = airportParser.createAirport(airportString);


    //Sets Table Cells in Airline Table Viewer to Airline attributes
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        airlineID.setCellValueFactory(new PropertyValueFactory<Airline, String>("ID"));
        airlineName.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        alias.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        IATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        ICAO.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        callsign.setCellValueFactory(new PropertyValueFactory<Airline, String>("Call Sign"));
        country.setCellValueFactory(new PropertyValueFactory<Airline, String>("Country"));
        active.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));
        airlineTable.getItems().setAll(airline);


        airportID.setCellValueFactory(new PropertyValueFactory<Airport, String>("ID"));
        airportName.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        city.setCellValueFactory(new PropertyValueFactory<Airport, String>("City"));
        airportCountry.setCellValueFactory(new PropertyValueFactory<Airport, String>("Country"));
        FAA.setCellValueFactory(new PropertyValueFactory<Airport, String>("FAA"));
        airportIATA.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA"));
        airportICAO.setCellValueFactory(new PropertyValueFactory<Airport, String>("ICAO"));
        latitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Latitude"));
        longitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Longitude"));
        altitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Altitude"));
        timezone.setCellValueFactory(new PropertyValueFactory<Airport, String>("Timezone"));
        DST.setCellValueFactory(new PropertyValueFactory<Airport, String>("DST"));
        airportTable.getItems().setAll(airport);

        waypointName.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("name"));
        waypointType.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("type"));
        waypointAltitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("altitude"));
        waypointLatitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("latitude"));
        waypointLongitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("longitude"));


    }


}
