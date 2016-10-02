package seng202.group8.Model.Deleters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Controller.MainController;
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

    /**
     * A method to delete a singular airline from the database and controller correctly updating all values
     * @param airline a airline object
     * @param routeHashMap a hashmap of routes
     * @param currentlyLoadedRoutes a observable list of currently loaded routes
     * @param airlineHashMap a hashmap of airlines
     * @param currentlyLoadedAirlines an observable list of currently loaded airlines
     */
    public void deleteSingleAirline(Airline airline, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes, HashMap<String, Airline> airlineHashMap, ObservableList<Airline> currentlyLoadedAirlines) {

        DatabaseSearcher dbs = new DatabaseSearcher();
        DatabaseSaver dbSave = new DatabaseSaver();
        Connection connSearch = Database.connect();
        Connection connDelete = Database.connect();

        //Get all routes associated with the airline
        String sql = dbs.buildRouteSearch("airlineid", Integer.toString(airline.getAirlineID()));
        ObservableList<Route> routeFromDatabase = dbs.searchRouteByOption(connSearch, sql);
        Database.disconnect(connSearch);
        ObservableList<Route> routesToDelete = FXCollections.observableArrayList();

        for (int i = 0; i < routeFromDatabase.size(); i++) {
            routesToDelete.add(MainController.getRouteHashMap().get(routeFromDatabase.get(i).getRouteID()));
        }

        //Delete the associated routes
        RouteDeleter rd = new RouteDeleter();
        rd.deleteListRoutes(routesToDelete, routeHashMap, currentlyLoadedRoutes);

        System.out.println(MainController.getCurrentlyLoadedRoutes().size());

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
        Database.disconnect(connDelete);
    }

}
