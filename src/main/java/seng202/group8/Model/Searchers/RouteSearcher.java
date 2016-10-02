package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Erika on 29-Aug-16.
 */
public class RouteSearcher {

    private ObservableList<Route> loadedRoutes = FXCollections.observableArrayList();

    /**
     * Method to overload object to pass through list of current routes
     *
     * @param loadedRoutes an observable list of routes.
     */
    public RouteSearcher(ObservableList<Route> loadedRoutes) {
        this.loadedRoutes = loadedRoutes;
    }

    /**
     * Return the currently stored list of routes
     *
     * @return an observable list of routes
     */
    public ObservableList<Route> getLoadedRoutes() {
        return loadedRoutes;
    }

    /**
     * A method to search through routes based off of airline name
     *
     * @param airline a string of an airline name to be matched
     */
    public void routesOfAirline(String airline) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();

        String regex = airline + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedRoutes.size(); i++) {
            Matcher m = p.matcher(loadedRoutes.get(i).getAirline().getName());
            if (m.find()) {
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    /**
     * A method to search through routes based off of airline id's
     *
     * @param airlineID an integer of an airline id to be matched
     */
    public void routesOfAirlineID(int airlineID) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();

        String regex = Integer.toString(airlineID) + "\\d*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedRoutes.size(); i++) {
            Matcher m = p.matcher(Integer.toString(loadedRoutes.get(i).getAirline().getAirlineID()));
            if (m.find()) {
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    /**
     * A method to search through routes based off of source airport name
     *
     * @param sourceAirportName a string of a source airport to be matched.
     */
    public void routesOfSource(String sourceAirportName) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();

        String regex = sourceAirportName + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedRoutes.size(); i++) {
            if (loadedRoutes.get(i).getSourceAirport().getName() != null) {
                Matcher m = p.matcher(loadedRoutes.get(i).getSourceAirport().getName());
                if (m.find()) {
                    matchingRoutes.add(loadedRoutes.get(i));
                }
            }
        }
        loadedRoutes = matchingRoutes;
    }

    /**
     * A method to search through routes based off of source airport id
     *
     * @param sourceAirportID a integer of a source airport to be matched
     */
    public void routesOfSourceID(int sourceAirportID) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();

        String regex = sourceAirportID + "\\d*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedRoutes.size(); i++) {
            Matcher m = p.matcher(Integer.toString(loadedRoutes.get(i).getSourceAirport().getAirportID()));
            if (m.find()) {
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    /**
     * A method to search through routes based off of destination airport name
     *
     * @param destinationAirport a string of a destination airport name to be matched
     */
    public void routesOfDestination(String destinationAirport) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();

        String regex = destinationAirport + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedRoutes.size(); i++) {
            if (loadedRoutes.get(i).getDestinationAirport().getName() != null) {
                Matcher m = p.matcher(loadedRoutes.get(i).getDestinationAirport().getName());
                if (m.find()) {
                    matchingRoutes.add(loadedRoutes.get(i));
                }
            }
        }
        loadedRoutes = matchingRoutes;
    }

    /**
     * A method to search through routes based off of destination airport id
     *
     * @param destinationAirportID a integer of a destination airport to be matched
     */
    public void routesOfDestinationID(int destinationAirportID) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();

        String regex = destinationAirportID + "\\d*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedRoutes.size(); i++) {
            Matcher m = p.matcher(Integer.toString(loadedRoutes.get(i).getDestinationAirport().getAirportID()));
            if (m.find()) {
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    /**
     * @param stops
     */
    public void routesOfStops(int stops) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++) {
            if (loadedRoutes.get(i).getStops() == stops) {
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfCodeshare(String codeshareStatus) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        boolean isCodeshare = codeshareStatus.equals("Codeshare");
        for (int i = 0; i < loadedRoutes.size(); i++) {
            if (loadedRoutes.get(i).isCodeshare() == isCodeshare) {
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfEquipment(String equipment) {
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();

        String regex = equipment + "\\w*";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < loadedRoutes.size(); i++) {
            Matcher m = p.matcher(loadedRoutes.get(i).getEquipment());
            if (m.find()) {
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

}
