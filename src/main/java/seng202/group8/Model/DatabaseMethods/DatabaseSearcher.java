package seng202.group8.Model.DatabaseMethods;

import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Callum on 27/08/16.
 */
public class DatabaseSearcher {


    /* Method to check whether the airport code is IATA or FAA */
    private void checkCodeType(Airport airport, String airportCode, String country){
       /* Airports located in the US have an FAA code. All others have an IATA code. */
        if(country.equals("United States")) {
            airport.setFAA(airportCode);
        } else {
            airport.setIATA(airportCode);
        }
    }

    /* Method to check whether an airline is currently active*/
    private void checkActive(Airline airline, String active) {
        if(active.equals("Y")) {
            airline.setActive(true);
        } else {
            airline.setActive(false);
        }
    }

    /* Temporary method to create an airline for a route */
    private Airline createAirline(String airlineName, int airlineID) {
        Airline airline = new Airline();
        airline.setName(airlineName);
        airline.setAirlineID(airlineID);
        return airline;
    }

    /* Temporary method to create an airport for a route */
    private Airport createAirport(String airportCode, int airportID) {
        Airport airport = new Airport();

        if (airportCode.length() == 3) {
            airport.setIATA(airportCode);
        } else if (airportCode.length() == 4) {
            airport.setICAO(airportCode);
        }

        airport.setAirportID(airportID);
        return airport;
    }

    /* Method to check whether an route is codeshared */
    private void checkCodeshared(Route route, String codeshare) {
        if (codeshare.equals("Y")) {
            route.setCodeshare(true);
        } else {
            route.setCodeshare(false);
        }
    }

    public String buildAirportSearch(String option, String name) {

        String sql = "SELECT DISTINCT * FROM AIRPORT WHERE " + option + " LIKE '" + name + "%'";
        return sql;

    }

    public String buildAirlineSearch(String option, String name) {

        String sql = "SELECT DISTINCT * FROM airline WHERE " + option + " LIKE '" + name + "%'";
        return sql;

    }

    public String buildRouteSearch(String option, String name) {

        String sql = "SELECT DISTINCT * FROM route WHERE " + option + " LIKE '" + name + "%'";
        return sql;

    }

    /* Method to add additional like statements to an sql statement */
    public String addAdditionalLikeOption(String sql, String table, String option, String name) {
        String addQuery = " UNION SELECT * FROM " + table + " WHERE " + option + " LIKE '" + name + "%'";
        return sql + addQuery;
    }


    public ObservableList<Airport> searchForAirportByOption(Connection conn, String sql) {

        ObservableList<Airport> airports = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            String query = sql + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Airport loadAirport = new Airport();
                loadAirport.setAirportID(result.getInt("airportid"));
                loadAirport.setName(result.getString("name"));
                loadAirport.setCity(result.getString("city"));
                loadAirport.setCountry(result.getString("country"));
                checkCodeType(loadAirport, result.getString("country"), result.getString("iata"));
                loadAirport.setICAO(result.getString("icao"));
                loadAirport.setLatitude(result.getDouble("latitude"));
                loadAirport.setLongitude(result.getDouble("longitude"));
                loadAirport.setAltitude(result.getInt("altitude"));
                loadAirport.setTimezone(result.getInt("timezone"));
                loadAirport.setDST(result.getString("dst").charAt(0));
                loadAirport.setTimezone(result.getInt("timezone"));
                airports.add(loadAirport);
            }
            result.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return airports;

    }

    public ObservableList<Airline> searchForAirlinesByOption(Connection conn, String sql) {

        ObservableList<Airline> airlines = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            String query = sql + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Airline loadedAirline = new Airline();
                loadedAirline.setAirlineID(result.getInt("airlineid"));
                loadedAirline.setName(result.getString("name"));
                loadedAirline.setAlias(result.getString("alias"));
                loadedAirline.setIATA(result.getString("iata"));
                loadedAirline.setICAO(result.getString("icao"));
                loadedAirline.setCallsign(result.getString("callsign"));
                loadedAirline.setCountry(result.getString("country"));
                checkActive(loadedAirline, result.getString("active"));
                airlines.add(loadedAirline);
            }
            result.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return airlines;
    }


    public ObservableList<Route> searchRouteByOption(Connection conn, String sql) {

        ObservableList<Route> routes = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            String query = sql + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Route loadRoute = new Route();
                //Create the airline for the route, needs to check in the future
                loadRoute.setRouteID(result.getInt("routeid"));
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
