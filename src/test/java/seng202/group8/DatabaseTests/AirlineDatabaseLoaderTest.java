package seng202.group8.DatabaseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.DatabaseMethods.AirlineDatabaseLoader;

import java.sql.Connection;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 25/08/16.
 */
public class AirlineDatabaseLoaderTest {

    Database db;
    Connection conn;
    AirlineDatabaseLoader dba;

    @Before
    public void initialise() {
        db = new Database();
        conn = db.testConnect();
        dba = new AirlineDatabaseLoader();
    }

    @After
    public void teardown() {
        db.disconnect(conn);
    }

    @Test
    public void testAllRoutesLoadCorrectly(){
        ArrayList<Airline> airlines = dba.loadAirlines(conn);
        //Currently 7 airlines in the database
        assertTrue(7 == airlines.size());
    }

    @Test
    public void testAirlineIDParsesCorrectly() {
        ArrayList<Airline> airlines = dba.loadAirlines(conn);
        Airline testAirline = new Airline();
        //Because we are using a set, have to find where in set airline to test is.
        for (Airline airline: airlines) {
            if (airline.getName().equals("Servicios Aereos Del Vaupes")) {
                testAirline = airline;
            }
        }
        assertTrue(testAirline.getName().equals("Servicios Aereos Del Vaupes"));
    }


}
