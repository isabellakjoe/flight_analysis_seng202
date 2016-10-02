package seng202.group8.Model.Deleters;

import javafx.collections.ObservableList;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Callum on 21/09/16.
 */
public class RouteDeleter {

    /**
     * A method to delete a single route from the database and the GUI
     *
     * @param route                 a route object
     * @param routeHashMap          a hashmap of routes
     * @param currentlyLoadedRoutes a observable list of routes
     */
    public void deleteSingleRoute(Route route, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes) {

        //Remove the route from the airports count of routes
        try {
            route.getSourceAirport().setNumRoutes(route.getSourceAirport().getNumRoutes() - 1);
        } catch (NullPointerException e) {
            //Do nothing here as airport no longer exists in the application
        }
        try {
            route.getDestinationAirport().setNumRoutes(route.getDestinationAirport().getNumRoutes() - 1);
        } catch (NullPointerException e) {
            //Do nothing here as airport no longer exists in the application
        }

        //Set up the variables
        DatabaseSaver dbs = new DatabaseSaver();
        Connection conn = Database.connect();

        //Create the arraylist so dbs knows what route to delete
        ArrayList<Integer> id = new ArrayList<Integer>();
        id.add(route.getRouteID());

        //Remove the route from the Observable GUI Array
        currentlyLoadedRoutes.remove(currentlyLoadedRoutes.lastIndexOf(route));

        //Remove the route from the Hashmap
        routeHashMap.remove(route.getRouteID());

        //Delete the route from the database
        dbs.deleteRoutes(conn, id);
        Database.disconnect(conn);

    }

    /**
     * A method to delete a list of routes from the database and the GUI
     *
     * @param routes
     * @param routeHashMap
     * @param currentlyLoadedRoutes
     */
    public void deleteListRoutes(ObservableList<Route> routes, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes) {

        //Set up the variables
        DatabaseSaver dbs = new DatabaseSaver();
        Connection conn = Database.connect();

        //ArrayList of route ids to delete from the database
        ArrayList<Integer> ids = new ArrayList<Integer>();

        if (routes.size() >= 1) {

            for (int i = 0; i < routes.size(); i++) {

                deleteSingleRoute(routes.get(i), routeHashMap, currentlyLoadedRoutes);

//                //Update the counts of the routes of each airport
//                routes.get(i).getSourceAirport().setNumRoutes(routes.get(i).getSourceAirport().getNumRoutes() - 1);
//                routes.get(i).getDestinationAirport().setNumRoutes(routes.get(i).getDestinationAirport().getNumRoutes() - 1);
//
//                ids.add(routes.get(i).getRouteID());
//                //Remove the route from the Observable Array
//                currentlyLoadedRoutes.remove(routes.get(i));
//                //Remove the route from the Hashmap
//                routeHashMap.remove(routes.get(i).getRouteID());

            }
        }

        dbs.deleteRoutes(conn, ids);
        Database.disconnect(conn);

    }

}
