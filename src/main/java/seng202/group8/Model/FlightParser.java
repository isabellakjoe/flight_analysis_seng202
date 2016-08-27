package seng202.group8.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Erika on 21-Aug-16.
 */
public class FlightParser {


    private BufferedReader br;
    private Flight resultFlight = new Flight();

    public FlightParser(BufferedReader br){
        this.br = br;
    }

    public Flight parseFlightFile(){
        Scanner textScanner = new Scanner(br);

        // Process header
        String nextLine = textScanner.nextLine();
        Airport sourceAirport = processAirportDetails(nextLine);
        resultFlight.setSourceAirport(sourceAirport);

        //Process waypoints
        nextLine = textScanner.nextLine();
        ObservableList<Waypoint> waypoints = FXCollections.observableArrayList();
        boolean finalWaypointProcessed = false;
        while(!finalWaypointProcessed && textScanner.hasNextLine()){
            Waypoint waypoint = processWaypointDetails(nextLine);
            waypoints.add(waypoint);
            nextLine = textScanner.nextLine();
            if (nextLine.substring(0, 2).equals("APT")) {
                finalWaypointProcessed = true;
            }
        }
        resultFlight.setWaypoints(waypoints);

        //Process footer
        Airport destinationAirport = processAirportDetails(nextLine);
        resultFlight.setDestinationAirport(destinationAirport);

        return resultFlight;
    }

    /*
    Method to process header and footer lines containing airport details.
    Currently creates a new airport - later should be changed to search for existing airport in database
     */
    private Airport processAirportDetails(String nextLine){
        ArrayList<String> inputValues = splitByComma(nextLine);
        Airport resultAirport = new Airport();
        resultAirport.setName(inputValues.get(1));
        int altitude =  parseToInt(inputValues.get(2));
        resultAirport.setAltitude(altitude);
        double latitude = parseToDouble(inputValues.get(3));
        resultAirport.setLatitude(latitude);
        double longitude = parseToDouble(inputValues.get(4));
        resultAirport.setLongitude(longitude);
        return resultAirport;
    }

    private Waypoint processWaypointDetails(String nextLine){
        ArrayList<String> inputValues = splitByComma(nextLine);
        Waypoint resultWaypoint = new Waypoint();
        resultWaypoint.setType(inputValues.get(0));
        resultWaypoint.setName(inputValues.get(1));
        double altitude =  parseToDouble(inputValues.get(2));
        resultWaypoint.setAltitude(altitude);
        double latitude = parseToDouble(inputValues.get(3));
        resultWaypoint.setLatitude(latitude);
        double longitude = parseToDouble(inputValues.get(4));
        resultWaypoint.setLongitude(longitude);
        return resultWaypoint;
    }

    /* Method to separate an input string based off of comma positioning */
    private ArrayList<String> splitByComma(String inputRoute) {
        /* Returns a new ArrayList, containing all of the string elements */
        return new ArrayList<String>(Arrays.asList(inputRoute.split(",")));
    }

    /* Method to parse a string to a float with error handling */
    private double parseToDouble(String inputNum) {
        try {
            return Double.parseDouble(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* Method to parse a string to an integer with error handling */
    private int parseToInt(String inputNum) {
        try {
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
