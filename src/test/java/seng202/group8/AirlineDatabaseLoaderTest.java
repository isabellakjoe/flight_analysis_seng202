package seng202.group8;

import org.junit.Test;
import seng202.group8.Model.Airline;
import seng202.group8.Model.AirlineDatabaseLoader;
import seng202.group8.Model.AirlineParser;

import java.sql.Connection;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 25/08/16.
 */
public class AirlineDatabaseLoaderTest {

    @Test
    public void testAllRoutesLoadCorrectly(){
        AirlineDatabaseLoader db = new AirlineDatabaseLoader();
        Connection conn = db.connect();
        Set<Airline> airlines = db.loadAirlines(conn);
        //Currently 7 airlines in the database
        assertTrue(7 == airlines.size());
        db.disconnect(conn);
    }

    @Test
    public void testAirlineIDParsesCorrectly() {
        AirlineDatabaseLoader db = new AirlineDatabaseLoader();
        Connection conn = db.connect();
        Set<Airline> airlines = db.loadAirlines(conn);
        Airline testAirline = new Airline();
        //Because we are using a set, have to find where in set airline to test is.
        for (Airline airline: airlines) {
            if (airline.getName().equals("Servicios Aereos Del Vaupes")) {
                testAirline = airline;
            }
        }
        assertTrue(testAirline.getName().equals("Servicios Aereos Del Vaupes"));
        db.disconnect(conn);
    }


}
