package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airline;

import java.sql.Connection;

/**
 * Created by Erika on 29-Aug-16.
 */
public class AirlineSearcher {

    private ObservableList<Airline> loadedAirlines = FXCollections.observableArrayList();

    public AirlineSearcher(ObservableList<Airline> loadedAirlines) {
        this.loadedAirlines = loadedAirlines;
    }

    public ObservableList<Airline> getLoadedAirlines() {
        return loadedAirlines;
    }

    private ObservableList<Airline> generateSearchList(String paramID, String searchParam) {
        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        String sql = dbs.buildAirlineSearch(paramID, searchParam);
        Connection conn = db.connect();
        ObservableList<Airline> airlines = dbs.searchForAirlinesByOption(conn, sql);
        db.disconnect(conn);

        return airlines;
    }

    public void airlinesOfID(String airlineID) {
        ObservableList<Airline> matchingAirlines = generateSearchList("airlineid", airlineID);
        System.out.println(matchingAirlines);
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfName(String name) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(name)", name);
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfAlias(String alias) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(alias)", alias);
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfIATA(String IATA) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(iata)", IATA);
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfICAO(String ICAO) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(icao)", ICAO);
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfCallsign(String callsign) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(callsign)", callsign);
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfCountry(String country) {
        ObservableList<Airline> matchingAirlines = generateSearchList("LOWER(country)", country);
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfActiveStatus(String activeStatus){
        ObservableList<Airline> matchingAirlines = generateSearchList("active", "Y");
        loadedAirlines = matchingAirlines;
    }



}
