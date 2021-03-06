package seng202.group8.Controller.EditObjectControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Deleters.AirportDeleter;
import seng202.group8.Model.Objects.Airport;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by esa46 on 20/09/16.
 */
public class EditAirportViewController {

    private MainController mainController;

    @FXML
    private Pane editAirportPane;
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
    private Button individualAirportBackButton;
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


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void makeVisible() {
        editAirportPane.setVisible(true);
    }

    public void makeInvisible() {
        editAirportPane.setVisible(false);
    }

    /**
     * Returns to table view
     *
     * @param e: The ActionEvent
     */
    @FXML
    public void backToTableView(ActionEvent e) {
        makeInvisible();
        mainController.backToTableView(e);
    }

    /**
     * Loads airport information into editable text fields
     *
     * @param e: The ActionEvent
     */
    @FXML
    public void editAirportData(ActionEvent e) {
        Airport currentAirport = mainController.airportTable.getSelectionModel().getSelectedItem();
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

    /**
     * Exits edit view without saving changes
     *
     * @param e: The ActionEvent
     */
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

    /**
     * Error checks input and displays necessary error messages
     *
     * @param input: A List of Aiport data
     * @return A boolean that is True if there are enough non-empty strings in the input.
     */
    @FXML
    private boolean editAirportErrors(List<String> input) {
        boolean filled = false;
        int size = input.size();
        List<Integer> doubles = Arrays.asList(7, 8, 9);

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

    /**
     * Hides all error messages related to editing an airport
     */
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

    /**
     * Reads user input and makes necessary changes to the airport in the database
     *
     * @param e: The ActionEvent
     */
    @FXML
    public void saveAirportChanges(ActionEvent e) {
        clearEditAirportErrors();
        Airport currentAirport = mainController.airportTable.getSelectionModel().getSelectedItem();

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

        if (currentAirport.getCountry().equals("United States")) {
            if (MainController.getAirportHashMap().get(IATA) != null && !IATA.equals(currentAirport.getFAA())) {
                noErrors = false;
            }
        } else {
            if (MainController.getAirportHashMap().get(IATA) != null && !IATA.equals(currentAirport.getIATA())) {
                noErrors = false;
            }
        }

        if (MainController.getAirportHashMap().get(ICAO) != null && !ICAO.equals(currentAirport.getICAO())) {
            noErrors = false;
        }

        if (noErrors) {

            //Delete the airport to be changed from the database
            DatabaseSaver dbSave = new DatabaseSaver();
            Connection connDelete = Database.connect();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(currentAirport.getAirportID());
            dbSave.deleteAirport(connDelete, ids);
            Database.disconnect(connDelete);

            MainController.getCurrentlyLoadedAirports().remove(currentAirport);

            currentAirport.setTimezone(Integer.parseInt(timezone));
            currentAirport.setDST(DST.charAt(0));
            currentAirport.setCountry(country);
            currentAirport.setCity(city);
            currentAirport.setLongitude(Double.parseDouble(longitude));
            currentAirport.setLatitude(Double.parseDouble(latitude));
            currentAirport.setAltitude(Double.parseDouble(altitude));

            if (!editFAAField.getText().equals("None")) {
                if (currentAirport.getCountry().equals("United States")) {
                    if (MainController.getAirportHashMap().get(currentAirport.getFAA()) != null) {
                        MainController.getAirportHashMap().remove(currentAirport.getFAA());
                        currentAirport.setFAA(FAA);
                        MainController.getAirportHashMap().put(FAA, currentAirport);
                    }
                }
            }
            if (!editAirportIATAField.getText().equals("None")) {
                if (MainController.getAirportHashMap().get(currentAirport.getIATA()) != null) {
                    MainController.getAirportHashMap().remove(currentAirport.getIATA());
                    currentAirport.setIATA(IATA);
                    MainController.getAirportHashMap().put(IATA, currentAirport);
                }
            }
            if (!editAirportICAOField.getText().equals("None")) {
                if (MainController.getAirportHashMap().get(currentAirport.getICAO()) != null) {
                    MainController.getAirportHashMap().remove(currentAirport.getICAO());
                    currentAirport.setICAO(ICAO);
                    MainController.getAirportHashMap().put(ICAO, currentAirport);
                }
            }

            MainController.getCurrentlyLoadedAirports().add(currentAirport);

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
            Connection connSave = Database.connect();
            ObservableList<Airport> newAirports = FXCollections.observableArrayList();
            newAirports.add(currentAirport);
            dbSave.saveAirports(connSave, newAirports);
            Database.disconnect(connDelete);

            mainController.airportTable.setItems(mainController.getCurrentlyLoadedAirports());
            mainController.setAirportComboBoxes();
            mainController.resetView();
            mainController.backToTableView(e);
        }
    }

    /**
     * Deletes selected airport from the database and updates table
     *
     * @param e: The ActionEvent
     */
    @FXML
    public void deleteAirport(ActionEvent e) {
        Airport airport = mainController.airportTable.getSelectionModel().getSelectedItems().get(0);
        //int jp = JOptionPane.showConfirmDialog(null, "WARNING!\nAre you sure you would like to delete " + airport.getName() + "?", "Delete Airport", JOptionPane.YES_NO_OPTION);
        //if(jp == YES_OPTION){
        AirportDeleter airportDeleter = new AirportDeleter();
        airportDeleter.deleteSingleAirport(airport, MainController.getRouteHashMap(), MainController.getCurrentlyLoadedRoutes(), MainController.getAirportHashMap(), MainController.getCurrentlyLoadedAirports());
        //}

        mainController.airportTable.setItems(mainController.getCurrentlyLoadedAirports());
        mainController.routeTable.setItems(mainController.getCurrentlyLoadedRoutes());
        mainController.setAirportsWithoutRoutes(mainController.airportTable);
        mainController.setAirportComboBoxes();
        mainController.resetTables();
        mainController.backToTableView(e);

    }

    /**
     * Prepares the pane showing airport information
     */
    public void setAirportInfo() {
        //Changes visible pane;
        mainController.airportTable.setVisible(false);
        editAirportPane.setVisible(true);
        //Sets all text to corresponding table row item.
        airportNameDisplay.setText(mainController.airportTable.getSelectionModel().getSelectedItem().getName());
        airportIDDisplay.setText(Integer.toString(mainController.airportTable.getSelectionModel().getSelectedItem().getAirportID()));
        airportFAADisplay.setText(mainController.airportTable.getSelectionModel().getSelectedItem().getFAA());
        airportIATADisplay.setText(mainController.airportTable.getSelectionModel().getSelectedItem().getIATA());
        airportICAODisplay.setText(mainController.airportTable.getSelectionModel().getSelectedItem().getICAO());
        airportDSTDisplay.setText(Integer.toString(mainController.airportTable.getSelectionModel().getSelectedItem().getDST()));
        airportTimezoneDisplay.setText(mainController.airportTable.getSelectionModel().getSelectedItem().getOlsonTimezone());
        airportCountryDisplay.setText(mainController.airportTable.getSelectionModel().getSelectedItem().getCountry());
        airportCityDisplay.setText(mainController.airportTable.getSelectionModel().getSelectedItem().getCity());
        airportLatitudeDisplay.setText(Double.toString(mainController.airportTable.getSelectionModel().getSelectedItem().getLatitude()));
        airportLongitudeDisplay.setText(Double.toString(mainController.airportTable.getSelectionModel().getSelectedItem().getLongitude()));
        airportAltitudeDisplay.setText(Double.toString(mainController.airportTable.getSelectionModel().getSelectedItem().getAltitude()));
    }

}
