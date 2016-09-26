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

    //Step One -> Remove from Observable ArrayList
    //Step Two -> Remove from Hashmap
    //Step Three -> Remove from Database
    //Step Four -> Return new Observable ArrayList to update GUI

    public void deleteSingleRoute(Route route, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes) {

        //Remove the route from the airports count of routes
        route.getSourceAirport().setNumRoutes(route.getSourceAirport().getNumRoutes() - 1);
        route.getDestinationAirport().setNumRoutes(route.getDestinationAirport().getNumRoutes() - 1);

        //Set up the variables
        Database db = new Database();
        DatabaseSaver dbs = new DatabaseSaver();
        Connection conn = db.connect();

        //Create the arraylist so dbs knows what route to delete
        ArrayList<Integer> id = new ArrayList<Integer>();
        id.add(route.getRouteID());

        //Remove the route from the Observable GUI Array
        currentlyLoadedRoutes.remove(currentlyLoadedRoutes.indexOf(route));

        //Remove the route from the Hashmap
        routeHashMap.remove(route.getRouteID());

        //Delete the route from the database
        dbs.deleteRoutes(conn, id);
        db.disconnect(conn);

    }

    public void deleteListRoutes(ObservableList<Route> routes, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes) {

        //Set up the variables
        Database db = new Database();
        DatabaseSaver dbs = new DatabaseSaver();
        Connection conn = db.connect();

        //ArrayList of route ids to delete from the database
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (int i = 0; i < routes.size(); i++) {

            ids.add(routes.get(i).getRouteID());
            //Remove the route from the Observable Array
            currentlyLoadedRoutes.remove(currentlyLoadedRoutes.indexOf(routes.get(i)));
            //Remove the route from the Hashmap
            routeHashMap.remove(routes.get(i).getRouteID());

        }

        dbs.deleteRoutes(conn, ids);
        db.disconnect(conn);

    }

}
