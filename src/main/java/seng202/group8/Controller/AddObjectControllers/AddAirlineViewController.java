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
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
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
    private Text addAirlineAliasErrorEmpty;
    @FXML
    private Text addAirlineCountryErrorEmpty;
    @FXML
    private Text addAirlineIDErrorType;


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void makeVisible() {
        addAirlineViewPane.setVisible(true);
    }

    public void makeInvisible() {
        addAirlineViewPane.setVisible(false);
    }

    @FXML
    private void cancelAddedAirline(ActionEvent e) {
        addedAirlineName.clear();
        addedAirlineID.clear();
        addedAirlineCountry.clear();
        addedAirlineAlias.clear();
        addedAirlineIATA.clear();
        addedAirlineICAO.clear();
        addedAirlineCallsign.clear();
        addedAirlineActive.setSelected(false);
        mainController.backToTableView(new ActionEvent());
    }

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


        if (count == 5) {
            filled = true;
        }
        return filled;

    }

    @FXML
    private void clearAirlineErrors() {
        addAirlineIDErrorEmpty.setVisible(false);
        addAirlineNameErrorEmpty.setVisible(false);
        addAirlineAliasErrorEmpty.setVisible(false);
        addAirlineCountryErrorEmpty.setVisible(false);
        addAirlineIDErrorType.setVisible(false);
    }

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
        List<String> notNullData = Arrays.asList(airlineID, name, alias, country);

        boolean noErrors = addAirlineError(notNullData);

        if (noErrors && mainController.getAirlineHashMap().get(IATA) == null && mainController.getAirlineHashMap().get(ICAO) == null) {
            ObservableList<Airline> airlines = FXCollections.observableArrayList();
            Airline newAirline = parser.createSingleAirline(data);
            airlines.add(newAirline);
            System.out.println(newAirline.getAirlineID());
            if (newAirline != null) {
                //Add the new airport to the database here
                Database db = new Database();
                DatabaseSaver dbSave = new DatabaseSaver();
                DatabaseSearcher dbSearch = new DatabaseSearcher();
                Connection connSave = db.connect();
                Connection connSearch = db.connect();
                dbSave.saveAirlines(connSave, airlines);
                db.disconnect(connSave);
                String sql = dbSearch.buildAirlineSearch("airlineid", airlineID);
                ObservableList<Airline> addedAirline = dbSearch.searchForAirlinesByOption(connSearch, sql);
                mainController.addToCurrentlyLoadedAirlines(addedAirline.get(0));
                db.disconnect(connSearch);
            }
            mainController.airlineTable.setItems(mainController.getCurrentlyLoadedAirlines());
            mainController.resetView();
            mainController.setAirlineComboBoxes();
            mainController.backToTableView(e);
        } else {
            System.out.println("IATA NOT UNIQUE");
        }
    }


}
