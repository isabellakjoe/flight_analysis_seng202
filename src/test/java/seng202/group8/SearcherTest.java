package seng202.group8;

/**
 * Created by esa46 on 30/08/16.
 */


import javafx.collections.ObservableList;
import org.junit.Test;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Parsers.FileLoader;
import seng202.group8.Model.Searchers.AirlineSearcher;
import seng202.group8.Model.Searchers.AirportSearcher;
import seng202.group8.Model.Searchers.RouteSearcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearcherTest {

    @Test
    public void airlineSearchIDTest() throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("SearchTestAirlines.txt")));
        ObservableList<Airline> airlines = loader.buildAirlines();
        AirlineSearcher searcher = new AirlineSearcher(airlines);
        searcher.airlinesOfID("7");
        Airline returnedAirline = searcher.getLoadedAirlines().get(0);
        Airline expectedAirline = airlines.get(0);
        assertEquals(returnedAirline, expectedAirline);
    }

    @Test
    public void airlineSearchEverythingButIDTest()throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("SearchTestAirlines.txt")));
        ObservableList<Airline> airlines = loader.buildAirlines();
        AirlineSearcher searcher = new AirlineSearcher(airlines);
        searcher.airlinesOfName("Astro Air International");
        searcher.airlinesOfAlias("AAI");
        searcher.airlinesOfIATA("ABC");
        searcher.airlinesOfICAO("AAV");
        searcher.airlinesOfCallsign("ASTRO-PHIL");
        searcher.airlinesOfCountry("United States");
        searcher.airlinesOfActiveStatus("Active");
        Airline returnedAirline = searcher.getLoadedAirlines().get(0);
        Airline expectedAirline = airlines.get(0);
        assertEquals(returnedAirline, expectedAirline);
    }

    @Test
    public void airportSearchIDTest() throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("SearchTestAirports.txt")));
        ObservableList<Airport> airports = loader.buildAirports();
        AirportSearcher searcher = new AirportSearcher(airports);
        searcher.airportsOfID("6");
        Airport returnedAirport = searcher.getLoadedAirports().get(0);
        Airport expectedAirport = airports.get(0);
        assertEquals(returnedAirport, expectedAirport);
    }

    @Test
    public void airportSearchEverythingButIDTest()throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("SearchTestAirports.txt")));
        ObservableList<Airport> airports = loader.buildAirports();
        AirportSearcher searcher = new AirportSearcher(airports);
        searcher.airportsOfName("Wewak Intl");
        searcher.airportsOfCity("Wewak");
        searcher.airportsOfCountry("Papua New Guinea");
        searcher.airportsOfIATA("WWK");
        searcher.airportsOfLatitude("-3.58");
        searcher.airportsOfLongitude("143.66");
        searcher.airportsOfAltitude("19");
        searcher.airportsOfTimezone("10");
        searcher.airportsOfDST("U");
        Airport returnedAirport = searcher.getLoadedAirports().get(0);
        Airport expectedAirport = airports.get(0);
        assertEquals(returnedAirport, expectedAirport);
    }


    @Test
    public void routeSearchTest()throws FileNotFoundException {
        FileLoader loader = new FileLoader(new BufferedReader(new FileReader("SearchTestRoutes.txt")));
        ObservableList<Route> routes = loader.buildRoutes();
        RouteSearcher searcher = new RouteSearcher(routes);
        searcher.routesOfSource("AOR");
        searcher.routesOfDestination("KUN");
        searcher.routesOfCodeshare("Codeshare");
        searcher.routesOfStops(11);
        Route returnedRoute = searcher.getLoadedRoutes().get(0);
        Route expectedRoute = routes.get(0);
        assertEquals(returnedRoute, expectedRoute);
    }




}
