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
import seng202.group8.Model.Parsers.AirlineParser;
import seng202.group8.Model.Parsers.AirportParser;
import seng202.group8.Model.Parsers.RouteParser;
import seng202.group8.Model.Searchers.AirlineSearcher;
import seng202.group8.Model.Searchers.AirportSearcher;
import seng202.group8.Model.Objects.*;
import seng202.group8.Model.Parsers.FileLoader;
import seng202.group8.Model.Searchers.RouteSearcher;

import javax.swing.*;
import java.io.*;

import java.net.URL;
import java.sql.Connection;
import java.util.*;



/**
 * Created by esa46 on 19/08/16.
 */

public class Controller implements Initializable{

    static ObservableList<Airline> currentlyLoadedAirlines = FXCollections.observableArrayList();
    ObservableList<Airport> currentlyLoadedAirports = FXCollections.observableArrayList();
    ObservableList<Route> currentlyLoadedRoutes = FXCollections.observableArrayList();
  //  Database mainDataBase = new Database();
    // Connection mainConn = mainDataBase.testConnect();

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
                setAirportComboBoxes();
                airportTable.setItems(airports);
                flightView.setVisible(false);
                tableView.setVisible(true);
            }
        } catch(FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    private void setAirportComboBoxes() {
        HashSet<String> countries = new HashSet<String>();
        for(int i = 0; i < currentlyLoadedAirports.size(); i++){
            countries.add(currentlyLoadedAirports.get(i).getCountry());
        }
        List sortedCountries = new ArrayList(countries);
        Collections.sort(sortedCountries);
        sortedCountries.add(0, "ALL COUNTRIES");
        airportCountrySearch.getItems().clear();
        airportCountrySearch.getItems().setAll(sortedCountries);

        //HashSet<String> timezones = new HashSet<String>();
        //airportCountryCombo.getItems().clear();
        //airportCountryCombo.getItems().setAll(countries);
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
                setAirlineComboBoxes();
                flightView.setVisible(false);
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    private void setAirlineComboBoxes(){
        ArrayList<String> activeStatuses = new ArrayList<String>();
        activeStatuses.add("ACTIVE OR INACTIVE");
        activeStatuses.add("Active");
        activeStatuses.add("Inactive");
        airlineActiveSearch.getItems().clear();
        airlineActiveSearch.getItems().setAll(activeStatuses);
        HashSet<String> countries = new HashSet<String>();
        for(int i = 0; i < currentlyLoadedAirlines.size(); i++){
            countries.add(currentlyLoadedAirlines.get(i).getCountry());
        }
        List sortedCountries = new ArrayList(countries);
        Collections.sort(sortedCountries);
        sortedCountries.add(0, "ALL COUNTRIES");
        airlineCountrySearch.getItems().clear();
        airlineCountrySearch.getItems().setAll(sortedCountries);

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
                setRouteComboBoxes();
                flightView.setVisible(false);
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    private void setRouteComboBoxes(){
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
        for(int i = 0; i < currentlyLoadedRoutes.size(); i++){
            sources.add(currentlyLoadedRoutes.get(i).getSourceAirportName());
            destinations.add(currentlyLoadedRoutes.get(i).getDestinationAirportName());
            equipment.add(currentlyLoadedRoutes.get(i).getEquipment());
            if (currentlyLoadedRoutes.get(i).getStops() > stops){
                stops = currentlyLoadedRoutes.get(i).getStops();
            }
        }

        ArrayList<String> stopsList = new ArrayList<String>();
        stopsList.add("ALL");

        for (int i = 0; i <= stops; i++){
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

    /*
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
        String country = (String) airportCountrySearch.getValue();
        String FAA = airportFAASearch.getText();
        String IATA = airportIATASearch.getText();
        String ICAO = airportICAOSearch.getText();
        String latitude = airportLatitudeSearch.getText();
        String longitude = airportLongitudeSearch.getText();
        String altitude = airportAltitudeSearch.getText();
        String timezone = airportTimezoneSearch.getText();
        String DST = airportDSTSearch.getText();

        if (airportID.length() > 0){ searcher.airportsOfID(airportID); }

        if (name.length() > 0){ searcher.airportsOfName(name); }

        if (city.length() > 0){ searcher.airportsOfCity(city); }

        if (country != null && ! country.equals("ALL COUNTRIES")) {searcher.airportsOfCountry(country); }

        if (FAA.length() > 0) { searcher.airportsOfFAA(FAA); }

        if (IATA.length() > 0) { searcher.airportsOfIATA(IATA); }

        if (ICAO.length() > 0) { searcher.airportsOfICAO(ICAO); }

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
        String country = (String) airlineCountrySearch.getValue();
        String activeStatus = (String) airlineActiveSearch.getValue();

        if (airlineID.length() > 0){ searcher.airlinesOfID(airlineID);}

        if (name.length() > 0){ searcher.airlinesOfName(name);}

        if (alias.length() > 0){ searcher.airlinesOfAlias(alias);}

        if (IATA.length() > 0){ searcher.airlinesOfIATA(IATA);}

        if (ICAO.length() > 0){ searcher.airlinesOfICAO(ICAO);}

        if (callsign.length() > 0){ searcher.airlinesOfCallsign(callsign);}

        if (country != null && ! country.equals("ALL COUNTRIES")){ searcher.airlinesOfCountry(country);}

        if (activeStatus != null && ! activeStatus.equals("ACTIVE OR INACTIVE")) {searcher.airlinesOfActiveStatus(activeStatus);}

        ObservableList<Airline> matchingAirlines = searcher.getLoadedAirlines();
        airlineTable.setItems(matchingAirlines);
    }

    @FXML
    private void routeSearch(ActionEvent e){
        flightView.setVisible(false);
        tableView.setVisible(true);


        RouteSearcher searcher = new RouteSearcher(currentlyLoadedRoutes);
        String airline = airlineSearch.getText();
        String airlineID = airlineSearchID.getText();
        String sourceAirport = (String)sourceSearch.getValue();
        String sourceID = sourceIDSearch.getText();
        String destinationAirport = (String) destinationSearch.getValue();
        String destinationID = destinationIDSearch.getText();
        String stops =  (String) stopoverSearch.getValue();
        String codeshareStatus = (String) codeshareSearch.getValue();
        String equipment = (String) equipmentSearch.getValue();

        if (airline.length() > 0){searcher.routesOfAirline(airline);}

        if (airlineID.length() > 0 ) {
            try {
                int intAirlineID = Integer.parseInt(airlineID);
                searcher.routesOfAirlineID(intAirlineID);
            }
            catch (NumberFormatException exception) {
                stopsErrorMessage.setVisible(true);
            }
        }

        if (sourceAirport != null && ! sourceAirport.equals("ALL")){searcher.routesOfSource(sourceAirport);}

        if (sourceID.length() > 0 ) {
            try {
                int intSourceID = Integer.parseInt(sourceID);
                searcher.routesOfSourceID(intSourceID);
            }
            catch (NumberFormatException exception) {
                stopsErrorMessage.setVisible(true);
            }
        }

        if (destinationAirport != null && ! destinationAirport.equals("ALL")){searcher.routesOfDestination(destinationAirport);}

        if (destinationID.length() > 0 ) {
            try {
                int intDestID = Integer.parseInt(destinationID);
                searcher.routesOfDestinationID(intDestID);
            }
            catch (NumberFormatException exception) {
                stopsErrorMessage.setVisible(true);
            }
        }

        if (stops != null && ! stops.equals("ALL") ) {
            try {
                int intStops = Integer.parseInt(stops);
                searcher.routesOfStops(intStops);
            }
            catch (NumberFormatException exception) {
                stopsErrorMessage.setVisible(true);
            }
        }

        if (codeshareStatus != null && ! codeshareStatus.equals("ALL")) {searcher.routesOfCodeshare(codeshareStatus);}

        if (equipment != null && ! equipment.equals("ALL")){searcher.routesOfEquipment(equipment);}

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();

        routeTable.setItems(matchingRoutes);
    }

    /* Method to add a new airport to the currentlyLoadedAirports from search text fields.
    Executed when the add button is clicked */
    @FXML
    private void airportAdd(ActionEvent e){
        AirportParser parser = new AirportParser();

        String airportID = airportIDSearch.getText();
        String name = airportNameSearch.getText();
        String city = airportCitySearch.getText();
        String country = "hey";
        String FAA = airportFAASearch.getText();
        String IATA = airportIATASearch.getText();
        String ICAO = airportICAOSearch.getText();
        String latitude = airportLatitudeSearch.getText();
        String longitude = airportLongitudeSearch.getText();
        String altitude = airportAltitudeSearch.getText();
        String timezone = airportTimezoneSearch.getText();
        String DST = airportDSTSearch.getText();

        String data = (airportID +","+ name +","+ city +","+ country +","+ FAA +","+ IATA +","+ ICAO +","+ latitude +","+ longitude +","+ altitude +","+ timezone +","+ DST);
        Airport newAirport = parser.createSingleAirport(data);
        if(newAirport != null){
            currentlyLoadedAirports.add(newAirport);
        }
        airportTable.setItems(currentlyLoadedAirports);

        }

    /* Method to add a new route to the currentlyLoadedRoutes from search text fields.
    Executed when the add button is clicked */
    @FXML
    private void routeAdd(ActionEvent e){
        RouteParser parser = new RouteParser();

        String airline = airlineSearch.getText();
        String airlineID = airlineSearchID.getText();
        String sourceAirport = "Hey";
        String sourceID = sourceIDSearch.getText();
        String destinationAirport = "Hey";
        String destinationID = destinationIDSearch.getText();
        String stops = "Hey";
        String codeshareStatus = "Hey";
        String equipment = "Hey";

        String data = (airline +","+ airlineID +","+ sourceAirport +","+ sourceID +","+ destinationAirport +","+ destinationID +","+ stops +","+ codeshareStatus +","+ equipment);
        Route newRoute = parser.createSingleRoute(data);
        if(newRoute != null){
            currentlyLoadedRoutes.add(newRoute);
        }
        routeTable.setItems(currentlyLoadedRoutes);
    }

    /* Method to add a new airline to the currentlyLoadedAirlines from search text fields.
    Executed when the add button is clicked */
    @FXML
    private void airlineAdd(ActionEvent e){
        AirlineParser parser = new AirlineParser();

        String airlineID = airlineIDSearch.getText();
        String name = airlineNameSearch.getText();
        String alias = airlineAliasSearch.getText();
        String IATA = airlineIATASearch.getText();
        String ICAO = airlineICAOSearch.getText();
        String callsign = airlineCallsignSearch.getText();
        String country = "hey";
        String activeStatus = (String) airlineActiveSearch.getValue();

        String data = (airlineID +","+ name +","+ alias +","+ IATA +","+ ICAO +","+ callsign +","+ country +","+ activeStatus);
        Airline newAirline = parser.createSingleAirline(data);
        if(newAirline != null){
            currentlyLoadedAirlines.add(newAirline);
        }
        airlineTable.setItems(currentlyLoadedAirlines);
    }


    @FXML
    private void airportBack(ActionEvent e){
        airportAdvancedButton.setVisible(true);
        airportBack.setVisible(false);
        airportCitySearch.setVisible(false);
        airportFAASearch.setVisible(false);
        airportIATASearch.setVisible(false);
        airportLongitudeSearch.setVisible(false);
        airportLatitudeSearch.setVisible(false);
        airportAltitudeSearch.setVisible(false);
        airportTimezoneSearch.setVisible(false);
        airportDSTSearch.setVisible(false);
        airportICAOSearch.setVisible(false);
        airportSearchButton.setLayoutX(182);
        airportSearchButton.setLayoutY(183);
        resetAirportSearch.setLayoutX(29);
        resetAirportSearch.setLayoutY(183);
    }

    @FXML
    private void showAirportSearch(ActionEvent e){
        airportAdvancedButton.setVisible(false);
        airportBack.setVisible(true);
        airportCitySearch.setVisible(true);
        airportFAASearch.setVisible(true);
        airportIATASearch.setVisible(true);
        airportLongitudeSearch.setVisible(true);
        airportLatitudeSearch.setVisible(true);
        airportAltitudeSearch.setVisible(true);
        airportTimezoneSearch.setVisible(true);
        airportDSTSearch.setVisible(true);
        airportICAOSearch.setVisible(true);
        airportSearchButton.setLayoutX(181);
        airportSearchButton.setLayoutY(552);
        resetAirportSearch.setLayoutX(29);
        resetAirportSearch.setLayoutY(552);

    }

    @FXML
    private void routeBack(ActionEvent e){
        routeAdvancedButton.setVisible(true);
        routeBack.setVisible(false);
        equipmentSearch.setVisible(false);
        codeshareSearch.setVisible(false);
        airlineSearchID.setVisible(false);
        destinationIDSearch.setVisible(false);
        sourceIDSearch.setVisible(false);
        routeSearch.setLayoutX(197);
        routeSearch.setLayoutY(251);
        resetRouteSearch.setLayoutX(29);
        resetRouteSearch.setLayoutY(251);
    }
    @FXML
    private void showRouteSearch(ActionEvent e) {
        routeAdvancedButton.setVisible(false);
        routeBack.setVisible(true);
        equipmentSearch.setVisible(true);
        codeshareSearch.setVisible(true);
        airlineSearchID.setVisible(true);
        destinationIDSearch.setVisible(true);
        sourceIDSearch.setVisible(true);
        routeSearch.setLayoutX(197);
        routeSearch.setLayoutY(455);
        resetRouteSearch.setLayoutX(29);
        resetRouteSearch.setLayoutY(455);


    }

    @FXML
    private void showAirlineSearch(ActionEvent e){
        airlineAdvancedButton.setVisible(false);
        airlineBack.setVisible(true);
        airlineAliasSearch.setVisible(true);
        airlineIATASearch.setVisible(true);
        airlineICAOSearch.setVisible(true);
        airlineCallsignSearch.setVisible(true);
        airlineSearchButton.setLayoutX(186);
        airlineSearchButton.setLayoutY(401);
        resetAirlineSearch.setLayoutX(29);
        resetAirlineSearch.setLayoutY(401);
    }

    @FXML
    private void airlineBack(ActionEvent e){
        airlineAdvancedButton.setVisible(true);
        airlineBack.setVisible(false);
        airlineAliasSearch.setVisible(false);
        airlineIATASearch.setVisible(false);
        airlineSearchID.setVisible(false);
        airlineICAOSearch.setVisible(false);
        airlineCallsignSearch.setVisible(false);
        airlineSearchButton.setLayoutX(186);
        airlineSearchButton.setLayoutY(235);
        resetAirlineSearch.setLayoutX(29);
        resetAirlineSearch.setLayoutY(235);
    }


    @FXML
    private Button routeAdvancedButton;
    @FXML
    private Button routeSearch;
    @FXML
    private Button resetRouteSearch;
    @FXML
    private Button routeBack;

    @FXML
    private Button airlineAdvancedButton;
    @FXML
    private Button airlineSearchButton;
    @FXML
    private Button resetAirlineSearch;
    @FXML
    private Button airlineBack;


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
        for(Airline str: currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        tableView.setVisible(true);
        flightView.setVisible(false);
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

                if (airline1 == !airline2){
                    return 1;
                }
                if (!airline1 == airline2){
                    return -1;
                }
                return 0;
            }
        };
        ArrayList<Airline> sortedAirlines = new ArrayList<Airline>();
        Collections.sort(currentlyLoadedAirlines, airlineActivityComparator);
        for(Airline str: currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        tableView.setVisible(true);
        flightView.setVisible(false);
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
        for(Airport str: currentlyLoadedAirports) {
            sortedAirports.add(str);
        }
        ObservableList<Airport> sortedObservableAirports = FXCollections.observableArrayList(sortedAirports);
        airportTable.setItems(sortedObservableAirports);
        tableView.setVisible(true);
        flightView.setVisible(false);
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
        for(Route str: currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        tableView.setVisible(true);
        flightView.setVisible(false);
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
        for(Route str: currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        tableView.setVisible(true);
        flightView.setVisible(false);
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

                if(route1 < route2){
                    return -1;
                }else if(route1 > route2){
                    return 1;
                }
                return 0;
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, stopsComparator);
        for(Route str: currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        tableView.setVisible(true);
        flightView.setVisible(false);
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
        for(Route str: currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        tableView.setVisible(true);
        flightView.setVisible(false);
    }

    /**
     * FXML imports for searching
     */



    @FXML
    private Button resetAirportSearch;
    @FXML
    private Button airportSearchButton;
    @FXML
    private Button airportBack;
    @FXML
    private Button airportAdvancedButton;
    @FXML
    private Text stopsErrorMessage;
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


    @FXML
    private Pane airlinePane;
    @FXML
    private Text airline_name;
    @FXML
    private Text airline_id;
    @FXML
    private Text airline_country;
    @FXML
    private Text airline_sign;
    @FXML
    private Text airline_iata;
    @FXML
    private Text airline_icao;
    @FXML
    private Text airline_alias;

    @FXML
    private Pane airportPane;
    @FXML
    private Text airport_name;
    @FXML
    private Text airport_id;
    @FXML
    private Text airport_faa;
    @FXML
    private Text airport_iata;
    @FXML
    private Text airport_icao;
    @FXML
    private Text airport_timezone;
    @FXML
    private Text airport_dst;
    @FXML
    private Text airport_country;
    @FXML
    private Text airport_city;
    @FXML
    private Text airport_long;
    @FXML
    private Text airport_lat;
    @FXML
    private Text airport_alt;

    @FXML
    private Pane routePane;
    @FXML
    private Text route_source;
    @FXML
    private Text route_dest;
    @FXML
    private Text route_stops;
    @FXML
    private Text route_airline;
    @FXML
    private Text route_equip;
    @FXML
    private Text route_share;

    @FXML
    private Pane tableView;
    @FXML
    private Pane flightView;




    //Back button event handler to return to airport tableview.
    public void backToAirport(ActionEvent e){
        airportTable.setVisible(true);
        airportPane.setVisible(false);
    }

    //Back button event handler to return to airline tableview.
    public void backToAirline(ActionEvent e){
        airlineTable.setVisible(true);
        airlinePane.setVisible(false);
    }

    //Back button event handler to return to route tableview.
    public void backToRoute(ActionEvent e){
        routeTable.setVisible(true);
        routePane.setVisible(false);
    }

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
                //Checks if table is empty then checks for double click
                if(!airportTable.getItems().isEmpty()) {
                    if (click.getClickCount() >= 2) {
                        //Changes visible pane;
                        airportTable.setVisible(false);
                        airportPane.setVisible(true);

                        //Sets all text to corresponding table row item.
                        airport_name.setText(airportTable.getSelectionModel().getSelectedItem().getName());
                        airport_id.setText(Integer.toString(airportTable.getSelectionModel().getSelectedItem().getAirportID()));
                        airport_faa.setText(airportTable.getSelectionModel().getSelectedItem().getFAA());
                        airport_iata.setText(airportTable.getSelectionModel().getSelectedItem().getIATA());
                        airport_icao.setText(airportTable.getSelectionModel().getSelectedItem().getICAO());
                        airport_dst.setText(Integer.toString(airportTable.getSelectionModel().getSelectedItem().getDST()));
                        airport_timezone.setText(airportTable.getSelectionModel().getSelectedItem().getOlsonTimezone());
                        airport_country.setText(airportTable.getSelectionModel().getSelectedItem().getCountry());
                        airport_city.setText(airportTable.getSelectionModel().getSelectedItem().getCity());
                        airport_lat.setText(Double.toString(airportTable.getSelectionModel().getSelectedItem().getLatitude()));
                        airport_long.setText(Double.toString(airportTable.getSelectionModel().getSelectedItem().getLongitude()));
                        airport_alt.setText(Double.toString(airportTable.getSelectionModel().getSelectedItem().getAltitude()));

                        /*TablePosition pos = airportTable.getSelectionModel().getSelectedCells().get(0);
                        int row = pos.getRow();
                        int col = pos.getColumn();
                        TableColumn column = pos.getTableColumn();
                        String val = column.getCellData(row).toString();
                        System.out.println("Selected Value, " + val + ", Column: " + col + ", Row: " + row);*/

                    /*if ( col == 2 ) {System.out.println("2");}
                    if ( col == 5 ) {System.out.println("5");}
                    if ( col == 6 ) {System.out.println("6");}
                    if ( col == 8 ) {System.out.println("8");}*/
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please add data before attempting to select.", "No Data Found!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        airlineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click)
            {
                //Checks if table is empty then checks for double click
                if(!airlineTable.getItems().isEmpty()) {
                    if (click.getClickCount() >= 2) {
                        //Changes visible pane;
                        airlineTable.setVisible(false);
                        airlinePane.setVisible(true);

                        //Sets all text to corresponding table row item.
                        airline_name.setText(airlineTable.getSelectionModel().getSelectedItem().getName());
                        airline_id.setText(Integer.toString(airlineTable.getSelectionModel().getSelectedItem().getAirlineID()));
                        airline_country.setText(airlineTable.getSelectionModel().getSelectedItem().getCountry());
                        airline_iata.setText(airlineTable.getSelectionModel().getSelectedItem().getIATA());
                        airline_icao.setText(airlineTable.getSelectionModel().getSelectedItem().getICAO());
                        airline_sign.setText(airlineTable.getSelectionModel().getSelectedItem().getCallsign());
                        airline_alias.setText(airlineTable.getSelectionModel().getSelectedItem().getAlias());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please add data before attempting to select.", "No Data Found!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        routeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click)
            {
                //Checks if table is empty then checks for double click
                if(!routeTable.getItems().isEmpty()) {
                    if (click.getClickCount() >= 2) {
                        //Changes visible pane;
                        routeTable.setVisible(false);
                        routePane.setVisible(true);

                        //Sets all text to corresponding table row item.
                        route_airline.setText(routeTable.getSelectionModel().getSelectedItem().getAirlineName());
                        route_source.setText(routeTable.getSelectionModel().getSelectedItem().getSourceAirportName());
                        route_dest.setText(routeTable.getSelectionModel().getSelectedItem().getDestinationAirportName());
                        route_equip.setText(routeTable.getSelectionModel().getSelectedItem().getEquipment());
                        route_share.setText(routeTable.getSelectionModel().getSelectedItem().getCodeshareString());
                        route_stops.setText(Integer.toString(routeTable.getSelectionModel().getSelectedItem().getStops()));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please add data before attempting to select.", "No Data Found!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
