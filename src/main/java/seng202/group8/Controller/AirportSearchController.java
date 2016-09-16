package seng202.group8.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group8.Model.DatabaseMethods.AirportDatabaseLoader;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Parsers.FileLoader;
import seng202.group8.Model.Searchers.AirportSearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static seng202.group8.Controller.MainController.currentlyLoadedAirports;

/**
 * Created by swa158 on 15/09/16.
 */
public class AirportSearchController {

    private MainController mainController;

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
    private Button airportAddButton;




    @FXML
    private void airportSearch(ActionEvent e) {
        mainController.resetView();
        mainController.tableView.setVisible(true);
        mainController.airportTable.setVisible(true);
        mainController.airportPane.setVisible(false);
        AirportSearcher searcher = new AirportSearcher(mainController.currentlyLoadedAirports);
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
        mainController.airportTable.setItems(matchingAirports);
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
    private void airportSearchBack(ActionEvent e) {
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


        mainController.getRouteSourceDestination(sortedNames);
    }



    @FXML
    private void resetAirportSearch(ActionEvent e) {
        mainController.airportTable.setItems(currentlyLoadedAirports);
        mainController.resetView();
        mainController.tableView.setVisible(true);
    }

    /* Method to add a new airline to the currentlyLoadedAirlines from search text fields.
    Executed when the add button is clicked */
    @FXML
    private void switchToAddAirport(ActionEvent e) {
        mainController.resetView();
        mainController.addAirportViewController.makeVisible();
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
