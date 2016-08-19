package seng202.group8.Model;

/**
 * Created by esa46 on 19/08/16.
 */
public class Airport {

    private int airportID;
    private String name;
    private String city;
    private String country;
    private int FAA;
    private String IATA;
    private String ICAO;
    private float latitude;
    private float longitude;
    private float altitude;
    private float timezone;
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

    public int getFAA() {
        return FAA;
    }

    public void setFAA(int FAA) {
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getTimezone() {
        return timezone;
    }

    public void setTimezone(float timezone) {
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
