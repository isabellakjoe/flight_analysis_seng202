package seng202.group8.Model.DatabaseMethods;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Callum on 30/08/16.
 */
public class Database {

    /**
     * Method used to connect to the test database located in the static database folder
     * IMPORTANT: Should only be used for when testing database methods.
     *
     * @return a static SQL Connection to the test database
     */
    public static Connection testConnect() {
        //Method to connect to the test database
        Connection conn = null;
        try {
            //Specifiy the pathname for jar containing database connection methods
            Class.forName("org.sqlite.JDBC");
            //Specify the path to the database here
            String url = ("jdbc:sqlite:src/main/database/static/testing.db");
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return conn;
    }

    /**
     * Method used to connect to the dynamic database used for the operation of the application.
     *
     * @return a static SQL Connection to the database
     */
    public static Connection connect() {
        //Method to connect to the actual database for the application
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = ("jdbc:sqlite:Database.db");
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }

        return conn;
    }

    /**
     * A static method used to close an established connection to the database with error handling
     *
     * @param conn an established connection to a database.
     */
    public static void disconnect(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**
     * A method to create the database for the application. Should be run on first launch of the application.
     * Tests whether a database already exists in the file system, if so deletes it and creates a new database
     * to be used as a form of memory for the application.
     */
    public static void createDatabase() {

        File f = new File("Database.db");
        if (f.exists()) {
            f.delete();
        }
        Connection conn;
        Statement stmtAirport;
        Statement stmtAirline;
        Statement stmtRoute;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = ("jdbc:sqlite:Database.db");
            conn = DriverManager.getConnection(url);

            stmtAirport = conn.createStatement();
            String sql = "CREATE TABLE airport (" +
                    "airportid int primary key," +
                    "name varchar(50) not null," +
                    "city varchar(50) not null," +
                    "country varchar(46) not null," +
                    "iata char(3)," +
                    "icao char(4)," +
                    "latitude decimal(9,6) not null," +
                    "longitude decimal(9,6) not null," +
                    "altitude int not null," +
                    "timezone decimal(4, 2) not null," +
                    "dst char(1) not null," +
                    "tz varchar(50) not null" +
                    ");";
            stmtAirport.executeUpdate(sql);
            stmtAirport.close();

            stmtAirline = conn.createStatement();
            sql = "CREATE TABLE airline (" +
                    "airlineid int primary key," +
                    "name varchar(50) not null," +
                    "alias varchar(50) not null," +
                    "iata char(2)," +
                    "icao char(3)," +
                    "callsign varchar(15)," +
                    "country varchar(46) not null," +
                    "active char(1)" +
                    ");";
            stmtAirline.executeUpdate(sql);
            stmtAirline.close();

            stmtRoute = conn.createStatement();
            sql = "CREATE TABLE route (" +
                    "routeid int primary key," +
                    "airlinecode varchar(4) not null," +
                    "airlineid int not null," +
                    "sourceairport varchar(4) not null," +
                    "sourceid int not null," +
                    "destinationairport varchar(4) not null," +
                    "destinationid int not null," +
                    "codeshare char(1)," +
                    "stops int not null," +
                    "equipment char(3) not null" +
                    ");";
            stmtRoute.executeUpdate(sql);
            stmtRoute.close();

            conn.close();

        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }


}
