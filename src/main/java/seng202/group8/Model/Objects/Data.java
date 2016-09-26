package seng202.group8.Model.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by esa46 on 22/09/16.
 *
 * PLEASE DON'T CHANGE ANYTHING IN HERE! I'm working on it :) :) :)
 */
public class Data implements Serializable {
//
//    public static ArrayList<Airport> airports;
//    public static ArrayList<Route> routes;
    public static int num;
//
//    public Data(ObservableList<Airline> airlines, ObservableList<Airport> airports, ObservableList<Route> routes, int num){
//        this.airlines = toAirlineArray(airlines);
//        this.airports = toAirportArray(airports);
//        this.routes = toRouteArray(routes);
//        this.num = num;
//    }

    public Data(int num){
        this.num = num;
    }

    public static int getNum (){
        return num;
    }

//
//
//    public static ArrayList<Airline> getAirlines() {
//        return airlines;
//    }
//
//    public static ArrayList<Airport> getAirports() {
//        return airports;
//    }
//
//    public static ArrayList<Route> getRoutes() {
//        return routes;
//    }

    private ArrayList<Airline> toAirlineArray(ObservableList<Airline> input){
        ArrayList<Airline> airlines = new ArrayList<Airline>();
        for (int i = 0; i < input.size(); i++){
            airlines.add(input.get(i));
        }
        return airlines;
    }

    private ArrayList<Airport> toAirportArray(ObservableList<Airport> input){
        ArrayList<Airport> airports = new ArrayList<Airport>();
        for (int i = 0; i < input.size(); i++){
            airports.add(input.get(i));
        }
        return airports;
    }
    private ArrayList<Route> toRouteArray(ObservableList<Route> input){
        ArrayList<Route> routes = new ArrayList<Route>();
        for (int i = 0; i < input.size(); i++){
            routes.add(input.get(i));
        }
        return routes;
    }
}
