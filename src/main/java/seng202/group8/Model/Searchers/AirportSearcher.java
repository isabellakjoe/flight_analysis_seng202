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

    // Private list used to store all Airport objects
    private ObservableList<Airport> loadedAirports = FXCollections.observableArrayList();

    /** A constructor for the Airport Searcher Object
     *
     * @param loadedAirports an observable list of airports
     */
    public AirportSearcher(ObservableList<Airport> loadedAirports) {
        this.loadedAirports = loadedAirports;
    }

    /** Return the list of all currently loaded airports
     *
     * @return an observable list of airports
     */
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

    /** Method to search for airport based off of ID
     *
     * @param airportID id of airport
     */
    public void airportsOfID(String airportID) {
        ObservableList<Airport> matchingAirports = generateSearchList("airportid", airportID);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of Name
     *
     * @param name name of airport
     */
    public void airportsOfName(String name) {
        ObservableList<Airport> matchingAirports = generateSearchList("LOWER(name)", name);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of City
     *
     * @param city city of airport
     */
    public void airportsOfCity(String city) {
        ObservableList<Airport> matchingAirports = generateSearchList("LOWER(city)", city);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of Country
     *
     * @param country country of airport
     */
    public void airportsOfCountry(String country) {
        ObservableList<Airport> matchingAirports = generateSearchList("LOWER(country)", country);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of FAA
     *
     * @param FAA faa of airport
     */
    public void airportsOfFAA(String FAA) {
        ObservableList<Airport> matchingAirports = generateSearchList("iata", FAA);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of IATA
     *
     * @param IATA iata of airport
     */
    public void airportsOfIATA(String IATA) {
        ObservableList<Airport> matchingAirports = generateSearchList("iata", IATA);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of ICAO
     *
     * @param ICAO icao of airport
     */
    public void airportsOfICAO(String ICAO) {
        ObservableList<Airport> matchingAirports = generateSearchList("icao", ICAO);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of Latitude
     *
     * @param latitude latitude of airport
     */
    public void airportsOfLatitude(String latitude) {
        ObservableList<Airport> matchingAirports = generateSearchList("latitude", latitude);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of Longitude
     *
     * @param longitude longitude of airport
     */
    public void airportsOfLongitude(String longitude) {
        ObservableList<Airport> matchingAirports = generateSearchList("longitude", longitude);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of Altitude
     *
     * @param altitude altitude of airport
     */
    public void airportsOfAltitude(String altitude) {
        ObservableList<Airport> matchingAirports = generateSearchList("altitude", altitude);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of Timezone
     *
     * @param timezone timezone of airport
     */
    public void airportsOfTimezone(String timezone) {
        ObservableList<Airport> matchingAirports = generateSearchList("timezone", timezone);
        loadedAirports = matchingAirports;
    }

    /** Method to search for airport based off of DST
     *
     * @param DST dst of airport
     */
    public void airportsOfDST(String DST) {
        ObservableList<Airport> matchingAirports = generateSearchList("dst", DST);
        loadedAirports = matchingAirports;
    }


}