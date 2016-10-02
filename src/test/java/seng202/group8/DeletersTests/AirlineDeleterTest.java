package seng202.group8.DeletersTests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.Deleters.AirlineDeleter;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Route;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by Callum on 22/09/16.
 */
public class AirlineDeleterTest {

    Airline airline;
    AirlineDeleter ad;
    static HashMap<Integer, Route> routeHashMap;
    static ObservableList<Route> currentlyLoadedRoutes;
    static HashMap<String, Airline> airlineHashMap;
    static ObservableList<Airline> currentlyLoadedAirlines;

    @Before
    public void initialise() {

        ad = new AirlineDeleter();
        airline = new Airline();
        airline.setAirlineID(10000);
        airline.setIATA("OXS");
        routeHashMap = new HashMap<Integer, Route>();
        currentlyLoadedRoutes = FXCollections.observableArrayList();
        airlineHashMap = new HashMap<String, Airline>();
        currentlyLoadedAirlines = FXCollections.observableArrayList();

        currentlyLoadedAirlines.add(airline);
        airlineHashMap.put(airline.getIATA(), airline);
    }

    @Test
    public void testAirlineRemovesFromLoadedAirlines() {

        ad.deleteSingleAirline(airline, routeHashMap, currentlyLoadedRoutes, airlineHashMap, currentlyLoadedAirlines);
        assertTrue(currentlyLoadedAirlines.size() == 0);

    }

    @Test
    public void testAirlineRemovesFromHashMap() {

        ad.deleteSingleAirline(airline, routeHashMap, currentlyLoadedRoutes, airlineHashMap, currentlyLoadedAirlines);
        assertTrue(airlineHashMap.get("OXS") == null);

    }

}
