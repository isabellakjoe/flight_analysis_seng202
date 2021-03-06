package seng202.group8.Controller.SearchObjectControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Searchers.AirlineSearcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esa46 on 21/09/16.
 */
public class SearchAirlineViewController {

    private MainController mainController;

    @FXML
    private Button airlineAddButton;
    @FXML
    private Button airlineAdvancedButton;
    @FXML
    private Button airlineSearchButton;
    @FXML
    private Button resetAirlineSearch;
    @FXML
    private Button airlineBackButton;
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


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /*
    Clears all search fields and reset tables from returned results to all records
     */
    @FXML
    private void resetAirlineSearch() {
        /* Clears all search fields*/
        airlineCountrySearch.setValue(null);
        airlineActiveSearch.setValue(null);
        airlineIDSearch.clear();
        airlineNameSearch.clear();
        airlineAliasSearch.clear();
        airlineIATASearch.clear();
        airlineICAOSearch.clear();
        airlineCallsignSearch.clear();

        mainController.airlineTable.setItems(mainController.getCurrentlyLoadedAirlines());
    }

    /*
    Executes a search for a record based on multiple criteria
     */
    @FXML
    private void airlineSearch(ActionEvent e) {
        mainController.resetView();
        mainController.tableView.setVisible(true);
        mainController.airlineTable.setVisible(true);
        mainController.switchToDataTab();
        AirlineSearcher searcher = new AirlineSearcher(mainController.getCurrentlyLoadedAirlines());

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
        mainController.airlineTable.setItems(matchingAirlines);
    }

    /*
    Switches to advanced search mode with more fields available
     */
    @FXML
    private void showAirlineSearch(ActionEvent e) {
        airlineAdvancedButton.setVisible(false);
        airlineIDSearch.setVisible(true);
        airlineBackButton.setVisible(true);
        airlineAliasSearch.setVisible(true);
        airlineIATASearch.setVisible(true);
        airlineICAOSearch.setVisible(true);
        airlineCallsignSearch.setVisible(true);
        airlineSearchButton.setLayoutY(415);
        resetAirlineSearch.setLayoutY(415);
        airlineAddButton.setVisible(false);
        mainController.switchToDataTab();
    }

    /*
    Returns to streamlined search
     */
    @FXML
    private void airlineSearchBack() {
        airlineAdvancedButton.setVisible(true);
        airlineBackButton.setVisible(false);
        airlineAliasSearch.setVisible(false);
        airlineIATASearch.setVisible(false);
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

    /*
    Changes view to allow adding a record
     */
    @FXML
    private void switchToAddAirline(ActionEvent e) {
        mainController.switchToAddAirline(e);
    }

    /*
    Sets the combo box for searching by active status
     */
    public void setActiveCombobox(ArrayList<String> activeStatuses) {
        airlineActiveSearch.getItems().clear();
        airlineActiveSearch.getItems().setAll(activeStatuses);
    }

    /*
    Sets the combo box for searching by active status
     */
    public void setCountryCombobox(List sortedCountries) {
        airlineCountrySearch.getItems().clear();
        airlineCountrySearch.getItems().setAll(sortedCountries);
    }

}
