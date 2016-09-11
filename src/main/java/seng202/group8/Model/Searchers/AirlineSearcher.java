package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import seng202.group8.Model.Objects.Airline;

/**
 * Created by Erika on 29-Aug-16.
 */
public class AirlineSearcher {

    private ObservableList<Airline> loadedAirlines = FXCollections.observableArrayList();

    public AirlineSearcher(ObservableList<Airline> loadedAirlines) {
        this.loadedAirlines = loadedAirlines;
    }

    public ObservableList<Airline> getLoadedAirlines() {
        return loadedAirlines;
    }

    public void airlinesOfID(String airlineID) {
            ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
            int intID = Integer.parseInt(airlineID);
            for (int i = 0; i < loadedAirlines.size(); i++) {
                if (loadedAirlines.get(i).getAirlineID() == intID) {
                    matchingAirlines.add(loadedAirlines.get(i));
                }
            }
            loadedAirlines = matchingAirlines;


    }

    public void airlinesOfName(String name) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirlines.size(); i++) {
            if (loadedAirlines.get(i).getName().equalsIgnoreCase(name)) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfAlias(String alias) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirlines.size(); i++) {
            if (loadedAirlines.get(i).getAlias().equalsIgnoreCase(alias)) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfIATA(String IATA) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirlines.size(); i++) {
            if (loadedAirlines.get(i).getIATA().equalsIgnoreCase(IATA)) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfICAO(String ICAO) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirlines.size(); i++) {
            if (loadedAirlines.get(i).getICAO().equalsIgnoreCase(ICAO)) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfCallsign(String callsign) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirlines.size(); i++) {
            if (loadedAirlines.get(i).getCallsign().equalsIgnoreCase(callsign)) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfCountry(String country) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
        for (int i = 0; i < loadedAirlines.size(); i++) {
            if (loadedAirlines.get(i).getCountry().equalsIgnoreCase(country)) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    public void airlinesOfActiveStatus(String activeStatus){
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();
        boolean isActive = activeStatus.equals("Active");
        for (int i = 0; i < loadedAirlines.size(); i++){
            if (loadedAirlines.get(i).isActive() == isActive){
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }



}
