package seng202.group8.Model.DatabaseMethods;

import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airline;
import seng202.group8.Model.Objects.Airport;
import seng202.group8.Model.Objects.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Callum on 27/08/16.
 */
public class DatabaseSaver {

    private String checkIATA(Airport airport) {
        //Method to get an airports IATA or FAA depending on specified country
        if (airport.getCountry().equals("United States")) {
            return airport.getFAA();
        } else {
            return airport.getIATA();
        }

    }

    public int getCurrentMaxRouteID(Connection conn) {
        int max = -1;
        try {
            Statement stmt = conn.createStatement();
            String sql = "select max(routeid) from route;";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                max = result.getInt(0);
            }
            System.out.println(max);
        } catch (SQLException e) {
            max = 1;
        }

        if (max < 0) {
            max = 1;
        }

        return max;
    }

    private String convertIntToString(int toConvert) {
        //Method used to convert a integer to a string
        return Integer.toString(toConvert);

    }

    private String convertDoubleToString(double toConvert) {
        //Method used to convert a double to a string
        return Double.toString(toConvert);

    }

    private char getCharOfBoolean(Boolean isActive) {
        //Method to return a char based off an input boolean
        if (isActive) {
            return 'Y';
        } else {
            return 'N';
        }
    }

    private String createAirportStatement(Airport airportToSave) {
        // A method used to create an sql statement used to insert an airport into the database.
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
        double altitude = airportToSave.getAltitude();
        String stringAlt = Double.toString(altitude);
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
        //A method used to create an sql statement used to insert an airline into the database.
        int id = airlineToSave.getAirlineID();
        String stringID = convertIntToString(id);
        String name = airlineToSave.getName();
        String alias = airlineToSave.getAlias();
        String iata = airlineToSave.getIATA();
        String icao = airlineToSave.getICAO();
        String callsign = airlineToSave.getCallsign();
        String country = airlineToSave.getCountry();
        char active = getCharOfBoolean(airlineToSave.isActive());

        String statement = "INSERT INTO airline VALUES (" + stringID + ",\"" + name + "\",\"" + alias + "\",\"" + iata +
                "\",\"" + icao + "\",\"" + callsign + "\",\"" + country + "\",\"" + active + "\");";

        return statement;


    }

    private String createRouteStatement(Route routeToSave) {
        //A method used to create an sql statement used to insert an route into the database.
        //Get the route's unique ID
        int id = routeToSave.getRouteID();
        String stringID = convertIntToString(id);
        //Get the name and code of the airline
        String code = routeToSave.getAirline().getName();
        int airlineID = routeToSave.getAirline().getAirlineID();
        String stringAirlineID = convertIntToString(airlineID);
        //Get the name and ID of the source airport
        String sourceAirportCode;
        if (routeToSave.getSourceAirport().getIATA() == null) {
            sourceAirportCode = routeToSave.getSourceAirport().getICAO();
        } else {
            sourceAirportCode = routeToSave.getSourceAirport().getIATA();
        }
        int sourceAirportID = routeToSave.getSourceAirport().getAirportID();
        String stringSourceID = convertIntToString(sourceAirportID);
        //Get the name and ID of the destination airport
        String destinationAirportCode;
        if (routeToSave.getDestinationAirport().getIATA() == null) {
            destinationAirportCode = routeToSave.getDestinationAirport().getICAO();
        } else {
            destinationAirportCode = routeToSave.getDestinationAirport().getIATA();
        }
        int destinationAirportID = routeToSave.getDestinationAirport().getAirportID();
        String stringDestinationID = convertIntToString(destinationAirportID);
        //Get whether the route is codeshared
        char codeshare = getCharOfBoolean(routeToSave.isCodeshare());
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

    /**
     * A method which takes an sql query and executes an update to the database to add Airport objects
     *
     * @param conn        a static connection to a database
     * @param airportList a observable list of airports
     */
    public void saveAirports(Connection conn, ObservableList<Airport> airportList) {

        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < airportList.size(); i++) {
                String sql = createAirportStatement(airportList.get(i));
                try {
                    stmt.executeUpdate(sql);
                }
                catch (java.sql.SQLException e){
                    System.out.print("Airport being added includes non unique data.\n");
                }
            }
            conn.commit();

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * A method which takes an sql query and executes an update to the database to add Airline objects
     *
     * @param conn        a static connection to a database
     * @param airlineList an observable list of airlines
     */
    public void saveAirlines(Connection conn, ObservableList<Airline> airlineList) {

        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < airlineList.size(); i++) {
                String sql = createAirlineStatement(airlineList.get(i));
                try {
                    stmt.executeUpdate(sql);
                }
                catch (java.sql.SQLException e){
                    System.out.print("Airline being added includes non unique data.\n");
                }
            }
            conn.commit();

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

    }

    /**
     * A method which takes an sql query and executes an update to the database to add Route objects
     *
     * @param conn      a static connection to a database
     * @param routeList an observable list of routes
     */
    public int saveRoutes(Connection conn, ObservableList<Route> routeList) {

        int routeIDStart = getCurrentMaxRouteID(conn);
        System.out.println("Current ID Starts at " + Integer.toString(routeIDStart));
        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < routeList.size(); i++) {
                routeList.get(i).setRouteID(routeIDStart);
                String sql = createRouteStatement(routeList.get(i));
                try {
                    stmt.executeUpdate(sql);
                }
                catch (java.sql.SQLException e){
                    System.out.print("Route being added includes non unique data.\n");
                }
                routeIDStart += 1;
            }
            conn.commit();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return routeIDStart;
    }

    public int saveRouteWithID(Connection conn, ObservableList<Route> routeList, int id) {

        int routeid = id;

        try {
            Statement stmt = conn.createStatement();
            for (int i = 0; i < routeList.size(); i++) {
                routeList.get(i).setRouteID(routeid);
                routeid += 1;
                String sql = createRouteStatement(routeList.get(i));
                try {
                    stmt.executeUpdate(sql);
                } catch (java.sql.SQLException e){
                    System.out.print("Route being added includes non unique data.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return routeid;
    }

    public void saveSingleRoute(Connection conn, Route route) {

        try {
            Statement stmt = conn.createStatement();
            String sql = createRouteStatement(route);
            stmt.executeUpdate(sql);
            conn.commit();
        } catch (SQLException e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

    }

    /**
     * A method which takes a list of route id's and deletes them from the database
     *
     * @param conn a static connection to the database
     * @param ids  a list of routes ids which are to be deleted
     */
    public void deleteRoutes(Connection conn, ArrayList<Integer> ids) {

        for (int i = 0; i < ids.size(); i++) {
            try {
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM route WHERE routeid='" + Integer.toString(ids.get(i)) + "';";
                stmt.executeUpdate(sql);
                conn.commit();
                stmt.close();
            } catch (Exception e) {
                System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
            }
        }

    }

    /**
     * A method which takes a list of airline id's and deletes them from the database
     *
     * @param conn a static connection to the database
     * @param ids  a list of airline ids which are to be deleted
     */
    public void deleteAirlines(Connection conn, ArrayList<Integer> ids) {

        for (int i = 0; i < ids.size(); i++) {
            try {
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM airline WHERE airlineid='" + Integer.toString(ids.get(i)) + "';";
                stmt.executeUpdate(sql);
                conn.commit();
                stmt.close();
            } catch (Exception e) {
                System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
            }
        }

    }

    /**
     * A method which takes a list of airport id's and deletes them from the database
     *
     * @param conn a static connection to the database
     * @param ids  a list of airport ids which are to be deleted
     */
    public void deleteAirport(Connection conn, ArrayList<Integer> ids) {

        for (int i = 0; i < ids.size(); i++) {
            try {
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM airport WHERE airportid='" + Integer.toString(ids.get(i)) + "';";
                stmt.executeUpdate(sql);
                conn.commit();
                stmt.close();
            } catch (Exception e) {
                System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
            }
        }

    }


}
