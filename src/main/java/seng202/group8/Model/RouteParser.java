package seng202.group8.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Callum on 21/08/16.
 */
public class RouteParser {

    /* Method to separate an input string based off of comma positioning */
    private ArrayList<String> splitByComma(String inputRoute) {
        /* Returns a new ArrayList, containing all of the string elements */
        return new ArrayList<String>(Arrays.asList(inputRoute.split(",")));
    }

    /* Method to parse a string to an integer with error handling */
    private int parseToInt(String inputNum) {
        try {
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* Temporary method to create an airline for a route */
    private Airline createAirline(String airlineName, int airlineID) {
        Airline airline = new Airline();
        airline.setName(airlineName);
        airline.setAirlineID(airlineID);
        return airline;
    }

    /* Temporary method to create an airport for a route */
    private Airport createAirport(String airportCode, int airportID) {
        Airport airport = new Airport();

        if (airportCode.length() == 3) {
            airport.setIATA(airportCode);
        } else if (airportCode.length() == 4) {
            airport.setICAO(airportCode);
        }

        airport.setAirportID(airportID);
        return airport;
    }

    /* Method to check whether an route is codeshared */
    private void checkCodeshared(Route route, String codeshare) {
        if (codeshare.equals("Y")) {
            route.setCodeshare(true);
        } else {
            route.setCodeshare(false);
        }
    }

    private Route createParsedRoute(ArrayList<String> routeInfo) {
        /* Create the route object */
        Route route = new Route();

        /** Creates the airline object, later this needs to check if airline
         * already loaded into the system
         */
        Airline airline = createAirline(routeInfo.get(0), Integer.parseInt(routeInfo.get(1)));
        route.setAirline(airline);

        /** Creates the airport objects for the route, later this also needs to check
         * whether 'new' airports are already in the system
         */
        Airport sourceAirport = createAirport(routeInfo.get(2), parseToInt(routeInfo.get(3)));
        route.setSourceAirport(sourceAirport);

        Airport destinationAirport = createAirport(routeInfo.get(4), parseToInt(routeInfo.get(5)));
        route.setDestinationAirport(destinationAirport);

        /* Sets whether the codeshare value is Y */
        checkCodeshared(route, routeInfo.get(6));

        /* Sets the number of stops the route hase */
        route.setStops(parseToInt(routeInfo.get(7)));

        /* Sets what plane the route uses */
        route.setEquipment(routeInfo.get(8));

        return route;
    }

    /* Public method which is used to create a single route */
    public Route createSingleRoute(String input) {
        ArrayList<String> routeInfo = splitByComma(input);
        return createParsedRoute(routeInfo);
    }

}
