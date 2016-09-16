package seng202.group8.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.DatabaseMethods.RouteDatabaseLoader;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.FileLoader;
import seng202.group8.Model.Searchers.RouteSearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ikj11 on 15/09/16.
 */
public class SearchRouteController {

    private MainController mainController;

    @FXML
    private Button routeAdvancedButton;
    @FXML
    private Button routeSearch;
    @FXML
    private Button resetRouteSearch;
    @FXML
    private Button routeBackButton;
    @FXML
    private Tab searchRouteTab;
    @FXML
    private Button routeAddButton;
    @FXML
    private Button editRouteDataButton;
    @FXML
    public TextField airlineSearchID;

    @FXML
    private TextField airlineSearch;

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
    private Button individualRouteBackButton;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    private void routeSearch(ActionEvent e) {
        mainController.resetView();
        mainController.tableView.setVisible(true);
        mainController.routeTable.setVisible(true);
        mainController.routePane.setVisible(false);


        RouteSearcher searcher = new RouteSearcher(mainController.currentlyLoadedRoutes);
        String airline = airlineSearch.getText();
        String airlineID = airlineSearchID.getText();
        String sourceAirport = (String) sourceSearch.getValue();
        String sourceID = sourceIDSearch.getText();
        String destinationAirport = (String) destinationSearch.getValue();
        String destinationID = destinationIDSearch.getText();
        String stops = (String) stopoverSearch.getValue();
        String codeshareStatus = (String) codeshareSearch.getValue();
        String equipment = (String) equipmentSearch.getValue();

        if (airline.length() > 0) {
            searcher.routesOfAirline(airline);
        }

        if (airlineID.length() > 0) {
            try {
                int intAirlineID = Integer.parseInt(airlineID);
                searcher.routesOfAirlineID(intAirlineID);
            } catch (NumberFormatException exception) {
            }
        }

        if (sourceAirport != null && !sourceAirport.equals("ALL")) {
            searcher.routesOfSource(sourceAirport);
        }

        if (sourceID.length() > 0) {
            try {
                int intSourceID = Integer.parseInt(sourceID);
                searcher.routesOfSourceID(intSourceID);
            } catch (NumberFormatException exception) {
            }
        }

        if (destinationAirport != null && !destinationAirport.equals("ALL")) {
            searcher.routesOfDestination(destinationAirport);
        }

        if (destinationID.length() > 0) {
            try {
                int intDestID = Integer.parseInt(destinationID);
                searcher.routesOfDestinationID(intDestID);
            } catch (NumberFormatException exception) {
            }
        }

        if (stops != null && !stops.equals("ALL")) {
            try {
                int intStops = Integer.parseInt(stops);
                searcher.routesOfStops(intStops);
            } catch (NumberFormatException exception) {
            }
        }

        if (codeshareStatus != null && !codeshareStatus.equals("ALL")) {
            searcher.routesOfCodeshare(codeshareStatus);
        }

        if (equipment != null && !equipment.equals("ALL")) {
            searcher.routesOfEquipment(equipment);
        }

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();

        mainController.routeTable.setItems(matchingRoutes);
    }
    @FXML
    private void switchToAddRoute(ActionEvent e) {
        mainController.resetView();
        mainController.addRouteViewController.makeVisible();
    }
    @FXML
    private void routeSearchBack(ActionEvent e) {
        routeAdvancedButton.setVisible(true);
        routeBackButton.setVisible(false);
        equipmentSearch.setVisible(false);
        codeshareSearch.setVisible(false);
        airlineSearchID.setVisible(false);
        destinationIDSearch.setVisible(false);
        sourceIDSearch.setVisible(false);
        routeSearch.setLayoutY(250);
        resetRouteSearch.setLayoutY(250);
        routeAddButton.setVisible(true);
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
        routeSearch.setLayoutY(455);
        resetRouteSearch.setLayoutY(455);
        routeAddButton.setVisible(false);
    }

