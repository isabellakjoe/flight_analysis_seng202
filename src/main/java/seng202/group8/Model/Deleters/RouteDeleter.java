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

    public void deleteSingleRoute(Route route, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes) {

        //Remove the route from the airports count of routes
        route.getSourceAirport().setNumRoutes(route.getSourceAirport().getNumRoutes() - 1);
        route.getDestinationAirport().setNumRoutes(route.getDestinationAirport().getNumRoutes() - 1);

        //Set up the variables
        DatabaseSaver dbs = new DatabaseSaver();
        Connection conn = Database.connect();

        //Create the arraylist so dbs knows what route to delete
        ArrayList<Integer> id = new ArrayList<Integer>();
        id.add(route.getRouteID());

        //Remove the route from the Observable GUI Array
        currentlyLoadedRoutes.remove(currentlyLoadedRoutes.indexOf(route));

        //Remove the route from the Hashmap
        routeHashMap.remove(route.getRouteID());

        //Delete the route from the database
        dbs.deleteRoutes(conn, id);
        Database.disconnect(conn);

    }

    public void deleteListRoutes(ObservableList<Route> routes, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes) {

        //Set up the variables
        DatabaseSaver dbs = new DatabaseSaver();
        Connection conn = Database.connect();

        //ArrayList of route ids to delete from the database
        ArrayList<Integer> ids = new ArrayList<Integer>();

        System.out.println(routes.size());

        if (routes.size() >= 1) {
            for (int i = 0; i < routes.size(); i++) {

                ids.add(routes.get(i).getRouteID());
                //Remove the route from the Observable Array
                currentlyLoadedRoutes.remove(routes.get(i));
                //Remove the route from the Hashmap
                routeHashMap.remove(routes.get(i).getRouteID());

            }
        }

        dbs.deleteRoutes(conn, ids);
        Database.disconnect(conn);

    }

}
