package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String regex = airportID + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirports.size(); i++) {
            Matcher m = p.matcher(Integer.toString(loadedAirports.get(i).getAirportID()));
            if (m.find()) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfName(String name) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = name + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirports.size(); i++) {
            Matcher m = p.matcher(loadedAirports.get(i).getName());
            if (m.find()) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfCity(String city) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = city + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirports.size(); i++) {
            Matcher m = p.matcher(loadedAirports.get(i).getCity());
            if (m.find()) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfCountry(String country) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = country + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirports.size(); i++) {
            Matcher m = p.matcher(loadedAirports.get(i).getCountry());
            if (m.find()) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfFAA(String FAA) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = FAA + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getFAA() != null) {
                Matcher m = p.matcher(loadedAirports.get(i).getFAA());
                if (m.find()) {
                    matchingAirports.add(loadedAirports.get(i));
                }
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfIATA(String IATA) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = IATA + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getIATA() != null) {
                Matcher m = p.matcher(loadedAirports.get(i).getIATA());
                if (m.find()) {
                    matchingAirports.add(loadedAirports.get(i));
                }
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfICAO(String ICAO) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = ICAO + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirports.size(); i++) {
            if (loadedAirports.get(i).getICAO() != null) {
                Matcher m = p.matcher(loadedAirports.get(i).getICAO());
                if (m.find()) {
                    matchingAirports.add(loadedAirports.get(i));
                }
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfLatitude(String latitude) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = latitude + "\\d*";
        Pattern p = Pattern.compile(regex);
        //float floatLat = Float.parseFloat(latitude);
        for (int i = 0; i < loadedAirports.size(); i++) {
            Matcher m = p.matcher(Double.toString(loadedAirports.get(i).getLatitude()));
            if (m.find()) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfLongitude(String longitude) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = longitude + "\\d*";
        Pattern p = Pattern.compile(regex);

        //float floatLong = Float.parseFloat(longitude);
        for (int i = 0; i < loadedAirports.size(); i++) {
            Matcher m = p.matcher(Double.toString(loadedAirports.get(i).getLongitude()));
            if (m.find()) {
                matchingAirports.add(loadedAirports.get(i));
            }
        }
        loadedAirports = matchingAirports;
    }

    public void airportsOfAltitude(String altitude) {
        ObservableList<Airport> matchingAirports = FXCollections.observableArrayList();

        String regex = altitude + "\\d*";
        Pattern p = Pattern.compile(regex);

        //int intAlt = Integer.parseInt(altitude);
        for (int i = 0; i < loadedAirports.size(); i++) {
            Matcher m = p.matcher(Double.toString(loadedAirports.get(i).getAltitude()));
            if (m.find()) {
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