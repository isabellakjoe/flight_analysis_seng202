package seng202.group8.Model;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 25/08/16.
 */
public class RouteDatabaseLoader extends RouteMethod{


    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = ("jdbc:sqlite:ApplicationDatabase.db");
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

        } catch(Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return conn;
    }

    public static void disconnect(Connection conn) {
        try {
            conn.close();
        } catch(Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Set<Route> loadRoutes(Connection conn) {

        Set<Route> routes = new HashSet<Route>();
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
