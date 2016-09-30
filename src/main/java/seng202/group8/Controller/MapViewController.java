package seng202.group8.Controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Searchers.RouteSearcher;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by esa46 on 15/09/16.
 */
public class MapViewController {

    private MainController mainController;
    private WebEngine webEngine;
    @FXML
    private CheckBox displayAllAirports;
    @FXML
    private CheckBox displayAllRoutes;
    @FXML
    private TextField equipmentSearchBox;
    @FXML
    private TextField airportSearchBox;
    @FXML
    private WebView webView;


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("maps.html").toExternalForm());
    }

    // Action Event for 'Display Airports' checkbox
    @FXML
    public void mapAirports(ActionEvent e) {
        if (displayAllAirports.isSelected()) {
            displayAirports(mainController.getCurrentlyLoadedAirports());
        } else {
            clearAirports();
        }
    }

    // Display markers for list of airports
    private void displayAirports(List airportList) {
        if (airportList.size() < 1000 && airportList.size() != 0) {
            showAirportMarkers(airportList);
        } else if (airportList.isEmpty()) {
            JOptionPane jp = new JOptionPane();
            jp.setSize(600, 600);
            jp.showMessageDialog(null, "No Airports to display.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            displayAllAirports.setSelected(false);
        } else {
            JOptionPane jp = new JOptionPane();
            jp.setSize(600, 600);
            JLabel msgLabel = new JLabel("Are you sure you want to display " +  airportList.size() + " airports? \nThis may take a while...", JLabel.CENTER);
            int reply = jp.showConfirmDialog(null, msgLabel, "Error Message", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                showAirportMarkers(airportList);
            } else {
                displayAllAirports.setSelected(false);
            }
        }
    }


    // Method that clears, creates and displays airport markers
    private void showAirportMarkers(List airports) {
        Iterator i = airports.iterator();
        while (i.hasNext()) {
            Airport airport = (Airport) i.next();
            Double latitude = airport.getLatitude();
            Double longitude = airport.getLongitude();
//            String createInfoScript = "createInfoString(" + airport.getAirportID() + ", " + airport.getName() + ", " +
//                    airport.getCity() + ", " + airport.getCountry() + ", " + airport.getIATA() + ", " + airport.getFAA() +
//                    airport.getICAO() + ", " + airport.getLatitude() + ", " + airport.getLongitude() + ", " +
//                    airport.getAltitude() + ", " + airport.getTimezone() + ", " + airport.getDST() + ", " +
//                    airport.getOlsonTimezone() + ')';
//            webEngine.executeScript(createInfoScript);
            String scriptToExecute = "createMarker(" + latitude + ", " + longitude + ")";
            webEngine.executeScript(scriptToExecute);
            webEngine.executeScript("showMarkers()");
        }
    }

    // Remove currently displayed airport markers
    private void clearAirports() { webEngine.executeScript("clearMarkers()");}


    // Action Event for 'Display Routes' checkbox
    @FXML
    public void mapRoutes(ActionEvent e) {
        if (displayAllRoutes.isSelected()) {
            displayRoutes(mainController.getCurrentlyLoadedRoutes());
        } else {
            clearRoutes();
        }
    }

    // Method for displaying routes on map
    private void displayRoutes(List routes) {
        if (routes.size() < 1000 && routes.size() != 0) {
            createMapRoutes(routes);
        } else if (routes.isEmpty()) {
            JOptionPane jp = new JOptionPane();
            jp.setSize(600, 600);
            jp.showMessageDialog(null, "No Routes to display.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            displayAllRoutes.setSelected(false);
        } else {
            JOptionPane jp = new JOptionPane();
            jp.setSize(600, 600);
            JLabel msgLabel = new JLabel("Are you sure you want to display " +  routes.size() + " routes? \nThis may take a while...", JLabel.CENTER);
            int reply = jp.showConfirmDialog(null, msgLabel, "Error Message", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                createMapRoutes(routes);
            } else {
                displayAllRoutes.setSelected(false);
            }
        }
    }

    // Method that clears, creates and displays airport markers
    private void createMapRoutes(List routes) {
        Iterator i = routes.iterator();
        while (i.hasNext()) {
            Route route = (Route) i.next();
            // get ids of source and destination airport
            String source = route.getSourceAirportName();
            String dest = route.getDestinationAirportName();
            // find airport in currentlyLoadedAirports to get coords
            double[] sourceCoords = getCoordinates(source);
            double[] destCoords = getCoordinates(dest);
            String scriptToExecute = "drawRouteLine(" + sourceCoords[0] + ", " + sourceCoords[1] + ", " + destCoords[0]
                    + ", " + destCoords[1] + ")";
            webEngine.executeScript(scriptToExecute);
        }
    }

    private double[] getCoordinates(String airportID) {
        double[] coords = {};
        List airports = mainController.getCurrentlyLoadedAirports();
        Iterator i = airports.iterator();
        while(i.hasNext()) {
            Airport airport = (Airport) i.next();
            if (airport.getName() == airportID) {
                double lat = airport.getLatitude();
                double lng = airport.getLongitude();
                coords = new double[] {lat, lng};
                break;
            }
        }
        return coords;
    }

    // Remove currently displayed airport markers
    private void clearRoutes() {
        webEngine.executeScript("clearRoutes()");
    }


    // KeyEvent function that calls displayRoutesByEquipment function when input is received in text field
    // and enter is pressed
    @FXML
    private void enterEquipmentPressed( KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            displayRoutesByEquipment(equipmentSearchBox.getText());
        }
    }


    private void displayRoutesByEquipment(String equipment) {

        RouteSearcher searcher = new RouteSearcher(mainController.getCurrentlyLoadedRoutes());


        if (equipment != null && !equipment.equals("ALL")) {
            searcher.routesOfEquipment(equipment);
        }

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();
        List routes = matchingRoutes;

        displayRoutes(routes);

    }

    // KeyEvent function that calls displayRoutesByAirport function when input is received in text field
    // and enter is pressed
    @FXML
    private void enterAirportPressed( KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            displayRoutesByAirports(airportSearchBox.getText());
        }
    }


    private void displayRoutesByAirports(String airport) {

        RouteSearcher searcher = new RouteSearcher(mainController.getCurrentlyLoadedRoutes());

        if (airport != null && !airport.equals("ALL")) {
            searcher.routesOfSource(airport);
        }

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();
        List routes = matchingRoutes;
        displayRoutes(routes);

    }


}
