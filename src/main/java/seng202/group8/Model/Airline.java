package seng202.group8.Model;

/**
 * Created by esa46 on 19/08/16.
 */
public class Airline {

    private int AirlineID;
    private String name;
    private String alias;
    private String IATA;
    private String ICAO;
    private String callsign;
    private String country;
    private boolean isActive;


    public int getAirlineID() {
        return AirlineID;
    }

    public void setAirlineID(int airlineID) {
        AirlineID = airlineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
