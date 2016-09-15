package seng202.group8.ParserAndLoaderTests;


import org.junit.Test;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Parsers.AirportParser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by ikj11 on 22/08/16.
 */
public class AirportParserTest {

    /* String containing IATA information */
    private String IATAString = "2006,Auckland Intl,Auckland,New Zealand,AKL,NZAA,-37.008056,174.791667,23,12,Z,Pacific/Auckland";
    /* String containing FAA and null information */
    private String FAAString = "6891,Putnam County Airport,Greencastle,United States,4I7,\\N,39.6335556,-86.8138056,842,-5,U,America/New_York";


    @Test
    /* Test to see if the airportID is correctly added to an airport object */
    public void testAirportID() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertTrue(airportTest.getAirportID() == 2006);

    }

    @Test
    /* Test to see if the Airport name is correctly added to an airport object */
    public void testAirportName() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertTrue(airportTest.getName().equals("Auckland Intl"));

    }

    @Test
    /* Test to see if the Airport ICAO is correctly added to an airport object */
    public void testAirportICAO() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertTrue(airportTest.getICAO().equals("NZAA"));
    }

    @Test
    /* Test to see if the Airport city is correctly added to an airport object */
    public void testAirportCity() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertTrue(airportTest.getCity().equals("Auckland"));
    }

    @Test
    /* Test to see if the Airport country is correctly added to an airport object */
    public void testAirportCountry() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertTrue(airportTest.getCountry().equals("New Zealand"));
    }

    @Test
    /* Test to see if the Airport latitude is correctly added to an airport object */
    public void testAirportLatitude() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertEquals(airportTest.getLatitude(), -37.008056);
    }

    @Test
    /* Test to see if the Airport Longitude is correctly added to an airport object */
    public void testAirportLongitude() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertEquals(airportTest.getLongitude(), 174.791667);
    }

    @Test
    /* Test to see if the Airport altitude is correctly addedto an airport object */
    public void testAirportAltitude() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertEquals(airportTest.getAltitude(), 23, 1e-8);
    }

    @Test
    /* Test to see if the Airport timezone is correctly added to an airport object */
    public void testAirportTimezone() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertEquals(airportTest.getTimezone(), 12);
    }

    @Test
    /* Test to see if the Airport DST is correctly added to an airport object */
    public void testAirportDST() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertEquals(airportTest.getDST(), 'Z');
    }

    @Test
    /* Test to see if the Airport Olson timezone is correctly added to an airport object */
    public void testAirportTimezoneInfoCorrect() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertTrue(airportTest.getOlsonTimezone().equals("Pacific/Auckland"));
    }

    @Test
    /* Test to see if the IATA information is correctly added to an airport object */
    public void testAirportIATA() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(IATAString);

        assertTrue(airportTest.getIATA().equals("AKL"));
    }


    @Test
    /* Test to see if the FAA information is correctly added to an airport object */
    public void testAirportFAA() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(FAAString);

        assertTrue(airportTest.getFAA().equals("4I7"));
    }

    @Test
    /* Test to see if null element is correctly added to an airport object */
    public void testNullInput() {
        AirportParser airportParser = new AirportParser();
        Airport airportTest = airportParser.createSingleAirport(FAAString);

        assertEquals(airportTest.getICAO(), null);
    }

}