    public void setRouteComboBoxes() {
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
        for (int i = 0; i < mainController.currentlyLoadedRoutes.size(); i++) {
            if (mainController.currentlyLoadedRoutes.get(i).getSourceAirport().getIATA() != null) {
                sources.add(mainController.currentlyLoadedRoutes.get(i).getSourceAirport().getIATA());
            } else {
                sources.add(mainController.currentlyLoadedRoutes.get(i).getSourceAirport().getICAO());
            }
            if (mainController.currentlyLoadedRoutes.get(i).getDestinationAirport().getIATA() != null) {
                destinations.add(mainController.currentlyLoadedRoutes.get(i).getDestinationAirport().getIATA());
            } else {
                destinations.add(mainController.currentlyLoadedRoutes.get(i).getDestinationAirport().getICAO());
            }
            equipment.add(mainController.currentlyLoadedRoutes.get(i).getEquipment());
            if (mainController.currentlyLoadedRoutes.get(i).getStops() > stops) {
                stops = mainController.currentlyLoadedRoutes.get(i).getStops();
            }
        }

        ArrayList<String> stopsList = new ArrayList<String>();
        stopsList.add("ALL");

        for (int i = 0; i <= stops; i++) {
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
    public void saveRouteChanges(ActionEvent e) {
        Route currentRoute = mainController.routeTable.getSelectionModel().getSelectedItem();

        //Delete the route from the database
        Database db = new Database();
        DatabaseSaver dbSave = new DatabaseSaver();
        Connection connDelete = db.connect();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(currentRoute.getRouteID());
        dbSave.deleteRoutes(connDelete, ids);
        db.disconnect(connDelete);

        currentRoute.setSourceAirportName(editSourceField.getText());
        currentRoute.setDestinationAirportName(editDestinationField.getText());
        //Add methods to find airports as well
        currentRoute.setStops(Integer.parseInt(editStopsField.getText()));

        if (!editEquipmentField.getText().equals("None")) {
            currentRoute.setEquipment(editEquipmentField.getText());
        }
        if (editCodeshareField.getText().equals("Yes")) {
            currentRoute.setCodeshare(true);
            mainController.routeShareDisplay.setText("Yes");
        } else if (editCodeshareField.getText().equals("No")) {
            currentRoute.setCodeshare(false);
            mainController.routeShareDisplay.setText("No");
        }


        mainController.routeSourceDisplay.setText(currentRoute.getSourceAirportName());
        mainController.routeDestinationDisplay.setText(currentRoute.getDestinationAirportName());
        mainController.routeStopsDisplay.setText(Integer.toString(currentRoute.getStops()));
        mainController.routeEquipmentDisplay.setText(currentRoute.getEquipment());

        editSourceField.setVisible(false);
        editDestinationField.setVisible(false);
        editStopsField.setVisible(false);
        editEquipmentField.setVisible(false);
        editCodeshareField.setVisible(false);
        ;


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


        setRouteComboBoxes();
    }

    @FXML
    public void editRouteData(ActionEvent e) {
        Route currentRoute = mainController.routeTable.getSelectionModel().getSelectedItem();
        editSourceField.setVisible(true);
        editDestinationField.setVisible(true);
        editStopsField.setVisible(true);
        editEquipmentField.setVisible(true);
        editCodeshareField.setVisible(true);

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
        if (currentRoute.isCodeshare() == true) {
            editCodeshareField.setText("Yes");
        } else {
            editCodeshareField.setText("No");
        }


    }
    @FXML
    public void cancelRouteChanges(ActionEvent e) {


        individualRouteBackButton.setVisible(true);
        editRouteDataButton.setVisible(true);

        saveRouteChangesButton.setVisible(false);
        cancelRouteChangesButton.setVisible(false);
        editSourceField.setVisible(false);
        editDestinationField.setVisible(false);
        editStopsField.setVisible(false);
        editEquipmentField.setVisible(false);
        editCodeshareField.setVisible(false);
        ;

    }
    /* Method to open up a file chooser for the user to select the Route Data file with error handling */
    public void addRouteData(ActionEvent e) {
        try {
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

                mainController.currentlyLoadedRoutes = routes;
                mainController.routeTable.setItems(routes);
                setRouteComboBoxes();
                mainController.resetView();
                mainController.tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }

    }

    @FXML
    private void resetRouteSearch(ActionEvent e){
        mainController.routeTable.setItems(mainController.currentlyLoadedRoutes);

    }




}
