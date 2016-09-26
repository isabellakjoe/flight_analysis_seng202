package seng202.group8.Controller.SearchObjectControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Searchers.AirportSearcher;

import java.util.List;

/**
 * Created by esa46 on 21/09/16.
 */
public class SearchAirportViewController {

    @FXML
    private Button airportAddButton;
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


    private MainController mainController;

    public void setCountryCombobox(List sortedCountries){
        airportCountrySearch.getItems().clear();
        airportCountrySearch.getItems().setAll(sortedCountries);
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

        mainController.airportTable.setItems(mainController.getCurrentlyLoadedAirports());
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
    private void airportSearch(ActionEvent e) {
        mainController.backToTableView(e);
        AirportSearcher searcher = new AirportSearcher(mainController.getCurrentlyLoadedAirports());
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
    private void switchToAddAirport(ActionEvent e) {
        mainController.switchToAddAirport(e);
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}