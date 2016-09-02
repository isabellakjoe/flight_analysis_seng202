package seng202.group8;

import org.junit.Test;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Parsers.AirportParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Erika on 02-Sep-16.
 */
public class AirportTest {

    private String testAirport1 = "2006,Auckland Intl,Auckland,New Zealand,AKL,NZAA,-37.008056,174.791667,23,12,Z,Pacific/Auckland";
    private String testAirport2 = "6891,Putnam County Airport,Greencastle,United States,4I7,\\N,39.6335556,-86.8138056,842,-5,U,America/New_York";
    @Test
    public void airportDistanceCalculatorBasicTest(){
        AirportParser parser = new AirportParser();

        Airport airport1 = parser.createSingleAirport(testAirport1);
        Airport airport2 = parser.createSingleAirport(testAirport2);
        double distance = airport1.calculateDistanceTo(airport2);
        assertEquals(distance, 13153, 50);


    }
}
