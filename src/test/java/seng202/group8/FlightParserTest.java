package seng202.group8;

/**
 * Created by Erika on 22-Aug-16.
 */


import javafx.collections.ObservableList;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Flight;
import seng202.group8.Model.Parsers.FlightParser;
import seng202.group8.Model.Objects.Waypoint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlightParserTest {

    private static Flight flight;

    @BeforeClass
    public static void setUpBeforeClass() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("NZCH-WSSS.csv"));
        FlightParser flightParser = new FlightParser(br);
        flight = flightParser.parseFlightFile();
    }

    @Test
    public void sourceAirportNameTest() {
        Airport sourceAirport = flight.getSourceAirport();
        String name = sourceAirport.getName();
        assertTrue(name.equals("NZCH"));
    }

    @Test
    public void sourceAirportAltitudeTest() {
        Airport sourceAirport = flight.getSourceAirport();
        int altitude = sourceAirport.getAltitude();
        assertTrue((altitude == 0));
    }

    @Test
    public void sourceAirportLatitudeTest() {
        Airport sourceAirport = flight.getSourceAirport();
        double latitude = sourceAirport.getLatitude();
        assertEquals(-43.48664019, latitude, 1e-8);
    }

    @Test
    public void sourceAirportLongitudeTest() {
        Airport sourceAirport = flight.getSourceAirport();
        double longitude = sourceAirport.getLongitude();
        assertEquals(172.53368221, longitude, 1e-8);
    }
    @Test
    public void destinationAirportNameTest() {
        Airport destinationAirport = flight.getDestinationAirport();
        String name = destinationAirport.getName();
        assertTrue(name.equals("WSSS"));
    }

    @Test
    public void firstWaypointTypeTest(){
        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        Waypoint firstWaypoint = waypoints.get(0);
        String type = firstWaypoint.getType();
        assertTrue(type.equals("VOR"));
    }

    @Test
    public void firstWaypointNameTest(){
        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        Waypoint firstWaypoint = waypoints.get(0);
        String name = firstWaypoint.getName();
        assertTrue(name.equals("CH"));
    }

    @Test
    public void firstWaypointAltitudeTest(){
        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        Waypoint firstWaypoint = waypoints.get(0);
        double altitude = firstWaypoint.getAltitude();
        assertEquals(400, altitude, 1e-2);
    }

    @Test
    public void firstWaypointLatitudeTest(){
        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        Waypoint firstWaypoint = waypoints.get(0);
        double latitude = firstWaypoint.getLatitude();
        assertEquals(-43.50411111, latitude, 1e-8);
    }

    @Test
    public void firstWaypointLongitudeTest(){
        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        Waypoint firstWaypoint = waypoints.get(0);
        double longitude = firstWaypoint.getLongitude();
        assertEquals(172.51463889000001, longitude, 1e-16);
    }

    @Test
    public void lastWaypointNameTest(){
        ObservableList<Waypoint> waypoints = flight.getWaypoints();
        Waypoint lastWaypoint = waypoints.get(28);
        String name = lastWaypoint.getName();
        assertTrue(name.equals("TI"));
    }

    @Test
    public void destinationAirportAltitudeTest() {
        Airport destinationAirport = flight.getDestinationAirport();
        int altitude = destinationAirport.getAltitude();
        assertTrue((altitude == 0));
    }

    @Test
    public void destinationAirportLatitudeTest() {
        Airport destinationAirport = flight.getDestinationAirport();
        double latitude = destinationAirport.getLatitude();
        assertEquals(1.3519171399999976, latitude, 1e-16);
    }

    @Test
    public void destinationAirportLongitudeTest() {
        Airport destinationAirport = flight.getDestinationAirport();
        double longitude = destinationAirport.getLongitude();
        assertEquals(103.99560303999999, longitude, 1e-16);
    }


}



