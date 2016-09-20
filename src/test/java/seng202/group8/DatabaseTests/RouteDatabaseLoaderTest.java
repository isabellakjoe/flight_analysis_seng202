package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.RouteDatabaseLoader;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 25/08/16.
 */
public class RouteDatabaseLoaderTest {

    //TODO: This test class needs to be refactored as routes will no longer be added without airlines and airports.

    Database db;
    Connection conn;
    RouteDatabaseLoader dbr;
    HashMap<Integer, Route> routeHashMap;
    HashMap<String, Airline> airlineHashMap;
    HashMap<String, Airport> airportHashMap;

    @Before
    public void initalise() {
        db = new Database();
        conn = db.testConnect();
        dbr = new RouteDatabaseLoader();
        routeHashMap = new HashMap<Integer, Route>();
        airlineHashMap = new HashMap<String, Airline>();
        airportHashMap = new HashMap<String, Airport>();
    }

    @After
    public void teardown() {
        db.disconnect(conn);
    }

    @Test
    public void testAllRoutesLoadCorrectly() {
        //ObservableList<Route> routes = dbr.loadRoutes(conn, routeHashMap, airlineHashMap, airportHashMap);
        //Currently 17 routes in the test database
        //assertTrue(17 == routes.size());
    }

    @Test
    public void testRouteLoads() {
        //ObservableList<Route> routes = dbr.loadRoutes(conn, routeHashMap, airlineHashMap, airportHashMap);
        //Route testRoute = new Route();
        //for (Route route : routes) {
            //if (route.getRouteID() == 500) {
                //testRoute = route;
            //}
        //}
        //assertTrue(testRoute.getSourceAirport().getAirportID() == 1824);
    }

}
