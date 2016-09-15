package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.AirportDatabaseLoader;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.Objects.Airport;

import java.sql.Connection;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 26/08/16.
 */
public class AirportDatabaseLoaderTest {

    Database db;
    Connection conn;
    AirportDatabaseLoader dba;

    @Before
    public void initialise() {
        db = new Database();
        dba = new AirportDatabaseLoader();
        conn = db.testConnect();
    }

    @After
    public void teardown() {
        db.disconnect(conn);
    }

    @Test
    public void testAllAirportsLoadCorrectly() {
        ObservableList<Airport> airports = dba.loadAirport(conn);
        //Currently 14 airlines in the database
        assertTrue(14 == airports.size());
    }

    @Test
    public void testAiportLoads() {
        ObservableList<Airport> airports = dba.loadAirport(conn);
        Airport testAirport = new Airport();
        for (Airport airport : airports) {
            if (airport.getAirportID() == 2962) {
                testAirport = airport;
            }
        }
        assertTrue(testAirport.getCountry().equals("Russia"));
    }


}
