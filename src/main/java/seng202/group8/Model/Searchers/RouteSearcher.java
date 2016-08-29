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

    public void routesOfSource(String sourceAirport){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getSourceAirportName().equalsIgnoreCase(sourceAirport)){
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

    public void routesOfStops(String stops){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        int intStops = parseInt(stops);
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).getStops() == intStops){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

    public void routesOfCodeshare(String codeshareStatus){
        ObservableList<Route> matchingRoutes = FXCollections.observableArrayList();
        boolean isCodeshare = codeshareStatus.equals("Y");
        for (int i = 0; i < loadedRoutes.size(); i++){
            if (loadedRoutes.get(i).isCodeshare() == isCodeshare){
                matchingRoutes.add(loadedRoutes.get(i));
            }
        }
        loadedRoutes = matchingRoutes;
    }

}
