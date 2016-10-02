package seng202.group8.ParserAndLoaderTests;

import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.RouteParser;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 21/08/16.
 */
public class RouteParserTest {

    HashMap<String, Airline> airlineHashMap;
    HashMap<String, Airport> airportHashMap;
    Airline air;
    Airport src;
    Airport dest;
    /* String containing IATA information */
    private String routeString = "2B,410,AER,2965,KZN,2990,Y,10,CR2";

    @Before
    public void intialise() {
        airlineHashMap = new HashMap<String, Airline>();
        airportHashMap = new HashMap<String, Airport>();
        air = new Airline();
        src = new Airport();
        dest = new Airport();

        air.setIATA("2B");
        air.setAirlineID(410);
        airlineHashMap.put("2B", air);

        src.setIATA("AER");
        src.setAirportID(2965);
        dest.setIATA("KZN");
        dest.setAirportID(2990);
        airportHashMap.put("AER", src);
        airportHashMap.put("KZN", dest);
    }

    @Test
    /* Test to see if the airline is added correctly to a route object */
    public void testAirlineParsesCorrectly() {

        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1001, airlineHashMap, airportHashMap);

        assertTrue(route.getAirline().getAirlineID() == 410);
    }

    @Test
    /* Test to see if the source airport is correctly added to a route object */
    public void testSourceAirportParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1002, airlineHashMap, airportHashMap);

        assertTrue(route.getSourceAirport().getAirportID() == 2965);
    }

    @Test
    /* Test to see if the destination airport is correctly added to a route object */
    public void testDestinationAirportParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1003, airlineHashMap, airportHashMap);

        assertTrue(route.getDestinationAirport().getAirportID() == 2990);
    }


    @Test
    /* Test to see whether codeshare boolean logic parses correctly */
    public void testCodeShareWorksCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1006, airlineHashMap, airportHashMap);

        assertEquals(route.isCodeshare(), true);
    }

    @Test
    /* Test to see if the number of stops parses correctly */
    public void testStopsParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1007, airlineHashMap, airportHashMap);
        assertEquals(route.getStops(), 10);
    }

    @Test
    /* Test to see whether the equipment specified is added to a route object correctly */
    public void testEquipmentParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1008, airlineHashMap, airportHashMap);
        assertTrue(route.getEquipment().equals("CR2"));
    }
}




