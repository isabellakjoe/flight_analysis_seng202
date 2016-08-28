package seng202.group8.Model.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by esa46 on 19/08/16.
 */
public class Flight {

    private Airport sourceAirport;
    private Airport destinationAirport;
    private ObservableList<Waypoint> waypoints = FXCollections.observableArrayList();

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

    public ObservableList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ObservableList<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public void addWaypoint(Waypoint waypoint){
        waypoints.add(waypoint);
    }

}
