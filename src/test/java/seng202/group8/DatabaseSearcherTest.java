package seng202.group8;

import javafx.collections.ObservableList;
import org.junit.Test;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 27/08/16.
 */
public class DatabaseSearcherTest {


    @Test
    public void testAirportSearchesCorrectly(){
        DatabaseSearcher db = new DatabaseSearcher();
        Connection conn = db.connect();
        ObservableList<Airport> airports = db.searchForAirportByOption(conn, "name", "Goro");
        //Only one in database like Goro, so output should be one element
        assertTrue(1 == airports.size());
        db.disconnect(conn);
    }

    @Test
    public void testAirportSearchesCorrectlyWithNameTest(){
        DatabaseSearcher db = new DatabaseSearcher();
        Connection conn = db.connect();
        ObservableList<Airport> airports = db.searchForAirportByOption(conn, "name", "Goro");
        //Only one in database like Goro, so output should be one element
        String airportName = airports.get(0).getName();
        assertTrue(airportName.equals("Goroka"));
        db.disconnect(conn);
    }

    @Test
    public void testAirportSearchesCountryCorrectly(){
        DatabaseSearcher db = new DatabaseSearcher();
        Connection conn = db.connect();
        ObservableList<Airport> airports = db.searchForAirportByOption(conn, "country", "Papua New");
        //Six elements currently in database with Papua New Guinea as a country
        assertTrue(6 == airports.size());
        db.disconnect(conn);
    }

    @Test
    public void testAirlineSearchesCorrectly(){
        DatabaseSearcher db = new DatabaseSearcher();
        Connection conn = db.connect();
        ObservableList<Airline> airlines = db.searchForAirlinesByOption(conn, "name", "Servicios");
        //Two elements in database like Servicios, so output should be one element
        assertTrue(2 == airlines.size());
        db.disconnect(conn);
    }

    @Test
    public void testAirlineSearchesCorrectlyWithNameTest(){
        DatabaseSearcher db = new DatabaseSearcher();
        Connection conn = db.connect();
        ObservableList<Airline> airlines = db.searchForAirlinesByOption(conn, "name", "Servicios Aereos Del");
        //Only one in database like Servicios, so output should be one element
        String airportName = airlines.get(0).getName();
        assertTrue(airportName.equals("Servicios Aereos Del Vaupes"));
        db.disconnect(conn);
    }

    @Test
    public void testAirlineSearchesCountryCorrectly(){
        DatabaseSearcher db = new DatabaseSearcher();
        Connection conn = db.connect();
        ObservableList<Airline> airlines = db.searchForAirlinesByOption(conn, "country", "Mex");
        //Two elements currently in database with Mexico as a country
        assertTrue(2 == airlines.size());
        db.disconnect(conn);
    }

    @Test
    public void testRouteSearchesAirlineCorrectly(){
        DatabaseSearcher db = new DatabaseSearcher();
        Connection conn = db.connect();
        ObservableList<Route> routes = db.searchRouteByOption(conn, "airlinecode", "2B");
        //Seven elements currently in database with Mexico as a country
        assertTrue(7 == routes.size());
        db.disconnect(conn);
    }

}
