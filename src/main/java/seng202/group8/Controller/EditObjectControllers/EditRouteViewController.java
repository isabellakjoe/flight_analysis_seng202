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
import seng202.group8.Controller.AddObjectControllers.AddAirlineViewController;
import seng202.group8.Controller.AddObjectControllers.AddAirportViewController;
import seng202.group8.Controller.AddObjectControllers.AddRouteViewController;
import seng202.group8.Controller.FlightViewController;
import seng202.group8.Controller.MainController;
import seng202.group8.Controller.MapViewController;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by esa46 on 20/09/16.
 */
public class EditRouteViewController {



    @FXML
    private Pane editRoutePane;
    @FXML
    private TextField editSourceField;
    @FXML
    private TextField editDestinationField;
    @FXML
    private TextField editStopsField;
    @FXML
    private TextField editEquipmentField;
    @FXML
    private CheckBox editCodeshareField;
    @FXML
    private Button saveRouteChangesButton;
    @FXML
    private Button cancelRouteChangesButton;
    @FXML
    private Button editRouteDataButton;
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
    private Button individualRouteBackButton;
    @FXML
    private Text editRouteSourceError;
    @FXML
    private Text editRouteDestError;
    @FXML
    private Text editRouteStopsError;
    @FXML
    private Text editRouteEquipError;

    private MainController mainController;

    @FXML
    public void backToTableView(ActionEvent e){
        mainController.backToTableView(e);
    }

    @FXML
    public void editRouteData(ActionEvent e) {
        Route currentRoute = mainController.routeTable.getSelectionModel().getSelectedItem();
        editSourceField.setVisible(true);
        editDestinationField.setVisible(true);
        editStopsField.setVisible(true);
        editEquipmentField.setVisible(true);
        editCodeshareField.setVisible(true);
        routeShareDisplay.setVisible(false);

        individualRouteBackButton.setVisible(false);
        editRouteDataButton.setVisible(false);

        saveRouteChangesButton.setVisible(true);
        cancelRouteChangesButton.setVisible(true);

        editSourceField.setText(currentRoute.getSourceAirportName());
        editDestinationField.setText(currentRoute.getDestinationAirportName());
        editStopsField.setText(Integer.toString(currentRoute.getStops()));
        if (currentRoute.getEquipment() != null) {
            editEquipmentField.setText(currentRoute.getEquipment());
        } else {
            editEquipmentField.setText("None");
        }
        if (currentRoute.isCodeshare()) {
            editCodeshareField.setSelected(true);
        } else {
            editCodeshareField.setSelected(false);
        }
    }

    @FXML
    public void cancelRouteChanges(ActionEvent e) {
        clearEditRouteErrors();
        individualRouteBackButton.setVisible(true);
        editRouteDataButton.setVisible(true);

        saveRouteChangesButton.setVisible(false);
        cancelRouteChangesButton.setVisible(false);
        editSourceField.setVisible(false);
        editDestinationField.setVisible(false);
        editStopsField.setVisible(false);
        editEquipmentField.setVisible(false);
        editCodeshareField.setVisible(false);
        routeShareDisplay.setVisible(true);
    }

    @FXML
    private boolean editRouteErrors(List<String> input) {

        boolean filled = false;
        int size = input.size();


        int count = 0;
        for (int i = 0; i < size; i++) {
            String current = input.get(i);

            if (current == null || current.equals("")) {

                switch (i) {
                    case 0:
                        editRouteSourceError.setVisible(true);
                        break;
                    case 1:
                        editRouteDestError.setVisible(true);
                        break;
                    case 2:
                        editRouteStopsError.setVisible(true);
                        break;
                    case 3:
                        editRouteEquipError.setVisible(true);
                        break;

                }
            } else {
                count += 1;
            }

            if ((i == 2) && !(current.equals(""))) {

                try {
                    Integer.parseInt(input.get(i));
                    count += 1;

                } catch (NumberFormatException e) {
                    editRouteStopsError.setVisible(true);
                }
            }
        }


        if (count == 5) {
            filled = true;
        }
        return filled;

    }

    @FXML
    private void clearEditRouteErrors() {
        editRouteSourceError.setVisible(false);
        editRouteDestError.setVisible(false);
        editRouteStopsError.setVisible(false);
        editRouteEquipError.setVisible(false);

    }

    @FXML
    public void saveRouteChanges(ActionEvent e) {
        clearEditRouteErrors();
        Route currentRoute = mainController.routeTable.getSelectionModel().getSelectedItem();

        String source = editSourceField.getText();
        String destination = editDestinationField.getText();
        String stops = editStopsField.getText();
        String equipment = editEquipmentField.getText();

        List<String> routeData = Arrays.asList(source, destination, stops, equipment);
        boolean noErrors = editRouteErrors(routeData);

        if(noErrors) {
            //Delete the route from the database
            Database db = new Database();
            DatabaseSaver dbSave = new DatabaseSaver();
            Connection connDelete = db.connect();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(currentRoute.getRouteID());
            dbSave.deleteRoutes(connDelete, ids);
            db.disconnect(connDelete);

            currentRoute.setSourceAirportName(source);
            currentRoute.setDestinationAirportName(destination);
            //Add methods to find airports as well
            currentRoute.setStops(Integer.parseInt(stops));

            if (!editEquipmentField.getText().equals("None")) {
                currentRoute.setEquipment(equipment);
            }
            if (editCodeshareField.isSelected()) {
                currentRoute.setCodeshare(true);
                routeShareDisplay.setText("Yes");
            } else {
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
            editCodeshareField.setVisible(false);
            routeShareDisplay.setVisible(true);

            individualRouteBackButton.setVisible(true);
            editRouteDataButton.setVisible(true);

            saveRouteChangesButton.setVisible(false);
            cancelRouteChangesButton.setVisible(false);

            //Save the updated route to the database
            Connection connSave = db.connect();
            ObservableList<Route> newRoutes = FXCollections.observableArrayList();
            newRoutes.add(currentRoute);
            dbSave.saveRoutes(connSave, newRoutes);
            db.disconnect(connDelete);

            mainController.setRouteComboBoxes();
        }
    }


    public void setRouteInfo(){
        //Changes visible pane;
        mainController.routeTable.setVisible(false);
        editRoutePane.setVisible(true);
        //Sets all text to corresponding table row item.
        routeAirlineDisplay.setText(mainController.routeTable.getSelectionModel().getSelectedItem().getAirlineName());
        routeSourceDisplay.setText(mainController.routeTable.getSelectionModel().getSelectedItem().getSourceAirportName());
        routeDestinationDisplay.setText(mainController.routeTable.getSelectionModel().getSelectedItem().getDestinationAirportName());
        routeEquipmentDisplay.setText(mainController.routeTable.getSelectionModel().getSelectedItem().getEquipment());
        routeShareDisplay.setText(mainController.routeTable.getSelectionModel().getSelectedItem().getCodeshareString());
        routeStopsDisplay.setText(Integer.toString(mainController.routeTable.getSelectionModel().getSelectedItem().getStops()));
    }

    public void makeInvisible() {
        editRoutePane.setVisible(false);
    }

    public void makeVisible() {
        editRoutePane.setVisible(true);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
