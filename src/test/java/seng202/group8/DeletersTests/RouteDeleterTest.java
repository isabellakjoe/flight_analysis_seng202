package seng202.group8.DeletersTests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.Model.Deleters.RouteDeleter;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Callum on 22/09/16.
 */
public class RouteDeleterTest {

    Route route;
    RouteDeleter rd;

    Airport src;
    Airport dest;

    static HashMap<Integer, Route> routeHashMap;
    static ObservableList<Route> currentlyLoadedRoutes;

    @Before
    public void initialise() {

        route = new Route();
        route.setRouteID(990);

        src = new Airport();
        dest = new Airport();

        src.setNumRoutes(1);
        dest.setNumRoutes(14);

        route.setSourceAirport(src);
        route.setDestinationAirport(dest);

        rd = new RouteDeleter();

        currentlyLoadedRoutes = FXCollections.observableArrayList();
        currentlyLoadedRoutes.add(route);
        routeHashMap = new HashMap<Integer, Route>();
        routeHashMap.put(route.getRouteID(), route);
    }

    @Test
    public void testRouteDeletesFromArrayList() {

        rd.deleteSingleRoute(route, routeHashMap, currentlyLoadedRoutes);
        assertTrue(currentlyLoadedRoutes.size() == 0);

    }

    @Test
    public void testRouteDeletesFromHashmap() {

        rd.deleteSingleRoute(route, routeHashMap, currentlyLoadedRoutes);
        assertTrue(routeHashMap.get(990) == null);

    }

    @Test
    public void testSrcRemovesRouteCount() {

        rd.deleteSingleRoute(route, routeHashMap, currentlyLoadedRoutes);
        assertTrue(src.getNumRoutes() == 0);
    }

    @Test
    public void testDestRemovesRouteCount() {

        rd.deleteSingleRoute(route, routeHashMap, currentlyLoadedRoutes);
        assertTrue(dest.getNumRoutes() == 13);

    }




}
