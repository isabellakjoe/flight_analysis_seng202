package seng202.group8.Controller.AddObjectControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import seng202.group8.Controller.MainController;
import seng202.group8.Model.DatabaseMethods.Database;
import seng202.group8.Model.DatabaseMethods.DatabaseSaver;
import seng202.group8.Model.DatabaseMethods.DatabaseSearcher;
import seng202.group8.Model.Objects.Route;
import seng202.group8.Model.Searchers.AirlineSearcher;
import seng202.group8.Model.Searchers.AirportSearcher;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;


/**
 * Created by esa46 on 15/09/16.
 */
public class AddRouteViewController {

    private MainController mainController;

    @FXML
    private GridPane addRouteViewPane;
    @FXML
    public ComboBox addedRouteAirline;
    @FXML
    public ComboBox addedRouteSource;
    @FXML
    public ComboBox addedRouteDestination;
    @FXML
    private TextField addedRouteEquipment;
    @FXML
    private TextField addedRouteStops;
    @FXML
    private CheckBox addedRouteCodeshare;
    @FXML
    private Text addRouteAirlineErrorEmpty;
    @FXML
    private Text addRouteSourceErrorEmpty;
    @FXML
    private Text addRouteDestErrorEmpty;
    @FXML
    private Text addRouteEquipErrorEmpty;
    @FXML
    private Text addRouteStopsErrorEmpty;
    @FXML
    private Text addRouteStopsErrorType;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void cancelAddedRoute(ActionEvent e) {
        clearRouteErrors();
        addedRouteAirline.setValue(null);
        addedRouteSource.setValue(null);
        addedRouteDestination.setValue(null);
        addedRouteEquipment.clear();
        addedRouteStops.clear();
        addedRouteCodeshare.setSelected(false);
        mainController.resetView();
        mainController.tableView.setVisible(true);

    }

    @FXML
    private boolean addRouteError(List<String> input) {

        boolean filled = false;
        int size = input.size();


        int count = 0;
        for (int i = 0; i < size; i++) {
            String current = input.get(i);

            if (current == null || current.equals("")) {

                switch (i) {
                    case 0:
                        addRouteStopsErrorEmpty.setVisible(true);
                        break;
                    case 1:
                        addRouteEquipErrorEmpty.setVisible(true);
                        break;
                    case 2:
                        addRouteAirlineErrorEmpty.setVisible(true);
                        break;
                    case 3:
                        addRouteSourceErrorEmpty.setVisible(true);
                        break;
                    case 4:
                        addRouteDestErrorEmpty.setVisible(true);
                        break;
                }
            } else {
                count += 1;
            }

            if ((i == 0) && !(current.equals(""))) {

                try {
                    Integer.parseInt(input.get(i));
                    count += 1;

                } catch (NumberFormatException e) {
                    addRouteStopsErrorType.setVisible(true);
                }
            }
        }


        if (count == 6) {
            filled = true;
        }
        return filled;

    }

    @FXML
    private void clearRouteErrors() {

        addRouteStopsErrorEmpty.setVisible(false);
        addRouteEquipErrorEmpty.setVisible(false);
        addRouteAirlineErrorEmpty.setVisible(false);
        addRouteSourceErrorEmpty.setVisible(false);
        addRouteDestErrorEmpty.setVisible(false);
        addRouteStopsErrorType.setVisible(false);

    }

    @FXML
    private void saveAddedRoute(ActionEvent e) {
        clearRouteErrors();

        Route newRoute = new Route();

        String stops = addedRouteStops.getText();
        String equipment = addedRouteEquipment.getText();
        String airline = (String) addedRouteAirline.getValue();
        String source = (String) addedRouteSource.getValue();
        String destination = (String) addedRouteDestination.getValue();

        List<String> routeDataList = Arrays.asList(stops, equipment, airline, source, destination);
        System.out.println(routeDataList);
        boolean noErrors = addRouteError(routeDataList);

        if (noErrors) {
            newRoute.setStops(Integer.parseInt(stops));
            newRoute.setEquipment(equipment);
            newRoute.setAirlineName(airline);
            newRoute.setSourceAirportName(source);
            newRoute.setDestinationAirportName(destination);

            AirportSearcher sourceSearcher = new AirportSearcher(mainController.getCurrentlyLoadedAirports());
            sourceSearcher.airportsOfName(newRoute.getSourceAirportName());

            AirportSearcher destinationSearcher = new AirportSearcher(mainController.getCurrentlyLoadedAirports());
            destinationSearcher.airportsOfName(newRoute.getDestinationAirportName());

            AirlineSearcher airlineSearcher = new AirlineSearcher(mainController.getCurrentlyLoadedAirlines());
            airlineSearcher.airlinesOfName(newRoute.getAirlineName());

            newRoute.setSourceAirport(sourceSearcher.getLoadedAirports().get(0));
            newRoute.getSourceAirport().setNumRoutes(newRoute.getSourceAirport().getNumRoutes() + 1);
            newRoute.setDestinationAirport(destinationSearcher.getLoadedAirports().get(0));
            newRoute.getDestinationAirport().setNumRoutes(newRoute.getDestinationAirport().getNumRoutes() + 1);
            newRoute.setAirline(airlineSearcher.getLoadedAirlines().get(0));

            ObservableList<Route> routes = FXCollections.observableArrayList();
            routes.add(newRoute);

            //Add the new airport to the database here
            Database db = new Database();
            DatabaseSaver dbSave = new DatabaseSaver();
            DatabaseSearcher dbSearch = new DatabaseSearcher();
            Connection connSave = db.connect();
            Connection connSearch = db.connect();
            mainController.setRouteIds(dbSave.saveRouteWithID(connSave, routes, mainController.getRouteIds()));
            mainController.putInRouteHashMap(routes.get(0));
            db.disconnect(connSave);

            mainController.addToCurrentlyLoadedRoutes(newRoute);
            mainController.routeTable.setItems(mainController.getCurrentlyLoadedRoutes());
            mainController.resetView();
            mainController.setRouteComboBoxes();
            mainController.tableView.setVisible(true);
        }
    }

    public void makeInvisible() {
        addRouteViewPane.setVisible(false);
    }

    public void makeVisible() {addRouteViewPane.setVisible(true);}
}