package seng202.group8.DatabaseTests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.AirlineParser;
import seng202.group8.Model.Parsers.AirportParser;
import seng202.group8.Model.Parsers.RouteParser;

import java.sql.Connection;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 29/08/16.
 */
public class DatabaseSaverTest {

    Database dbOne;
    DatabaseSaver dbsave;
    DatabaseSearcher dbsearch;
    Connection connSave;
    Connection connSearch;
    Connection connDelete;


    @Before
    public void initialise() {
        dbOne = new Database();
        dbsave = new DatabaseSaver();
        dbsearch = new DatabaseSearcher();
        connSave = dbOne.testConnect();
        connSearch = dbOne.testConnect();
        connDelete = dbOne.testConnect();
    }

    @Test
    public void testAirportSavesCorrectly() {
        //Create Objects to save and load to and from the database
        AirportParser ap = new AirportParser();
        //Create an airport to save to the database
        Airport testAirport = ap.createSingleAirport("54,Inuvik Mike Zubko,Inuvik,Canada,YEV,CYEV,68.304167,-133.482778,224,-7,A,America/Edmonton");
        //Create an array to save the new airport to the database
        ObservableList<Airport> testArray = FXCollections.observableArrayList();
        testArray.add(testAirport);
        //Save the aiport to the database here
        dbsave.saveAirports(connSave, testArray);
        dbOne.disconnect(connSave);
        //Search for the new saved aiport here
        String sqlStatement = dbsearch.buildAirportSearch("name", "Inuvik Mike Zubko");
        ObservableList<Airport> airports = dbsearch.searchForAirportByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSearch);
        //Close the database queries here
        //Current test database should only return one result
        //Remove the aiport from the database for test repetability.
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(54);
        dbsave.deleteAirport(connDelete, ids);
        dbOne.disconnect(connDelete);
        //Check here if correct airline inserted into database
        Airport returnAirport = airports.get(0);
        assertTrue(returnAirport.getName().equals(testAirport.getName()));

    }


    @Test
    public void testAirlineSavesCorrectly() {
        //Create the objects to save and load to and from the database
        AirlineParser ap = new AirlineParser();
        //Create a test airline to save to the airport
        Airline testAirline = ap.createSingleAirline("96,Aegean Airlines,\\N,A3,AEE,AEGEAN,Greece,Y");
        ObservableList<Airline> testArray = FXCollections.observableArrayList();
        testArray.add(testAirline);
        //Create the connections to the database
        //Save the new airline here
        dbsave.saveAirlines(connSave, testArray);
        dbOne.disconnect(connSave);
        //Search for the new airline in the database here
        String sqlStatement = dbsearch.buildAirlineSearch("name", "Aegean Airlines");
        ObservableList<Airline> airlines = dbsearch.searchForAirlinesByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSearch);
        //Current test database should only return one result
        //Remove the airport from the database for test repeatability.
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(96);
        dbsave.deleteAirlines(connDelete, ids);
        dbOne.disconnect(connDelete);
        Airline returnAirline = airlines.get(0);
        assertTrue(returnAirline.getAirlineID() == testAirline.getAirlineID());
    }

    @Test
    public void testRouteSavesCorrectly() {
        RouteParser rp = new RouteParser();
        //Make a route to test and add it to an array
        Route testRoute = rp.createSingleRoute("2N,3652,VBY,746,ARN,737,,0,SF3 ATP, 1001");
        testRoute.setRouteID(10);
        ObservableList<Route> testArray = FXCollections.observableArrayList();
        testArray.add(testRoute);
        //Create the connections to the database
        //Save the new route here
        dbsave.saveRoutes(connSave, testArray);
        //Search for the new saved route, only one currently in database with id 10
        String sqlStatement = dbsearch.buildRouteSearch("equipment", "SF3");
        ObservableList<Route> returnedRoutes = dbsearch.searchRouteByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSave);
        dbOne.disconnect(connSearch);
        //Current test database should only return one result
        //Remove the route from the database for test repetability.
        ArrayList<Integer> idsToDelete = new ArrayList<Integer>();
        idsToDelete.add(10);
        dbsave.deleteRoutes(connDelete, idsToDelete);
        dbOne.disconnect(connDelete);
        Route testReturnedRoute = returnedRoutes.get(0);
        assertTrue(testReturnedRoute.getRouteID() == testRoute.getRouteID());
    }

    @Test
    public void testRouteDeletesProperly() {

        RouteParser rp = new RouteParser();
        //Make a route to test and add it to an array
        Route testRoute = rp.createSingleRoute("2N,3652,VBY,746,ARN,737,,0,SF3 ATP, 1001");
        testRoute.setRouteID(10);
        ObservableList<Route> testArray = FXCollections.observableArrayList();
        testArray.add(testRoute);

        //Add route to the database
        dbsave.saveRoutes(connSave, testArray);
        dbOne.disconnect(connSave);

        //Delete it
        ArrayList<Integer> idsToDelete = new ArrayList<Integer>();
        idsToDelete.add(10);
        dbsave.deleteRoutes(connDelete, idsToDelete);
        dbOne.disconnect(connDelete);

        //Search for deleted data
        String sqlStatement = dbsearch.buildRouteSearch("routeid", "10");
        ObservableList<Route> returnedRoutes = dbsearch.searchRouteByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSearch);

        //Assertion Test
        assertTrue(returnedRoutes.size() == 0);


    }

    @Test
    public void testAirportDeletesProperly() {

        //Create Objects to save and load to and from the database
        AirportParser ap = new AirportParser();
        //Create an airport to save to the database
        Airport testAirport = ap.createSingleAirport("54,Inuvik Mike Zubko,Inuvik,Canada,YEV,CYEV,68.304167,-133.482778,224,-7,A,America/Edmonton");
        //Create an array to save the new airport to the database
        ObservableList<Airport> testArray = FXCollections.observableArrayList();
        testArray.add(testAirport);

        //Add Airport to the database
        dbsave.saveAirports(connSave, testArray);
        dbOne.disconnect(connSave);

        //Delete It
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(54);
        dbsave.deleteAirport(connDelete, ids);
        dbOne.disconnect(connDelete);

        //Search for deleted data
        String sqlStatement = dbsearch.buildAirportSearch("airportid", "54");
        ObservableList<Airport> airports = dbsearch.searchForAirportByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSearch);

        //Assertion Test
        assertTrue(airports.size() == 0);
    }


    @Test
    public void testAirlineDeletesProperly() {

        //Create the objects to save and load to and from the database
        AirlineParser ap = new AirlineParser();
        //Create a test airline to save to the airport
        Airline testAirline = ap.createSingleAirline("96,Aegean Airlines,\\N,A3,AEE,AEGEAN,Greece,Y");
        ObservableList<Airline> testArray = FXCollections.observableArrayList();
        testArray.add(testAirline);

        //Add airline to the database
        dbsave.saveAirlines(connSave, testArray);
        dbOne.disconnect(connSave);

        //Delete airline from the database
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(96);
        dbsave.deleteAirlines(connDelete, ids);
        dbOne.disconnect(connDelete);

        //Search for deleted data
        String sqlStatement = dbsearch.buildAirlineSearch("airlineid", "96");
        ObservableList<Airline> airlines = dbsearch.searchForAirlinesByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSearch);

        //Assertion Test
        assertTrue(airlines.size() == 0);
    }

}
