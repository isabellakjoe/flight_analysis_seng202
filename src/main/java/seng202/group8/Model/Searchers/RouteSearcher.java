package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;

import static java.lang.Integer.parseInt;

/**
 * Created by Erika on 29-Aug-16.
 */
public class RouteSearcher {

    private ObservableList<Route> loadedRoutes = FXCollections.observableArrayList();

    public RouteSearcher(ObservableList<Route> loadedRoutes) {
        this.loadedRoutes = loadedRoutes;
    }

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

    public void routesOfAirline(String airline){
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(airlinecode)", airline);
        loadedRoutes = matchingRoutes;
    }
    public void routesOfAirlineID(int airlineID){
        ObservableList<Route> matchingRoutes = generateSearchList("airlineid", Integer.toString(airlineID));
        loadedRoutes = matchingRoutes;
    }

    public void routesOfSource(String sourceAirportName){
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(sourceairport)", sourceAirportName);
        loadedRoutes = matchingRoutes;
    }
    public void routesOfSourceID(int sourceAirportID){
        ObservableList<Route> matchingRoutes = generateSearchList("sourceid", Integer.toString(sourceAirportID));
        loadedRoutes = matchingRoutes;
    }

    public void routesOfDestination(String destinationAirport){
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(destinationairport)", destinationAirport);
        loadedRoutes = matchingRoutes;
    }

    public void routesOfDestinationID(int destinationAirportID){
        ObservableList<Route> matchingRoutes = generateSearchList("destinationid", Integer.toString(destinationAirportID));
        loadedRoutes = matchingRoutes;
    }

    public void routesOfStops(int stops){
        ObservableList<Route> matchingRoutes = generateSearchList("stops", Integer.toString(stops));
        loadedRoutes = matchingRoutes;
    }

    public void routesOfCodeshare(String codeshareStatus){
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(codeshare)", codeshareStatus);
        loadedRoutes = matchingRoutes;
    }

    public void routesOfEquipment(String equipment){
        ObservableList<Route> matchingRoutes = generateSearchList("LOWER(equipment)", equipment);
        loadedRoutes = matchingRoutes;
    }

}
