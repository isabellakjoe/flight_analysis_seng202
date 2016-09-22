package seng202.group8.DeletersTests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.Deleters.AirportDeleter;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by Callum on 22/09/16.
 */
public class AirportDeleterTest {

    Airport airport;
    AirportDeleter ad;
    static HashMap<Integer, Route> routeHashMap;
    static ObservableList<Route> currentlyLoadedRoutes;
    static HashMap<String, Airport> airportHashMap;
    static ObservableList<Airport> currentlyLoadedAirports;

    @Before
    public void initialise() {

        airport = new Airport();
        airport.setAirportID(100000);
        airport.setIATA("ABC");
        airport.setCountry("NZ");
        ad = new AirportDeleter();
        routeHashMap = new HashMap<Integer, Route>();
        currentlyLoadedRoutes = FXCollections.observableArrayList();
        airportHashMap = new HashMap<String, Airport>();
        currentlyLoadedAirports = FXCollections.observableArrayList();

        currentlyLoadedAirports.add(airport);
        airportHashMap.put(airport.getIATA(), airport);

    }

    @Test
    public void testAirportRemovesFromArray() {

        ad.deleteSingleAirport(airport, routeHashMap, currentlyLoadedRoutes, airportHashMap, currentlyLoadedAirports);
        assertTrue(currentlyLoadedAirports.size() == 0);

    }

    @Test
    public void testAirportRemovesFromHashMap() {

        ad.deleteSingleAirport(airport, routeHashMap, currentlyLoadedRoutes, airportHashMap, currentlyLoadedAirports);
        assertTrue(airportHashMap.get("ABC") == null);

    }


}
