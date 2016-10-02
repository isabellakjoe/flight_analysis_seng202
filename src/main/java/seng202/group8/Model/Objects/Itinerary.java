package seng202.group8.Model.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by esa46 on 2/10/16.
 */
public class Itinerary implements Serializable{

    private Route route;
    private ArrayList<Route> routes = new ArrayList<Route>();
    transient ObservableList<Route> routesForTable = FXCollections.observableArrayList();


    public void addToRoutes(Route route){
        routes.add(route);
    }

    public Route returnFirstRoute(){
        return routes.get(0);
    }

    public void setRoute(Route route){
        this.route = route;
    }

    public Route getRoute(){
        return this.route;
    }

}
