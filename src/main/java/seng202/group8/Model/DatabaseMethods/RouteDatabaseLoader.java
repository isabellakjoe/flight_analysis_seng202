package seng202.group8.Model.DatabaseMethods;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Objects.RouteMethod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by Callum on 25/08/16.
 */
public class RouteDatabaseLoader extends RouteMethod {

    /**
     * A Method used to load all routes from the database
     *
     * @param conn a static connection to the database
     * @param routeHashMap a hashmap storing routes based off of unique ID's predifined by the operation of the app
     * @param airlineHashMap a hashmap storing airlines based off of unique IATA/ICAO fields
     * @param airportHashMap a hashmap storing airports based off of uique IATA/ICAO fields
     * @return a observable list of all routes in the database
     */
    public ObservableList<Route> loadRoutes(Connection conn, HashMap<Integer, Route> routeHashMap, HashMap<String, Airline> airlineHashMap, HashMap<String, Airport> airportHashMap) {

        ObservableList<Route> routes = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM route");
            while (result.next()) {
                Route loadRoute = new Route();
                //Check if the airline is currently in the application. If so link the objects together
                if (airlineHashMap.get(result.getString("airlinecode")) != null) {
                    loadRoute.setAirline(airlineHashMap.get(result.getString("airlinecode")));
                    loadRoute.setAirlineName(airlineHashMap.get(result.getString("airlinecode")).getName());
                } else {
                    System.out.println("Error: Airline does not exist");
                    continue;
                }
                if (airportHashMap.get(result.getString("sourceairport")) instanceof Airport) {
                    loadRoute.setSourceAirport(airportHashMap.get(result.getString("sourceairport")));
                    loadRoute.setSourceAirportName(airportHashMap.get(result.getString("sourceairport")).getName());
                    int numSrcRoutes = airportHashMap.get(result.getString("sourceairport")).getNumRoutes() + 1;
                    airportHashMap.get(result.getString("sourceairport")).setNumRoutes(numSrcRoutes);
                } else {
                    
                    continue;
                }
                if (airportHashMap.get(result.getString("destinationairport")) instanceof Airport) {
                    loadRoute.setDestinationAirport(airportHashMap.get(result.getString("destinationairport")));
                    loadRoute.setDestinationAirportName(airportHashMap.get(result.getString("destinationairport")).getName());
                    int numDestRoutes = airportHashMap.get(result.getString("destinationairport")).getNumRoutes() + 1;
                    airportHashMap.get(result.getString("destinationairport")).setNumRoutes(numDestRoutes);
                } else {
                    continue;
                }

                checkCodeshared(loadRoute, result.getString("codeshare"));
                loadRoute.setStops(result.getInt("stops"));
                loadRoute.setEquipment(result.getString("equipment"));
                loadRoute.setRouteID(result.getInt("routeid"));
                routes.add(loadRoute);
                routeHashMap.put(loadRoute.getRouteID(), loadRoute);
            }
            result.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return routes;

    }

}
