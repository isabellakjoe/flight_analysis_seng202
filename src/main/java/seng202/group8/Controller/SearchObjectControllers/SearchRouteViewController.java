package seng202.group8.Controller.SearchObjectControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Searchers.RouteSearcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esa46 on 21/09/16.
 */
public class SearchRouteViewController {
    private MainController mainController;

    @FXML
    private TextField airlineSearch;
    @FXML
    private TextField airlineSearchID;
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
    private Button routeAddButton;
    @FXML
    private Button routeAdvancedButton;
    @FXML
    private Button routeSearch;
    @FXML
    private Button resetRouteSearch;
    @FXML
    private Button routeBackButton;


    public void setCodeshareCombobox(ArrayList<String> codeshareStatuses){
        codeshareSearch.getItems().clear();
        codeshareSearch.getItems().setAll(codeshareStatuses);
    }

    public void setSourceCombobox(List sortedSources) {
        sourceSearch.getItems().clear();
        sourceSearch.getItems().setAll(sortedSources);
    }

    public void setDestinationCombobox(List sortedDestinations) {
        destinationSearch.getItems().clear();
        destinationSearch.getItems().setAll(sortedDestinations);
    }

    public void setEquipmentCombobox(List sortedEquipment) {
        equipmentSearch.getItems().clear();
        equipmentSearch.getItems().setAll(sortedEquipment);
    }

    public void setStopoverCombobox(List stopsList) {
        stopoverSearch.getItems().clear();
        stopoverSearch.getItems().setAll(stopsList);
    }

    @FXML
    private void resetRouteSearch() {
        /* Clears all search fields*/
        airlineSearch.clear();
        sourceSearch.setValue(null);
        destinationSearch.setValue(null);
        stopoverSearch.setValue(null);
        equipmentSearch.setValue(null);
        codeshareSearch.setValue(null);
        airlineSearchID.clear();
        sourceIDSearch.clear();
        destinationIDSearch.clear();

        mainController.routeTable.setItems(mainController.getCurrentlyLoadedRoutes());
    }

    @FXML
    private void switchToAddRoute(ActionEvent e){
        mainController.switchToAddRoute(e);
    }
    @FXML
    private void routeSearch(ActionEvent e) {
        mainController.resetView();
        mainController.tableView.setVisible(true);
        mainController.routeTable.setVisible(true);


        RouteSearcher searcher = new RouteSearcher(mainController.getCurrentlyLoadedRoutes());

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
                System.out.print(intStops);
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

        //This is a patch for deliverable two, needs to be implemented properly in the loader class at some point

        for (Route route: matchingRoutes) {
            route.setAirlineName(route.getAirline().getName());
            route.setSourceAirportName(route.getSourceAirport().getName());
            route.setDestinationAirportName(route.getDestinationAirport().getName());
        }

        mainController.routeTable.setItems(matchingRoutes);
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

    @FXML
    private void routeSearchBack() {
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
        /*Clear hidden parameters*/
        equipmentSearch.setValue(null);
        codeshareSearch.setValue(null);
        airlineSearchID.clear();
        sourceIDSearch.clear();
        destinationIDSearch.clear();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
