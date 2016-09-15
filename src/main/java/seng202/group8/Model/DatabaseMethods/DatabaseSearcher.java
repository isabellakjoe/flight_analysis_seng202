package seng202.group8.Model.DatabaseMethods;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Callum on 27/08/16.
 */
public class DatabaseSearcher {


    /* Method to check whether the airport code is IATA or FAA */
    private void checkCodeType(Airport airport, String airportCode, String country) {
       /* Airports located in the US have an FAA code. All others have an IATA code. */
        if (country.equals("United States")) {
            airport.setFAA(airportCode);
        } else {
            airport.setIATA(airportCode);
        }
    }

    /* Method to check whether an airline is currently active*/
    private void checkActive(Airline airline, String active) {
        if (active.equals("Y")) {
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

    /**
     * Method used to build an sql statement for searching through airports in the database
     *
     * @param option string parameter for which table column to search through
     * @param name   string parameter for which table column must match
     * @return a sql statement
     */
    public String buildAirportSearch(String option, String name) {

        String sql = "SELECT DISTINCT * FROM airport WHERE " + option + " LIKE '%" + name + "%'";
        return sql;

    }

    /**
     * Method used to build an sql statement for searching through airlines in the database
     *
     * @param option string parameter for which table column to search through
     * @param name   string parameter for which table column must match
     * @return a sql statement
     */
    public String buildAirlineSearch(String option, String name) {

        String sql = "SELECT DISTINCT * FROM airline WHERE " + option + " LIKE '%" + name + "%'";
        return sql;

    }

    /**
     * Method used to build an sql statment for searching through routes in the database
     *
     * @param option string parameter for which table column to search through
     * @param name   string parameter for which table column must match
     * @return a sql statement
     */
    public String buildRouteSearch(String option, String name) {

        String sql = "SELECT DISTINCT * FROM route WHERE " + option + " LIKE '%" + name + "%'";
        return sql;

    }

    /**
     * Method to add additional like statements to an sql statement
     *
     * @param sql    an sql statement which needs additional parameters
     * @param table  the table of the database
     * @param option the column of the table
     * @param name   the string parameter for which the tables column must match
     * @return
     */
    public String addAdditionalLikeOption(String sql, String table, String option, String name) {
        String addQuery = " UNION SELECT * FROM " + table + " WHERE " + option + " LIKE '" + name + "%'";
        return sql + addQuery;
    }

    /**
     * Method to build a sql query for the number of routes a source airport has
     *
     * @param orderby string parameter to order the list ASC or DESC
     * @return a sql statement containing airport ids and the number of routes
     */
    public String buildSrcRouteQuery(String orderby) {
        String sql = "SELECT airport.airportid, COUNT(*) from airport INNER JOIN route ON airport.airportid = route.sourceid GROUP BY airport.airportid ORDER BY COUNT(*) " + orderby + " ;";
        return sql;
    }

    /**
     * Method to build a sql query for the number of routes a destination airport has
     *
     * @param orderby string parameter to order the list ASC or DESC
     * @return a sql statement containing airport ids and the number of routes
     */
    public String buildDestRouteQuery(String orderby) {
        String sql = "SELECT airport.airportid, COUNT(*) from airport INNER JOIN route ON airport.airportid = route.destinationid GROUP BY airport.airportid ORDER BY COUNT(*) " + orderby + " ;";
        return sql;
    }

    private ObservableList<Integer> checkForZeroRoutes(ArrayList<ArrayList<Integer>> airportRouteList) {
        // Method used to find a list of all of the ids of airports which have no routes
        ObservableList<Integer> zeroRouteAirports = FXCollections.observableArrayList();
        for (int i = 0; i < airportRouteList.size(); i++) {
            if (airportRouteList.get(i).get(1) == 0) {
                zeroRouteAirports.add(airportRouteList.get(i).get(0));
            }
        }

        return zeroRouteAirports;

    }

    private ObservableList<Integer> getNumOfRoutes(Connection conn, String sqlSrc, String sqlDest) {
        //A method to get the number of routes each airport in the database has
        ArrayList<ArrayList<Integer>> numSrcRoutes = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> numDestRoutes = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> totalNumRoutes = new ArrayList<ArrayList<Integer>>();

        try {
            Statement stmtSrc = conn.createStatement();
            ResultSet resultSrc = stmtSrc.executeQuery(sqlSrc);
            while (resultSrc.next()) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(resultSrc.getInt("airportid"));
                temp.add(resultSrc.getInt("numroutes"));
                numSrcRoutes.add(temp);
            }
            resultSrc.close();
            stmtSrc.close();

            Statement stmtDest = conn.createStatement();
            ResultSet resultDest = stmtSrc.executeQuery(sqlDest);
            while (resultSrc.next()) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(resultDest.getInt("airportid"));
                temp.add(resultDest.getInt("numroutes"));
                numDestRoutes.add(temp);
            }
            resultDest.close();
            stmtDest.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        for (int i = 0; i < numSrcRoutes.size(); i++) {
            int src = numSrcRoutes.get(i).get(1);
            int dest = numDestRoutes.get(i).get(1);
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(numSrcRoutes.get(i).get(0));
            temp.add(src + dest);
            totalNumRoutes.add(temp);
        }

        return checkForZeroRoutes(totalNumRoutes);

    }

    /**
     * A methid used to find all of the airports without routes
     *
     * @param conn a static connection to a database
     * @return a list of all of the id's of airports which currently have no routes
     */
    public ObservableList<Integer> findAirportsWithNoRoutes(Connection conn) {
        String sqlSrc = "SELECT airport.airportid, count(route.sourceid) AS numroutes FROM airport LEFT OUTER JOIN route ON airport.airportid = route.sourceid GROUP BY airport.airportid;";
        String sqlDest = "SELECT airport.airportid, count(route.destinationid) AS numroutes FROM airport LEFT OUTER JOIN route ON airport.airportid = route.destinationid GROUP BY airport.airportid;";

        //Here we return a list of all of the airports in the database which have no routes
        return getNumOfRoutes(conn, sqlSrc, sqlDest);

    }

    /**
     * A method used to search the database for airports which match a certain constraints
     *
     * @param conn a static connection to a database
     * @param sql  a sql statement for airports
     * @return an observable list of airport objects which match search criteria
     */
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

    /**
     * A method used to search the database for airlines which match a certain constraints
     *
     * @param conn a static connection to a database
     * @param sql  a sql statement for airlines
     * @return an observable list of airport objects which match search criteria
     */
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

    /**
     * A method used to search the database for airlines which match a certain constraints
     *
     * @param conn a static connection to a database
     * @param sql  a sql statement for routes
     * @return an observable list of airport objects which match search criteria
     */
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


    /**
     * A method used to get the number of routes an airport has
     *
     * @param conn a static connection to a database
     * @param sql  a sql statement to compute the number of routes
     * @return an arraylist of airport ids without the number of routes contained
     */
    public ObservableList<Integer> getNumRoutesOfAirport(Connection conn, String sql) {

        ObservableList<Integer> airportIDs = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                airportIDs.add(result.getInt("airportid"));
            }
            result.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return airportIDs;

    }


}
