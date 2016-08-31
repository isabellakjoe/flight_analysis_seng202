package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 27/08/16.
 */
public class DatabaseSearcherTest {


    @Test
    public void testAirportSearchesCorrectly(){
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildAirportSearch("name", "Goro");
        ObservableList<Airport> airports = dbs.searchForAirportByOption(conn, sqlStatement);
        //Only one in database like Goro, so output should be one element
        assertTrue(1 == airports.size());
        db.disconnect(conn);
    }

    @Test
    public void testAirportSearchesCorrectlyWithNameTest(){
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildAirportSearch("name", "Goro");
        ObservableList<Airport> airports = dbs.searchForAirportByOption(conn, sqlStatement);
        //Only one in database like Goro, so output should be one element
        String airportName = airports.get(0).getName();
        assertTrue(airportName.equals("Goroka"));
        db.disconnect(conn);
    }

    @Test
    public void testAirportSearchesCountryCorrectly(){
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildAirportSearch("country", "Papua New");
        ObservableList<Airport> airports = dbs.searchForAirportByOption(conn, sqlStatement);
        //Six elements currently in database with Papua New Guinea as a country
        assertTrue(6 == airports.size());
        db.disconnect(conn);
    }

    @Test
    public void testAirlineSearchesCorrectly(){
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildAirlineSearch("name", "Servicios");
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sqlStatement);
        //Two elements in database like Servicios, so output should be one element
        assertTrue(2 == airlines.size());
        db.disconnect(conn);
    }

    @Test
    public void testAirlineSearchesCorrectlyWithNameTest(){
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildAirlineSearch("name", "Servicios Aereos Del");
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sqlStatement);
        //Only one in database like Servicios, so output should be one element
        String airportName = airlines.get(0).getName();
        assertTrue(airportName.equals("Servicios Aereos Del Vaupes"));
        db.disconnect(conn);
    }

    @Test
    public void testAirlineSearchesCountryCorrectly(){
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildAirlineSearch("country", "Mex");
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sqlStatement);
        //Two elements currently in database with Mexico as a country
        assertTrue(2 == airlines.size());
        db.disconnect(conn);
    }

    @Test
    public void testRouteSearchesAirlineCorrectly(){
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildRouteSearch("airlinecode", "2B");
        ObservableList<Route> routes = dbs.searchRouteByOption(conn, sqlStatement);
        //Seven elements currently in database with Mexico as a country
        assertTrue(7 == routes.size());
        db.disconnect(conn);
    }

    @Test
    public void testUnionStatement() {
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        Connection conn = db.testConnect();
        String sqlStatement = dbs.buildRouteSearch("sourceairport", "ASF");
        String newStatement = dbs.addAdditionalLikeOption(sqlStatement, "route", "sourceairport", "DME");
        //There are currently four distinct entities in database which match this query
        ObservableList<Route> routes = dbs.searchRouteByOption(conn, newStatement);
        assertTrue(4 == routes.size());
        db.disconnect(conn);

    }

}
