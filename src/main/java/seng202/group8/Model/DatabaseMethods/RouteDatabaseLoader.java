package seng202.group8.Model.DatabaseMethods;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Objects.RouteMethod;

import java.sql.*;

/**
 * Created by Callum on 25/08/16.
 */
public class RouteDatabaseLoader extends RouteMethod {

    /** A Method used to load all routes from the database
     *
     * @param conn a static connection to the database
     * @return a observable list of all routes in the database
     */
    public ObservableList<Route> loadRoutes(Connection conn) {

        ObservableList<Route> routes = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM route");
            while (result.next()) {
                Route loadRoute = new Route();
                //Create the airline for the route, needs to check in the future
                Airline routeAirline = createAirline(result.getString("airlinecode"), result.getInt("airlineid"));
                loadRoute.setAirline(routeAirline);
                loadRoute.setAirlineName(result.getString("airlinecode"));
                Airport sourceAirport = createAirport(result.getString("sourceairport"), result.getInt("sourceid"));
                Airport destinationAirport = createAirport(result.getString("destinationAirport"), result.getInt("destinationid"));
                loadRoute.setSourceAirport(sourceAirport);
                loadRoute.setSourceAirportName(result.getString("sourceairport"));
                loadRoute.setDestinationAirport(destinationAirport);
                loadRoute.setDestinationAirportName(result.getString("destinationAirport"));
                checkCodeshared(loadRoute, result.getString("codeshare"));
                loadRoute.setStops(result.getInt("stops"));
                loadRoute.setEquipment(result.getString("equipment"));
                loadRoute.setRouteID(result.getInt("routeid"));
                routes.add(loadRoute);
            }
            result.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return routes;

    }

}
