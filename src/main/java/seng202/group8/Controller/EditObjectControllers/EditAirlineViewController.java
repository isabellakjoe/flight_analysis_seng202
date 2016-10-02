package seng202.group8.Controller.EditObjectControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Deleters.AirlineDeleter;
import seng202.group8.Model.Objects.Airline;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by esa46 on 20/09/16.
 */
public class EditAirlineViewController {

    @FXML
    private Pane editAirlinePane;

    @FXML
    private Button editAirlineDataButton;
    @FXML
    private TextField editCallsignField;
    @FXML
    private TextField editAirlineIATAField;
    @FXML
    private TextField editAirlineICAOField;
    @FXML
    private TextField editAliasField;
    @FXML
    private CheckBox editActiveField;
    @FXML
    private TextField editAirlineCountryField;
    @FXML
    private Button saveAirlineChangesButton;
    @FXML
    private Button cancelAirlineChangesButton;
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
    private Button individualAirlineBackButton;
    @FXML
    private Text editAirlineAliasError;
    @FXML
    private Text editAirlineCountryError;
    @FXML
    private Text editAirlineAliasErrorMessage;

    private MainController mainController;

    @FXML
    public void backToTableView(ActionEvent e){
        makeInvisible();
        mainController.backToTableView(e);
    }

    @FXML
    public void editAirlineData(ActionEvent e) {
        Airline currentAirline = mainController.airlineTable.getSelectionModel().getSelectedItem();

        editCallsignField.setVisible(true);
        editAirlineIATAField.setVisible(true);
        editAirlineICAOField.setVisible(true);
        editAliasField.setVisible(true);
        editActiveField.setVisible(true);
        editAirlineCountryField.setVisible(true);

        airlineActiveDisplay.setVisible(false);

        editAirlineDataButton.setVisible(false);
        individualAirlineBackButton.setVisible(false);
        saveAirlineChangesButton.setVisible(true);
        cancelAirlineChangesButton.setVisible(true);


        editAirlineCountryField.setText(currentAirline.getCountry());
        if (currentAirline.getCallsign() != null) {
            editCallsignField.setText(currentAirline.getCallsign());
        } else {
            editCallsignField.setText("None");
        }
        if (currentAirline.getIATA() != null) {
            editAirlineIATAField.setText(currentAirline.getIATA());
        } else {
            editAirlineIATAField.setText("None");
        }
        if (currentAirline.getICAO() != null) {
            editAirlineICAOField.setText(currentAirline.getICAO());
        } else {
            editAirlineICAOField.setText("None");
        }
        if (currentAirline.getAlias() != null) {
            editAliasField.setText(currentAirline.getAlias());
        } else {
            editAliasField.setText("None");
        }
        if (currentAirline.isActive() == true) {
            editActiveField.setSelected(true);
        } else {
            editActiveField.setSelected(false);
        }


    }

    @FXML
    public void cancelAirlineChanges(ActionEvent e) {
        clearEditAirlineErrors();
        editAirlineDataButton.setVisible(true);
        individualAirlineBackButton.setVisible(true);
        saveAirlineChangesButton.setVisible(false);
        cancelAirlineChangesButton.setVisible(false);
        editCallsignField.setVisible(false);
        editAirlineIATAField.setVisible(false);
        editAirlineICAOField.setVisible(false);
        editAliasField.setVisible(false);
        editActiveField.setVisible(false);
        editAirlineCountryField.setVisible(false);

    }
    @FXML
    private void clearEditAirlineErrors() {
        editAirlineAliasError.setVisible(false);
        editAirlineCountryError.setVisible(false);
        editAirlineAliasErrorMessage.setVisible(false);

    }
    @FXML
    private boolean editAirlineErrors(List<String> input) {

        boolean filled = false;
        int size = input.size();


        int count = 0;
        for (int i = 0; i < size; i++) {
            String current = input.get(i);
            if (current.equals("")) {
                switch (i) {
                    case 0:
                        editAirlineAliasError.setVisible(true);
                        editAirlineAliasErrorMessage.setVisible(true);
                        break;
                    case 1:
                        editAirlineCountryError.setVisible(true);
                        break;
                }
            } else {
                count += 1;
            }
        }

        if (count == 2) {
            filled = true;
        }
        return filled;

    }

