package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.DatabaseMethods.AirportDatabaseLoader;

import java.sql.Connection;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 26/08/16.
 */
public class AirportDatabaseLoaderTest {



    @Test
    public void testAllRoutesLoadCorrectly(){
        Database db = new Database();
        AirportDatabaseLoader dba = new AirportDatabaseLoader();
        Connection conn = db.testConnect();
        ObservableList<Airport> airports = dba.loadAirport(conn);
        //Currently 14 airlines in the database
        assertTrue(14 == airports.size());
        db.disconnect(conn);
    }

}
