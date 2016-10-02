package seng202.group8.Controller.MapViewControllers;

import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Flight;
import seng202.group8.Model.Objects.Waypoint;

import java.util.Collections;
import java.util.Iterator;


/**
 * Created by swa158 on 2/10/16.
 */
public class ToJSONArray {

    public static ObservableList<Waypoint> waypoints = Flight.getWaypoints();

    public static String toJSONFlightPath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Iterator i = waypoints.iterator();
        while (i.hasNext()) {
            Waypoint way = (Waypoint) i.next();
            stringBuilder.append(String.format("{lat: %f, lng: %f}, ", way.getLatitude(), way.getLongitude()));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
        }
    }
