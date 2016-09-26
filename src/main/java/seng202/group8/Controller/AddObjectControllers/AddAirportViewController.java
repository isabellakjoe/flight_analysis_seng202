package seng202.group8.Controller.AddObjectControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Parsers.AirportParser;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * Created by swa158 on 15/09/16.
 */
public class
AddAirportViewController {

    private MainController mainController;


    @FXML
    private Text addAirportIDErrorEmpty;
    @FXML
    private Text addAirportNameErrorEmpty;
    @FXML
    private Text addAirportCityErrorEmpty;
    @FXML
    private Text addAirportCountryErrorEmpty;
    @FXML
    private Text addAirportLatErrorEmpty;
    @FXML
    private Text addAirportLongErrorEmpty;
    @FXML
    private Text addAirportAltErrorEmpty;
    @FXML
    private Text addAirportTimeErrorEmpty;
    @FXML
    private Text addAirportDSTErrorEmpty;
    @FXML
    private Text addAirportOlsenErrorEmpty;
    @FXML
    private Text addAirportIDErrorType;
    @FXML
    private Text addAirportCodeErrorType;
    @FXML
    private Text addAirportLatErrorType;
    @FXML
    private Text addAirportLongErrorType;
    @FXML
    private Text addAirportAltErrorType;
    @FXML
    private Text addAirportTimeErrorType;
    @FXML
    private Text addAirportDSTErrorType;

    @FXML
    private Pane addAirportViewPane;
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
    private void cancelAddedAirport(ActionEvent e) {
        clearAirportErrors();
        addAirportViewPane.setVisible(false);
        mainController.backToTableView(e);
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
    private void saveAddedAirport(ActionEvent e) {
        clearAirportErrors();
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


        List<String> airportDataList = Arrays.asList(airportID, name, city, country, code, ICAO, latitude, longitude, altitude, timezone, DST, olsen);

        String data = (airportID + "," + name + "," + city + "," + country + "," + code + "," + ICAO + "," + latitude + "," + longitude + "," + altitude + "," + timezone + "," + DST + "," + olsen);

        boolean noErrors = addAirportError(airportDataList);

        if (noErrors && mainController.getAirportHashMap().get(ICAO) == null && mainController.getAirportHashMap().get(code) == null) {
            Airport newAirport = parser.createSingleAirport(data);
            ObservableList<Airport> airports = FXCollections.observableArrayList();
            airports.add(newAirport);
            if (newAirport != null) {
                //Add the new airport to the database here
                Database db = new Database();
                DatabaseSaver dbSave = new DatabaseSaver();
                DatabaseSearcher dbSearch = new DatabaseSearcher();
                Connection connSave = db.connect();
                Connection connSearch = db.connect();
                dbSave.saveAirports(connSave, airports);
                db.disconnect(connSave);
                String sql = dbSearch.buildAirportSearch("airportid", airportID);
                ObservableList<Airport> addedAirport = dbSearch.searchForAirportByOption(connSearch, sql);
                db.disconnect(connSearch);
                mainController.addToCurrentlyLoadedAirports(addedAirport.get(0));
            }
            mainController.airportTable.setItems(mainController.getCurrentlyLoadedAirports());
            mainController.resetView();
            mainController.setAirportComboBoxes();
            mainController.backToTableView(e);

            System.out.println(mainController.airportTable);
        }
    }

    @FXML
    public void clearAirportErrors() {
        addAirportIDErrorEmpty.setVisible(false);
        addAirportNameErrorEmpty.setVisible(false);
        addAirportCityErrorEmpty.setVisible(false);
        addAirportCountryErrorEmpty.setVisible(false);
        addAirportLatErrorEmpty.setVisible(false);
        addAirportLongErrorEmpty.setVisible(false);
        addAirportAltErrorEmpty.setVisible(false);
        addAirportTimeErrorEmpty.setVisible(false);
        addAirportDSTErrorEmpty.setVisible(false);
        addAirportOlsenErrorEmpty.setVisible(false);

        addAirportIDErrorType.setVisible(false);
        addAirportCodeErrorType.setVisible(false);
        addAirportAltErrorType.setVisible(false);
        addAirportTimeErrorType.setVisible(false);
        addAirportDSTErrorType.setVisible(false);
        addAirportLatErrorType.setVisible(false);
        addAirportLongErrorType.setVisible(false);
    }

    @FXML
    public boolean addAirportError(List<String> input) {

        boolean filled = false;
        int size = input.size();
        List<Integer> ints = Arrays.asList(0, 8, 9);
        List<Integer> doubles = Arrays.asList(6, 7);

        int count = 0;
        for (int i = 0; i < size; i++) {
            String current = input.get(i);
            if (current.equals("")) {
                switch (i) {

                    case 0:
                        addAirportIDErrorEmpty.setVisible(true);
                        break;
                    case 1:
                        addAirportNameErrorEmpty.setVisible(true);
                        break;
                    case 2:
                        addAirportCityErrorEmpty.setVisible(true);
                        break;
                    case 3:
                        addAirportCountryErrorEmpty.setVisible(true);
                        break;
                    case 6:
                        addAirportLatErrorEmpty.setVisible(true);
                        break;
                    case 7:
                        addAirportLongErrorEmpty.setVisible(true);
                        break;
                    case 8:
                        addAirportAltErrorEmpty.setVisible(true);
                        break;
                    case 9:
                        addAirportTimeErrorEmpty.setVisible(true);
                        break;
                    case 10:
                        addAirportDSTErrorEmpty.setVisible(true);
                        break;
                    case 11:
                        addAirportOlsenErrorEmpty.setVisible(true);
                        break;
                }
            } else {
                count += 1;
            }

            if (ints.contains(i) && !(current.equals(""))) {

                try {
                    Integer.parseInt(input.get(i));
                    count += 1;

                } catch (NumberFormatException e) {

                    switch (i) {
                        case 0:
                            addAirportIDErrorType.setVisible(true);
                            break;
                        case 8:
                            addAirportAltErrorType.setVisible(true);
                            break;
                        case 9:
                            addAirportTimeErrorType.setVisible(true);
                            break;
                    }
                }
            } else if (doubles.contains(i) && !(current.equals(""))) {
                try {
                    Double.parseDouble(input.get(i));

                    count += 1;
                } catch (NumberFormatException e) {

                    switch (i) {
                        case 6:
                            addAirportLatErrorType.setVisible(true);
                            break;
                        case 7:
                            addAirportLongErrorType.setVisible(true);
                            break;

                    }
                }
            }

        }

        if (count == 17) {
            filled = true;
        }
        return filled;

    }


    public void makeInvisible() {
        addAirportViewPane.setVisible(false);
    }

    public void makeVisible() {
        addAirportViewPane.setVisible(true);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
