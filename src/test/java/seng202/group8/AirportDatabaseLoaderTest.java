package seng202.group8;

import org.junit.Test;
import seng202.group8.Model.Airport;
import seng202.group8.Model.AirportDatabaseLoader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 26/08/16.
 */
public class AirportDatabaseLoaderTest {

    @Test
    public void testAllRoutesLoadCorrectly(){
        AirportDatabaseLoader db = new AirportDatabaseLoader();
        Connection conn = db.connect();
        ArrayList<Airport> airports = db.loadAirport(conn);
        //Currently 7 airlines in the database
        assertTrue(9 == airports.size());
        db.disconnect(conn);
    }

}
