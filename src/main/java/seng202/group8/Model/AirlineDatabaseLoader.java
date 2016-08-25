package seng202.group8.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 25/08/16.
 */
public class AirlineDatabaseLoader extends AirlineMethod {

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = ("jdbc:sqlite:ApplicationDatabase.db");
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return conn;
    }

    public static void disconnect(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Set<Airline> loadAirlines(Connection conn) {

        Set<Airline> airlines = new HashSet<Airline>();
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
