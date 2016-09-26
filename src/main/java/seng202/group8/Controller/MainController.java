package seng202.group8.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.group8.Controller.AddObjectControllers.AddAirlineViewController;
import seng202.group8.Controller.AddObjectControllers.AddAirportViewController;
import seng202.group8.Controller.AddObjectControllers.AddRouteViewController;
import seng202.group8.Model.DatabaseMethods.*;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Flight;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.FileLoader;
import seng202.group8.Model.Searchers.AirlineSearcher;
import seng202.group8.Model.Searchers.AirportSearcher;
import seng202.group8.Model.Searchers.RouteSearcher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.util.*;



/**
 * Created by esa46 on 19/08/16.
 */


public class MainController implements Initializable {

    //These lists are used to display the currently loaded objects
    private static ObservableList<Airline> currentlyLoadedAirlines = FXCollections.observableArrayList();
    private static ObservableList<Airport> currentlyLoadedAirports = FXCollections.observableArrayList();
    private static ObservableList<Route> currentlyLoadedRoutes = FXCollections.observableArrayList();

    @FXML
    public TableView<Airline> airlineTable;

    @FXML
    public TableView<Airport> airportTable;
    @FXML
    public TableView<Route> routeTable;
    @FXML
    public Pane tableView;
    //These lists are used to hold the information about currently loaded objects.
    private HashMap<String, Airline> airlineHashMap = new HashMap<String, Airline>();
    private HashMap<String, Airport> airportHashMap = new HashMap<String, Airport>();
    private HashMap<Integer, Route> routeHashMap = new HashMap<Integer, Route>();
    //This integer here is currently to keep track of route id's
    private int routeIds = 0;
    @FXML
    private FlightViewController flightViewController;
    @FXML
    private MapViewController mapViewController;
    @FXML
    private AddAirlineViewController addAirlineViewController;
    @FXML
    private AddAirportViewController addAirportViewController;
    @FXML
    private AddRouteViewController addRouteViewController;
    @FXML
    private EditAirlineViewController editAirlineViewController;
    @FXML
    private TextField editSourceField;
    @FXML
    private TextField editDestinationField;
    @FXML
    private TextField editStopsField;
    @FXML
    private TextField editEquipmentField;
    @FXML
    private CheckBox editCodeshareField;
    @FXML
    private Button saveRouteChangesButton;
    @FXML
    private Button cancelRouteChangesButton;
    /*
     * Populate the flightView table with waypoints
     */
    @FXML
    private Button editRouteDataButton;
    @FXML
    private Button airportAddButton;
    @FXML
    private Button airlineAddButton;
    @FXML
    private Button routeAddButton;
    @FXML
    private TextField editFAAField;
    @FXML
    private TextField editAirportIATAField;
    @FXML
    private TextField editAirportICAOField;
    @FXML
    private TextField editTimezoneField;
    @FXML
    private TextField editDSTField;
    @FXML
    private TextField editAirportCountryField;
    @FXML
    private TextField editLatitudeField;
    @FXML
    private TextField editAirportCityField;
    @FXML
    private TextField editLongitudeField;
    @FXML
    private TextField editAltitudeField;
    @FXML
    private Button saveAirportChangesButton;
    @FXML
    private Button cancelAirportChangesButton;
    @FXML
    private Button editAirportDataButton;
    @FXML
    private Button routeAdvancedButton;
    @FXML
    private Button routeSearch;
    @FXML
    private Button resetRouteSearch;
    @FXML
    private Button routeBackButton;
    @FXML
    private Button airlineAdvancedButton;
    @FXML
    private Button airlineSearchButton;
    @FXML
    private Button resetAirlineSearch;
    @FXML
    private Button airlineBackButton;
    @FXML
    private Button resetAirportSearch;
    @FXML
    private Button airportSearchButton;
    @FXML
    private Button airportBackButton;
    @FXML
    private Button airportAdvancedButton;
    @FXML
    private TextField airportIDSearch;
    @FXML
    private TextField airportNameSearch;
    @FXML
    private TextField airportCitySearch;
    @FXML
    private ComboBox airportCountrySearch;
    @FXML
    private TextField airportFAASearch;
    @FXML
    private TextField airportIATASearch;
    @FXML
    private TextField airportICAOSearch;
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
    private ComboBox airlineCountrySearch;
    @FXML
    private ComboBox airlineActiveSearch;
    @FXML
    private TextField airlineSearch;
    @FXML
    private TextField airlineSearchID;
    @FXML
    private ComboBox sourceSearch;
    @FXML
    private TextField sourceIDSearch;
    @FXML
    private ComboBox destinationSearch;
    @FXML
    private TextField destinationIDSearch;
    @FXML
    private ComboBox stopoverSearch;
    @FXML
    private ComboBox codeshareSearch;
    @FXML
    private ComboBox equipmentSearch;
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
    @FXML
    private Pane airportPane;
    @FXML
    private Text airportNameDisplay;
    @FXML
    private Text airportIDDisplay;
    @FXML
    private Text airportFAADisplay;
    @FXML
    private Text airportIATADisplay;
    @FXML
    private Text airportICAODisplay;
    @FXML
    private Text airportTimezoneDisplay;
    @FXML
    private Text airportDSTDisplay;
    @FXML
    private Text airportCountryDisplay;
    @FXML
    private Text airportCityDisplay;
    @FXML
    private Text airportLongitudeDisplay;
    @FXML
    private Text airportLatitudeDisplay;
    /**
     * Initializing flight column names
     */
    @FXML
    private Text airportAltitudeDisplay;
    @FXML
    private Pane routePane;
    @FXML
    private Text routeSourceDisplay;
    @FXML
    private Text routeDestinationDisplay;
    @FXML
    private Text routeStopsDisplay;
    @FXML
    private Text routeAirlineDisplay;
    @FXML
    private Text routeEquipmentDisplay;
    @FXML
    private Text routeShareDisplay;
    @FXML
    private Button individualAirportBackButton;
    @FXML
    private Button individualRouteBackButton;
    @FXML
    private MenuItem getDistanceMenu;
    @FXML
    private ContextMenu distanceMenu;
    @FXML
    private Text editAirportFAAError;
    @FXML
    private Text editAirportIATAError;
    @FXML
    private Text editAirportICAOError;
    @FXML
    private Text editAirportTimeError;
    @FXML
    private Text editAirportDSTError;
    @FXML
    private Text editAirportCountryError;
    @FXML
    private Text editAirportCityError;
    @FXML
    private Text editAirportLongError;
    @FXML
    private Text editAirportLatError;
    @FXML
    private Text editAirportAltError;
    /**
     * Initialising error messages in the Route edit view

     */

    @FXML
    private Text editRouteSourceError;
    @FXML
    private Text editRouteDestError;
    @FXML
    private Text editRouteStopsError;
    @FXML
    private Text editRouteEquipError;

    public static ObservableList<Airline> getCurrentlyLoadedAirlines() {
        return currentlyLoadedAirlines;
    }

    public static void setCurrentlyLoadedAirlines(ObservableList<Airline> currentlyLoadedAirlines) {
        MainController.currentlyLoadedAirlines = currentlyLoadedAirlines;
    }

    public static void addToCurrentlyLoadedAirlines(Airline airline) {
        MainController.currentlyLoadedAirlines.add(airline);
    }

