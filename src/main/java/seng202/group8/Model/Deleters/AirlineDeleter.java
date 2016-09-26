package seng202.group8.Model.Deleters;

import javafx.collections.ObservableList;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Callum on 21/09/16.
 */
public class AirlineDeleter {


    //Step 1 -> Delete all associated routes
    //Step 2 -> Delete from Observable Airline List
    //Step 3 -> Delete from Airline Hashmap
    //Step 4 -> Delete from Database
    //Step 5 -> Return Observable Airline List

    public void deleteSingleAirline(Airline airline, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes, HashMap<String, Airline> airlineHashMap, ObservableList<Airline> currentlyLoadedAirlines) {

        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        DatabaseSaver dbSave = new DatabaseSaver();
        Connection connSearch = db.connect();
        Connection connDelete = db.connect();

        //Get all routes associated with the airline
        String sql = dbs.buildRouteSearch("airlineid", Integer.toString(airline.getAirlineID()));
        ObservableList<Route> routesToDelete = dbs.searchRouteByOption(connSearch, sql);
        db.disconnect(connSearch);

        //Delete the associated routes
        RouteDeleter rd = new RouteDeleter();
        rd.deleteListRoutes(routesToDelete, routeHashMap, currentlyLoadedRoutes);

        //Get id so database knows which one to delete
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(airline.getAirlineID());

        //Remove the airline from the observable list
        currentlyLoadedAirlines.remove(currentlyLoadedAirlines.indexOf(airline));

        //Remove the airline from the hashmap
        if (airline.getIATA() != null) {
            airlineHashMap.remove(airline.getIATA());
        } else {
            airlineHashMap.remove(airline.getICAO());
        }

        //Delete the airline from the database
        dbSave.deleteAirlines(connDelete, ids);
        db.disconnect(connDelete);
    }

}
