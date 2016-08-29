package seng202.group8.Model.DatabaseMethods;

import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Callum on 27/08/16.
 */
public class DatabaseSaver {

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = ("jdbc:sqlite:ApplicationDatabase.db");
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

        } catch(Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return conn;
    }

    public static void disconnect(Connection conn) {
        try {
            conn.close();
        } catch(Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private String checkIATA(Airport airport) {

        if (airport.getCountry().equals("United States")) {
            return airport.getFAA();
        } else {
            return airport.getIATA();
        }

    }

    private String convertIntToString(int toConvert) {

        return Integer.toString(toConvert);

    }

    private String convertDoubleToString(double toConvert) {

        return Double.toString(toConvert);

    }

    /* Method to check whether an airline is currently active*/
    private char checkActive(Boolean isActive) {
        if (isActive) {
            return 'Y';
        } else {
            return 'N';
        }
    }

    /* Method to check whether an route is codeshared */
    private char setCodeshare(boolean codeshare) {
        if (codeshare) {
            return 'Y';
        } else {
            return 'N';
        }
    }

    private String createAirportStatement(Airport airportToSave) {
        //Function to create an sql statement to insert an airport
        int id = airportToSave.getAirportID();
        String stringID = convertIntToString(id);
        String name = airportToSave.getName();
        String city = airportToSave.getCity();
        String country = airportToSave.getCountry();
        String code = checkIATA(airportToSave);
        String icao = airportToSave.getICAO();
        double latitude = airportToSave.getLatitude();
        String stringLat = convertDoubleToString(latitude);
        double longitude = airportToSave.getLongitude();
        String stringLong = convertDoubleToString(longitude);
        int altitude = airportToSave.getAltitude();
        String stringAlt = convertIntToString(altitude);
        int timezone = airportToSave.getTimezone();
        String stringTime = convertIntToString(timezone);
        char dst = airportToSave.getDST();
        String tz = airportToSave.getOlsonTimezone();

        String statement = "INSERT INTO airport VALUES (" + stringID + ",\"" + name + "\",\"" + city + "\",\"" + country +
                                                        "\",\"" + code + "\",\"" + icao + "\"," + stringLat + "," +
                                                        stringLong + "," + stringAlt + "," + stringTime + ",\"" +
                                                        dst + "\",\"" + tz + "\");";

        return statement;
    }

    private String createAirlineStatement(Airline airlineToSave) {
        //Function to create airline insert statement

        int id = airlineToSave.getAirlineID();
        String stringID = convertIntToString(id);
        String name = airlineToSave.getName();
        String alias = airlineToSave.getAlias();
        String iata = airlineToSave.getIATA();
        String icao = airlineToSave.getICAO();
        String callsign = airlineToSave.getCallsign();
        String country = airlineToSave.getCountry();
        char active = checkActive(airlineToSave.isActive());

        String statement = "INSERT INTO airline VALUES (" + stringID + ",\"" + name + "\",\"" + alias + "\",\"" + iata +
                                                        "\",\"" + icao + "\",\"" + callsign + "\",\"" + country + "\",\"" + active + "\");";

        return statement;


    }

    private String createRouteStatement(Route routeToSave) {
        //Function to create route insert statement

        //Get the route's unique ID
        int id = routeToSave.getRouteID();
        String stringID = convertIntToString(id);
        //Get the name and code of the airline
        String code = routeToSave.getAirline().getName();
        int airlineID = routeToSave.getAirline().getAirlineID();
        String stringAirlineID = convertIntToString(airlineID);
        //Get the name and ID of the source airport
        String sourceAirportCode = routeToSave.getSourceAirport().getICAO();
        int sourceAirportID = routeToSave.getSourceAirport().getAirportID();
        String stringSourceID = convertIntToString(sourceAirportID);
        //Get the name and ID of the destination airport
        String destinationAirportCode = routeToSave.getDestinationAirport().getICAO();
        int destinationAirportID = routeToSave.getDestinationAirport().getAirportID();
        String stringDestinationID = convertIntToString(destinationAirportID);
        //Get whether the route is codeshared
        char codeshare = setCodeshare(routeToSave.isCodeshare());
        //Get the number of stops
        int stops = routeToSave.getStops();
        String stringStops = convertIntToString(stops);
        //Get the equipment used
        String equipment = routeToSave.getEquipment();

        String statement = "INSERT INTO route VALUES (" + stringID + ",\"" + code + "\"," + stringAirlineID + ",\"" +
                                                    sourceAirportCode + "\"," + stringSourceID + ",\"" + destinationAirportCode +
                                                    "\"," + stringDestinationID + ",\"" + codeshare + "\"," + stringStops + ",\"" +
                                                    equipment + "\");";
        return statement;


    }


    public void saveAirports(Connection conn, ObservableList<Airport> airportList) {

        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < airportList.size(); i++) {
                String sql = createAirportStatement(airportList.get(i));
                stmt.executeUpdate(sql);
            }
            conn.commit();

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void saveAirlines(Connection conn, ObservableList<Airline> airlineList) {

        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < airlineList.size(); i++) {
                String sql = createAirlineStatement(airlineList.get(i));
                stmt.executeUpdate(sql);
            }
            conn.commit();

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void saveRoutes(Connection conn, ObservableList<Route> routeList) {

        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < routeList.size(); i++) {
                String sql = createRouteStatement(routeList.get(i));
                stmt.executeUpdate(sql);
            }
            conn.commit();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

    }



}
