package seng202.group8.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.Searchers.AirlineSearcher;
import seng202.group8.Model.Searchers.AirportSearcher;
import seng202.group8.Model.Objects.*;
import seng202.group8.Model.Parsers.FileLoader;
import seng202.group8.Model.Searchers.RouteSearcher;

import java.io.*;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by esa46 on 19/08/16.
 */
public class Controller implements Initializable {

    ObservableList<Airline> currentlyLoadedAirlines = FXCollections.observableArrayList();
    ObservableList<Airport> currentlyLoadedAirports = FXCollections.observableArrayList();
    ObservableList<Route> currentlyLoadedRoutes = FXCollections.observableArrayList();



    @FXML
    private Pane tableView;
    @FXML
    private Pane flightView;


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
                ObservableList<Airport> airports = load.buildAirports();
                currentlyLoadedAirports = airports;
                airportTable.setItems(airports);
                flightView.setVisible(false);
                tableView.setVisible(true);
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
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airline data file
                ObservableList<Airline> airlines = load.buildAirlines();
                currentlyLoadedAirlines = airlines;
                airlineTable.setItems(airlines);
                flightView.setVisible(false);
                tableView.setVisible(true);
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
                ObservableList<Route> routes = load.buildRoutes();
                currentlyLoadedRoutes = routes;
                routeTable.setItems(routes);
                flightView.setVisible(false);
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    @FXML
    /* Method to open up a file chooser for the user to select the Flight Data file  with error handling*/
    public void addFlightData(ActionEvent e){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open flight datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                Flight flight = load.buildFlight();
                flightViewSetUp(flight);
                //Swap panes from raw data to the flight viewer
                tableView.setVisible(false);
                flightView.setVisible(true);
            }
        } catch(FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    private void filterAirportByCountry(){

    }


    /**
     * Populate the flightView table with waypoints
     */
    private void flightViewSetUp(Flight flight){

        //Set source airport information
        Airport source = flight.getSourceAirport();
        String sourceName = source.getName();
        int sourceAltitude = source.getAltitude();
        double sourceLatitude = source.getLatitude();
        double sourceLongitude = source.getLongitude();
        aName.setText(sourceName);
        aAltitude.setText(Integer.toString(sourceAltitude));
        aLatitude.setText(Double.toString(sourceLatitude));
        aLongitude.setText(Double.toString(sourceLongitude));

        //Set destination airport information
        Airport destination = flight.getDestinationAirport();
        String destinationName = destination.getName();
        int destinationAltitude = destination.getAltitude();
        double destinationLatitude = destination.getLatitude();
        double destinationLongitude = destination.getLongitude();
        bName.setText(destinationName);
        bAltitude.setText(Integer.toString(destinationAltitude));
        bLatitude.setText(Double.toString(destinationLatitude));
        bLongitude.setText(Double.toString(destinationLongitude));

        //Create header string
        String headerString = "Flight from " + sourceName + " to " + destinationName;
        System.out.println(headerString);
        titleString.setText(headerString);

        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        flightTable.setItems(waypoints);

    }


    @FXML
    public void resetSearch(ActionEvent e){
        routeTable.setItems(currentlyLoadedRoutes);
        airlineTable.setItems(currentlyLoadedAirlines);
        airportTable.setItems(currentlyLoadedAirports);
        flightView.setVisible(false);
        tableView.setVisible(true);
    }

    @FXML
    private void airportSearch(ActionEvent e){
        flightView.setVisible(false);
        tableView.setVisible(true);
        AirportSearcher searcher = new AirportSearcher(currentlyLoadedAirports);
        String airportID = airportIDSearch.getText();
        String name = airportNameSearch.getText();
        String city = airportCitySearch.getText();
        String country = airportCountrySearch.getText();
        String FAA = airportFAASearch.getText();
        String IATA = airportIATASearch.getText();
        String latitude = airportLatitudeSearch.getText();
        String longitude = airportLongitudeSearch.getText();
        String altitude = airportAltitudeSearch.getText();
        String timezone = airportTimezoneSearch.getText();
        String DST = airportDSTSearch.getText();

        if (airportID.length() > 0){ searcher.airportsOfID(airportID); }

        if (name.length() > 0){ searcher.airportsOfName(name); }

        if (city.length() > 0){ searcher.airportsOfCity(city); }

        if (country.length() > 0) {searcher.airportsOfCountry(country); }

        if (FAA.length() > 0) { searcher.airportsOfFAA(FAA); }

        if (IATA.length() > 0) { searcher.airportsOfIATA(IATA); }

        if (latitude.length() > 0) { searcher.airportsOfLatitude(latitude); }

        if (longitude.length() > 0) { searcher.airportsOfLongitude(longitude); }

        if (altitude.length() > 0) { searcher.airportsOfAltitude(altitude); }

        if (timezone.length() > 0) { searcher.airportsOfTimezone(timezone); }

        if (DST.length() > 0) { searcher.airportsOfDST(DST); }

        ObservableList<Airport> matchingAirports = searcher.getLoadedAirports();
        airportTable.setItems(matchingAirports);
    }


    @FXML
    private void airlineSearch(ActionEvent e){
        flightView.setVisible(false);
        tableView.setVisible(true);
        AirlineSearcher searcher = new AirlineSearcher(currentlyLoadedAirlines);

        String airlineID = airlineIDSearch.getText();
        String name = airlineNameSearch.getText();
        String alias = airlineAliasSearch.getText();
        String IATA = airlineIATASearch.getText();
        String ICAO = airlineICAOSearch.getText();
        String callsign = airlineCallsignSearch.getText();
        String country = airlineCountrySearch.getText();
        String activeStatus = airlineActiveSearch.getText();

        if (airlineID.length() > 0){ searcher.airlinesOfID(airlineID);}

        if (name.length() > 0){ searcher.airlinesOfName(name);}

        if (alias.length() > 0){ searcher.airlinesOfAlias(alias);}

        if (IATA.length() > 0){ searcher.airlinesOfIATA(IATA);}

        if (ICAO.length() > 0){ searcher.airlinesOfICAO(ICAO);}

        if (callsign.length() > 0){ searcher.airlinesOfCallsign(callsign);}

        if (country.length() > 0){ searcher.airlinesOfCountry(country);}

        if (activeStatus.length() > 0) {searcher.airlinesOfActiveStatus(activeStatus);}

        ObservableList<Airline> matchingAirlines = searcher.getLoadedAirlines();
        airlineTable.setItems(matchingAirlines);
    }


    @FXML
    private void routeSearch(ActionEvent e){
        flightView.setVisible(false);
        tableView.setVisible(true);
        codeshareErrorMessage.setVisible(false);
        stopsErrorMessage.setVisible(false);

        RouteSearcher searcher = new RouteSearcher(currentlyLoadedRoutes);
        String sourceAirport = sourceSearch.getText();
        String destinationAirport = destinationSearch.getText();
        String stops = stopoverSearch.getText();
        String codeshareStatus = codeshareSearch.getText();

        if (sourceAirport.length() > 0){searcher.routesOfSource(sourceAirport);}

        if (destinationAirport.length() > 0){searcher.routesOfDestination(destinationAirport);}

        if (stops.length() > 0 ) {
            try {
                int intStops = Integer.parseInt(stops);
                searcher.routesOfStops(intStops);
            }
            catch (NumberFormatException exception) {
                stopsErrorMessage.setVisible(true);
            }
        }

        if (codeshareStatus.length() > 0) {
            if (codeshareStatus.equals("Y") || codeshareStatus.equals("N")) {
                searcher.routesOfCodeshare(codeshareStatus);
            } else {
                codeshareErrorMessage.setVisible(true);
            }
        }

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();

        routeTable.setItems(matchingRoutes);
    }

    /**
     * FXML imports for searching
     */


    @FXML
    private Text stopsErrorMessage;
    @FXML
    private Text codeshareErrorMessage;

    @FXML
    private TextField airportIDSearch;
    @FXML
    private TextField airportNameSearch;
    @FXML
    private TextField airportCitySearch;
    @FXML
    private TextField airportCountrySearch;
    @FXML
    private TextField airportFAASearch;
    @FXML
    private TextField airportIATASearch;
    @FXML
    private TextField airportLatitudeSearch;
    @FXML
    private TextField airportLongitudeSearch;
    @FXML
    private TextField airportAltitudeSearch;
    @FXML
    private TextField airportTimezoneSearch;
    @FXML
    private TextField airportDSTSearch;


    @FXML
    private TextField airlineIDSearch;
    @FXML
    private TextField airlineNameSearch;
    @FXML
    private TextField airlineAliasSearch;
    @FXML
    private TextField airlineIATASearch;
    @FXML
    private TextField airlineICAOSearch;
    @FXML
    private TextField airlineCallsignSearch;
    @FXML
    private TextField airlineCountrySearch;
    @FXML
    private TextField airlineActiveSearch;

    @FXML
    private TextField sourceSearch;
    @FXML
    private TextField destinationSearch;
    @FXML
    private TextField stopoverSearch;
    @FXML
    private TextField codeshareSearch;


    /**
     * Setting up for flight table
     */
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


    @FXML
    private TableView<Route> routeTable;
    @FXML
    private TableColumn<Route, String> routeAirlineName;
    @FXML
    private TableColumn<Route, String> source;
    @FXML
    private TableColumn<Route, String> destination;
    @FXML
    private TableColumn<Route, String> codeshare;
    @FXML
    private TableColumn<Route, String> stops;
    @FXML
    private TableColumn<Route, String> equipment;




    /**
     * Test Airline to be put in table. Needs to eventually read airlinedata already in database
     * Could be used as a test case
     */
    //private String airlineString = "324,All Nippon Airways,ANA All Nippon Airways,NH,ANA,ALL NIPPON,Japan,Y";
    //AirlineParser airlineParser = new AirlineParser();
    //Airline airline = airlineParser.createSingleAirline(airlineString);


    /**
     * Test Airport to be put in table. Needs to eventually read airport data already in database
     * Could be used as a test case
     */
    //private String airportString = "2006,Auckland Intl,Auckland,New Zealand,AKL,NZAA,-37.008056,174.791667,23,12,Z,Pacific/Auckland";
    //AirportParser airportParser = new AirportParser();
    //Airport airport = airportParser.createAirport(airportString);


    //Sets Table Cells in Airline Table Viewer to Airline attributes
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        airlineID.setCellValueFactory(new PropertyValueFactory<Airline, String>("airlineID"));
        airlineName.setCellValueFactory(new PropertyValueFactory<Airline, String>("name"));
        alias.setCellValueFactory(new PropertyValueFactory<Airline, String>("alias"));
        IATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        ICAO.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        callsign.setCellValueFactory(new PropertyValueFactory<Airline, String>("callsign"));
        country.setCellValueFactory(new PropertyValueFactory<Airline, String>("country"));
        active.setCellValueFactory(new PropertyValueFactory<Airline, String>("active"));
        //airlineTable.getItems().setAll(airline);


        airportID.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportID"));
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
        //airportTable.getItems().setAll(airport);

        routeAirlineName.setCellValueFactory(new PropertyValueFactory<Route, String>("airlineName"));
        source.setCellValueFactory(new PropertyValueFactory<Route, String>("sourceAirportName"));
        destination.setCellValueFactory(new PropertyValueFactory<Route, String>("destinationAirportName"));
        codeshare.setCellValueFactory(new PropertyValueFactory<Route, String>("codeshareString"));
        stops.setCellValueFactory(new PropertyValueFactory<Route, String>("stops"));
        equipment.setCellValueFactory(new PropertyValueFactory<Route, String>("equipment"));


        waypointName.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("name"));
        waypointType.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("type"));
        waypointAltitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("altitude"));
        waypointLatitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("latitude"));
        waypointLongitude.setCellValueFactory(new PropertyValueFactory<Waypoint, String>("longitude"));

        // Allows individual cells to be selected as opposed to rows
        //airportTable.getSelectionModel().setCellSelectionEnabled(true);
        airportTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click)
            {
                //Checks if table is empty then checks for doubleclick
                if (!airportTable.getItems().isEmpty() && click.getClickCount() >= 2)
                {
                    System.out.println("Double clicked!");
                    TablePosition pos = airportTable.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();
                    int col = pos.getColumn();
                    TableColumn column = pos.getTableColumn();
                    String val = column.getCellData(row).toString();
                    System.out.println("Selected Value, " + val + ", Column: " + col + ", Row: " + row);
                    if ( col == 2 ) {System.out.println("2");}
                    if ( col == 5 ) {System.out.println("5");}
                    if ( col == 6 ) {System.out.println("6");}
                    if ( col == 8 ) {System.out.println("8");}
                }
            }
        });

        airlineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            //clickevent handler for doubleclicking a table cell.
            public void handle(MouseEvent click)
            {
                if (!airlineTable.getItems().isEmpty() && click.getClickCount() >= 2)
                {
                    TablePosition pos = airlineTable.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();
                    int col = pos.getColumn();
                    TableColumn column = pos.getTableColumn();
                    String val = column.getCellData(row).toString();
                    System.out.println("Selected Value, " + val + ", Column: " + col + ", Row: " + row);
                    if ( col == 2 ) {System.out.println("2");}
                    if ( col == 5 ) {System.out.println("5");}
                    if ( col == 6 ) {System.out.println("6");}
                    if ( col == 8 ) {System.out.println("8");}
                }
            }
        });

        routeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            //clickevent handler for doubleclicking a table cell.
            public void handle(MouseEvent click)
            {
                if (!routeTable.getItems().isEmpty() && click.getClickCount() >= 2)
                {
                    TablePosition pos = routeTable.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();
                    int col = pos.getColumn();
                    TableColumn column = pos.getTableColumn();
                    String val = column.getCellData(row).toString();
                    System.out.println("Selected Value, " + val + ", Column: " + col + ", Row: " + row);
                    if ( col == 2 ) {System.out.println("2");}
                    if ( col == 5 ) {System.out.println("5");}
                    if ( col == 6 ) {System.out.println("6");}
                    if ( col == 8 ) {System.out.println("8");}
                }
            }
        });
    }
}
