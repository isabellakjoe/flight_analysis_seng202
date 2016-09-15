package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 27/08/16.
 */
public class DatabaseSearcherTest {

    Database db;
    DatabaseSearcher dbs;
    Connection conn;

    @Before
    public void initialise() {
        db = new Database();
        dbs = new DatabaseSearcher();
        conn = db.testConnect();
    }

    @After
    public void teardown() {
        db.disconnect(conn);
    }


    @Test
    public void testAirportSearchesCorrectly() {
        String sqlStatement = dbs.buildAirportSearch("name", "Goro");
        ObservableList<Airport> airports = dbs.searchForAirportByOption(conn, sqlStatement);
        //Only one in database like Goro, so output should be one element
        assertTrue(1 == airports.size());
    }

    @Test
    public void testAirportSearchesCorrectlyWithNameTest() {
        String sqlStatement = dbs.buildAirportSearch("name", "Goro");
        ObservableList<Airport> airports = dbs.searchForAirportByOption(conn, sqlStatement);
        //Only one in database like Goro, so output should be one element
        String airportName = airports.get(0).getName();
        assertTrue(airportName.equals("Goroka"));
    }

    @Test
    public void testAirportSearchesCountryCorrectly() {
        String sqlStatement = dbs.buildAirportSearch("country", "Papua New");
        ObservableList<Airport> airports = dbs.searchForAirportByOption(conn, sqlStatement);
        //Six elements currently in database with Papua New Guinea as a country
        assertTrue(6 == airports.size());
    }

    @Test
    public void testAirlineSearchesCorrectly() {
        String sqlStatement = dbs.buildAirlineSearch("name", "Servicios");
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sqlStatement);
        //Two elements in database like Servicios, so output should be one element
        assertTrue(2 == airlines.size());
    }

    @Test
    public void testAirlineSearchesCorrectlyWithNameTest() {
        String sqlStatement = dbs.buildAirlineSearch("name", "Servicios Aereos Del");
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sqlStatement);
        //Only one in database like Servicios, so output should be one element
        String airportName = airlines.get(0).getName();
        assertTrue(airportName.equals("Servicios Aereos Del Vaupes"));
    }

    @Test
    public void testAirlineSearchesCountryCorrectly() {
        String sqlStatement = dbs.buildAirlineSearch("country", "Mex");
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sqlStatement);
        //Two elements currently in database with Mexico as a country
        assertTrue(2 == airlines.size());
    }

    @Test
    public void testRouteSearchesAirlineCorrectly() {
        String sqlStatement = dbs.buildRouteSearch("airlinecode", "2B");
        ObservableList<Route> routes = dbs.searchRouteByOption(conn, sqlStatement);
        //Seven elements currently in database with Mexico as a country
        assertTrue(7 == routes.size());
    }

    @Test
    public void testUnionStatement() {
        String sqlStatement = dbs.buildRouteSearch("sourceairport", "ASF");
        String newStatement = dbs.addAdditionalLikeOption(sqlStatement, "route", "sourceairport", "DME");
        //There are currently four distinct entities in database which match this query
        ObservableList<Route> routes = dbs.searchRouteByOption(conn, newStatement);
        assertTrue(4 == routes.size());

    }

    @Test
    public void testNumRoutesOfSourceAirportComputesCorrectlyDESC() {
        String sql = dbs.buildSrcRouteQuery("DESC");
        ObservableList<Integer> airportIDs = dbs.getNumRoutesOfAirport(conn, sql);
        //In the current test database, the airport with the most routes has ID 2937
        assertTrue(airportIDs.get(0) == 2937);
    }

    @Test
    public void testNumRoutesOfSourceAirportComputesCorrectlyASC() {
        String sql = dbs.buildSrcRouteQuery("ASC");
        ObservableList<Integer> airportIDs = dbs.getNumRoutesOfAirport(conn, sql);
        //In the current database, the airport with the least amount of routes has ID 1824
        assertTrue(airportIDs.get(0) == 1824);
    }

    @Test
    public void testNumRoutesofDestAirportComputesCorrectlyDESC() {
        String sql = dbs.buildDestRouteQuery("DESC");
        ObservableList<Integer> airportIDs = dbs.getNumRoutesOfAirport(conn, sql);
        //In the current test database the destination airport with the most routes has id 2990
        assertTrue(airportIDs.get(0) == 2990);
    }

    @Test
    public void testNumRoutesOfDestAirportComputesCorrectlyASC() {
        String sql = dbs.buildDestRouteQuery("ASC");
        ObservableList<Integer> airportIDs = dbs.getNumRoutesOfAirport(conn, sql);
        //In the current test database the lowest num of routes for destination airport is 2962
        assertTrue(airportIDs.get(0) == 2962);
    }

    @Test
    public void testNumRoutesComputesProperly() {
        ObservableList<Integer> routelessAirports = dbs.findAirportsWithNoRoutes(conn);
        assertTrue(routelessAirports.size() == 10);
    }

}
