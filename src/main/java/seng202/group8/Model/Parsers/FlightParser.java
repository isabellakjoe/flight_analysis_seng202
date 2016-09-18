package seng202.group8.Model.Parsers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Flight;
import seng202.group8.Model.Objects.Waypoint;

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

    /**
     * Constructor for Flight Parser.
     *
     * @param br: A BufferedReader of a Flight File.
     */
    public FlightParser(BufferedReader br) {
        this.br = br;
    }

    /**
     * Method to Create a Flight Object.
     *
     * @return A FLight Object.
     */
    public Flight parseFlightFile() {
        Scanner textScanner = new Scanner(br);

        // Process header
        String nextLine = textScanner.nextLine();
        Airport sourceAirport = processAirportDetails(nextLine);
        resultFlight.setSourceAirport(sourceAirport);

        //Process waypoints
        nextLine = textScanner.nextLine();
        ObservableList<Waypoint> waypoints = FXCollections.observableArrayList();
        boolean finalWaypointProcessed = false;
        while (!finalWaypointProcessed && textScanner.hasNextLine()) {
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

    /**
     * Method to process header and footer lines containing Airport data.
     *
     * @param nextLine: The next line to be processed.
     * @return An Airport Object.
     */
    private Airport processAirportDetails(String nextLine) {
        ArrayList<String> inputValues = splitByComma(nextLine);
        Airport resultAirport = new Airport();
        resultAirport.setName(inputValues.get(1));
        int altitude = parseToInt(inputValues.get(2));
        resultAirport.setAltitude(altitude);
        double latitude = parseToDouble(inputValues.get(3));
        resultAirport.setLatitude(latitude);
        double longitude = parseToDouble(inputValues.get(4));
        resultAirport.setLongitude(longitude);
        return resultAirport;
    }

    /**
     * Method to process header and footer lines containing Waypoint data.
     *
     * @param nextLine: The next line to be processed.
     * @return An Waypoint Object.
     */
    private Waypoint processWaypointDetails(String nextLine) {
        ArrayList<String> inputValues = splitByComma(nextLine);
        Waypoint resultWaypoint = new Waypoint();
        resultWaypoint.setType(inputValues.get(0));
        resultWaypoint.setName(inputValues.get(1));
        double altitude = parseToDouble(inputValues.get(2));
        resultWaypoint.setAltitude(altitude);
        double latitude = parseToDouble(inputValues.get(3));
        resultWaypoint.setLatitude(latitude);
        double longitude = parseToDouble(inputValues.get(4));
        resultWaypoint.setLongitude(longitude);
        return resultWaypoint;
    }

    /** Method to split a string of Route Data at each comma.
     *
     * @param inputRoute: A string of Route Data.
     * @return An ArrayList of Route Data.
     */
    private ArrayList<String> splitByComma(String inputRoute) {
        /* Returns a new ArrayList, containing all of the string elements */
        return new ArrayList<String>(Arrays.asList(inputRoute.split(",")));
    }

    /** Method to convert a String of numbers to a Float.
     *
     * @param inputNum: A number in String form.
     * @return The number as a Float or -1 if the string is not a number.
     */
    private double parseToDouble(String inputNum) {
        try {
            return Double.parseDouble(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Method to convert a String of numbers to a Integer.
     *
     * @param inputNum: A number in String form.
     * @return The number as an Integer or -1 if the string is not a number.
     */
    private int parseToInt(String inputNum) {
        try {
            return Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
