package seng202.group8.Model.Objects;

import java.util.ArrayList;

/**
 * Created by esa46 on 19/08/16.
 * <p>
 * Airline object class.
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
    private double altitude;
    private int timezone;
    private char DST;
    private String olsonTimezone;


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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
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


    /**
     * Method that calculates distance (in km, rounded to nearest 100km) to second airport
     *
     * @param airport being measured to
     * @return distanceKm (double)
     */
    public double calculateDistanceTo(Airport airport){
        double distanceKm;
        double earthRadiusKm = 6371;
        double latDiff = Math.toRadians(airport.getLatitude() - this.getLatitude());
        double longDiff = Math.toRadians(airport.getLongitude() - this.getLongitude());
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(Math.toRadians(this.getLatitude()))
                * Math.cos(Math.toRadians(airport.getLatitude())) * Math.sin(longDiff / 2) * Math.sin(longDiff / 2);
        double b = Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        distanceKm = (earthRadiusKm * b * 2);

        distanceKm = (Math.round(distanceKm * 0.01) / 0.01);
        return distanceKm;
    }


    /**
     * Method to check if airport being added has params equal to another airline already in database.
     *
     * @param other (Airport) being checked against
     * @return isEqual (boolean) true if parameter already in database, false otherwise
     */
    public boolean isEqualTo(Airport other) {
        boolean isEqual = true;
        if (!this.name.equals(other.getName())) {
            isEqual = false;
        }
        if (this.airportID != other.getAirportID()) {
            isEqual = false;
        }
        if (this.latitude != other.getLatitude()) {
            isEqual = false;
        }
        if (this.longitude != other.getLongitude()) {
            isEqual = false;
        }
        if (this.altitude != other.getAltitude()) {
            isEqual = false;
        }
        if (!this.city.equals(other.getCity())) {
            isEqual = false;
        }
        if (!this.country.equals(other.getCountry())) {
            isEqual = false;
        }
        if (this.DST != other.getDST()) {
            isEqual = false;
        }
        if (this.timezone != other.getTimezone()) {
            isEqual = false;
        }
        if (!this.olsonTimezone.equals(other.getOlsonTimezone())) {
            isEqual = false;
        }
        if (this.FAA != other.getFAA()) {
            isEqual = false;
        }
        if (!this.IATA.equals(other.getIATA())) {
            isEqual = false;
        }
        if (!this.ICAO.equals(other.getICAO())) {
            isEqual = false;
        }
        return isEqual;
    }


}
