package seng202.group8.Model.Parsers;

import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Objects.RouteMethod;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Callum on 21/08/16.
 */
public class RouteParser extends RouteMethod {

    private Route createParsedRoute(ArrayList<String> routeInfo, int routeID) {
        /* Create the route object */
        Route route = new Route();


        /** Creates the airline object, later this needs to check if airline
         * already loaded into the system
         */
        /* check if airlineID is null, if is sets ID to -1 */
        if (routeInfo.get(1) == "") {
            Airline airline = createAirline(routeInfo.get(0), -1);
            route.setAirline(airline);
        } else {
            Airline airline = createAirline(routeInfo.get(0), Integer.parseInt(routeInfo.get(1)));
            route.setAirline(airline);
        }

        /** Creates the airport objects for the route, later this also needs to check
         * whether 'new' airports are already in the system
         * Checks if airport is null
         */
        /* check if source airportID is null, if is sets ID to -1 */
        if (routeInfo.get(3) == "") {
            Airport sourceAirport = createAirport(routeInfo.get(2), -1);
            route.setSourceAirport(sourceAirport);
        } else {
            Airport sourceAirport = createAirport(routeInfo.get(2), parseToInt(routeInfo.get(3)));
            route.setSourceAirport(sourceAirport);
        }
        /* check if destination airportID is null, if is sets ID to -1 */
        if (routeInfo.get(5) == "") {
            Airport destinationAirport = createAirport(routeInfo.get(4), -1);
            route.setDestinationAirport(destinationAirport);
        } else {
            Airport destinationAirport = createAirport(routeInfo.get(4), parseToInt(routeInfo.get(5)));
            route.setDestinationAirport(destinationAirport);
        }

        /* Sets whether the codeshare value is Y */
        checkCodeshared(route, routeInfo.get(6));

        /* Sets the number of stops the route hase */
        route.setStops(parseToInt(routeInfo.get(7)));

        /* Sets what plane the route uses */
        route.setEquipment(routeInfo.get(8));

        route.setRouteID(routeID);

        route.setAirlineName(routeInfo.get(0));

        route.setSourceAirportName(routeInfo.get(2));

        route.setDestinationAirportName(routeInfo.get(4));


        return route;
    }

    /* Public method which is used to create a single route */

    /** Method to create a Route Object.
     *
     * @param input: String of Route data.
     * @return A Route Object or null if the input is null.
     */

    public Route createSingleRoute(String input, int routeID) {
        ArrayList<String> routeInfo = refactorData(input);
        if (routeInfo != null) {
            return createParsedRoute(routeInfo, routeID);
        } else {
            return null;
        }
    }


}
