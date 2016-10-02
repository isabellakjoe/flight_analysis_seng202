package seng202.group8.Model.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by esa46 on 2/10/16.
 */
public class Itinerary implements Serializable {

    private Route route;
    private ArrayList<Route> routes = new ArrayList<Route>();

    public void addToRoutes(Route route) {
        routes.add(route);
    }

    public ObservableList<Route> getObservableRoutes() {
        ObservableList<Route> routes = FXCollections.observableArrayList();
        for (int i = 0; i < this.routes.size(); i++) {
            routes.add(this.routes.get(i));
        }
        return routes;
    }

    public void removeFromRoutes(int index) {
        routes.remove(index);
    }

}
