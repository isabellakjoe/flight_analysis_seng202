package seng202.group8.Model.DatabaseMethods;

import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Objects.RouteMethod;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Callum on 25/08/16.
 */
public class RouteDatabaseLoader extends RouteMethod {


    public ArrayList<Route> loadRoutes(Connection conn) {

        ArrayList<Route> routes = new ArrayList<Route>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM route");
            while (result.next()) {
                Route loadRoute = new Route();
                //Create the airline for the route, needs to check in the future
                Airline routeAirline = createAirline(result.getString("airlinecode"), result.getInt("airlineid"));
                loadRoute.setAirline(routeAirline);
                Airport sourceAirport = createAirport(result.getString("sourceairport"), result.getInt("sourceid"));
                Airport destinationAirport = createAirport(result.getString("destinationAirport"), result.getInt("destinationid"));
                loadRoute.setSourceAirport(sourceAirport);
                loadRoute.setDestinationAirport(destinationAirport);
                checkCodeshared(loadRoute, result.getString("codeshare"));
                loadRoute.setStops(result.getInt("stops"));
                loadRoute.setEquipment(result.getString("equipment"));
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