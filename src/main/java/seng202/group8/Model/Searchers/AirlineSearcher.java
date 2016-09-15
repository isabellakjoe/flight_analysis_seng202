package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airline;

import java.sql.Connection;

/**
 * Created by Erika on 29-Aug-16.
 */
public class AirlineSearcher {

    // Private list used to contain all loaded airlines
    private ObservableList<Airline> loadedAirlines = FXCollections.observableArrayList();

    /**
     * A constructor for the AirlineSearcher Object
     *
     * @param loadedAirlines a observable list of airlines
     */
    public AirlineSearcher(ObservableList<Airline> loadedAirlines) {
        this.loadedAirlines = loadedAirlines;
    }

    /**
     * Return the list of all currently loaded airlines
     *
     * @return an observable list of airlines
     */
    public ObservableList<Airline> getLoadedAirlines() {
        return loadedAirlines;
    }

    private ObservableList<Airline> generateSearchList(String paramID, String searchParam) {
        // Method to search for airlines through the database
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        String sql = dbs.buildAirlineSearch(paramID, searchParam);
        Connection conn = db.connect();
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sql);
        db.disconnect(conn);

        return airlines;
    }

    /**
     * Method to search for airline based off of ID
     *
     * @param airlineID ID of a airline
     */
    public void airlinesOfID(String airlineID) {
        ObservableList<Airline> matchingAirlines = generateSearchList("airlineid", airlineID);
        System.out.println(matchingAirlines);
        loadedAirlines = matchingAirlines;
    }

    /**
     * Method to search for airline based off of name
     *
     * @param name name of a airline
     */
    public void airlinesOfName(String name) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(name)", name);
        loadedAirlines = matchingAirlines;
    }

    /**
     * Method to search for a airline based off of alias
     *
     * @param alias alias of a airline
     */
    public void airlinesOfAlias(String alias) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(alias)", alias);
        loadedAirlines = matchingAirlines;
    }

    /**
     * Method to search for a airline based off of IATA
     *
     * @param IATA iata of a airline
     */
    public void airlinesOfIATA(String IATA) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(iata)", IATA);
        loadedAirlines = matchingAirlines;
    }

    /**
     * Method to search for a airline based off of ICAO
     *
     * @param ICAO icao of a airline
     */
    public void airlinesOfICAO(String ICAO) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(icao)", ICAO);
        loadedAirlines = matchingAirlines;
    }

    /**
     * Method to search for a airline based off of Callsign
     *
     * @param callsign callsign of a airline
     */
    public void airlinesOfCallsign(String callsign) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(callsign)", callsign);
        loadedAirlines = matchingAirlines;
    }

    /**
     * Method to search for a airline based off of Country
     *
     * @param country country of a airline
     */
    public void airlinesOfCountry(String country) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(country)", country);
        loadedAirlines = matchingAirlines;
    }

    /**
     * Method to search for a airline based off of it's status
     *
     * @param activeStatus status of a airline
     */
    public void airlinesOfActiveStatus(String activeStatus) {
        ObservableList<Airline> matchingAirlines = generateSearchList("active", "Y");
        loadedAirlines = matchingAirlines;
    }


}
