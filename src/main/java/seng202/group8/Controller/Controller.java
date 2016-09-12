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
import seng202.group8.Model.DatabaseMethods.*;
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
    static ObservableList<Airport> currentlyLoadedAirports = FXCollections.observableArrayList();
    static ObservableList<Route> currentlyLoadedRoutes = FXCollections.observableArrayList();
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
                //Load all of the content from the database here
                ObservableList<Airport> airports = adl.loadAirport(connTwo);
                dbTwo.disconnect(connTwo);
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
                //ObservableList<Airline> airlines = load.buildAirlines();

                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                AirlineDatabaseLoader adl = new AirlineDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();

                dbsave.saveAirlines(connOne, load.buildAirlines());
                dbOne.disconnect(connOne);

                ObservableList<Airline> airlines = adl.loadAirlines(connTwo);
                dbTwo.disconnect(connTwo);

                currentlyLoadedAirlines = airlines;
                airlineTable.setItems(airlines);
                setAirlineComboBoxes();
                flightView.setVisible(false);
                addAirportView.setVisible(false);
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
                //ObservableList<Route> routes = load.buildRoutes();

                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                RouteDatabaseLoader rdl = new RouteDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();

                dbsave.saveRoutes(connOne, load.buildRoutes());
                dbOne.disconnect(connOne);

                ObservableList<Route> routes = rdl.loadRoutes(connTwo);
                dbTwo.disconnect(connTwo);

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
        System.out.println(headerString);
        titleString.setText(headerString);

        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        flightTable.setItems(waypoints);

    }


    @FXML
    private void resetSearch(ActionEvent e){
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
    private void switchToAddAirport(ActionEvent e){
        tableView.setVisible(false);
        addAirportView.setVisible(true);
    }

    @FXML
    private void cancelAddedAirport(ActionEvent e){
        tableView.setVisible(true);
        addAirportView.setVisible(false);
        addedAirportName.clear();
        addedAirportID.clear();
        addedAirportCountry.clear();
        addedAirportCity.clear();
        addedAirportCode.clear();
        addedAirportICAO.clear();
        addedAirportLatitude.clear();
        addedAirportLongitude.clear();
        addedAirportAltitude.clear();
        addedAirportTimezone.clear();
        addedAirportDST.clear();
        addedAirportOlsen.clear();
    }

    @FXML
    private void saveAddedAirport(ActionEvent e){

        AirportParser parser = new AirportParser();

        String airportID = addedAirportID.getText();
        String name = addedAirportName.getText();
        String city = addedAirportCity.getText();
        String country = addedAirportCountry.getText();
        String code = addedAirportCode.getText();
        String ICAO = addedAirportICAO.getText();
        String latitude = addedAirportLatitude.getText();
        String longitude = addedAirportLongitude.getText();
        String altitude = addedAirportAltitude.getText();
        String timezone = addedAirportTimezone.getText();
        String DST = addedAirportDST.getText();
        String olsen = addedAirportOlsen.getText();

        String data = (airportID +","+ name +","+ city +","+ country +","+ code +","+ ICAO +","+ latitude +","+ longitude +","+ altitude +","+ timezone +","+ DST + "," + olsen);
        Airport newAirport = parser.createSingleAirport(data);
        if(newAirport != null){
            currentlyLoadedAirports.add(newAirport);
        }
        airportTable.setItems(currentlyLoadedAirports);
        tableView.setVisible(true);
        addAirportView.setVisible(false);
    }
//
    @FXML
    private void switchToAddAirline(ActionEvent e){
        tableView.setVisible(false);
        addAirlineView.setVisible(true);
    }
//
    @FXML
    private void cancelAddedAirline(ActionEvent e){
        addedAirlineName.clear();
        addedAirlineID.clear();
        addedAirlineCountry.clear();
        addedAirlineAlias.clear();
        addedAirlineIATA.clear();
        addedAirlineICAO.clear();
        addedAirlineCallsign.clear();
        addedAirlineActive.setSelected(false);
        addAirlineView.setVisible(false);
        tableView.setVisible(true);
    }
//
    @FXML
    private void saveAddedAirline(ActionEvent e){
        AirlineParser parser = new AirlineParser();
        String airlineID = addedAirlineID.getText();
        String name = addedAirlineName.getText();
        String alias = addedAirlineAlias.getText();
        String IATA = addedAirlineIATA.getText();
        String ICAO = addedAirlineICAO.getText();
        String callsign = addedAirlineCallsign.getText();
        String country = addedAirlineCountry.getText();
        String isActive = "N";
        if (addedAirlineActive.isSelected() == true){
            isActive = "Y";
        }

        String data = airlineID + ',' + name + ',' + alias + ',' + IATA + ',' + ICAO + '+' + callsign + ',' + country + ',' + isActive;
        Airline newAirline = parser.createSingleAirline(data);
        currentlyLoadedAirlines.add(newAirline);
        airlineTable.setItems(currentlyLoadedAirlines);
        tableView.setVisible(true);
        addAirlineView.setVisible(false);

 }


    @FXML
    private void airportSearchBack(ActionEvent e){
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
        airportSearchButton.setLayoutX(182);
        airportSearchButton.setLayoutY(183);
        resetAirportSearch.setLayoutX(29);
        resetAirportSearch.setLayoutY(183);
    }

    @FXML
    private void showAirportSearch(ActionEvent e){
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
        airportSearchButton.setLayoutX(181);
        airportSearchButton.setLayoutY(552);
        resetAirportSearch.setLayoutX(29);
        resetAirportSearch.setLayoutY(552);

    }

    @FXML
    private void routeSearchBack(ActionEvent e){
        routeAdvancedButton.setVisible(true);
        routeBackButton.setVisible(false);
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
        routeBackButton.setVisible(true);
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
        airlineBackButton.setVisible(true);
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
    private void airlineSearchBack(ActionEvent e){
        airlineAdvancedButton.setVisible(true);
        airlineBackButton.setVisible(false);
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
        for(Airline str: currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        tableView.setVisible(true);
        flightView.setVisible(false);
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




    @FXML
    public void editAirlineData(ActionEvent e){
        Airline currentAirline = airlineTable.getSelectionModel().getSelectedItem();
        editAirlineIDField.setVisible(true);
        editCallsignField.setVisible(true);
        editAirlineIATAField.setVisible(true);
        editAirlineICAOField.setVisible(true);
        editAliasField.setVisible(true);
        editActiveField.setVisible(true);
        editAirlineCountryField.setVisible(true);

        saveAirlineChangesButton.setVisible(true);
        cancelAirlineChangesButton.setVisible(true);

        editAirlineIDField.setText(Integer.toString(currentAirline.getAirlineID()));
        editAirlineCountryField.setText(currentAirline.getCountry());
        if (currentAirline.getCallsign() != null){
            editCallsignField.setText(currentAirline.getCallsign());
        }
        else{
            editCallsignField.setText("None");
        }
        if (currentAirline.getIATA() != null){
            editAirlineIATAField.setText(currentAirline.getIATA());
        }
        else{
            editAirlineIATAField.setText("None");
        }
        if (currentAirline.getICAO() != null){
            editAirlineICAOField.setText(currentAirline.getICAO());
        }
        else{
            editAirlineICAOField.setText("None");
        }
        if (currentAirline.getAlias() != null){
            editAliasField.setText(currentAirline.getAlias());
        }
        else{
            editAliasField.setText("None");
        }
        if (currentAirline.isActive() == true){
            editActiveField.setText("Yes");
        }
        else{
            editActiveField.setText("No");
        }


    }

    @FXML
    public void cancelAirlineChanges(ActionEvent e){

        saveAirlineChangesButton.setVisible(false);
        cancelAirlineChangesButton.setVisible(false);
        editAirlineIDField.setVisible(false);
        editCallsignField.setVisible(false);
        editAirlineIATAField.setVisible(false);
        editAirlineICAOField.setVisible(false);
        editAliasField.setVisible(false);
        editActiveField.setVisible(false);
        editAirlineCountryField.setVisible(false);

    }

    @FXML
    public void saveAirlineChanges(ActionEvent e){
        Airline currentAirline = airlineTable.getSelectionModel().getSelectedItem();

        currentAirline.setAirlineID(Integer.parseInt(editAirlineIDField.getText()));
        if (!editCallsignField.getText().equals("None")) {
            currentAirline.setCallsign(editCallsignField.getText());
        }
        if (!editAirlineIATAField.getText().equals("None")) {
            currentAirline.setIATA(editAirlineIATAField.getText());
        }
        if (!editAirlineICAOField.getText().equals("None")) {
            currentAirline.setICAO(editAirlineICAOField.getText());
        }
        if (!editAliasField.getText().equals("None")) {
            currentAirline.setAlias(editAliasField.getText());
        }
        if (editActiveField.getText().equals("Yes")){
            currentAirline.setActive(true);
            airlineActiveDisplay.setText("Yes");
        }
        else if (editActiveField.getText().equals("No")){
            currentAirline.setActive(false);
            airlineActiveDisplay.setText("No");
        }
        currentAirline.setCountry(editAirlineCountryField.getText());

        airlineIDDisplay.setText(Integer.toString(currentAirline.getAirlineID()));
        airlineCallsignDisplay.setText(currentAirline.getCallsign());
        airlineIATADisplay.setText(currentAirline.getIATA());
        airlineICAODisplay.setText(currentAirline.getICAO());
        airlineAliasDisplay.setText(currentAirline.getAlias());
        airlineCountryDisplay.setText(currentAirline.getCountry());

        editAirlineIDField.setVisible(false);
        editCallsignField.setVisible(false);
        editAirlineIATAField.setVisible(false);
        editAirlineICAOField.setVisible(false);
        editAliasField.setVisible(false);
        editActiveField.setVisible(false);
        editAirlineCountryField.setVisible(false);

        saveAirlineChangesButton.setVisible(false);
        cancelAirlineChangesButton.setVisible(false);

        setAirlineComboBoxes();
    }

    @FXML
    public void editAirportData(ActionEvent e){
        Airport currentAirport = airportTable.getSelectionModel().getSelectedItem();
        editAirportIDField.setVisible(true);
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

        saveAirportChangesButton.setVisible(true);
        cancelAirportChangesButton.setVisible(true);

        editAirportIDField.setText(Integer.toString(currentAirport.getAirportID()));
        editAirportCountryField.setText(currentAirport.getCountry());
        if (currentAirport.getFAA() != null){
            editFAAField.setText(currentAirport.getFAA());
        }
        else{
            editFAAField.setText("None");
        }
        if (currentAirport.getIATA() != null){
            editAirportIATAField.setText(currentAirport.getIATA());
        }
        else{
            editAirportIATAField.setText("None");
        }
        if (currentAirport.getICAO() != null){
            editAirportICAOField.setText(currentAirport.getICAO());
        }
        else{
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
    public void cancelAirportChanges(ActionEvent e){

        saveAirportChangesButton.setVisible(false);
        cancelAirportChangesButton.setVisible(false);
        editAirportIDField.setVisible(false);
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
    public void saveAirportChanges(ActionEvent e){
        Airport currentAirport = airportTable.getSelectionModel().getSelectedItem();

        currentAirport.setAirportID(Integer.parseInt(editAirportIDField.getText()));

        if (!editFAAField.getText().equals("None")) {
            currentAirport.setFAA(editFAAField.getText());
        }
        if (!editAirportIATAField.getText().equals("None")) {
            currentAirport.setIATA(editAirportIATAField.getText());
        }
        if (!editAirportICAOField.getText().equals("None")) {
            currentAirport.setICAO(editAirportICAOField.getText());
        }

        currentAirport.setTimezone(Integer.parseInt(editTimezoneField.getText()));
        currentAirport.setDST(editDSTField.getText().charAt(0));
        currentAirport.setCountry(editAirportCountryField.getText());
        currentAirport.setCity(editAirportCityField.getText());
        currentAirport.setLongitude(Double.parseDouble(editLongitudeField.getText()));
        currentAirport.setLatitude(Double.parseDouble(editLatitudeField.getText()));
        currentAirport.setAltitude(Double.parseDouble(editAltitudeField.getText()));

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

        editAirportIDField.setVisible(false);
        editFAAField.setVisible(false);
        editAirportIATAField.setVisible(false);
        editAirportICAOField.setVisible(false);
        editTimezoneField.setVisible(false);
        editDSTField.setVisible(false);
        editAirportCountryField.setVisible(false);
        editAirportCityField.setVisible(false);
        editLatitudeField.setVisible(false);
        editLongitudeField.setVisible(false);
        editAltitudeField.setVisible(false);;

        saveAirportChangesButton.setVisible(false);
        cancelAirportChangesButton.setVisible(false);

        setAirportComboBoxes();
    }


    @FXML
    public void editRouteData(ActionEvent e){
        Route currentRoute = routeTable.getSelectionModel().getSelectedItem();
        editSourceField.setVisible(true);
        editDestinationField.setVisible(true);
        editStopsField.setVisible(true);
        editEquipmentField.setVisible(true);
        editCodeshareField.setVisible(true);


        saveRouteChangesButton.setVisible(true);
        cancelRouteChangesButton.setVisible(true);

        editSourceField.setText(currentRoute.getSourceAirportName());
        editDestinationField.setText(currentRoute.getDestinationAirportName());
        editStopsField.setText(Integer.toString(currentRoute.getStops()));
        if (currentRoute.getEquipment() != null){
            editEquipmentField.setText(currentRoute.getEquipment());
        }
        else{
            editEquipmentField.setText("None");
        }
        if (currentRoute.isCodeshare() == true){
            editCodeshareField.setText("Yes");
        }
        else{
            editCodeshareField.setText("No");
        }


    }

    @FXML
    public void cancelRouteChanges(ActionEvent e){

        saveRouteChangesButton.setVisible(false);
        cancelRouteChangesButton.setVisible(false);
        editSourceField.setVisible(false);
        editDestinationField.setVisible(false);
        editStopsField.setVisible(false);
        editEquipmentField.setVisible(false);
        editCodeshareField.setVisible(false);;

    }

    @FXML
    public void saveRouteChanges(ActionEvent e){
        Route currentRoute = routeTable.getSelectionModel().getSelectedItem();

        currentRoute.setSourceAirportName(editSourceField.getText());
        currentRoute.setDestinationAirportName(editDestinationField.getText());
        //Add methods to find airports as well
        currentRoute.setStops(Integer.parseInt(editStopsField.getText()));

        if (!editEquipmentField.getText().equals("None")) {
            currentRoute.setEquipment(editEquipmentField.getText());
        }
        if (editCodeshareField.getText().equals("Yes")){
            currentRoute.setCodeshare(true);
            routeShareDisplay.setText("Yes");
        }
        else if (editCodeshareField.getText().equals("No")){
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
        editCodeshareField.setVisible(false);;

        saveRouteChangesButton.setVisible(false);
        cancelRouteChangesButton.setVisible(false);

        setRouteComboBoxes();
    }

    /**
     * FXML imports for searching
     */


    @FXML
    private TextField editSourceField;
    @FXML
    private TextField editDestinationField;
    @FXML
    private TextField editStopsField;
    @FXML
    private TextField editEquipmentField;
    @FXML
    private TextField editCodeshareField;
    @FXML
    private Button saveRouteChangesButton;
    @FXML
    private Button cancelRouteChangesButton;
    @FXML
    private Button editRouteDataDutton;



    @FXML
    private TextField editAirportIDField;
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
    private Button editAirportDataDutton;




    @FXML
    private Button routeAdvancedButton;
    @FXML
    private Button routeSearch;
    @FXML
    private Button resetRouteSearch;
    @FXML
    private Button routeBackButton;
    @FXML
    private TextField editCallsignField;
    @FXML
    private TextField editAirlineIATAField;
    @FXML
    private TextField editAirlineICAOField;
    @FXML
    private TextField editAliasField;
    @FXML
    private TextField editActiveField;
    @FXML
    private TextField editAirlineCountryField;
    @FXML
    private Button airlineAdvancedButton;
    @FXML
    private Button airlineSearchButton;
    @FXML
    private Button resetAirlineSearch;
    @FXML
    private Button airlineBackButton;
    @FXML
    private Button saveAirlineChangesButton;
    @FXML
    private TextField editAirlineIDField;
    @FXML
    private Button cancelAirlineChangesButton;
    @FXML
    private Button resetAirportSearch;
    @FXML
    private Button airportSearchButton;
    @FXML
    private Button airportBackButton;
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
    private Text airlineNameDisplay;
    @FXML
    private Text airlineIDDisplay;
    @FXML
    private Text airlineCountryDisplay;
    @FXML
    private Text airlineCallsignDisplay;
    @FXML
    private Text airlineIATADisplay;
    @FXML
    private Text airlineICAODisplay;
    @FXML
    private Text airlineAliasDisplay;
    @FXML
    private Text airlineActiveDisplay;

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
    private Pane tableView;
    @FXML
    private Pane flightView;
    @FXML
    private Pane addAirportView;
    @FXML
    private Pane addAirlineView;

    @FXML
    private TextField addedAirportID;
    @FXML
    private TextField addedAirportName;
    @FXML
    private TextField addedAirportCity;
    @FXML
    private TextField addedAirportCountry;
    @FXML
    private TextField addedAirportCode;
    @FXML
    private TextField addedAirportICAO;
    @FXML
    private TextField addedAirportLatitude;
    @FXML
    private TextField addedAirportLongitude;
    @FXML
    private TextField addedAirportAltitude;
    @FXML
    private TextField addedAirportTimezone;
    @FXML
    private TextField addedAirportDST;
    @FXML
    private TextField addedAirportOlsen;

    @FXML
    private TextField addedAirlineID;
    @FXML
    private TextField addedAirlineName;
    @FXML
    private TextField addedAirlineAlias;
    @FXML
    private TextField addedAirlineCountry;
    @FXML
    private TextField addedAirlineIATA;
    @FXML
    private TextField addedAirlineICAO;
    @FXML
    private TextField addedAirlineCallsign;
    @FXML
    private CheckBox addedAirlineActive;




    private void resetTables(){
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


    public void backToTableView(ActionEvent e){
        resetTables();
        addAirportView.setVisible(false);
        airportPane.setVisible(false);
        airlinePane.setVisible(false);
        routePane.setVisible(false);
        airportTable.setVisible(true);
        airlineTable.setVisible(true);
        routeTable.setVisible(true);
        tableView.setVisible(true);
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
                        airlineNameDisplay.setText(airlineTable.getSelectionModel().getSelectedItem().getName());
                        airlineIDDisplay.setText(Integer.toString(airlineTable.getSelectionModel().getSelectedItem().getAirlineID()));
                        airlineCountryDisplay.setText(airlineTable.getSelectionModel().getSelectedItem().getCountry());
                        airlineIATADisplay.setText(airlineTable.getSelectionModel().getSelectedItem().getIATA());
                        airlineICAODisplay.setText(airlineTable.getSelectionModel().getSelectedItem().getICAO());
                        airlineCallsignDisplay.setText(airlineTable.getSelectionModel().getSelectedItem().getCallsign());
                        airlineAliasDisplay.setText(airlineTable.getSelectionModel().getSelectedItem().getAlias());
                        if (airlineTable.getSelectionModel().getSelectedItem().isActive()) {
                            airlineActiveDisplay.setText("Yes");
                        }
                        else{
                            airlineActiveDisplay.setText("No");
                        }
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
                        routeAirlineDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getAirlineName());
                        routeSourceDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getSourceAirportName());
                        routeDestinationDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getDestinationAirportName());
                        routeEquipmentDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getEquipment());
                        routeShareDisplay.setText(routeTable.getSelectionModel().getSelectedItem().getCodeshareString());
                        routeStopsDisplay.setText(Integer.toString(routeTable.getSelectionModel().getSelectedItem().getStops()));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please add data before attempting to select.", "No Data Found!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
