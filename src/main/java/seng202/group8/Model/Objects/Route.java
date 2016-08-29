package seng202.group8.Model.Objects;

/**
 * Created by esa46 on 19/08/16.
 */
public class Route {

    private int routeID;
    private Airline airline;
    private String airlineName;
    private Airport sourceAirport;
    private String sourceAirportName;
    private Airport destinationAirport;
    private String destinationAirportName;
    private boolean isCodeshare;
    private String codeshareString;
    private int stops;
    private String equipment;


    public int getRouteID() { return routeID;}

    public void setRouteID(int routeID) { this.routeID = routeID;}

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getAirlineName() { return airlineName; }

    public void setAirlineName(String airlineName) {this.airlineName = airlineName;}

    public Airport getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(Airport sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    public String getSourceAirportName() {return sourceAirportName; }

    public void setSourceAirportName(String sourceAirportName) {this.sourceAirportName = sourceAirportName; }

    public String getDestinationAirportName() {return destinationAirportName; }

    public void setDestinationAirportName(String destinationAirportName) {this.destinationAirportName = destinationAirportName; }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public boolean isCodeshare() {
        return isCodeshare;
    }

    public void setCodeshare(boolean codeshare) {
        isCodeshare = codeshare;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public String getCodeshareString(){ return codeshareString; }

    public void setCodeshareString(String codeshareString){ this.codeshareString= codeshareString; }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public boolean isEqualTo(Route other) {
        boolean isEqual = true;
        if (!this.airline.getName().equals(other.getAirline().getName())) {
            isEqual = false;
        }
        if (this.sourceAirport.getAirportID() != (other.getSourceAirport().getAirportID())) {
            isEqual = false;
        }
        if (this.destinationAirport.getAirportID() != (other.getDestinationAirport().getAirportID())){
            isEqual = false;
        }
        if (this.isCodeshare != other.isCodeshare()) {
            isEqual = false;
        }
        if (this.stops != other.getStops()) {
            isEqual = false;
        }
        if (!this.equipment.equals(other.getEquipment())) {
            isEqual = false;
        }
        return isEqual;
    }
}
