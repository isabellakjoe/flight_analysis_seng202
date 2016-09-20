package seng202.group8.DatabaseTests;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.AirlineDatabaseLoader;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.Objects.Airline;

import java.sql.Connection;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 25/08/16.
 */
public class AirlineDatabaseLoaderTest {

    Database db;
    Connection conn;
    AirlineDatabaseLoader dba;
    HashMap<String, Airline> airlineIATAHashMap;

    @Before
    public void initialise() {
        db = new Database();
        conn = db.testConnect();
        dba = new AirlineDatabaseLoader();
        airlineIATAHashMap = new HashMap<String, Airline>();
    }

    @After
    public void teardown() {
        db.disconnect(conn);
    }

    @Test
    public void testAllAirlinesLoadCorrectly() {
        ObservableList<Airline> airlines = dba.loadAirlines(conn, airlineIATAHashMap);
        //Currently 7 airlines in the database
        assertTrue(7 == airlines.size());
    }

    @Test
    public void testAirlineLoadsCorrectly() {
        ObservableList<Airline> airlines = dba.loadAirlines(conn, airlineIATAHashMap);
        Airline testAirline = new Airline();
        //Because we are using a set, have to find where in set airline to test is.
        for (Airline airline : airlines) {
            if (airline.getName().equals("Servicios Aereos Del Vaupes")) {
                testAirline = airline;
            }
        }
        assertTrue(testAirline.getName().equals("Servicios Aereos Del Vaupes"));
    }

}
