package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.RouteDatabaseLoader;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 25/08/16.
 */
public class RouteDatabaseLoaderTest {

    Database db;
    Connection conn;
    RouteDatabaseLoader dbr;

    @Before
    public void initalise() {
        db = new Database();
        conn = db.testConnect();
        dbr = new RouteDatabaseLoader();
    }

    @After
    public void teardown() {
        db.disconnect(conn);
    }

    @Test
    public void testAllRoutesLoadCorrectly(){
        ObservableList<Route> routes = dbr.loadRoutes(conn);
        //Currently 16 routes in the database
        assertTrue(16 == routes.size());
    }

    @Test
    public void testRouteLoads() {
        ObservableList<Route> routes = dbr.loadRoutes(conn);
        Route testRoute = new Route();
        for (Route route: routes) {
            if (route.getRouteID() == 500) {
                testRoute = route;
            }
        }
        assertTrue(testRoute.getSourceAirport().getAirportID() == 1824);
    }

}
