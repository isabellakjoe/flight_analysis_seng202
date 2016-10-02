package seng202.group8.Model.Objects;

import java.io.Serializable;

/**
 * Created by esa46 on 19/08/16.
 * Airline object class.
 */
public class Airline implements Serializable {

    private int airlineID;
    private String name;
    private String alias;
    private String IATA;
    private String ICAO;
    private String callsign;
    private String country;
    private boolean isActive;


    public int getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
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

    public boolean hasAlias() {
        return this.alias != null;
    }

    /**
     * Method to check if Airline being added has any parameter equal to another Airline already in database
     *
     * @param other (Airline) being checked against
     * @return isEqual (boolean) true if parameter already in database, false otherwise
     */
    public boolean isEqualTo(Airline other) {
        boolean isEqual = true;
        if (!this.name.equals(other.getName())) {
            isEqual = false;
        }
        if (this.airlineID != other.getAirlineID()) {
            isEqual = false;
        }
        if (this.hasAlias()) {
            if (other.hasAlias()) {
                if (!this.alias.equals(other.getAlias())) {
                    isEqual = false;
                }
            } else {
                isEqual = false;
            }
        }
        if (!this.IATA.equals(other.getIATA())) {
            isEqual = false;
        }
        if (!this.ICAO.equals(other.getICAO())) {
            isEqual = false;
        }
        if (!this.callsign.equals(other.getCallsign())) {
            isEqual = false;
        }
        if (!this.country.equals(other.getCountry())) {
            isEqual = false;
        }
        return isEqual;
    }
}
