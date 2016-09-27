package seng202.group8;

/**
 * Created by Callum on 22/08/16.
 */

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.DatabaseMethods.AirlineDatabaseLoader;
import seng202.group8.Model.DatabaseMethods.AirportDatabaseLoader;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.RouteDatabaseLoader;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;

public class Main extends Application {

    public static void main(String[] args) {

        Database.createDatabase();
        Connection airlineConn = Database.connect();
        Connection airportConn = Database.connect();
        Connection routeConn = Database.connect();

        //Load all airlines, airports and routes into the database

        AirportDatabaseLoader airportLoader = new AirportDatabaseLoader();
        AirlineDatabaseLoader airlineLoader = new AirlineDatabaseLoader();
        RouteDatabaseLoader routeLoader = new RouteDatabaseLoader();

        ObservableList<Airline> savedAirlines = airlineLoader.loadAirlines(airlineConn, MainController.getAirlineHashMap());
        Database.disconnect(airlineConn);
        for (Airline airline: savedAirlines) {
            MainController.getCurrentlyLoadedAirlines().add(airline);
        }

        ObservableList<Airport> savedAirports = airportLoader.loadAirport(airportConn, MainController.getAirportHashMap());
        Database.disconnect(airportConn);
        for (Airport airport: savedAirports) {
            MainController.addToCurrentlyLoadedAirports(airport);
        }

        ObservableList<Route> savedRoutes = routeLoader.loadRoutes(routeConn, MainController.getRouteHashMap(), MainController.getAirlineHashMap(), MainController.getAirportHashMap());
        Database.disconnect(routeConn);
        for (Route route: savedRoutes) {
            MainController.addToCurrentlyLoadedRoutes(route);
        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Adds the FXML files to the GUI */
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
        /* Set the name of the GUI */
        primaryStage.setTitle("Hop2it");
        primaryStage.setScene(new Scene(root, 1100, 800));
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1100);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

}
