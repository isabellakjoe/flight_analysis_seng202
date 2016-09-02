package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airport;

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

    public void airportsOfID(String airportID) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        int intID = Integer.parseInt(airportID);
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getAirportID() == intID) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfName(String name) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getName().equalsIgnoreCase(name)) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfCity(String city) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getCity().equalsIgnoreCase(city)) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfCountry(String country) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getCountry().equalsIgnoreCase(country)) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfFAA(String FAA) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getFAA() != null && loadedAirports.get(i).getFAA().equalsIgnoreCase(FAA)) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfIATA(String IATA) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getIATA() != null && loadedAirports.get(i).getIATA().equalsIgnoreCase(IATA)) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfICAO(String ICAO) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getICAO() != null && loadedAirports.get(i).getICAO().equalsIgnoreCase(ICAO)) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfLatitude(String latitude) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        float floatLat = Float.parseFloat(latitude);
        for (int i = 0; i < loadedAirports.size(); i++) {

            if (Math.abs(loadedAirports.get(i).getLatitude() - floatLat) < 0.01) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfLongitude(String longitude) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        float floatLong = Float.parseFloat(longitude);
        for (int i = 0; i < loadedAirports.size(); i++) {

            if (Math.abs(loadedAirports.get(i).getLongitude() - floatLong) < 0.01) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfAltitude(String altitude) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        int intAlt = Integer.parseInt(altitude);
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getAltitude() == intAlt) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfTimezone(String timezone) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        int intTZ = Integer.parseInt(timezone);
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getTimezone() == intTZ) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfDST(String DST) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();
        char charDST = DST.charAt(0);
        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getDST() == charDST) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }


}