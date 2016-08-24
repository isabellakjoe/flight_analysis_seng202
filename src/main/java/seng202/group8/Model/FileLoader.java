package seng202.group8.Model;

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

    public BufferedReader reader;
    public ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

    public FileLoader(BufferedReader br){
        this.reader = br;
    }

    public BufferedReader getFile(BufferedReader br){
        this.reader = br;
        return br;
    }

    public Set<Airport> buildAirports(){
        Scanner textScanner = new Scanner(reader);
        Set<Airport> airports = new HashSet<Airport>();
        AirportParser parser = new AirportParser();

        String currentString;
        while(textScanner.hasNextLine()){
            currentString = textScanner.nextLine();
            Airport airport = parser.createAirport(currentString);
            airports.add(airport);
        }
        return airports;
    }

    public Set<Airline> buildAirlines(){
        Scanner textScanner = new Scanner(reader);
        Set<Airline> airlines = new HashSet<Airline>();
        AirlineParser parser = new AirlineParser();

        String currentString;
        while(textScanner.hasNextLine()){
            currentString = textScanner.nextLine();
            Airline airline = parser.createSingleAirline(currentString);
            airlines.add(airline);
        }
        return airlines;
    }

    public Set<Route> buildRoute(){
        Scanner textScanner = new Scanner(reader);
        Set<Route> routes = new HashSet<Route>();
        RouteParser parser = new RouteParser();
        String currentString;
        while(textScanner.hasNextLine()){
            currentString = textScanner.nextLine();
            Route route = parser.createSingleRoute(currentString);
            routes.add(route);
        }
        return routes;
    }




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
