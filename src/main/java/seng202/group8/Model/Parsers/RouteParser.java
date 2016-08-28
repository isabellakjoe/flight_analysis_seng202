package seng202.group8.Model.Parsers;

import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Objects.RouteMethod;

import java.util.ArrayList;

/**
 * Created by Callum on 21/08/16.
 */
public class RouteParser extends RouteMethod {

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

        route.setAirlineName(routeInfo.get(0));

        route.setSourceAirportName(routeInfo.get(2));

        route.setDestinationAirportName(routeInfo.get(4));


        return route;
    }

    /* Public method which is used to create a single route */
    public Route createSingleRoute(String input) {
        ArrayList<String> routeInfo = splitByComma(input);
        return createParsedRoute(routeInfo);
    }

}