    public static ObservableList<Airport> getCurrentlyLoadedAirports() {
        return currentlyLoadedAirports;
    }

    public static void setCurrentlyLoadedAirports(ObservableList<Airport> currentlyLoadedAirports) {
        MainController.currentlyLoadedAirports = currentlyLoadedAirports;
    }

    public static void addToCurrentlyLoadedAirports(Airport airport) {
        MainController.currentlyLoadedAirports.add(airport);
    }

    public static ObservableList<Route> getCurrentlyLoadedRoutes() {
        return currentlyLoadedRoutes;
    }

    public static void setCurrentlyLoadedRoutes(ObservableList<Route> currentlyLoadedRoutes) {
        MainController.currentlyLoadedRoutes = currentlyLoadedRoutes;
    }

    public static void addToCurrentlyLoadedRoutes(Route route) {
        MainController.currentlyLoadedRoutes.add(route);
    }

    public int getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(int routeIds) {
        this.routeIds = routeIds;
    }

    public void putInRouteHashMap(Route route) {
        this.routeHashMap.put(this.getRouteIds(), route);
    }

    /* Method to open up a file chooser for the user to select the Airport Data file  with error handling*/
    public void addAirportData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airport datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airport data file
                //ObservableList<Airport> airports = load.buildAirports();
                //Create the database objects and connections here
                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                AirportDatabaseLoader adl = new AirportDatabaseLoader();
                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();
                //Save all of the loaded files to the database here
                dbsave.saveAirports(connOne, load.buildAirports());
                dbOne.disconnect(connOne);
                //Load all of the content from the database here, and add all airports to the hashmap.
                ObservableList<Airport> airports = adl.loadAirport(connTwo, airportHashMap);
                dbTwo.disconnect(connTwo);
                currentlyLoadedAirports = airports;
                airportTable.setItems(airports);
                setAirportComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }

    }

    public void setAirportComboBoxes() {

        HashSet<String> countries = new HashSet<String>();
        HashSet<String> names = new HashSet<String>();

        for (int i = 0; i < currentlyLoadedAirports.size(); i++) {
            countries.add(currentlyLoadedAirports.get(i).getCountry());
            names.add(currentlyLoadedAirports.get(i).getName());
        }
        List sortedCountries = new ArrayList(countries);
        List sortedNames = new ArrayList(names);

        Collections.sort(sortedNames);
        Collections.sort(sortedCountries);
        sortedCountries.add(0, "ALL COUNTRIES");
        airportCountrySearch.getItems().clear();
        airportCountrySearch.getItems().setAll(sortedCountries);


        addRouteViewController.addedRouteSource.getItems().clear();
        addRouteViewController.addedRouteSource.getItems().addAll(sortedNames);
        addRouteViewController.addedRouteDestination.getItems().clear();
        addRouteViewController.addedRouteDestination.getItems().addAll(sortedNames);
    }

    /* Method to open up a file chooser for the user to select the Airline Data file with error handling */
    public void addAirlineData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airline datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airline data file
                //ObservableList<Airline> airlines = load.buildAirlines();

                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                AirlineDatabaseLoader adl = new AirlineDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();

                dbsave.saveAirlines(connOne, load.buildAirlines());
                dbOne.disconnect(connOne);

                ObservableList<Airline> airlines = adl.loadAirlines(connTwo, airlineHashMap);
                dbTwo.disconnect(connTwo);

                currentlyLoadedAirlines = airlines;
                airlineTable.setItems(airlines);
                setAirlineComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }

    }

    public void setAirlineComboBoxes() {
        ArrayList<String> activeStatuses = new ArrayList<String>();
        activeStatuses.add("ACTIVE OR INACTIVE");
        activeStatuses.add("Active");
        activeStatuses.add("Inactive");
        airlineActiveSearch.getItems().clear();
        airlineActiveSearch.getItems().setAll(activeStatuses);

        HashSet<String> names = new HashSet<String>();
        HashSet<String> countries = new HashSet<String>();
        for (int i = 0; i < currentlyLoadedAirlines.size(); i++) {
            countries.add(currentlyLoadedAirlines.get(i).getCountry());
            names.add(currentlyLoadedAirlines.get(i).getName());
        }

        List sortedCountries = new ArrayList(countries);
        List sortedAirlines = new ArrayList(names);

        Collections.sort(sortedAirlines);
        Collections.sort(sortedCountries);

        sortedCountries.add(0, "ALL COUNTRIES");

        airlineCountrySearch.getItems().clear();
        airlineCountrySearch.getItems().setAll(sortedCountries);

        addRouteViewController.addedRouteAirline.getItems().clear();
        addRouteViewController.addedRouteAirline.getItems().addAll(sortedAirlines);
    }

    /* Method to open up a file chooser for the user to select the Route Data file with error handling */
    public void addRouteData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Route datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the route data file
                //ObservableList<Route> routes = load.buildRoutes();

                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                RouteDatabaseLoader rdl = new RouteDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();

                if (routeIds == 0) {
                    routeIds = dbsave.saveRoutes(connOne, load.buildRoutes());
                } else {
                    routeIds = dbsave.saveRouteWithID(connOne, load.buildRoutes(), routeIds);
                }
                dbOne.disconnect(connOne);
                ObservableList<Route> routes = rdl.loadRoutes(connTwo, routeHashMap, airlineHashMap, airportHashMap);
                dbTwo.disconnect(connTwo);

                currentlyLoadedRoutes = routes;
                routeTable.setItems(routes);
                setRouteComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }

    }

    public void setRouteComboBoxes() {
        ArrayList<String> codeshareStatuses = new ArrayList<String>();
        codeshareStatuses.add("ALL");
        codeshareStatuses.add("Codeshare");
        codeshareStatuses.add("Non Codeshare");
        codeshareSearch.getItems().clear();
        codeshareSearch.getItems().setAll(codeshareStatuses);

        HashSet<String> sources = new HashSet<String>();
        HashSet<String> destinations = new HashSet<String>();
        HashSet<String> equipment = new HashSet<String>();

        int stops = 0;
        for (int i = 0; i < currentlyLoadedRoutes.size(); i++) {
            if (currentlyLoadedRoutes.get(i).getSourceAirport().getIATA() != null) {
                sources.add(currentlyLoadedRoutes.get(i).getSourceAirport().getIATA());
            } else {
                sources.add(currentlyLoadedRoutes.get(i).getSourceAirport().getICAO());
            }
            if (currentlyLoadedRoutes.get(i).getDestinationAirport().getIATA() != null) {
                destinations.add(currentlyLoadedRoutes.get(i).getDestinationAirport().getIATA());
            } else {
                destinations.add(currentlyLoadedRoutes.get(i).getDestinationAirport().getICAO());
            }
            equipment.add(currentlyLoadedRoutes.get(i).getEquipment());
            if (currentlyLoadedRoutes.get(i).getStops() > stops) {
                stops = currentlyLoadedRoutes.get(i).getStops();
            }
        }

        ArrayList<String> stopsList = new ArrayList<String>();
        stopsList.add("ALL");

        for (int i = 0; i <= stops; i++) {
            stopsList.add(Integer.toString(i));
        }

        List sortedSources = new ArrayList(sources);
        List sortedDestinations = new ArrayList(destinations);
        List sortedEquipment = new ArrayList(equipment);

        Collections.sort(sortedSources);
        Collections.sort(sortedDestinations);
        Collections.sort(sortedEquipment);


        sortedSources.add(0, "ALL");
        sortedDestinations.add(0, "ALL");
        sortedEquipment.add(0, "ALL");

        sourceSearch.getItems().clear();
        sourceSearch.getItems().setAll(sortedSources);
        destinationSearch.getItems().clear();
        destinationSearch.getItems().setAll(sortedDestinations);
        equipmentSearch.getItems().clear();
        equipmentSearch.getItems().setAll(sortedEquipment);
        stopoverSearch.getItems().clear();
        stopoverSearch.getItems().setAll(stopsList);
    }

    @FXML
    /* Method to open up a file chooser for the user to select the Flight Data file  with error handling*/
    public void addFlightData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open flight datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                Flight flight = load.buildFlight();
                flightViewController.setUpFlightView(flight);
                //Swap panes from raw data to the flight viewer
                resetView();

                if (flightViewController.getIsValid()) {
                    flightViewController.makeVisible();
                    flightViewController.isValid = false;

                } else {
                    backToTableView(e);
                    JOptionPane jp = new JOptionPane();
                    jp.setSize(600, 600);
                    jp.showMessageDialog(null, "Data you are trying to add is invalid", "Error Message", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }
    }

    @FXML
    private void resetRouteSearch() {
        /* Clears all search fields*/
        airlineSearch.clear();
        sourceSearch.setValue(null);
        destinationSearch.setValue(null);
        stopoverSearch.setValue(null);
        equipmentSearch.setValue(null);
        codeshareSearch.setValue(null);
        airlineSearchID.clear();
        sourceIDSearch.clear();
        destinationIDSearch.clear();

        routeTable.setItems(currentlyLoadedRoutes);
    }

    @FXML
    private void resetAirportSearch(){
        /* Clears all search fields*/
        airportCountrySearch.setValue(null);
        airportIDSearch.clear();
        airportNameSearch.clear();
        airportCitySearch.clear();
        airportFAASearch.clear();
        airportIATASearch.clear();
        airportICAOSearch.clear();
        airportLongitudeSearch.clear();
        airportLatitudeSearch.clear();
        airportAltitudeSearch.clear();
        airportTimezoneSearch.clear();
        airportDSTSearch.clear();

        airportTable.setItems(currentlyLoadedAirports);
    }

    @FXML
    private void resetAirlineSearch(){
        /* Clears all search fields*/
        airlineCountrySearch.setValue(null);
        airlineActiveSearch.setValue(null);
        airlineIDSearch.clear();
        airlineNameSearch.clear();
        airlineAliasSearch.clear();
        airlineIATASearch.clear();
        airlineICAOSearch.clear();
        airlineCallsignSearch.clear();

        airlineTable.setItems(currentlyLoadedAirlines);
    }

    @FXML
    private void airportSearch(ActionEvent e) {
        resetView();
        tableView.setVisible(true);
        airportTable.setVisible(true);
        airportPane.setVisible(false);
        AirportSearcher searcher = new AirportSearcher(currentlyLoadedAirports);
        String airportID = airportIDSearch.getText();
        String name = airportNameSearch.getText();
        String city = airportCitySearch.getText();
        String country = (String) airportCountrySearch.getValue();
        String FAA = airportFAASearch.getText();
        String IATA = airportIATASearch.getText();
        String ICAO = airportICAOSearch.getText();
        String latitude = airportLatitudeSearch.getText();
        String longitude = airportLongitudeSearch.getText();
        String altitude = airportAltitudeSearch.getText();
        String timezone = airportTimezoneSearch.getText();
        String DST = airportDSTSearch.getText();

        if (airportID.length() > 0) {
            searcher.airportsOfID(airportID);
        }

        if (name.length() > 0) {
            searcher.airportsOfName(name);
        }

        if (city.length() > 0) {
            searcher.airportsOfCity(city);
        }

        if (country != null && !country.equals("ALL COUNTRIES")) {
            searcher.airportsOfCountry(country);
        }

        if (FAA.length() > 0) {
            searcher.airportsOfFAA(FAA);
        }

        if (IATA.length() > 0) {
            searcher.airportsOfIATA(IATA);
        }

        if (ICAO.length() > 0) {
            searcher.airportsOfICAO(ICAO);
        }

        if (latitude.length() > 0) {
            searcher.airportsOfLatitude(latitude);
        }

        if (longitude.length() > 0) {
            searcher.airportsOfLongitude(longitude);
        }

        if (altitude.length() > 0) {
            searcher.airportsOfAltitude(altitude);
        }

        if (timezone.length() > 0) {
            searcher.airportsOfTimezone(timezone);
        }

        if (DST.length() > 0) {
            searcher.airportsOfDST(DST);
        }

        ObservableList<Airport> matchingAirports = searcher.getLoadedAirports();
        airportTable.setItems(matchingAirports);
    }

    @FXML
    private void airlineSearch(ActionEvent e) {
        resetView();
        tableView.setVisible(true);
        airlineTable.setVisible(true);
        editAirlineViewController.makeInvisible();
        AirlineSearcher searcher = new AirlineSearcher(currentlyLoadedAirlines);

        String airlineID = airlineIDSearch.getText();
        String name = airlineNameSearch.getText();
        String alias = airlineAliasSearch.getText();
        String IATA = airlineIATASearch.getText();
        String ICAO = airlineICAOSearch.getText();
        String callsign = airlineCallsignSearch.getText();
        String country = (String) airlineCountrySearch.getValue();
        String activeStatus = (String) airlineActiveSearch.getValue();

        if (airlineID.length() > 0) {
            searcher.airlinesOfID(airlineID);
        }

        if (name.length() > 0) {
            searcher.airlinesOfName(name);
        }

        if (alias.length() > 0) {
            searcher.airlinesOfAlias(alias);
        }

        if (IATA.length() > 0) {
            searcher.airlinesOfIATA(IATA);
        }

        if (ICAO.length() > 0) {
            searcher.airlinesOfICAO(ICAO);
        }

        if (callsign.length() > 0) {
            searcher.airlinesOfCallsign(callsign);
        }

        if (country != null && !country.equals("ALL COUNTRIES")) {
            searcher.airlinesOfCountry(country);
        }

        if (activeStatus != null && !activeStatus.equals("ACTIVE OR INACTIVE")) {
            searcher.airlinesOfActiveStatus(activeStatus);
        }


        ObservableList<Airline> matchingAirlines = searcher.getLoadedAirlines();
        airlineTable.setItems(matchingAirlines);
    }

    @FXML
    private void routeSearch(ActionEvent e) {
        resetView();
        tableView.setVisible(true);
        routeTable.setVisible(true);
        routePane.setVisible(false);


        RouteSearcher searcher = new RouteSearcher(currentlyLoadedRoutes);
        String airline = airlineSearch.getText();
        String airlineID = airlineSearchID.getText();
        String sourceAirport = (String) sourceSearch.getValue();
        String sourceID = sourceIDSearch.getText();
        String destinationAirport = (String) destinationSearch.getValue();
        String destinationID = destinationIDSearch.getText();
        String stops = (String) stopoverSearch.getValue();
        String codeshareStatus = (String) codeshareSearch.getValue();
        String equipment = (String) equipmentSearch.getValue();

        if (airline.length() > 0) {
            searcher.routesOfAirline(airline);
        }

        if (airlineID.length() > 0) {
            try {
                int intAirlineID = Integer.parseInt(airlineID);
                searcher.routesOfAirlineID(intAirlineID);
            } catch (NumberFormatException exception) {
            }
        }

        if (sourceAirport != null && !sourceAirport.equals("ALL")) {
            searcher.routesOfSource(sourceAirport);
        }

        if (sourceID.length() > 0) {
            try {
                int intSourceID = Integer.parseInt(sourceID);
                searcher.routesOfSourceID(intSourceID);
            } catch (NumberFormatException exception) {
            }
        }

        if (destinationAirport != null && !destinationAirport.equals("ALL")) {
            searcher.routesOfDestination(destinationAirport);
        }

        if (destinationID.length() > 0) {
            try {
                int intDestID = Integer.parseInt(destinationID);
                searcher.routesOfDestinationID(intDestID);
            } catch (NumberFormatException exception) {
            }
        }

        if (stops != null && !stops.equals("ALL")) {
            try {
                int intStops = Integer.parseInt(stops);
                searcher.routesOfStops(intStops);
            } catch (NumberFormatException exception) {
            }
        }

        if (codeshareStatus != null && !codeshareStatus.equals("ALL")) {
            searcher.routesOfCodeshare(codeshareStatus);
        }

        if (equipment != null && !equipment.equals("ALL")) {
            searcher.routesOfEquipment(equipment);
        }

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();

        //This is a patch for deliverable two, needs to be implemented propelry in the loader class at some point

        for (Route route: matchingRoutes) {
            route.setAirlineName(route.getAirline().getName());
            route.setSourceAirportName(route.getSourceAirport().getIATA());
            route.setDestinationAirportName(route.getDestinationAirport().getIATA());
        }

        routeTable.setItems(matchingRoutes);
    }

    /* Method to add a new airline to the currentlyLoadedAirlines from search text fields.
    Executed when the add button is clicked */
    @FXML
    private void switchToAddAirport(ActionEvent e) {
        resetView();
        addAirportViewController.makeVisible();
    }



    //
    @FXML
    private void switchToAddAirline(ActionEvent e) {
        resetView();
        addAirlineViewController.makeVisible();
    }


    @FXML
    private void switchToAddRoute(ActionEvent e) {
        resetView();
        addRouteViewController.makeVisible();
    }



    @FXML
    private void airportSearchBack() {
        airportAdvancedButton.setVisible(true);
        airportBackButton.setVisible(false);
        airportCitySearch.setVisible(false);
        airportFAASearch.setVisible(false);
        airportIATASearch.setVisible(false);
        airportLongitudeSearch.setVisible(false);
        airportLatitudeSearch.setVisible(false);
        airportAltitudeSearch.setVisible(false);
        airportTimezoneSearch.setVisible(false);
        airportDSTSearch.setVisible(false);
        airportICAOSearch.setVisible(false);
        airportSearchButton.setLayoutY(250);
        resetAirportSearch.setLayoutY(250);
        airportAddButton.setVisible(true);
        /*Clear hidden parameters*/
        airportCitySearch.clear();
        airportFAASearch.clear();
        airportIATASearch.clear();
        airportICAOSearch.clear();
        airportLongitudeSearch.clear();
        airportLatitudeSearch.clear();
        airportAltitudeSearch.clear();
        airportTimezoneSearch.clear();
        airportDSTSearch.clear();
    }

    @FXML
    private void showAirportSearch(ActionEvent e) {
        airportAdvancedButton.setVisible(false);
        airportBackButton.setVisible(true);
        airportCitySearch.setVisible(true);
        airportFAASearch.setVisible(true);
        airportIATASearch.setVisible(true);
        airportLongitudeSearch.setVisible(true);
        airportLatitudeSearch.setVisible(true);
        airportAltitudeSearch.setVisible(true);
        airportTimezoneSearch.setVisible(true);
        airportDSTSearch.setVisible(true);
        airportICAOSearch.setVisible(true);

        airportSearchButton.setLayoutY(570);
        resetAirportSearch.setLayoutY(570);
        airportAddButton.setVisible(false);
    }

    @FXML
    private void routeSearchBack() {
        routeAdvancedButton.setVisible(true);
        routeBackButton.setVisible(false);
        equipmentSearch.setVisible(false);
        codeshareSearch.setVisible(false);
        airlineSearchID.setVisible(false);
        destinationIDSearch.setVisible(false);
        sourceIDSearch.setVisible(false);
        routeSearch.setLayoutY(250);
        resetRouteSearch.setLayoutY(250);
        routeAddButton.setVisible(true);
        /*Clear hidden parameters*/
        equipmentSearch.setValue(null);
        codeshareSearch.setValue(null);
        airlineSearchID.clear();
        sourceIDSearch.clear();
        destinationIDSearch.clear();
    }

    @FXML
    private void showRouteSearch(ActionEvent e) {
        routeAdvancedButton.setVisible(false);
        routeBackButton.setVisible(true);
        equipmentSearch.setVisible(true);
        codeshareSearch.setVisible(true);
        airlineSearchID.setVisible(true);
        destinationIDSearch.setVisible(true);
        sourceIDSearch.setVisible(true);
        routeSearch.setLayoutY(455);
        resetRouteSearch.setLayoutY(455);
        routeAddButton.setVisible(false);
    }

    @FXML
    private void showAirlineSearch(ActionEvent e) {
        airlineAdvancedButton.setVisible(false);
        airlineBackButton.setVisible(true);
        airlineAliasSearch.setVisible(true);
        airlineIATASearch.setVisible(true);
        airlineICAOSearch.setVisible(true);
        airlineCallsignSearch.setVisible(true);
        airlineSearchButton.setLayoutY(415);
        resetAirlineSearch.setLayoutY(415);
        airlineAddButton.setVisible(false);
    }

    @FXML
    private void airlineSearchBack() {
        airlineAdvancedButton.setVisible(true);
        airlineBackButton.setVisible(false);
        airlineAliasSearch.setVisible(false);
        airlineIATASearch.setVisible(false);
        airlineSearchID.setVisible(false);
        airlineICAOSearch.setVisible(false);
        airlineCallsignSearch.setVisible(false);
        airlineSearchButton.setLayoutY(250);
        resetAirlineSearch.setLayoutY(250);
        airlineAddButton.setVisible(true);
        /*Clear hidden parameters*/
        airlineAliasSearch.clear();
        airlineIATASearch.clear();
        airlineICAOSearch.clear();
        airlineCallsignSearch.clear();
    }

    private void filterAirlinesByName(ActionEvent e) {

        Comparator<Airline> airlineNameComparator = new Comparator<Airline>() {
            public int compare(Airline o1, Airline o2) {
                String airline1 = o1.getName();
                String airline2 = o2.getName();

                return airline1.compareTo(airline2);
            }
        };
        ArrayList<Airline> sortedAirlines = new ArrayList<Airline>();
        Collections.sort(currentlyLoadedAirlines, airlineNameComparator);
        for (Airline str : currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded airlines by country
    Need to display an error message if airlines aren't yet loaded
     */
    @FXML
    private void FilterAirlinesByCountry(ActionEvent e) {

        Comparator<Airline> airlineCountryComparator = new Comparator<Airline>() {
            public int compare(Airline o1, Airline o2) {
                String airline1 = o1.getCountry();
                String airline2 = o2.getCountry();

                return airline1.compareTo(airline2);
            }
        };
        ArrayList<Airline> sortedAirlines = new ArrayList<Airline>();
        Collections.sort(currentlyLoadedAirlines, airlineCountryComparator);
        for (Airline str : currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded airlines by activity
    Need to display an error message if airlines aren't yet loaded
    */
    @FXML
    private void FilterAirlinesByActivity(ActionEvent e) {

        Comparator<Airline> airlineActivityComparator = new Comparator<Airline>() {
            public int compare(Airline o1, Airline o2) {
                boolean airline1 = o1.isActive();
                boolean airline2 = o2.isActive();

                if (airline1 == !airline2) {
                    return 1;
                }
                if (!airline1 == airline2) {
                    return -1;
                }
                return 0;
            }
        };
        ArrayList<Airline> sortedAirlines = new ArrayList<Airline>();
        Collections.sort(currentlyLoadedAirlines, airlineActivityComparator);
        for (Airline str : currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded airports by country
    Need to display an error message if airports aren't yet loaded
     */
    @FXML
    private void FilterAirportsByCountry(ActionEvent e) {

        Comparator<Airport> airportCountryComparator = new Comparator<Airport>() {
            public int compare(Airport o1, Airport o2) {
                String airport1 = o1.getCountry();
                String airport2 = o2.getCountry();

                return airport1.compareTo(airport2);
            }
        };
        ArrayList<Airport> sortedAirports = new ArrayList<Airport>();
        Collections.sort(currentlyLoadedAirports, airportCountryComparator);
        for (Airport str : currentlyLoadedAirports) {
            sortedAirports.add(str);
        }
        ObservableList<Airport> sortedObservableAirports = FXCollections.observableArrayList(sortedAirports);
        airportTable.setItems(sortedObservableAirports);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by Source Airport
    Need to display an error message if routes aren't yet loaded
     */
    @FXML
    private void FilterRoutesBySourceAirport(ActionEvent e) {

        Comparator<Route> sourceAirportComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                String route1 = o1.getSourceAirportName();
                String route2 = o2.getSourceAirportName();

                return route1.compareTo(route2);
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, sourceAirportComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by Destination Airport
    Need to display an error message if routes aren't yet loaded
     */
    @FXML
    private void FilterRoutesByDestinationAirport(ActionEvent e) {

        Comparator<Route> destinationAirportComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                String route1 = o1.getDestinationAirportName();
                String route2 = o2.getDestinationAirportName();

                return route1.compareTo(route2);
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, destinationAirportComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by number of stops
   Need to display an error message if routes aren't yet loaded
    */
    @FXML
    private void FilterRoutesByStops(ActionEvent e) {

        Comparator<Route> stopsComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                int route1 = o1.getStops();
                int route2 = o2.getStops();

                if (route1 < route2) {
                    return -1;
                } else if (route1 > route2) {
                    return 1;
                }
                return 0;
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, stopsComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by equipment
    Need to display an error message if routes aren't yet loaded
     */
    @FXML
    private void FilterRoutesByEquipment(ActionEvent e) {

        Comparator<Route> equipmentComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                String route1 = o1.getEquipment();
                String route2 = o2.getEquipment();

                return route1.compareTo(route2);
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, equipmentComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }



    @FXML
    public void editAirportData(ActionEvent e) {
        Airport currentAirport = airportTable.getSelectionModel().getSelectedItem();
        editFAAField.setVisible(true);
        editAirportIATAField.setVisible(true);
        editAirportICAOField.setVisible(true);
        editTimezoneField.setVisible(true);
        editDSTField.setVisible(true);
        editAirportCountryField.setVisible(true);
        editAirportCityField.setVisible(true);
        editLatitudeField.setVisible(true);
        editLongitudeField.setVisible(true);
        editAltitudeField.setVisible(true);

        individualAirportBackButton.setVisible(false);
        editAirportDataButton.setVisible(false);

        saveAirportChangesButton.setVisible(true);
        cancelAirportChangesButton.setVisible(true);

        editAirportCountryField.setText(currentAirport.getCountry());
        if (currentAirport.getFAA() != null) {
            editFAAField.setText(currentAirport.getFAA());
        } else {
            editFAAField.setText("None");
        }
        if (currentAirport.getIATA() != null) {
            editAirportIATAField.setText(currentAirport.getIATA());
        } else {
            editAirportIATAField.setText("None");
        }
        if (currentAirport.getICAO() != null) {
            editAirportICAOField.setText(currentAirport.getICAO());
        } else {
            editAirportICAOField.setText("None");
        }
        editTimezoneField.setText(Integer.toString(currentAirport.getTimezone()));
        editDSTField.setText(Character.toString(currentAirport.getDST()));
        editAirportCountryField.setText(currentAirport.getCountry());
        editAirportCityField.setText(currentAirport.getCity());
        editLatitudeField.setText(Double.toString(currentAirport.getLatitude()));
        editLongitudeField.setText(Double.toString(currentAirport.getLongitude()));
        editAltitudeField.setText(Double.toString(currentAirport.getAltitude()));
    }

    @FXML
    public void cancelAirportChanges(ActionEvent e) {
        clearEditAirportErrors();

        individualAirportBackButton.setVisible(true);
        editAirportDataButton.setVisible(true);
        saveAirportChangesButton.setVisible(false);
        cancelAirportChangesButton.setVisible(false);
        editFAAField.setVisible(false);
        editAirportIATAField.setVisible(false);
        editAirportICAOField.setVisible(false);
        editTimezoneField.setVisible(false);
        editDSTField.setVisible(false);
        editAirportCountryField.setVisible(false);
        editAirportCityField.setVisible(false);
        editLatitudeField.setVisible(false);
        editLongitudeField.setVisible(false);
        editAltitudeField.setVisible(false);
    }

    @FXML
    public void clearEditAirportErrors() {
        editAirportFAAError.setVisible(false);
        editAirportIATAError.setVisible(false);
        editAirportICAOError.setVisible(false);
        editAirportTimeError.setVisible(false);
        editAirportDSTError.setVisible(false);
        editAirportCountryError.setVisible(false);
        editAirportCityError.setVisible(false);
        editAirportLongError.setVisible(false);
        editAirportLatError.setVisible(false);
        editAirportAltError.setVisible(false);

    }
    @FXML
    private boolean editAirportErrors(List<String> input){
        boolean filled = false;
        int size = input.size();
        List<Integer> doubles = Arrays.asList(7,8,9);

        int count = 0;
        for (int i = 0; i < size; i++) {
            String current = input.get(i);
            if (current.equals("")) {
                switch (i) {

                    case 0:
                        editAirportFAAError.setVisible(true);
                        break;
                    case 1:
                        editAirportIATAError.setVisible(true);
                        break;
                    case 2:
                        editAirportICAOError.setVisible(true);
                        break;
                    case 3:
                        editAirportTimeError.setVisible(true);
                        break;
                    case 4:
                        editAirportDSTError.setVisible(true);
                        break;
                    case 5:
                        editAirportCountryError.setVisible(true);
                        break;
                    case 6:
                        editAirportCityError.setVisible(true);
                        break;
                    case 7:
                        editAirportLongError.setVisible(true);
                        break;
                    case 8:
                        editAirportLatError.setVisible(true);
                        break;
                    case 9:
                        editAirportAltError.setVisible(true);
                        break;
                }
            } else {
                count += 1;
            }

            if (i == 3 && !(current.equals(""))) {

                try {
                    Integer.parseInt(input.get(i));
                    count += 1;

                } catch (NumberFormatException e) {
                    editAirportTimeError.setVisible(true);
                    break;

                }
            } else if (doubles.contains(i) && !(current.equals(""))) {
                try {
                    Double.parseDouble(input.get(i));

                    count += 1;
                } catch (NumberFormatException e) {

                    switch (i) {
                        case 7:
                            editAirportLongError.setVisible(true);
                            break;
                        case 8:
                            editAirportLatError.setVisible(true);
                            break;
                        case 9:
                            editAirportAltError.setVisible(true);
                            break;

                    }
                }
            }

        }

        if (count == 14) {
            filled = true;
        }
        return filled;

    }

    @FXML
    public void saveAirportChanges(ActionEvent e) {
        clearEditAirportErrors();
        Airport currentAirport = airportTable.getSelectionModel().getSelectedItem();

        //Delete the airport to be changed from the database
        Database db = new Database();
        DatabaseSaver dbSave = new DatabaseSaver();
        Connection connDelete = db.connect();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(currentAirport.getAirportID());
        dbSave.deleteAirport(connDelete, ids);
        db.disconnect(connDelete);


        String FAA = editFAAField.getText();
        String IATA = editAirportIATAField.getText();
        String ICAO = editAirportICAOField.getText();
        String timezone = editTimezoneField.getText();
        String DST = editDSTField.getText();
        String country = editAirportCountryField.getText();
        String city = editAirportCityField.getText();
        String longitude = editLongitudeField.getText();
        String latitude = editLatitudeField.getText();
        String altitude = editAltitudeField.getText();

        List<String> airportData = Arrays.asList(FAA, IATA, ICAO, timezone, DST, country, city, longitude, latitude, altitude);

        boolean noErrors = editAirportErrors(airportData);

        if(noErrors) {


            if (!editFAAField.getText().equals("None")) {
                currentAirport.setFAA(FAA);
            }
            if (!editAirportIATAField.getText().equals("None")) {
                currentAirport.setIATA(IATA);
            }
            if (!editAirportICAOField.getText().equals("None")) {
                currentAirport.setICAO(ICAO);
            }

            currentAirport.setTimezone(Integer.parseInt(timezone));
            currentAirport.setDST(DST.charAt(0));
            currentAirport.setCountry(country);
            currentAirport.setCity(city);
            currentAirport.setLongitude(Double.parseDouble(longitude));
            currentAirport.setLatitude(Double.parseDouble(latitude));
            currentAirport.setAltitude(Double.parseDouble(altitude));

            airportIDDisplay.setText(Integer.toString(currentAirport.getAirportID()));
            airportFAADisplay.setText(currentAirport.getFAA());
            airportIATADisplay.setText(currentAirport.getIATA());
            airportICAODisplay.setText(currentAirport.getICAO());
            airportTimezoneDisplay.setText(Integer.toString(currentAirport.getTimezone()));
            airportDSTDisplay.setText(Character.toString(currentAirport.getDST()));
            airportCountryDisplay.setText(currentAirport.getCountry());
            airportCityDisplay.setText(currentAirport.getCity());
            airportLongitudeDisplay.setText(Double.toString(currentAirport.getLongitude()));
            airportLatitudeDisplay.setText(Double.toString(currentAirport.getLatitude()));
            airportAltitudeDisplay.setText(Double.toString(currentAirport.getAltitude()));


            editFAAField.setVisible(false);
            editAirportIATAField.setVisible(false);
            editAirportICAOField.setVisible(false);
            editTimezoneField.setVisible(false);
            editDSTField.setVisible(false);
            editAirportCountryField.setVisible(false);
            editAirportCityField.setVisible(false);
            editLatitudeField.setVisible(false);
            editLongitudeField.setVisible(false);
            editAltitudeField.setVisible(false);


            individualAirportBackButton.setVisible(true);
            editAirportDataButton.setVisible(true);

            saveAirportChangesButton.setVisible(false);
            cancelAirportChangesButton.setVisible(false);

            //Save the updated airline to the database
            Connection connSave = db.connect();
            ObservableList<Airport> newAirports = FXCollections.observableArrayList();
            newAirports.add(currentAirport);
            dbSave.saveAirports(connSave, newAirports);
            db.disconnect(connDelete);

            setAirportComboBoxes();
        }
    }

    @FXML
    public void editRouteData(ActionEvent e) {
        Route currentRoute = routeTable.getSelectionModel().getSelectedItem();
        editSourceField.setVisible(true);
        editDestinationField.setVisible(true);
        editStopsField.setVisible(true);
        editEquipmentField.setVisible(true);
        editCodeshareField.setVisible(true);
        routeShareDisplay.setVisible(false);

        individualRouteBackButton.setVisible(false);
        editRouteDataButton.setVisible(false);

        saveRouteChangesButton.setVisible(true);
        cancelRouteChangesButton.setVisible(true);

        editSourceField.setText(currentRoute.getSourceAirportName());
        editDestinationField.setText(currentRoute.getDestinationAirportName());
        editStopsField.setText(Integer.toString(currentRoute.getStops()));
        if (currentRoute.getEquipment() != null) {
            editEquipmentField.setText(currentRoute.getEquipment());
        } else {
            editEquipmentField.setText("None");
        }
        if (currentRoute.isCodeshare()) {
            editCodeshareField.setSelected(true);
        } else {
            editCodeshareField.setSelected(false);
        }
    }

    @FXML
    public void cancelRouteChanges(ActionEvent e) {
        clearEditRouteErrors();
        individualRouteBackButton.setVisible(true);
        editRouteDataButton.setVisible(true);

        saveRouteChangesButton.setVisible(false);
        cancelRouteChangesButton.setVisible(false);
        editSourceField.setVisible(false);
        editDestinationField.setVisible(false);
        editStopsField.setVisible(false);
        editEquipmentField.setVisible(false);
        editCodeshareField.setVisible(false);
        routeShareDisplay.setVisible(true);
    }

    @FXML
    private boolean editRouteErrors(List<String> input) {

        boolean filled = false;
        int size = input.size();


        int count = 0;
        for (int i = 0; i < size; i++) {
            String current = input.get(i);

            if (current == null || current.equals("")) {

                switch (i) {
                    case 0:
                        editRouteSourceError.setVisible(true);
                        break;
                    case 1:
                        editRouteDestError.setVisible(true);
                        break;
                    case 2:
                        editRouteStopsError.setVisible(true);
                        break;
                    case 3:
                        editRouteEquipError.setVisible(true);
                        break;

                }
            } else {
                count += 1;
            }

            if ((i == 2) && !(current.equals(""))) {

                try {
                    Integer.parseInt(input.get(i));
                    count += 1;

                } catch (NumberFormatException e) {
                    editRouteStopsError.setVisible(true);
                }
            }
        }


        if (count == 5) {
            filled = true;
        }
        return filled;

    }

    @FXML
    private void clearEditRouteErrors() {
        editRouteSourceError.setVisible(false);
        editRouteDestError.setVisible(false);
        editRouteStopsError.setVisible(false);
        editRouteEquipError.setVisible(false);

    }

    @FXML
    public void saveRouteChanges(ActionEvent e) {
        clearEditRouteErrors();
        Route currentRoute = routeTable.getSelectionModel().getSelectedItem();

        String source = editSourceField.getText();
        String destination = editDestinationField.getText();
        String stops = editStopsField.getText();
        String equipment = editEquipmentField.getText();

        List<String> routeData = Arrays.asList(source, destination, stops, equipment);
        boolean noErrors = editRouteErrors(routeData);

        if(noErrors) {
            //Delete the route from the database
            Database db = new Database();
            DatabaseSaver dbSave = new DatabaseSaver();
            Connection connDelete = db.connect();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(currentRoute.getRouteID());
            dbSave.deleteRoutes(connDelete, ids);
            db.disconnect(connDelete);

            currentRoute.setSourceAirportName(source);
            currentRoute.setDestinationAirportName(destination);
            //Add methods to find airports as well
            currentRoute.setStops(Integer.parseInt(stops));

            if (!editEquipmentField.getText().equals("None")) {
                currentRoute.setEquipment(equipment);
            }
            if (editCodeshareField.isSelected()) {
                currentRoute.setCodeshare(true);
                routeShareDisplay.setText("Yes");
            } else {
                currentRoute.setCodeshare(false);
                routeShareDisplay.setText("No");
            }


            routeSourceDisplay.setText(currentRoute.getSourceAirportName());
            routeDestinationDisplay.setText(currentRoute.getDestinationAirportName());
            routeStopsDisplay.setText(Integer.toString(currentRoute.getStops()));
            routeEquipmentDisplay.setText(currentRoute.getEquipment());

            editSourceField.setVisible(false);
            editDestinationField.setVisible(false);
            editStopsField.setVisible(false);
            editEquipmentField.setVisible(false);
            editCodeshareField.setVisible(false);
            routeShareDisplay.setVisible(true);

            individualRouteBackButton.setVisible(true);
            editRouteDataButton.setVisible(true);

            saveRouteChangesButton.setVisible(false);
            cancelRouteChangesButton.setVisible(false);

            //Save the updated route to the database
            Connection connSave = db.connect();
            ObservableList<Route> newRoutes = FXCollections.observableArrayList();
            newRoutes.add(currentRoute);
            dbSave.saveRoutes(connSave, newRoutes);
            db.disconnect(connDelete);

            setRouteComboBoxes();
        }
    }

    public void resetView() {
        tableView.setVisible(false);
        flightViewController.makeInvisible();
        addAirportViewController.makeInvisible();
        addAirlineViewController.makeInvisible();
        addRouteViewController.makeInvisible();
    }

    private void resetTables() {
        airportTable.getColumns().clear();
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

        airportTable.getColumns().addAll(airportName, city, airportCountry);
        airportTable.setItems(currentlyLoadedAirports);

        airlineTable.getColumns().clear();
        airlineID.setCellValueFactory(new PropertyValueFactory<Airline, String>("airlineID"));
        airlineName.setCellValueFactory(new PropertyValueFactory<Airline, String>("name"));
        alias.setCellValueFactory(new PropertyValueFactory<Airline, String>("alias"));
        IATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        ICAO.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        callsign.setCellValueFactory(new PropertyValueFactory<Airline, String>("callsign"));
        country.setCellValueFactory(new PropertyValueFactory<Airline, String>("country"));
        active.setCellValueFactory(new PropertyValueFactory<Airline, String>("active"));
        airlineTable.getColumns().addAll(airlineName, alias, country, active);
        airlineTable.setItems(currentlyLoadedAirlines);

        routeTable.getColumns().clear();

        routeAirlineName.setCellValueFactory(new PropertyValueFactory<Route, String>("airlineName"));
        source.setCellValueFactory(new PropertyValueFactory<Route, String>("sourceAirportName"));
        destination.setCellValueFactory(new PropertyValueFactory<Route, String>("destinationAirportName"));
        codeshare.setCellValueFactory(new PropertyValueFactory<Route, String>("codeshareString"));
        stops.setCellValueFactory(new PropertyValueFactory<Route, String>("stops"));
        equipment.setCellValueFactory(new PropertyValueFactory<Route, String>("equipment"));
        routeTable.getColumns().addAll(routeAirlineName, source, destination);
        routeTable.setItems(currentlyLoadedRoutes);

    }

    public void backToTableView(ActionEvent e) {
        resetTables();
        addAirportViewController.makeInvisible();
        addAirlineViewController.makeInvisible();
        addRouteViewController.makeInvisible();
        airportPane.setVisible(false);
        editAirlineViewController.makeInvisible();
        routePane.setVisible(false);
        //flightViewContent.setVisible(false);
        airportTable.setVisible(true);
        airlineTable.setVisible(true);
        routeTable.setVisible(true);
        tableView.setVisible(true);
    }

    //Sets Table Cells in Airline Table Viewer to Airline attributes
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        mapViewController.setMainController(this);
        mapViewController.initMap();

        flightViewController.setMainController(this);
        mapViewController.setMainController(this);
        addAirlineViewController.setMainController(this);
        addAirportViewController.setMainController(this);
        addRouteViewController.setMainController(this);
        editAirlineViewController.setMainController(this);


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


        // Allows individual cells to be selected as opposed to rows
        //airportTable.getSelectionModel().setCellSelectionEnabled(true);
        airportTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click) {
            //Checks if table is empty then checks for double click
            if (!airportTable.getItems().isEmpty()) {
                if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                    airportInfo();
                }
            }
            }
        });

        airlineTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click) {
            //Checks if table is empty then checks for double click
            if (!airlineTable.getItems().isEmpty()) {
                if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                    editAirlineViewController.setAirlineInfo();
                }
            }
            }
        });

        routeTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click) {
            //Checks if table is empty then checks for double click
            if (!routeTable.getItems().isEmpty()) {
                if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                    routeInfo();
                }
            }
            }
        });

        airportTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        airportTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
               @Override
               public void handle(ContextMenuEvent event) {
                   if(airportTable.getSelectionModel().getSelectedItems().size() != 2){
                       getDistanceMenu.setDisable(true);
                   }else{
                       getDistanceMenu.setDisable(false);
                   }
               }
           }
        );
    }

    public void airportInfo(){
        //Changes visible pane;
        airportTable.setVisible(false);
        airportPane.setVisible(true);
        //Sets all text to corresponding table row item.
        airportNameDisplay.setText(airportTable.getSelectionModel().getSelectedItem().getName());
        airportIDDisplay.setText(Integer.toString(airportTable.getSelectionModel().getSelectedItem().getAirportID()));
        airportFAADisplay.setText(airportTable.getSelectionModel().getSelectedItem().getFAA());
        airportIATADisplay.setText(airportTable.getSelectionModel().getSelectedItem().getIATA());
        airportICAODisplay.setText(airportTable.getSelectionModel().getSelectedItem().getICAO());
        airportDSTDisplay.setText(Integer.toString(airportTable.getSelectionModel().getSelectedItem().getDST()));
        airportTimezoneDisplay.setText(airportTable.getSelectionModel().getSelectedItem().getOlsonTimezone());
        airportCountryDisplay.setText(airportTable.getSelectionModel().getSelectedItem().getCountry());
        airportCityDisplay.setText(airportTable.getSelectionModel().getSelectedItem().getCity());
        airportLatitudeDisplay.setText(Double.toString(airportTable.getSelectionModel().getSelectedItem().getLatitude()));
        airportLongitudeDisplay.setText(Double.toString(airportTable.getSelectionModel().getSelectedItem().getLongitude()));
        airportAltitudeDisplay.setText(Double.toString(airportTable.getSelectionModel().getSelectedItem().getAltitude()));
    }


    public void routeInfo(){
        //Changes visible pane;
        routeTable.setVisible(false);
        routePane.setVisible(true);
        //Sets all text to corresponding table row item.
        routeAirlineDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getAirlineName());
        routeSourceDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getSourceAirportName());
        routeDestinationDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getDestinationAirportName());
        routeEquipmentDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getEquipment());
        routeShareDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getCodeshareString());
        routeStopsDisplay.setText(Integer.toString(routeTable.getSelectionModel().getSelectedItem().getStops()));
    }

    @FXML
    public void getDistance(ActionEvent e){
        distanceMenu.hide();
        if(airportTable.getSelectionModel().getSelectedItems().size() == 2){
            Airport airport1 = airportTable.getSelectionModel().getSelectedItems().get(0);
            Airport airport2 = airportTable.getSelectionModel().getSelectedItems().get(1);

            double distance = airport1.calculateDistanceTo(airport2);
            JOptionPane jp = new JOptionPane();
            jp.setSize(700, 600);
            jp.showMessageDialog(null, "From "+airport1.getName()+" to "+airport2.getName() + "\nThe distance is "+distance+" km.", "Calculated Distance", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     *
     * Temporary shift from mapviewcontroller so map pane can be edited in scene builder using main.fxml
     * Transfer back into mapView.fxml and uncomment comments with "MapViewController Uncomment"
     *
     * transfer all methods after this point
     */
//    @FXML
//    private CheckBox displayAllAirports;
//    @FXML
//    private CheckBox displayAllRoutes;
//    @FXML
//    private WebView webView;
//    private WebEngine webEngine;
//
//    public void initMap() {
//        webEngine = webView.getEngine();
//        webEngine.load(getClass().getClassLoader().getResource("maps.html").toExternalForm());
//
//    }
//
//    // Action Event for 'Display Airports' checkbox
//    @FXML
//    public void mapAirports(ActionEvent e) {
//        if (displayAllAirports.isSelected()) {
//            displayAirports(currentlyLoadedAirports);
//        } else {
//            clearAirports();
//        }
//    }
//
//    // Remove currently displayed airport markers
//    private void clearAirports() {
//        webEngine.executeScript("clearMarkers()");
//    }
//
//    // Display markers for list of airports
//    private void displayAirports(List airportList) {
//        if (airportList.size() < 1000 && airportList.size() != 0) {
//            showAirportMarkers(airportList);
//        } else if (airportList.isEmpty()) {
//            JOptionPane jp = new JOptionPane();
//            jp.setSize(600, 600);
//            jp.showMessageDialog(null, "No Airports to display.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
//            displayAllAirports.setSelected(false);
//        } else {
//            JOptionPane jp = new JOptionPane();
//            jp.setSize(600, 600);
//            JLabel msgLabel = new JLabel("Are you sure you want to display " +  airportList.size() + " airports? \nThis may take a while...", JLabel.CENTER);
//            int reply = jp.showConfirmDialog(null, msgLabel, "Error Message", JOptionPane.YES_NO_OPTION);
//            if (reply == JOptionPane.YES_OPTION) {
//                showAirportMarkers(airportList);
//            } else {
//                displayAllAirports.setSelected(false);
//            }
//        }
//    }
//
//
//    // Method that clears, creates and displays airport markers
//    private void showAirportMarkers(List airports) {
//        Iterator i = airports.iterator();
//        while (i.hasNext()) {
//            Airport airport = (Airport) i.next();
//            Double latitude = airport.getLatitude();
//            Double longitude = airport.getLongitude();
//            webEngine.executeScript("clearMarkers()");
//            String scriptToExecute = "createMarker(" + latitude + ',' + longitude + ");";
//            webEngine.executeScript(scriptToExecute);
//            webEngine.executeScript("showMarkers()");
//        }
//    }
//
//
//    // Action Event for 'Display Routes' checkbox
//    @FXML
//    public void mapRoutes(ActionEvent e) {
//        if (displayAllRoutes.isSelected()) {
//            displayRoutes(currentlyLoadedRoutes);
//        } else {
//            clearAirports();
//        }
//    }
//
//    // Method for displaying routes on map
//    private void displayRoutes(List routes) {
//        if (routes.size() < 1000 && routes.size() != 0) {
//            createMapRoutes(routes);
//        } else if (routes.isEmpty()) {
//            JOptionPane jp = new JOptionPane();
//            jp.setSize(600, 600);
//            jp.showMessageDialog(null, "No Routes to display.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
//            displayAllRoutes.setSelected(false);
//        } else {
//            JOptionPane jp = new JOptionPane();
//            jp.setSize(600, 600);
//            JLabel msgLabel = new JLabel("Are you sure you want to display " +  routes.size() + " routes? \nThis may take a while...", JLabel.CENTER);
//            int reply = jp.showConfirmDialog(null, msgLabel, "Error Message", JOptionPane.YES_NO_OPTION);
//            if (reply == JOptionPane.YES_OPTION) {
//                createMapRoutes(routes);
//            } else {
//                displayAllRoutes.setSelected(false);
//            }
//        }
//    }
//
//    // Method that clears, creates and displays airport markers
//    private void createMapRoutes(List routes) {
//        Iterator i = routes.iterator();
//        while (i.hasNext()) {
//            Route route = (Route) i.next();
//            // get ids of source and destination airport
//            String source = route.getSourceAirportName();
//            String dest = route.getDestinationAirportName();
//            // find airport in currentlyLoadedAirports to get coords
//            double[] sourceCoords = getCoordinates(source);
//            double[] destCoords = getCoordinates(dest);
//            String scriptToExecute = "drawRouteLine(" + sourceCoords + ", " + destCoords + ")";
//            webEngine.executeScript(scriptToExecute);
//        }
//    }
//
//    private double[] getCoordinates(String airportID) {
//        Iterator i = currentlyLoadedAirports.iterator();
//        while (i.hasNext()) {
//            Airport airport = (Airport) i.next();
//            if (airport.getICAO() == airportID || airport.getIATA() == airportID) {
//                double lat = airport.getLatitude();
//                double lng = airport.getLongitude();
//                double[] coord = {lat, lng};
//                return coord;
//            } else {
//                continue;
//            }
//        }
//        //reached end of airports list without a match
//        double[] null_ = {};
//        return null_;
//    }
}