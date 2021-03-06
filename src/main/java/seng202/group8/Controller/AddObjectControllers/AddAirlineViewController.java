package seng202.group8.Controller.AddObjectControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Parsers.AirlineParser;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * Created by esa46 on 15/09/16.
 */
public class AddAirlineViewController {

    private MainController mainController;

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
    @FXML
    private Pane addAirlineViewPane;
    @FXML
    private Text addAirlineIDErrorEmpty;
    @FXML
    private Text addAirlineNameErrorEmpty;
    @FXML
    private Text addAirlineIATAErrorEmpty;
    @FXML
    private Text addAirlineICAOErrorEmpty;
    @FXML
    private Text addAirlineAliasErrorEmpty;
    @FXML
    private Text addAirlineCountryErrorEmpty;
    @FXML
    private Text addAirlineIDErrorType;
    @FXML
    private Text addAirlineIATAErrorType;
    @FXML
    private Text addAirlineICAOErrorType;


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void makeVisible() {
        addAirlineViewPane.setVisible(true);
    }

    public void makeInvisible() {
        addAirlineViewPane.setVisible(false);
    }

    /**
     * Clear all airline information fields and return to table view without adding airline
     *
     * @param e: The ActionEvent
     */
    @FXML
    private void cancelAddedAirline(ActionEvent e) {
        clearAirlineErrors();
        makeInvisible();
        mainController.backToTableView(e);
        //Clear all text fields
        addedAirlineName.clear();
        addedAirlineID.clear();
        addedAirlineCountry.clear();
        addedAirlineAlias.clear();
        addedAirlineIATA.clear();
        addedAirlineICAO.clear();
        addedAirlineCallsign.clear();
        addedAirlineActive.setSelected(false);
    }

    /**
     * Read user input to create and save an airline
     *
     * @param e: The ActionEvent
     */
    @FXML
    private void saveAddedAirline(ActionEvent e) {
        clearAirlineErrors();
        AirlineParser parser = new AirlineParser();
        String airlineID = addedAirlineID.getText();
        String name = addedAirlineName.getText();
        String alias = addedAirlineAlias.getText();
        String IATA = addedAirlineIATA.getText();
        String ICAO = addedAirlineICAO.getText();
        String callsign = addedAirlineCallsign.getText();
        String country = addedAirlineCountry.getText();
        String isActive = "N";
        if (addedAirlineActive.isSelected() == true) {
            isActive = "Y";
        }

        String data = airlineID + ',' + name + ',' + alias + ',' + IATA + ',' + ICAO + ',' + callsign + ',' + country + ',' + isActive;
        List<String> notNullData = Arrays.asList(airlineID, name, alias, country, IATA, ICAO);

        boolean noErrors = addAirlineError(notNullData);

        if (noErrors && mainController.getAirlineHashMap().get(IATA) == null && mainController.getAirlineHashMap().get(ICAO) == null) {
            ObservableList<Airline> airlines = FXCollections.observableArrayList();
            Airline newAirline = parser.createSingleAirline(data);
            airlines.add(newAirline);

            if (newAirline != null) {
                //Add the new airport to the database here
                DatabaseSaver dbSave = new DatabaseSaver();
                Connection connSave = Database.connect();
                dbSave.saveAirlines(connSave, airlines);
                Database.disconnect(connSave);

                mainController.addToCurrentlyLoadedAirlines(newAirline);
                MainController.getAirlineHashMap().put(newAirline.getIATA(), newAirline);
            }
            mainController.airlineTable.setItems(mainController.getCurrentlyLoadedAirlines());
            mainController.resetView();
            cancelAddedAirline(e);
            mainController.setAirlineComboBoxes();
            mainController.backToTableView(e);
        } else if (noErrors && mainController.getAirlineHashMap().get(IATA) == null) {
            System.out.println("ICAO NOT UNIQUE");
            addAirlineICAOErrorType.setVisible(true);

        } else if (noErrors && mainController.getAirlineHashMap().get(ICAO) == null) {
            System.out.println("IATA NOT UNIQUE");
            addAirlineIATAErrorType.setVisible(true);
        }
    }

    /**
     * Error check airline information fields and return a boolean of whether input is valid.
     * Show error messages for invalid input.
     *
     * @param input: A list of Strings
     * @return: A boolean that is True if there are enough non-empty strings in the input.
     */
    @FXML
    private boolean addAirlineError(List<String> input) {

        boolean filled = false;
        int size = input.size();

        int count = 0;
        for (int i = 0; i < size; i++) {
            String current = input.get(i);
            if (current.equals("")) {
                switch (i) {
                    case 0:
                        addAirlineIDErrorEmpty.setVisible(true);
                        break;
                    case 1:
                        addAirlineNameErrorEmpty.setVisible(true);
                        break;
                    case 2:
                        addAirlineAliasErrorEmpty.setVisible(true);
                        break;
                    case 3:
                        addAirlineCountryErrorEmpty.setVisible(true);
                        break;
                    case 4:
                        addAirlineIATAErrorEmpty.setVisible(true);
                        break;
                    case 5:
                        addAirlineICAOErrorEmpty.setVisible(true);
                        break;
                }
            } else {
                count += 1;
            }
            if ((i == 0) && !(current.equals(""))) {

                try {
                    Integer.parseInt(input.get(i));
                    count += 1;

                } catch (NumberFormatException e) {
                    addAirlineIDErrorType.setVisible(true);
                }
            }
        }
        if (count == 7) {
            filled = true;
        }
        return filled;
    }

    /**
     * Hide all error messages related to adding an airline
     */
    @FXML
    private void clearAirlineErrors() {
        addAirlineIDErrorEmpty.setVisible(false);
        addAirlineNameErrorEmpty.setVisible(false);
        addAirlineAliasErrorEmpty.setVisible(false);
        addAirlineCountryErrorEmpty.setVisible(false);
        addAirlineIATAErrorEmpty.setVisible(false);
        addAirlineICAOErrorEmpty.setVisible(false);
        addAirlineIDErrorType.setVisible(false);
        addAirlineIATAErrorType.setVisible(false);
        addAirlineICAOErrorType.setVisible(false);
    }

}
