package seng202.group8.Model.DatabaseMethods;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.AirlineMethod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by Callum on 25/08/16.
 */
public class AirlineDatabaseLoader extends AirlineMethod {

    /**
     * Function to return an observable list of Airlines which have been loaded from the database
     *
     * @param conn a connection to the database
     * @return an observable list of airports
     */
    public ObservableList<Airline> loadAirlines(Connection conn, HashMap<String, Airline> airlineIATAHashMap) {

        ObservableList<Airline> airlines = FXCollections.observableArrayList();
        try {
            //Create a statement and execute the query to get all airlines from the database
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM airline");
            while (result.next()) {
                Airline loadedAirline = new Airline();
                loadedAirline.setAirlineID(result.getInt("airlineid"));
                loadedAirline.setName(result.getString("name"));
                loadedAirline.setAlias(result.getString("alias"));
                loadedAirline.setIATA(result.getString("iata"));
                loadedAirline.setICAO(result.getString("icao"));
                loadedAirline.setCallsign(result.getString("callsign"));
                loadedAirline.setCountry(result.getString("country"));
                checkActive(loadedAirline, result.getString("active"));
                airlines.add(loadedAirline);
                if (loadedAirline.getIATA() != null) {
                    airlineIATAHashMap.put(loadedAirline.getIATA(), loadedAirline);
                } else if (loadedAirline.getICAO() != null){
                    airlineIATAHashMap.put(loadedAirline.getICAO(), loadedAirline);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return airlines;
    }

}
