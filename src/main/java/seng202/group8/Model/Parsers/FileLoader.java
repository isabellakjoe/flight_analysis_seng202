package seng202.group8.Model.Parsers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Flight;
import seng202.group8.Model.Objects.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by asc132 on 22/08/2016.
 */
public class FileLoader {

    private BufferedReader reader;
    private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

    /**
     * Constructor for the FileLoader class
     *
     * @param br: A BufferedReader
     */
    public FileLoader(BufferedReader br) {
        this.reader = br;
    }

    /**
     * A method to create an ObservableList of Airports.
     *
     * @return The created Airport ObservableList.
     */
    public ObservableList<Airport> buildAirports() {
        Scanner textScanner = new Scanner(reader);
        ObservableList<Airport> airports = FXCollections.observableArrayList();
        AirportParser parser = new AirportParser();

        String currentString;
        while (textScanner.hasNextLine()) {
            currentString = textScanner.nextLine();
            Airport airport = parser.createSingleAirport(currentString);
            if (airport != null) {
                airports.add(airport);
            }
        }
        return airports;
    }

    /**
     * A method to create an ObservableList of Airlines.
     *
     * @return The created Airline ObservableList.
     */
    public ObservableList<Airline> buildAirlines() {
        Scanner textScanner = new Scanner(reader);
        ObservableList<Airline> airlines = FXCollections.observableArrayList();
        AirlineParser parser = new AirlineParser();

        String currentString;
        while (textScanner.hasNextLine()) {
            currentString = textScanner.nextLine();
            Airline airline = parser.createSingleAirline(currentString);
            if (airline != null) {
                airlines.add(airline);
            }
        }
        return airlines;
    }

    /**
     * A method to create an ObservableList of Routes.
     *
     * @return The created Routes ObservableList.
     */
    public ObservableList<Route> buildRoutes() {
        Scanner textScanner = new Scanner(reader);
        ObservableList<Route> routes = FXCollections.observableArrayList();
        RouteParser parser = new RouteParser();
        String currentString;
        Database db = new Database();
        Connection conn = db.connect();
        DatabaseSaver dbSave = new DatabaseSaver();
        int routeID = dbSave.getCurrentMaxRouteID(conn);
        db.disconnect(conn);
        while (textScanner.hasNextLine()) {
            currentString = textScanner.nextLine();
            Route route = parser.createSingleRoute(currentString, routeID);
            routeID += 1;
            if (route != null) {
                routes.add(route);
            }
        }
        return routes;
    }

    /**
     * Method to create a Flight object.
     *
     * @return The Flight Object.
     */
    public Flight buildFlight() {
        FlightParser parser = new FlightParser(reader);
        Flight flight = parser.parseFlightFile();
        return flight;
    }



/*Reads a file from a buffered reader and outputs each line as an arraylist of properties.*/

    /**
     * Method that reads a a file from a BufferedReader
     *
     * @param br: The BufferedReader of a File.
     * @return An ArrayList of ArrayLists of Strings.
     */
    public ArrayList<ArrayList<String>> readData(BufferedReader br) {
        String line;
        try {
            do {
                line = br.readLine();
                if (line != null) {
                    ArrayList<String> list = new ArrayList<String>();
                    for (int i = 0; i <= (line.split(",").length - 1); i++) {
                        list.add((line.split(",")[i].replace("\"", "")));
                    }
                    data.add(list);
                }
            } while (line != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
