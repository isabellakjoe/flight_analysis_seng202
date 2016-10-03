package seng202.group8.Controller.MapViewControllers;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group8.Controller.MainController;
import seng202.group8.Controller.PopupController;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Flight;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Objects.Waypoint;
import seng202.group8.Model.Searchers.RouteSearcher;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by esa46 on 15/09/16.
 */
public class MapViewController extends Component {

    @FXML
    public TabPane mapViewPane;
    private MainController mainController;
    private WebEngine webEngine;
    @FXML
    private CheckBox displayAllAirports;
    @FXML
    private CheckBox displayAllRoutes;
    @FXML
    private CheckBox addFlightPath;
    @FXML
    private TextField equipmentSearchBox;
    @FXML
    private TextField airportSearchBox;
    @FXML
    private WebView webView;

    /**
     * Method to assign the mainController
     *
     * @param mainController: The MainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Method to intialize the map html
     */
    public void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("maps.html").toExternalForm());
    }

    /**
     * Method to display all currently loaded airports
     *
     * @param e: The ActionEvent
     */
    @FXML
    public void mapAirports(ActionEvent e) {
        if (displayAllAirports.isSelected()) {
            displayAirports(mainController.getCurrentlyLoadedAirports());
        } else {
            clearAirports();
        }
    }

    /**
     * Method to display markers for list of airports
     *
     * @param airportList: A list of Airports
     */
    private void displayAirports(List airportList) {
        if (airportList.size() < 1000 && airportList.size() != 0) {
            showAirportMarkers(airportList);
            displayAllAirports.setSelected(true);
        } else if (airportList.isEmpty()) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/popup.fxml"));

                Parent root = loader.load();

                Stage stage = new Stage();

                PopupController cont = loader.getController();
                cont.setUpEmptyAirports(stage);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(new Scene(root));

                stage.show();


            } catch (IOException io) {
                io.printStackTrace();
            }

            displayAllAirports.setSelected(false);
        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/popup.fxml"));

                Parent root = loader.load();

                Stage stage = new Stage();

                PopupController cont = loader.getController();
                String type = "airport";
                cont.setUpYesNo(stage, airportList, type);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(new Scene(root));

                stage.show();
                displayAllAirports.setSelected(false);


            } catch (IOException io) {
                io.printStackTrace();
            }

        }
    }


    /**
     * Method that clears, creates and displays airport markers
     *
     * @param airports: A list of Airports
     */
    public void showAirportMarkers(List airports) {
        Iterator i = airports.iterator();
        while (i.hasNext()) {
            Airport airport = (Airport) i.next();
            Double latitude = airport.getLatitude();
            Double longitude = airport.getLongitude();
            String scriptToExecute = "createMarker(" + latitude + ", " + longitude + ")";
            webEngine.executeScript(scriptToExecute);
            webEngine.executeScript("showMarkers()");
        }
    }

    /**
     * Method to remove currently displayed airport markers
     */
    public void clearAirports() {
        webEngine.executeScript("clearMarkers()");
    }


    /**
     * Method to display all currently loaded routes
     *
     * @param e: An ActionEvent
     */
    @FXML
    public void mapRoutes(ActionEvent e) {
        if (displayAllRoutes.isSelected()) {
            displayRoutes(mainController.getCurrentlyLoadedRoutes());
        } else {
            clearRoutes();
        }
    }

    /**
     * Method for displaying routes on map
     *
     * @param routes: a list of Routes
     */
    private void displayRoutes(List routes) {
        if (routes.size() < 1000 && routes.size() != 0) {
            createMapRoutes(routes);
            displayAllRoutes.setSelected(true);
        } else if (routes.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/popup.fxml"));

                Parent root = loader.load();

                Stage stage = new Stage();

                PopupController cont = loader.getController();
                cont.setUpEmptyRoutes(stage);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(new Scene(root));

                stage.show();
                displayAllRoutes.setSelected(false);


            } catch (IOException io) {
                io.printStackTrace();
            }

            displayAllRoutes.setSelected(false);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/popup.fxml"));

                Parent root = loader.load();

                Stage stage = new Stage();

                PopupController cont = loader.getController();
                String type = "route";
                cont.setUpYesNo(stage, routes, type);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(new Scene(root));

                stage.show();
                displayAllRoutes.setSelected(false);


            } catch (IOException io) {
                io.printStackTrace();
            }

        }
    }

    /**
     * Method that clears, creates and displays airport markers
     *
     * @param routes: a list of Routes
     */
    public void createMapRoutes(List routes) {
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

    /**
     * Method to get the coordinates of an Airport from the currently loaded airports
     *
     * @param airportID: A String of the Name of the airport
     * @return The coordinates as a Double
     */
    public double[] getCoordinates(String airportID) {
        double[] coords = {};
        List airports = mainController.getCurrentlyLoadedAirports();
        Iterator i = airports.iterator();
        while (i.hasNext()) {
            Airport airport = (Airport) i.next();
            if (airport.getName() == airportID) {
                double lat = airport.getLatitude();
                double lng = airport.getLongitude();
                coords = new double[]{lat, lng};
                break;
            }
        }
        return coords;
    }

    /**
     * Method to remove currently displayed paths
     */
    public void clearRoutes() {
        webEngine.executeScript("clearRoutes()");
    }


    /**
     * Method that calls displayRoutesByEquipment function when input is received in text field and enter is pressed
     *
     * @param key: A KeyEvent
     */
    @FXML
    private void enterEquipmentPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            displayRoutesByEquipment(equipmentSearchBox.getText());
        }
    }

    /**
     * Method to display all Routes using a particular equipment
     *
     * @param equipment: A string representing a type of Equipment
     */
    private void displayRoutesByEquipment(String equipment) {

        RouteSearcher searcher = new RouteSearcher(mainController.getCurrentlyLoadedRoutes());


        if (equipment != null && !equipment.equals("ALL")) {
            searcher.routesOfEquipment(equipment);
        }

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();
        List routes = matchingRoutes;
        clearRoutes();
        airportSearchBox.setText(null);
        displayAllRoutes.setSelected(false);
        displayRoutes(routes);

    }

    /**
     * Method that calls displayRoutesByAirport method when input is received in text field and enter is pressed
     *
     * @param key
     */
    @FXML
    private void enterAirportPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            displayRoutesByAirports(airportSearchBox.getText());
        }
    }

    /**
     * Method to display all routes from a specific Airport
     *
     * @param airport: A string name of a particular Airport
     */
    private void displayRoutesByAirports(String airport) {

        RouteSearcher searcher = new RouteSearcher(mainController.getCurrentlyLoadedRoutes());

        if (airport != null && !airport.equals("ALL")) {
            searcher.routesOfSource(airport);
        }

        ObservableList<Route> matchingRoutes = searcher.getLoadedRoutes();
        List routes = matchingRoutes;
        clearRoutes();
        equipmentSearchBox.setText(null);
        displayAllRoutes.setSelected(false);
        displayRoutes(routes);

    }

    /**
     * Method to clear all Airports from the map
     *
     * @param e: The ActionEvent
     */
    @FXML
    private void clearAllAirports(ActionEvent e) {
        clearAirports();
        displayAllAirports.setSelected(false);
    }

    /**
     * Method to clear all Routes from the map
     *
     * @param e: The ActionEvent
     */
    @FXML
    private void clearAllRoutes(ActionEvent e) {
        clearRoutes();
        displayAllRoutes.setSelected(false);
        equipmentSearchBox.setText(null);
        airportSearchBox.setText(null);
    }

    /**
     * Method to display the loaded Flight on the map
     *
     * @param e: The ActionEvent
     */
    @FXML
    private void displayFlightPath(ActionEvent e) {
        if (addFlightPath.isSelected()) {
            displayFlight();
        } else {
            clearFlight();
        }

    }

    /**
     * Method to load the waypoints of a flight
     */
    private void displayFlight() {
        List<Waypoint> waypoints = Flight.getWaypoints();
        if (waypoints.size() < 1000 && waypoints.size() != 0) {
            createFlightPath();
        } else if (waypoints.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/popup.fxml"));

                Parent root = loader.load();

                Stage stage = new Stage();

                PopupController cont = loader.getController();
                cont.setUpEmptyFlights(stage);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(new Scene(root));

                stage.show();
                addFlightPath.setSelected(false);

            } catch (IOException io) {
                io.printStackTrace();
            }

        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/popup.fxml"));

                Parent root = loader.load();

                Stage stage = new Stage();

                PopupController cont = loader.getController();
                String type = "flight";
                cont.setUpYesNo(stage, waypoints, type);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(new Scene(root));

                stage.show();


            } catch (IOException io) {
                io.printStackTrace();
            }

        }
    }

    /**
     * Method to display the flightpath waypoints on the map
     */
    public void createFlightPath() {
        String scriptToExecute = "displayFlight(" + ToJSONArray.toJSONFlightPath() + ");";
        webEngine.executeScript(scriptToExecute);
    }

    /**
     * Method to clear all Flight waypoints on the map
     */
    private void clearFlight() {
        String scriptToExecute = "clearFlightPaths();";
        webEngine.executeScript(scriptToExecute);
    }


}
