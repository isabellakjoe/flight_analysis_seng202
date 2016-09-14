package seng202.group8.DatabaseTests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.DatabaseMethods.DatabaseUpdater;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.AirlineParser;
import seng202.group8.Model.Parsers.AirportParser;
import seng202.group8.Model.Parsers.RouteParser;

import javax.jws.Oneway;
import java.applet.AudioClip;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 13/09/16.
 */
public class DatabaseUpdaterTest {

    Database db;
    DatabaseUpdater dbu;
    DatabaseSearcher dbsSearch;
    DatabaseSaver dbSave;
    Connection connUpdate;
    Connection connSave;

    @Before
    public void initalise() {

        db = new Database();
        dbu = new DatabaseUpdater();
        dbsSearch = new DatabaseSearcher();
        dbSave = new DatabaseSaver();
        //Connect to the static testing database...
        connUpdate = db.testConnect();
        connSave = db.testConnect();

    }

    @Test
    public void testAirlineUpdatesNameCorrectly() {

        //Create the airline object to test the database
        String testAirline = "10,Astro Air International,AAI,ABC,AAV,ASTRO-PHIL,United States,N";
        AirlineParser ap = new AirlineParser();
        Airline airline = ap.createSingleAirline(testAirline);
        ObservableList<Airline> airlines = FXCollections.observableArrayList();
        airlines.add(airline);

        dbSave.saveAirlines(connSave, airlines);
        db.disconnect(connSave);

        ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
        ArrayList<String> valuesOne = new ArrayList<String>();
        ArrayList<String> valuesTwo = new ArrayList<String>();
        valuesOne.add("name");
        valuesOne.add("XYZ");
        valuesTwo.add("country");
        valuesTwo.add("CHCH");
        values.add(valuesOne);
        values.add(valuesTwo);
        dbu.updateAirline(connUpdate, values, "10");
        String sql = dbsSearch.buildAirlineSearch("airlineid", "10");
        ObservableList<Airline> resultAirline = dbsSearch.searchForAirlinesByOption(connUpdate, sql);
        db.disconnect(connUpdate);

        //Remove the Airline from the database for test repetability.
        //TODO: Implements as a class later
        Connection connDelete = db.testConnect();
        try {
            Statement stmt = connDelete.createStatement();
            String sqlTwo = "DELETE FROM airline WHERE airlineid='10';";
            stmt.executeUpdate(sqlTwo);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        assertTrue(resultAirline.get(0).getCountry().equals("CHCH"));
    }

    @Test
    public void testAirportUpdatesCorrectly() {

        String testAirport = "17929,Wewak Intl,Wewak,Papua New Guinea,WWK,AYWK,-3.583828,143.669186,19,10,U,Port_Moresby";
        AirportParser ap = new AirportParser();
        Airport airport = ap.createSingleAirport(testAirport);
        ObservableList<Airport> airports = FXCollections.observableArrayList();
        airports.add(airport);

        dbSave.saveAirports(connSave, airports);
        db.disconnect(connSave);

        ArrayList<ArrayList<String>> updates = new ArrayList<ArrayList<String>>();
        ArrayList<String> nameUpdate = new ArrayList<String>();
        nameUpdate.add("name");
        nameUpdate.add("UC International Airport");
        updates.add(nameUpdate);
        dbu.updateAirport(connUpdate, updates, "17929");
        String sql = dbsSearch.buildAirportSearch("airportid", "17929");
        ObservableList<Airport> resultAirport = dbsSearch.searchForAirportByOption(connUpdate, sql);
        db.disconnect(connUpdate);

        //Remove the Airline from the database for test repetability.
        //TODO: Implements as a class later
        Connection connDelete = db.testConnect();
        try {
            Statement stmt = connDelete.createStatement();
            String sqlTwo = "DELETE FROM airport WHERE airportid='17929';";
            stmt.executeUpdate(sqlTwo);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        assertTrue(resultAirport.get(0).getName().equals("UC International Airport"));

    }

    @Test
    public void testRouteUpdatesCorrectly() {

        String testRoute = "3B,411,AOR,2865,KUL,2991,Y,11,CR2,5499";
        RouteParser rp = new RouteParser();
        Route route = rp.createSingleRoute(testRoute);
        ObservableList<Route> routes = FXCollections.observableArrayList();
        routes.add(route);

        dbSave.saveRoutes(connSave, routes);
        db.disconnect(connSave);

        ArrayList<ArrayList<String>> updates = new ArrayList<ArrayList<String>>();
        ArrayList<String> sourceAirportCode = new ArrayList<String>();
        sourceAirportCode.add("stops");
        sourceAirportCode.add("1");
        updates.add(sourceAirportCode);

        dbu.updateRoute(connUpdate, updates, "5499");
        String sql = dbsSearch.buildRouteSearch("routeid", "5499");
        ObservableList<Route> resultRoutes = dbsSearch.searchRouteByOption(connUpdate, sql);
        db.disconnect(connUpdate);


        //Remove the Airline from the database for test repetability.
        //TODO: Implements as a class later
        Connection connDelete = db.testConnect();
        try {
            Statement stmt = connDelete.createStatement();
            String sqlTwo = "DELETE FROM route WHERE routeid='5499';";
            stmt.executeUpdate(sqlTwo);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        assertTrue(resultRoutes.get(0).getStops() == 1);
    }



}
