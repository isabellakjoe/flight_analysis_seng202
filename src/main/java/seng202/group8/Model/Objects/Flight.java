package seng202.group8.Model.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by esa46 on 19/08/16.
 * <p>
 * Flight Object class.
 */
public class Flight {

    private static ObservableList<Waypoint> waypoints = FXCollections.observableArrayList();
    private Airport sourceAirport;
    private Airport destinationAirport;

    public static ObservableList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ObservableList<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public Airport getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(Airport sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

}
