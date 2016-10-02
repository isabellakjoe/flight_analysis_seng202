package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Airline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Erika on 29-Aug-16.
 */
public class AirlineSearcher {

    private ObservableList<Airline> loadedAirlines = FXCollections.observableArrayList();

    /**
     * Set the current list of loaded airlines within the object
     * @param loadedAirlines a observable list of airlines
     */
    public AirlineSearcher(ObservableList<Airline> loadedAirlines) {
        this.loadedAirlines = loadedAirlines;
    }

    /**
     * Get the list of airlines from the instance of the object
     * @return an observable list of airlines
     */
    public ObservableList<Airline> getLoadedAirlines() {
        return loadedAirlines;
    }

    /**
     * A method to search through the list of airlines based off of ID integer
     * @param airlineID a string representation of the airline id
     */
    public void airlinesOfID(String airlineID) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();

        String regex = airlineID + "\\w*";
        Pattern p = Pattern.compile(regex);

        //int intID = Integer.parseInt(airlineID);
        for (int i = 0; i < loadedAirlines.size(); i++) {
            Matcher m = p.matcher(Integer.toString(loadedAirlines.get(i).getAirlineID()));
            if (m.find()) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    /**
     * A method to search through a list of airlines by an airline name
     * @param name a string containing a name which is to be matched
     */
    public void airlinesOfName(String name) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();

        String regexString = name + "\\w*";
        Pattern p = Pattern.compile(regexString);

        for (int i = 0; i < loadedAirlines.size(); i++) {
            Matcher m = p.matcher(loadedAirlines.get(i).getName());
            if (m.find()) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    /**
     * A method to search through airlines by alias
     * @param alias a string containing the alias to be matched
     */
    public void airlinesOfAlias(String alias) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();

        String regex = alias + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirlines.size(); i++) {
            Matcher m = p.matcher(loadedAirlines.get(i).getAlias());
            if (m.find()) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    /**
     * A method to search through airlines based off of iata
     * @param IATA a string containing the iata to be matched
     */
    public void airlinesOfIATA(String IATA) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();

        String regex = IATA + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirlines.size(); i++) {
            Matcher m = p.matcher(loadedAirlines.get(i).getIATA());
            if (m.find()) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    /**
     * A method to search through airlines based off of icao
     * @param ICAO a string containing the icao to be matched
     */
    public void airlinesOfICAO(String ICAO) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();

        String regex = ICAO + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirlines.size(); i++) {
            Matcher m = p.matcher(loadedAirlines.get(i).getICAO());
            if (m.find()) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    /**
     * A method to search through airlines based off of callsign
     * @param callsign a string containg the callsign to be matched
     */
    public void airlinesOfCallsign(String callsign) {
        ObservableList<Airline> matchingAirlines = FXCollections.observableArrayList();

        String regex = callsign + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedAirlines.size(); i++) {
            Matcher m = p.matcher(loadedAirlines.get(i).getCallsign());
            if (m.find()) {
                matchingAirlines.add(loadedAirlines.get(i));
            }
        }
        loadedAirlines = matchingAirlines;
    }

    /**
     * A method to search through airlines based off of country
     * @param country a string containing the country to be matched
     */
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
        System.out.println(activeStatus);
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
