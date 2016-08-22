package seng202.group8.Model;

/**
 * Created by esa46 on 19/08/16.
 */
public class Airport {

    private int airportID;
    private String name;
    private String city;
    private String country;
    private String FAA;
    private String IATA;
    private String ICAO;
    private double latitude;
    private double longitude;
    private int altitude;
    private int timezone;
    private char DST;

    public int getAirportID() {
        return airportID;
    }

    public void setAirportID(int airportID) {
        this.airportID = airportID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFAA() {
        return FAA;
    }

    public void setFAA(String FAA) {
        this.FAA = FAA;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public char getDST() {
        return DST;
    }

    public void setDST(char DST) {
        this.DST = DST;
    }

    public String getOlsonTimezone() {
        return olsonTimezone;
    }

    public void setOlsonTimezone(String olsonTimezone) {
        this.olsonTimezone = olsonTimezone;
    }

    private String olsonTimezone;



}
