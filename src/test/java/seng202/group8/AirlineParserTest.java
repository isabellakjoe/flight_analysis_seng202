package seng202.group8;

import org.junit.Test;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Parsers.AirlineParser;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by swa158 on 22/08/16.
 */
public class AirlineParserTest {

    private String airlineString = "324,All Nippon Airways,ANA All Nippon Airways,NH,ANA,ALL NIPPON,Japan,Y";
    private String noAliasString = "345,Air New Zealand,\\N,NZ,ANZ,NEW ZEALAND,New Zealand,Y";

    @Test
    public void testAirlineIDParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.getAirlineID(), 324);
    }

    @Test
    public void testNameParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.getName(), "All Nippon Airways");
    }

    @Test
    public void testAliasParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.getAlias(), "ANA All Nippon Airways");
    }

    @Test
    public void testNoAliasParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(noAliasString);

        assertEquals(airline.getAlias(), null);
    }

    @Test
    public void testIATAParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.getIATA(), "NH");
    }
    @Test
    public void testICAOParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.getICAO(), "ANA");
    }

    @Test
    public void testCallsignParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.getCallsign(), "ALL NIPPON");
    }

    @Test
    public void testCountryParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.getCountry(), "Japan");
    }

    @Test
    public void testIsActiveParsesCorrectly() {
        AirlineParser airlineParser = new AirlineParser();
        Airline airline = airlineParser.createSingleAirline(airlineString);

        assertEquals(airline.isActive(), true);
    }

}
