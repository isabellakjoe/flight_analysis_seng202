package seng202.group8.Model.Parsers;

import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Objects.RouteMethod;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Callum on 21/08/16.
 */
public class RouteParser extends RouteMethod {

    private Route createParsedRoute(ArrayList<String> routeInfo, int routeID, HashMap<String, Airline> airlineHashMap, HashMap<String, Airport> airportHashMap) {
        /* Create the route object */
        Route route = new Route();


        if (airlineHashMap.get(routeInfo.get(0)) != null) {
            route.setAirline(airlineHashMap.get(routeInfo.get(0)));
            route.setAirlineName(airlineHashMap.get(routeInfo.get(0)).getName());
        }

        if (airportHashMap.get(routeInfo.get(2)) != null) {
            route.setSourceAirport(airportHashMap.get(routeInfo.get(2)));
            route.setSourceAirportName(airportHashMap.get(routeInfo.get(2)).getName());
        }

        if (airportHashMap.get(routeInfo.get(4)) != null) {
            route.setDestinationAirport(airportHashMap.get(routeInfo.get(4)));
            route.setDestinationAirportName(airportHashMap.get(routeInfo.get(4)).getName());
        }

        /* Sets whether the codeshare value is Y */
        checkCodeshared(route, routeInfo.get(6));

        /* Sets the number of stops the route hase */
        route.setStops(parseToInt(routeInfo.get(7)));

        /* Sets what plane the route uses */
        route.setEquipment(routeInfo.get(8));

        route.setRouteID(routeID);

        return route;
    }

    /* Public method which is used to ToJSONArray a single route */

    /**
     * Method to ToJSONArray a Route Object.
     *
     * @param input: String of Route data.
     * @return A Route Object or null if the input is null.
     */

    public Route createSingleRoute(String input, int routeID, HashMap<String, Airline> airlineHashMap, HashMap<String, Airport> airportHashMap) {
        ArrayList<String> routeInfo = refactorData(input);
        if (routeInfo != null) {
            return createParsedRoute(routeInfo, routeID, airlineHashMap, airportHashMap);
        } else {
            return null;
        }
    }


}
