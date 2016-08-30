package seng202.group8.Model.DatabaseMethods;

import seng202.group8.Model.Objects.AirlineMethod;
import seng202.group8.Model.Objects.Airline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Callum on 25/08/16.
 */
public class AirlineDatabaseLoader extends AirlineMethod {


    public ArrayList<Airline> loadAirlines(Connection conn) {

        ArrayList<Airline> airlines = new ArrayList<Airline>();
        try {
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
            }
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return airlines;
    }

}
