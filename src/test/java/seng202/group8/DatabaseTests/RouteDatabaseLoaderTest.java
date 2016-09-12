package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
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

    @Test
    public void testAllRoutesLoadCorrectly(){
        Database db = new Database();
        RouteDatabaseLoader dbr = new RouteDatabaseLoader();
        Connection conn = db.testConnect();
        ObservableList<Route> routes = dbr.loadRoutes(conn);
        //Currently 16 routes in the database
        assertTrue(16 == routes.size());
        db.disconnect(conn);
    }


}
