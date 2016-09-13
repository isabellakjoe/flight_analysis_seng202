package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Airport;

import java.sql.Connection;

/**
 * Created by Erika on 29-Aug-16.
 */
public class AirportSearcher {

    private ObservableList<Airport> loadedAirports = FXCollections.observableArrayList();

    public AirportSearcher(ObservableList<Airport> loadedAirports) {
        this.loadedAirports = loadedAirports;
    }

    public ObservableList<Airport> getLoadedAirports() {
        return loadedAirports;
    }

    private ObservableList<Airport> generateSearchList(String paramID, String searchParam) {

        Database db = new Database();
        DatabaseSearcher dbs = new DatabaseSearcher();
        String sql = dbs.buildAirportSearch(paramID, searchParam);
        Connection conn = db.connect();
        ObservableList<Airport> airports = dbs.searchForAirportByOption(conn, sql);
        db.disconnect(conn);

        return airports;

    }

    public void airportsOfID(String airportID) {
        ObservableList<Airport> matchingAirports = generateSearchList("airportid", airportID);
        loadedAirports = matchingAirports;
    }

    public void airportsOfName(String name) {
        ObservableList<Airport> matchingAirports = generateSearchList("LOWER(name)", name);
        loadedAirports = matchingAirports;
    }

    public void airportsOfCity(String city) {
        ObservableList<Airport> matchingAirports = generateSearchList("LOWER(city)", city);
        loadedAirports = matchingAirports;
    }

    public void airportsOfCountry(String country) {
        ObservableList<Airport> matchingAirports = generateSearchList("LOWER(country)", country);
        loadedAirports = matchingAirports;
    }

    public void airportsOfFAA(String FAA) {
        ObservableList<Airport> matchingAirports = generateSearchList("iata", FAA);
        loadedAirports = matchingAirports;
    }

    public void airportsOfIATA(String IATA) {
        ObservableList<Airport> matchingAirports = generateSearchList("iata", IATA);
        loadedAirports = matchingAirports;
    }

    public void airportsOfICAO(String ICAO) {
        ObservableList<Airport> matchingAirports = generateSearchList("icao", ICAO);
        loadedAirports = matchingAirports;
    }

    public void airportsOfLatitude(String latitude) {
        ObservableList<Airport> matchingAirports = generateSearchList("latitude", latitude);
        loadedAirports = matchingAirports;
    }

    public void airportsOfLongitude(String longitude) {
        ObservableList<Airport> matchingAirports = generateSearchList("longitude", longitude);
        loadedAirports = matchingAirports;
    }

    public void airportsOfAltitude(String altitude) {
        ObservableList<Airport> matchingAirports = generateSearchList("altitude", altitude);
        loadedAirports = matchingAirports;
    }

    public void airportsOfTimezone(String timezone) {
        ObservableList<Airport> matchingAirports = generateSearchList("timezone", timezone);
        loadedAirports = matchingAirports;
    }

    public void airportsOfDST(String DST) {
        ObservableList<Airport> matchingAirports = generateSearchList("dst", DST);
        loadedAirports = matchingAirports;
    }


}