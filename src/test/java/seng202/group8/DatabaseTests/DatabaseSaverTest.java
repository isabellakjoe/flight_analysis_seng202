package seng202.group8.DatabaseTests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @Test
    public void testAirportSavesCorrectly(){
        //Create Objects to save and load to and from the database
        Database dbOne = new Database();
        Database dbTwo = new Database();
        DatabaseSaver dbsave = new DatabaseSaver();
        DatabaseSearcher dbsearch = new DatabaseSearcher();
        AirportParser ap = new AirportParser();
        //Create an airport to save to the database
        Airport testAirport = ap.createSingleAirport("54,Inuvik Mike Zubko,Inuvik,Canada,YEV,CYEV,68.304167,-133.482778,224,-7,A,America/Edmonton");
        //Create an array to save the new airport to the database
        ObservableList<Airport> testArray = FXCollections.observableArrayList();
        testArray.add(testAirport);
        Connection connSave = dbOne.testConnect();
        Connection connSearch = dbTwo.testConnect();
        //Save the aiport to the database here
        dbsave.saveAirports(connSave, testArray);
        //Search for the new saved aiport here
        ObservableList<Airport> airports = dbsearch.searchForAirportByOption(connSearch, "name", "Inuvik Mike Zubko");
        //Close the database queries here
        dbOne.disconnect(connSave);
        dbTwo.disconnect(connSearch);
        //Current test database should only return one result
        //Remove the aiport from the database for test repetability.
        //TODO: Implements as a class later
        Database dbThree = new Database();
        Connection connDelete = dbThree.testConnect();
        try {
            Statement stmt = connDelete.createStatement();
            String sql = "DELETE FROM airport WHERE name=\'Inuvik Mike Zubko\';";
            stmt.executeUpdate(sql);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
        dbThree.disconnect(connDelete);
        //Check here if correct airline inserted into database
        Airport returnAirport = airports.get(0);
        assertTrue(returnAirport.getName().equals(testAirport.getName()));

    }


    @Test
    public void testAirlineSavesCorrectly() {
        //Create the objects to save and load to and from the database
        Database dbOne = new Database();
        Database dbTwo = new Database();
        DatabaseSaver dbsave = new DatabaseSaver();
        DatabaseSearcher dbsearch = new DatabaseSearcher();
        AirlineParser ap = new AirlineParser();
        //Create a test airline to save to the airport
        Airline testAirline = ap.createSingleAirline("96,Aegean Airlines,\\N,A3,AEE,AEGEAN,Greece,Y");
        ObservableList<Airline> testArray = FXCollections.observableArrayList();
        testArray.add(testAirline);
        //Create the connections to the database
        Connection connSave = dbOne.testConnect();
        Connection connSearch = dbTwo.testConnect();
        //Save the new airline here
        dbsave.saveAirlines(connSave, testArray);
        //Search for the new airline in the database here
        ObservableList<Airline> airlines = dbsearch.searchForAirlinesByOption(connSearch, "name", "Aegean Airlines");
        dbOne.disconnect(connSave);
        dbTwo.disconnect(connSearch);
        //Current test database should only return one result
        //Remove the aiport from the database for test repetability.
        //TODO: Implements as a class later
        Database dbThree = new Database();
        Connection connDelete = dbThree.testConnect();
        try {
            Statement stmt = connDelete.createStatement();
            String sql = "DELETE FROM airline WHERE name=\'Aegean Airlines\';";
            stmt.executeUpdate(sql);
            connDelete.commit();
            stmt.close();
        } catch (Exception e ) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        dbThree.disconnect(connDelete);
        Airline returnAirline = airlines.get(0);
        assertTrue(returnAirline.getAirlineID() == testAirline.getAirlineID());

        }

    @Test
    public void testRouteSavesCorrectly() {
        Database dbOne = new Database();
        Database dbTwo = new Database();
        //Create the objects to save and search the database
        DatabaseSaver dbsave = new DatabaseSaver();
        DatabaseSearcher dbsearch = new DatabaseSearcher();
        RouteParser rp = new RouteParser();
        //Make a route to test and add it to an array
        Route testRoute = rp.createSingleRoute("2N,3652,VBY,746,ARN,737,,0,SF3 ATP");
        testRoute.setRouteID(10);
        ObservableList<Route> testArray = FXCollections.observableArrayList();
        testArray.add(testRoute);
        //Create the connections to the database
        Connection connSave = dbOne.testConnect();
        Connection connSearch = dbTwo.testConnect();
        //Save the new route here
        dbsave.saveRoutes(connSave, testArray);
        //Search for the new saved route, only one currently in database with id 10
        ObservableList<Route>returnedRoutes = dbsearch.searchRouteByOption(connSearch, "equipment", "SF3");
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
        dbThree.disconnect(connDelete);
        Route testReturnedRoute = returnedRoutes.get(0);
        assertTrue(testReturnedRoute.getRouteID() == testRoute.getRouteID());


    }

}
