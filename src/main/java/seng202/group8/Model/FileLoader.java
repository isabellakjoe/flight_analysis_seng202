package seng202.group8.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by asc132 on 22/08/2016.
 */
public class FileLoader {

    private BufferedReader reader;
    private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

    public FileLoader(BufferedReader br){
        this.reader = br;
    }

    /*Reads the buffered reader to a local buffered reader.*/
    public BufferedReader getFile(BufferedReader br){
        this.reader = br;
        return br;
    }

    public ObservableList<Airport> buildAirports(){
        Scanner textScanner = new Scanner(reader);
        ObservableList<Airport> airports = FXCollections.observableArrayList();
        AirportParser parser = new AirportParser();

        String currentString;
        while(textScanner.hasNextLine()){
            currentString = textScanner.nextLine();
            Airport airport = parser.createAirport(currentString);
            airports.add(airport);
        }
        return airports;
    }

    public ObservableList<Airline> buildAirlines(){
        Scanner textScanner = new Scanner(reader);
        ObservableList<Airline> airlines = FXCollections.observableArrayList();
        AirlineParser parser = new AirlineParser();

        String currentString;
        while(textScanner.hasNextLine()){
            currentString = textScanner.nextLine();
            Airline airline = parser.createSingleAirline(currentString);
            airlines.add(airline);
        }
        return airlines;
    }

    public ObservableList<Route> buildRoutes(){
        Scanner textScanner = new Scanner(reader);
        ObservableList<Route> routes = FXCollections.observableArrayList();
        RouteParser parser = new RouteParser();
        String currentString;
        while(textScanner.hasNextLine()){
            currentString = textScanner.nextLine();
            Route route = parser.createSingleRoute(currentString);
            routes.add(route);
        }
        return routes;
    }

    public Flight buildFlight(){
        FlightParser parser = new FlightParser(reader);
        Flight flight = parser.parseFlightFile();
        return flight;
    }



/*Reads a file from a buffered reader and outputs each line as an arraylist of properties.*/
    public ArrayList<ArrayList<String>> readData(BufferedReader br){
        String line;
        try {
            do {
                line = br.readLine();
                if (line != null) {
                    ArrayList<String> list = new ArrayList<String>();
                    for(int i = 0; i <= (line.split(",").length - 1); i++) {
                        list.add((line.split(",")[i].replace("\"", "")));
                    }
                    data.add(list);
                }
            } while(line != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
