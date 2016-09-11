package seng202.group8.Model.Searchers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group8.Model.Objects.Route;

import static java.lang.Integer.parseInt;

/**
 * Created by Erika on 29-Aug-16.
 */
public class RouteSearcher {

    private ObservableList<Route> loadedRoutes = FXCollections.observableArrayList();

    public RouteSearcher(ObservableList<Route> loadedRoutes) {
        this.loadedRoutes = loadedRoutes;
    }

    public ObservableList<Route> getLoadedRoutes() {
        return loadedRoutes;
    }

    public void routesOfAirline(String airline){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getAirlineName().equals(airline)){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }
    public void routesOfAirlineID(int airlineID){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getAirline().equals(airlineID)){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfSource(String sourceAirportName){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getSourceAirportName().equalsIgnoreCase(sourceAirportName)){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }
    public void routesOfSourceID(int sourceAirportID){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getSourceAirport().equals(sourceAirportID)){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfDestination(String destinationAirport){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getDestinationAirportName().equalsIgnoreCase(destinationAirport)){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfDestinationID(int destinationAirportID){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getDestinationAirport().equals(destinationAirportID)){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfStops(int stops){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getStops() == stops){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfCodeshare(String codeshareStatus){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        boolean isCodeshare = codeshareStatus.equals("Codeshare");
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).isCodeshare() == isCodeshare){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfEquipment(String equipment){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getEquipment().equalsIgnoreCase(equipment)){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

}
