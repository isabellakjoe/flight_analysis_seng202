package seng202.group8.Model.Deleters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Callum on 21/09/16.
 */
public class AirportDeleter {

    /**
     * A method to delete a singular airport from the database and controller correctly updating all values
     *
     * @param airport                 a airport object
     * @param routeHashMap            a hashmap of routes
     * @param currentlyLoadedRoutes   a observable list of currently loaded routes
     * @param airportHashMap          a hashmap of airports
     * @param currentlyLoadedAirports a observable list of currently loaded airports
     */
    public void deleteSingleAirport(Airport airport, HashMap<Integer, Route> routeHashMap, ObservableList<Route> currentlyLoadedRoutes, HashMap<String, Airport> airportHashMap, ObservableList<Airport> currentlyLoadedAirports) {

        //Set up the database and objects
        DatabaseSearcher dbs = new DatabaseSearcher();
        DatabaseSaver dbSave = new DatabaseSaver();
        Connection connSearch = Database.connect();
        Connection connDelete = Database.connect();

        //Get all routes associated with this airport
        String sql = dbs.buildRouteSearch("sourceid", Integer.toString(airport.getAirportID()));
        sql = dbs.addAdditionalLikeOption(sql, "route", "destinationid", Integer.toString(airport.getAirportID()));
        ObservableList<Route> routesFromDatabase = dbs.searchRouteByOption(connSearch, sql);
        Database.disconnect(connSearch);
        ObservableList<Route> routesToDelete = FXCollections.observableArrayList();

        for (int i = 0; i < routesFromDatabase.size(); i++) {
            routesToDelete.add(MainController.getRouteHashMap().get(routesFromDatabase.get(i).getRouteID()));
        }

        //Delete the routes from the app
        RouteDeleter rd = new RouteDeleter();
        rd.deleteListRoutes(routesToDelete, routeHashMap, currentlyLoadedRoutes);

        //Get id of airport so database knows which one to delete
        ArrayList<Integer> id = new ArrayList<Integer>();
        id.add(airport.getAirportID());

        //Remove the airport from the current observable list of integers
        currentlyLoadedAirports.remove(currentlyLoadedAirports.indexOf(airport));

        //Remove the airport from the current hashmap
        if (airport.getCountry().equals("United States")) {
            airportHashMap.remove(airport.getFAA());
        } else if (airport.getIATA() != null) {
            airportHashMap.remove(airport.getIATA());
        } else {
            airportHashMap.remove(airport.getICAO());
        }

        //Remove the airport from the database
        dbSave.deleteAirport(connDelete, id);
        Database.disconnect(connDelete);

    }

}
