package seng202.group8.DatabaseTests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
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
import java.sql.Statement;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 29/08/16.
 */
public class DatabaseSaverTest {

    Database dbOne;
    Database dbTwo;
    Database dbThree;
    DatabaseSaver dbsave;
    DatabaseSearcher dbsearch;
    Connection connSave;
    Connection connSearch;
    Connection connDelete;


    @Before
    public void initialise() {
        dbOne = new Database();
        dbTwo = new Database();
        dbThree = new Database();
        dbsave = new DatabaseSaver();
        dbsearch = new DatabaseSearcher();
        connSave = dbOne.testConnect();
        connSearch = dbTwo.testConnect();
        connDelete = dbThree.testConnect();
    }

    @Test
    public void testAirportSavesCorrectly(){
        //Create Objects to save and load to and from the database
        AirportParser ap = new AirportParser();
        //Create an airport to save to the database
        Airport testAirport = ap.createSingleAirport("54,Inuvik Mike Zubko,Inuvik,Canada,YEV,CYEV,68.304167,-133.482778,224,-7,A,America/Edmonton");
        //Create an array to save the new airport to the database
        ObservableList<Airport> testArray = FXCollections.observableArrayList();
        testArray.add(testAirport);
        //Save the aiport to the database here
        dbsave.saveAirports(connSave, testArray);
        //Search for the new saved aiport here
        String sqlStatement = dbsearch.buildAirportSearch("name","Inuvik Mike Zubko");
        ObservableList<Airport> airports = dbsearch.searchForAirportByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSave);
        dbTwo.disconnect(connSearch);
        //Close the database queries here
        //Current test database should only return one result
        //Remove the aiport from the database for test repetability.
        //TODO: Implements as a class later
        try {
            Statement stmt = connDelete.createStatement();
            String sql = "DELETE FROM airport WHERE name=\'Inuvik Mike Zubko\';";
            stmt.executeUpdate(sql);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            //System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
        //Check here if correct airline inserted into database
        Airport returnAirport = airports.get(0);
        assertTrue(returnAirport.getName().equals(testAirport.getName()));
        dbThree.disconnect(connDelete);

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
        //Search for the new airline in the database here
        String sqlStatement = dbsearch.buildAirlineSearch("name", "Aegean Airlines");
        ObservableList<Airline> airlines = dbsearch.searchForAirlinesByOption(connSearch, sqlStatement);
        //Current test database should only return one result
        //Remove the aiport from the database for test repetability.
        dbOne.disconnect(connSave);
        dbTwo.disconnect(connSearch);
        //TODO: Implements as a class later
        try {
            Statement stmt = connDelete.createStatement();
            String sql = "DELETE FROM airline WHERE name=\'Aegean Airlines\';";
            stmt.executeUpdate(sql);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        Airline returnAirline = airlines.get(0);
        assertTrue(returnAirline.getAirlineID() == testAirline.getAirlineID());

        dbThree.disconnect(connDelete);

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
        ObservableList<Route>returnedRoutes = dbsearch.searchRouteByOption(connSearch, sqlStatement);
        dbOne.disconnect(connSave);
        dbTwo.disconnect(connSearch);
        //Current test database should only return one result
        //Remove the route from the database for test repetability.
        //TODO: Implements as a class later
        Database dbThree = new Database();
        Connection connDelete = dbThree.testConnect();
        try {
            Statement stmt = connDelete.createStatement();
            String sql = "DELETE FROM route WHERE routeid=10;";
            stmt.executeUpdate(sql);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
        Route testReturnedRoute = returnedRoutes.get(0);
        assertTrue(testReturnedRoute.getRouteID() == testRoute.getRouteID());
        dbThree.disconnect(connDelete);


    }

}
