package seng202.group8.Model.DatabaseMethods;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.AirportMethod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by Callum on 26/08/16.
 */
public class AirportDatabaseLoader extends AirportMethod {
    /**
     * Method used to get a list of all the airports currently in the database
     *
     * @param conn a connection to the database
     * @return an observable list of all the airports currently in the database
     */
    public ObservableList<Airport> loadAirport(Connection conn, HashMap<String, Airport> airportIATAHashMap) {

        ObservableList<Airport> airports = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM airport");
            while (result.next()) {
                Airport loadAirport = new Airport();
                loadAirport.setAirportID(result.getInt("airportid"));
                loadAirport.setName(result.getString("name"));
                loadAirport.setCity(result.getString("city"));
                loadAirport.setCountry(result.getString("country"));
                checkCodeType(loadAirport, result.getString("iata"), result.getString("country"));
                loadAirport.setICAO(result.getString("icao"));
                loadAirport.setLatitude(result.getDouble("latitude"));
                loadAirport.setLongitude(result.getDouble("longitude"));
                loadAirport.setAltitude(result.getInt("altitude"));
                loadAirport.setTimezone(result.getInt("timezone"));
                loadAirport.setDST(result.getString("dst").charAt(0));
                loadAirport.setTimezone(result.getInt("timezone"));
                airports.add(loadAirport);
                if (loadAirport.getCountry().equals("United States")) {
                    if (loadAirport.getFAA() != null) {
                        airportIATAHashMap.put(loadAirport.getFAA(), loadAirport);
                    } else {
                        airportIATAHashMap.put(loadAirport.getICAO(), loadAirport);
                    }
                } else {
                    if (loadAirport.getIATA() != null) {
                        airportIATAHashMap.put(loadAirport.getIATA(), loadAirport);
                    } else {
                        airportIATAHashMap.put(loadAirport.getICAO(), loadAirport);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return airports;
    }

}
