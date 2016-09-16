package seng202.group8.ParserAndLoaderTests;

import org.junit.Test;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.RouteParser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 21/08/16.
 */
public class RouteParserTest {

    /* String containing IATA information */
    private String routeString = "2B,410,AER,2965,KZN,2990,Y,10,CR2";
    /* String containing ICAO information */
    private String ICAOString = "9W,3000,TRVA,3153,BOMV,2997,,0,73H";
    /* String containing empty inforamtion */
    private String emptyString = "4Y,\\N,WNA,\\N,PKA,\\N,,0,CNA";

    @Test
    /* Test to see if the airline is added correctly to a route object */
    public void testAirlineParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1001);

        Airline airline = new Airline();
        airline.setName("2B");
        airline.setAirlineID(410);

        assertTrue(route.getAirline().getName().equals(airline.getName()));
        assertTrue(route.getAirline().getAirlineID() == airline.getAirlineID());
    }

    @Test
    /* Test to see if the source airport is correctly added to a route object */
    public void testSourceAirportParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1002);

        /* IATA TEST CASE */
        Airport sourceAirport = new Airport();
        sourceAirport.setIATA("AER");
        sourceAirport.setAirportID(2965);

        assertEquals(route.getSourceAirport().getIATA(), sourceAirport.getIATA());
        assertTrue(route.getSourceAirport().getAirportID() == sourceAirport.getAirportID());
    }

    @Test
    /* Test to see if the destination airport is correctly added to a route object */
    public void testDestinationAirportParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1003);

        /* IATA TEST CASE */
        Airport destinationAirport = new Airport();
        destinationAirport.setIATA("KZN");
        destinationAirport.setAirportID(2990);

        assertEquals(route.getDestinationAirport().getIATA(), destinationAirport.getIATA());
        assertTrue(route.getDestinationAirport().getAirportID() == destinationAirport.getAirportID());
    }

    @Test
    public void testSourceAirportICAOParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(ICAOString, 1004);

        /* ICAO TEST CASE */
        Airport sourceAirport = new Airport();
        sourceAirport.setICAO("TRVA");
        sourceAirport.setAirportID(3153);

        assertEquals(route.getSourceAirport().getICAO(), sourceAirport.getICAO());
        assertTrue(route.getSourceAirport().getAirportID() == sourceAirport.getAirportID());
    }

    @Test
    public void testDesinationAirportICAOParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(ICAOString, 1005);

        /* ICAO TEST CASE */
        Airport destinationAirport = new Airport();
        destinationAirport.setICAO("BOMV");
        destinationAirport.setAirportID(2997);

        assertEquals(route.getDestinationAirport().getICAO(), destinationAirport.getICAO());
        assertTrue(route.getDestinationAirport().getAirportID() == destinationAirport.getAirportID());
    }

    @Test
    /* Test to see whether codeshare boolean logic parses correctly */
    public void testCodeShareWorksCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1006);

        assertEquals(route.isCodeshare(), true);
    }

    @Test
    /* Test to see if the number of stops parses correctly */
    public void testStopsParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1007);
        assertEquals(route.getStops(), 10);
    }

    @Test
    /* Test to see whether the equipment specified is added to a route object correctly */
    public void testEquipmentParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(routeString, 1008);
        assertTrue(route.getEquipment().equals("CR2"));
    }

    @Test
    /* Test to see if the airline is added correctly to a route object when null */
    public void testEmptyAirlineParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(emptyString, 1010);

        Airline airline = new Airline();
        airline.setName("4Y");
        airline.setAirlineID(-1);

        assertTrue(route.getAirline().getName().equals(airline.getName()));
        assertTrue(route.getAirline().getAirlineID() == airline.getAirlineID());
    }

    /* Test to see if source airport is added correctly to a route object when null */
    @Test
    public void testEmptySourceAirportParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(emptyString, 10002);

        Airport sourceAirport = new Airport();
        sourceAirport.setICAO("WNA");
        sourceAirport.setAirportID(-1);

        /**
         * Something wrong here, IATA is the equivalent of ICAO???
         **/
        assertEquals(route.getSourceAirport().getICAO(), sourceAirport.getIATA());
        assertTrue(route.getSourceAirport().getAirportID() == sourceAirport.getAirportID());
    }

    /* Test to see if destination airport is added correctly to a route object when null */
    @Test
    public void testEmptyDestinationAirportParsesCorrectly() {
        RouteParser routeParser = new RouteParser();
        Route route = routeParser.createSingleRoute(emptyString, 9999);

        Airport destinationAirport = new Airport();
        destinationAirport.setICAO("WNA");
        destinationAirport.setAirportID(-1);

        /**
         * Something wrong here, IATA is the equivalent of ICAO???
         **/
        assertEquals(route.getSourceAirport().getICAO(), destinationAirport.getIATA());
        assertTrue(route.getSourceAirport().getAirportID() == destinationAirport.getAirportID());
    }
}