    @FXML
    public void saveAirlineChanges(ActionEvent e) {
        clearEditAirlineErrors();
        Airline currentAirline = mainController.airlineTable.getSelectionModel().getSelectedItem();

        String callsign = editCallsignField.getText();
        String IATA = editAirlineIATAField.getText();
        String ICAO = editAirlineICAOField.getText();
        String alias = editAliasField.getText();
        String country = editAirlineCountryField.getText();

        List<String> airlineData = Arrays.asList(alias, country);
        boolean noErrors = editAirlineErrors(airlineData);

        if (MainController.getAirlineHashMap().get(IATA) != null && !IATA.equals(currentAirline.getIATA())) {
            noErrors = false;
        }
        if (MainController.getAirlineHashMap().get(ICAO) != null && !ICAO.equals(currentAirline.getICAO())) {
            noErrors = false;
        }

        if(noErrors) {
            //Delete the current airline object from the database
            DatabaseSaver dbSave = new DatabaseSaver();
            Connection connDelete = Database.connect();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(currentAirline.getAirlineID());
            //Remove the airline from the database
            dbSave.deleteAirlines(connDelete, ids);
            Database.disconnect(connDelete);

            //Remove the airline from the GUI
            MainController.getCurrentlyLoadedAirlines().remove(currentAirline);


            if (!editCallsignField.getText().equals("None")) {
                currentAirline.setCallsign(callsign);
            }
            if (!editAirlineIATAField.getText().equals("None")) {
                if (MainController.getAirlineHashMap().get(currentAirline.getIATA()) != null) {
                    MainController.getAirlineHashMap().remove(currentAirline.getIATA());
                }
                currentAirline.setIATA(IATA);
                MainController.getAirlineHashMap().put(IATA, currentAirline);
            }
            if (!editAirlineICAOField.getText().equals("None")) {
                currentAirline.setICAO(ICAO);
                if (MainController.getAirlineHashMap().get(currentAirline.getIATA()) == null) {
                    MainController.getAirlineHashMap().put(ICAO, currentAirline);
                }
            }
            if (!editAliasField.getText().equals("None")) {
                currentAirline.setAlias(alias);
            }
            if (editActiveField.isSelected()) {
                currentAirline.setActive(true);
                airlineActiveDisplay.setText("Yes");
            } else if (!(editActiveField.isSelected())) {
                currentAirline.setActive(false);
                airlineActiveDisplay.setText("No");
            }
            currentAirline.setCountry(country);

            airlineIDDisplay.setText(Integer.toString(currentAirline.getAirlineID()));
            airlineCallsignDisplay.setText(currentAirline.getCallsign());
            airlineIATADisplay.setText(currentAirline.getIATA());
            airlineICAODisplay.setText(currentAirline.getICAO());
            airlineAliasDisplay.setText(currentAirline.getAlias());
            airlineCountryDisplay.setText(currentAirline.getCountry());


            editCallsignField.setVisible(false);
            editAirlineIATAField.setVisible(false);
            editAirlineICAOField.setVisible(false);
            editAliasField.setVisible(false);
            editActiveField.setVisible(false);
            editAirlineCountryField.setVisible(false);

            editAirlineDataButton.setVisible(true);
            individualAirlineBackButton.setVisible(true);
            saveAirlineChangesButton.setVisible(false);
            cancelAirlineChangesButton.setVisible(false);
            airlineActiveDisplay.setVisible(true);

            //Save the updated airline to the database
            Connection connSave = Database.connect();
            ObservableList<Airline> newAirlines = FXCollections.observableArrayList();
            newAirlines.add(currentAirline);
            dbSave.saveAirlines(connSave, newAirlines);
            Database.disconnect(connDelete);

            MainController.getCurrentlyLoadedAirlines().add(currentAirline);

            mainController.airlineTable.setItems(mainController.getCurrentlyLoadedAirlines());
            mainController.resetView();
            mainController.setAirlineComboBoxes();
            mainController.backToTableView(e);
        }
    }

    public void setAirlineInfo(){
        //Changes visible pane;
        mainController.airlineTable.setVisible(false);
        editAirlinePane.setVisible(true);
        //Sets all text to corresponding table row item.
        airlineNameDisplay.setText(mainController.airlineTable.getSelectionModel().getSelectedItem().getName());
        airlineIDDisplay.setText(Integer.toString(mainController.airlineTable.getSelectionModel().getSelectedItem().getAirlineID()));
        airlineCountryDisplay.setText(mainController.airlineTable.getSelectionModel().getSelectedItem().getCountry());
        airlineIATADisplay.setText(mainController.airlineTable.getSelectionModel().getSelectedItem().getIATA());
        airlineICAODisplay.setText(mainController.airlineTable.getSelectionModel().getSelectedItem().getICAO());
        airlineCallsignDisplay.setText(mainController.airlineTable.getSelectionModel().getSelectedItem().getCallsign());
        airlineAliasDisplay.setText(mainController.airlineTable.getSelectionModel().getSelectedItem().getAlias());
        if (mainController.airlineTable.getSelectionModel().getSelectedItem().isActive()) {
            airlineActiveDisplay.setText("Yes");
        } else {
            airlineActiveDisplay.setText("No");
        }
    }

/* DO NOT DELETE PLEASE!!

*/
    public void deleteAirline(ActionEvent e){
        Airline airline = mainController.airlineTable.getSelectionModel().getSelectedItem();
        //int jp = JOptionPane.showConfirmDialog(null, "WARNING!\nAre you sure you would like to delete " + airline.getName() + "?", "Delete Airline", JOptionPane.YES_NO_OPTION);
        //if(jp == YES_OPTION){
            AirlineDeleter airlineDeleter = new AirlineDeleter();
            airlineDeleter.deleteSingleAirline(airline, MainController.getRouteHashMap(), MainController.getCurrentlyLoadedRoutes(), MainController.getAirlineHashMap(), MainController.getCurrentlyLoadedAirlines());
        //}

        System.out.println("Deleted Airline + Route");
        System.out.println(MainController.getCurrentlyLoadedRoutes().size());

        mainController.airlineTable.setItems(mainController.getCurrentlyLoadedAirlines());
        mainController.routeTable.setItems(mainController.getCurrentlyLoadedRoutes());
        //Call the method here to make sure routes deleted by removal of airline
        mainController.setAirportsWithoutRoutes(mainController.airportTable);
        mainController.resetView();
        mainController.setAirlineComboBoxes();
        mainController.backToTableView(e);

    }

    public void makeInvisible() {
        editAirlinePane.setVisible(false);
    }

    public void makeVisible() {
        editAirlinePane.setVisible(true);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
