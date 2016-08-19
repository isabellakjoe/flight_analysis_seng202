package seng202.group8.Model;

import java.util.ArrayList;

/**
 * Created by esa46 on 19/08/16.
 */
public class Flight {

    private Airport sourceAirport;
    private Airport destinationAirport;
    private ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();

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

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public void addWaypoint(Waypoint waypoint){
        waypoints.add(waypoint);
    }

}
