package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;

/**
 * Created by Erika on 29-Aug-16.
 */
public class RouteSearcher {

    private ObservableList<Route> loadedRoutes = FXCollections.observableArrayList();

    /**
     * A constructor for the route seacher object
     *
     * @param loadedRoutes an observalble list of routes
     */
    public RouteSearcher(ObservableList<Route> loadedRoutes) {
        this.loadedRoutes = loadedRoutes;
    }

    /**
     * Return the list of all currently loaded routes
     *
     * @return an observable list of routes
     */
    public ObservableList<Route> getLoadedRoutes() {
        return loadedRoutes;
    }

    private ObservableList<Route> generateSearchList(String paramID, String searchParam) {

        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        String sql = dbs.buildRouteSearch(paramID, searchParam);
        Connection conn = db.connect();
        ObservableList<Route> routes = dbs.searchRouteByOption(conn, sql);
        db.disconnect(conn);

        return routes;
    }

    /**
     * Method to search for route based off of airlinecode
     *
     * @param airline name of airline
     */
    public void routesOfAirline(String airline) {
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(airlinecode)", airline);
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for route based off of airlineid
     *
     * @param airlineID id of airline
     */
    public void routesOfAirlineID(int airlineID) {
        ObservableList<Route> matchingRoutes = generateSearchList("airlineid", Integer.toString(airlineID));
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for route based off of source airport name
     *
     * @param sourceAirportName name of source airport
     */
    public void routesOfSource(String sourceAirportName) {
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(sourceairport)", sourceAirportName);
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for route based off of source aiport id
     *
     * @param sourceAirportID id of source airport
     */
    public void routesOfSourceID(int sourceAirportID) {
        ObservableList<Route> matchingRoutes = generateSearchList("sourceid", Integer.toString(sourceAirportID));
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for route based off destination airport name
     *
     * @param destinationAirport name of destination airport
     */
    public void routesOfDestination(String destinationAirport) {
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(destinationairport)", destinationAirport);
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for route based off of destination airport id
     *
     * @param destinationAirportID id of destination airport
     */
    public void routesOfDestinationID(int destinationAirportID) {
        ObservableList<Route> matchingRoutes = generateSearchList("destinationid", Integer.toString(destinationAirportID));
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for route based off of number of stops
     *
     * @param stops number of stops in route
     */
    public void routesOfStops(int stops) {
        ObservableList<Route> matchingRoutes = generateSearchList("stops", Integer.toString(stops));
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for airport based off being codeshared
     *
     * @param codeshareStatus string if airport codeshared
     */
    public void routesOfCodeshare(String codeshareStatus) {
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(codeshare)", codeshareStatus);
        loadedRoutes = matchingRoutes;
    }

    /**
     * Method to search for route based off of equipment
     *
     * @param equipment equipment used in route
     */
    public void routesOfEquipment(String equipment) {
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(equipment)", equipment);
        loadedRoutes = matchingRoutes;
    }

}
