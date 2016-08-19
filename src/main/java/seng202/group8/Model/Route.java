package seng202.group8.Model;

/**
 * Created by esa46 on 19/08/16.
 */
public class Route {

    private Airline airline;
    private Airport sourceAirport;
    private Airport destinationAirport;
    private boolean isCodeshare;
    private int stops;
    private String equipment;

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airport getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(Airport sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
