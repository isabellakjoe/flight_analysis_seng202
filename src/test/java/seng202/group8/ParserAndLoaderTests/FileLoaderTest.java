package seng202.group8.ParserAndLoaderTests;

import javafx.collections.ObservableList;
import org.junit.Test;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.AirlineParser;
import seng202.group8.Model.Parsers.AirportParser;
import seng202.group8.Model.Parsers.FileLoader;
import seng202.group8.Model.Parsers.RouteParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by asc132 on 22/08/2016.
 */
public class FileLoaderTest {

    private ArrayList<String> auck = new ArrayList<String>(Arrays.asList("2006","Auckland Intl","Auckland","New Zealand","AKL","NZAA","-37.008056","174.791667","23","12","Z","Pacific/Auckland"));

    @Test
    public void buildAirportsTest() throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("PerfectAirportsOnly.txt")));
        ObservableList<Airport> airportsActual = loader.buildAirports();
        ArrayList<Airport> airportsExpected = new ArrayList<Airport>();
        AirportParser parser = new AirportParser();
        Airport airport1 = parser.createSingleAirport("2006,Auckland Intl,Auckland,New Zealand,AKL,NZAA,-37.008056,174.791667,23,12,Z,Pacific/Auckland");
        Airport airport2 = parser.createSingleAirport("2007,Christchurch Intl,Christchurch,New Zealand,AKL,NZAA,-37.008056,174.791667,23,12,Z,Pacific/Auckland");
        Airport airport3 = parser.createSingleAirport("2008,Wellington Intl,Wellington,New Zealand,AKL,NZAA,-37.008056,174.791667,23,12,Z,Pacific/Auckland");
        airportsExpected.add(airport1);
        airportsExpected.add(airport2);
        airportsExpected.add(airport3);
        boolean isEqual = true;
        for(int i = 0; i < 3; i++){
            Airport a = airportsExpected.get(i);
            Airport b = airportsActual.get(i);
            if (!a.isEqualTo(b)){
                isEqual = false;
            }
        }
        assertTrue(isEqual);
    }

    @Test
    public void buildAirlinesTest() throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("PerfectAirlinesOnly.txt")));
        ObservableList<Airline> airlinesActual = loader.buildAirlines();
        ArrayList<Airline> airlinesExpected = new ArrayList<Airline>();
        AirlineParser parser = new AirlineParser();
        Airline airline1 = parser.createSingleAirline("324,All Nippon Airways,ANA All Nippon Airways,NH,ANA,ALL NIPPON,Japan,Y");
        Airline airline2 = parser.createSingleAirline("345,Air New Zealand,\\N,NZ,ANZ,NEW ZEALAND,New Zealand,Y");
        Airline airline3 = parser.createSingleAirline("346,Air NZ,\\N,NZ,ANZ,NZ,NZ,Y");
        airlinesExpected.add(airline1);
        airlinesExpected.add(airline2);
        airlinesExpected.add(airline3);
        boolean isEqual = true;
        for(int i = 0; i < 3; i++){
            Airline a = airlinesExpected.get(i);
            Airline b = airlinesActual.get(i);
            if (!a.isEqualTo(b)){
                isEqual = false;
                System.out.println(i);
            }
        }
        assertTrue(isEqual);
    }

    @Test
    public void buildRoutesTest() throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("PerfectRoutesOnly.txt")));
        ObservableList<Route> routesActual = loader.buildRoutes();
        ArrayList<Route> routesExpected = new ArrayList<Route>();
        RouteParser parser = new RouteParser();
        Route route1 = parser.createSingleRoute("3B,411,AOR,2865,KUN,2991,Y,11,CR2");
        Route route2 = parser.createSingleRoute("2B,410,AER,2965,KZN,2990,Y,10,CR2");
        Route route3 = parser.createSingleRoute("9W,3000,TRVA,3153,BOMV,2997,,0,73H");
        routesExpected.add(route1);
        routesExpected.add(route2);
        routesExpected.add(route3);
        boolean isEqual = true;
        for(int i = 0; i < 3; i++){
            Route a = routesExpected.get(i);
            Route b = routesActual.get(i);
            if (!a.isEqualTo(b)){
                isEqual = false;
            }
        }
        assertTrue(isEqual);
    }

    /*Test to see if the test file is loaded properly*/
    @Test
    public void testGetFile() throws FileNotFoundException {
        FileLoader load = new FileLoader(new BufferedReader(new FileReader("test.txt")));
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        assertEquals(br, load.getFile(br));
    }

    /*Test to see if the test file is read and stored properly.*/
    @Test
    public void testReadData() throws FileNotFoundException {
        FileLoader load = new FileLoader(new BufferedReader(new FileReader("test.txt")));
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        load.getFile(br);
        ArrayList<ArrayList<String>> test = load.readData(br);
        assertEquals(auck, test.get(0));
    }
}
