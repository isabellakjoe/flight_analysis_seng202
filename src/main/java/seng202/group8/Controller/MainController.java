package seng202.group8.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group8.Controller.AddObjectControllers.AddAirlineViewController;
import seng202.group8.Controller.AddObjectControllers.AddAirportViewController;
import seng202.group8.Controller.AddObjectControllers.AddRouteViewController;
import seng202.group8.Controller.EditObjectControllers.EditAirlineViewController;
import seng202.group8.Controller.EditObjectControllers.EditAirportViewController;
import seng202.group8.Controller.EditObjectControllers.EditRouteViewController;
import seng202.group8.Controller.SearchObjectControllers.SearchAirlineViewController;
import seng202.group8.Controller.SearchObjectControllers.SearchAirportViewController;
import seng202.group8.Controller.SearchObjectControllers.SearchRouteViewController;
import seng202.group8.Model.DatabaseMethods.*;
import seng202.group8.Model.Objects.*;
import seng202.group8.Model.Parsers.FileLoader;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.util.*;

/**
 * Created by esa46 on 19/08/16.
 */

public class MainController implements Initializable {

    //These lists are used to display the currently loaded objects
    private static ObservableList<Airline> currentlyLoadedAirlines = FXCollections.observableArrayList();
    private static ObservableList<Airport> currentlyLoadedAirports = FXCollections.observableArrayList();
    private static ObservableList<Route> currentlyLoadedRoutes = FXCollections.observableArrayList();

    private Data loadedData = null;
    @FXML
    public TableView<Airline> airlineTable;
    @FXML
    public TableView<Airport> airportTable;
    @FXML
    public TableView<Route> routeTable;
    @FXML
    public Pane tableView;
    //These lists are used to hold the information about currently loaded objects.
    private static HashMap<String, Airline> airlineHashMap = new HashMap<String, Airline>();
    private static HashMap<String, Airport> airportHashMap = new HashMap<String, Airport>();
    private static HashMap<Integer, Route> routeHashMap = new HashMap<Integer, Route>();
    //This integer here is currently to keep track of route id's
    private int routeIds = 0;
    @FXML
    private FlightViewController flightViewController;
    @FXML
    private MapViewController mapViewController;
    @FXML
    private AddAirlineViewController addAirlineViewController;
    @FXML
    private AddAirportViewController addAirportViewController;
    @FXML
    private AddRouteViewController addRouteViewController;
    @FXML
    private EditAirlineViewController editAirlineViewController;
    @FXML
    private EditAirportViewController editAirportViewController;
    @FXML
    private EditRouteViewController editRouteViewController;
    @FXML
    private SearchRouteViewController searchRouteViewController;
    @FXML
    private SearchAirlineViewController searchAirlineViewController;
    @FXML
    private SearchAirportViewController searchAirportViewController;
    @FXML
    private TableColumn<Airline, String> airlineID;
    @FXML
    private TableColumn<Airline, String> airlineName;
    @FXML
    private TableColumn<Airline, String> alias;
    @FXML
    private TableColumn<Airline, String> IATA;
    @FXML
    private TableColumn<Airline, String> ICAO;
    @FXML
    private TableColumn<Airline, String> callsign;
    @FXML
    private TableColumn<Airline, String> country;
    @FXML
    private TableColumn<Airline, String> active;
    @FXML
    private TableColumn<Airport, String> airportID;
    @FXML
    private TableColumn<Airport, String> airportName;
    @FXML
    private TableColumn<Airport, String> city;
    @FXML
    private TableColumn<Airport, String> airportCountry;
    @FXML
    private TableColumn<Airport, String> FAA;
    @FXML
    private TableColumn<Airport, String> airportIATA;
    @FXML
    private TableColumn<Airport, String> airportICAO;
    @FXML
    private TableColumn<Airport, String> latitude;
    @FXML
    private TableColumn<Airport, String> longitude;
    @FXML
    private TableColumn<Airport, String> altitude;
    @FXML
    private TableColumn<Airport, String> timezone;
    @FXML
    private TableColumn<Airport, String> DST;
    @FXML
    private TableColumn<Route, String> routeAirlineName;
    @FXML
    private TableColumn<Route, String> source;
    @FXML
    private TableColumn<Route, String> destination;
    @FXML
    private TableColumn<Route, String> codeshare;
    @FXML
    private TableColumn<Route, String> stops;
    @FXML
    private TableColumn<Route, String> equipment;
    @FXML
    private MenuItem getDistanceMenu;
    @FXML
    private ContextMenu distanceMenu;

    public static ObservableList<Airline> getCurrentlyLoadedAirlines() {
        return currentlyLoadedAirlines;
    }

    public static void addToCurrentlyLoadedAirlines(Airline airline) {
        MainController.currentlyLoadedAirlines.add(airline);
    }

    public static ObservableList<Airport> getCurrentlyLoadedAirports() {
        return currentlyLoadedAirports;
    }

    public static void addToCurrentlyLoadedAirports(Airport airport) {
        MainController.currentlyLoadedAirports.add(airport);
    }

    public static ObservableList<Route> getCurrentlyLoadedRoutes() {
        return currentlyLoadedRoutes;
    }

    public static void addToCurrentlyLoadedRoutes(Route route) {
        MainController.currentlyLoadedRoutes.add(route);
    }

    public int getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(int routeIds) {
        this.routeIds = routeIds;
    }

    public void putInRouteHashMap(Route route) {
        this.routeHashMap.put(this.getRouteIds(), route);
    }

    public HashMap<String, Airline> getAirlineHashMap() {return airlineHashMap;}

    public HashMap<String, Airport> getAirportHashMap() {return airportHashMap;}

    public void fileSave(ActionEvent e){
        //PLEASE DON'T DELETE ME!'
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save loaded data");
//        File file = fileChooser.showSaveDialog(new Stage());
//        if (file != null){
//            //Check user wants to overwrite
//        }
//        try{
//            FileOutputStream fileStream = new FileOutputStream(file);
//            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
//            Data data = new Data(5);
//            //System.out.println(data.getAirlines().size());
//            //System.out.println(data.getAirports().size());
//            objectStream.writeObject(data);
//            objectStream.flush();
//            objectStream.close();
//            fileStream.close();
//            }
//
//        catch(FileNotFoundException ex) {
//
//        }
//        catch(IOException ex){
//
//        }
    }
//
    public void fileOpen(ActionEvent e){
        //PLEASE DON'T DELETE!
//        try {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Open datafile"); //Text in the window header
//            File file = fileChooser.showOpenDialog(new Stage());
//            if (file != null) {
//                FileInputStream filestream = new FileInputStream(file);
//                ObjectInputStream objectStream = new ObjectInputStream(filestream);
//                Object openedData = objectStream.readObject();
//                if (openedData == null){
//                    System.out.println("File null");
//                }
//                if (!(openedData instanceof Data)){
//                    System.out.println("Malformed data");
//                    return;
//                }
//                loadedData = (Data) openedData;
//
//                //System.out.println(((Data) openedData).getAirlines().size());
//                System.out.println(loadedData.getNum());
//                //currentlyLoadedAirlines = FXCollections.observableArrayList(((Data) openedData).getAirlines());
//                //currentlyLoadedAirports = FXCollections.observableArrayList(((Data) openedData).getAirports());
//                //currentlyLoadedRoutes = FXCollections.observableArrayList(((Data) openedData).getRoutes());
//
//                System.out.println("Gothere");
//                resetTables();
//            }
//         }
//        catch (FileNotFoundException ex) {
//            System.out.println("FILE NOT FOUND");
//        }
//        catch(java.io.IOException ex){
//            System.out.println("Oops, IO exception");
//        }
//        catch(ClassNotFoundException ex){
//            System.out.println("Double Oops");
//        }
    }

    /* Method to open up a file chooser for the user to select the Airport Data file  with error handling*/
    public void addAirportData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airport datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airport data file
                //ObservableList<Airport> airports = load.buildAirports();
                //Create the database objects and connections here
                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                AirportDatabaseLoader adl = new AirportDatabaseLoader();
                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();
                //Save all of the loaded files to the database here
                dbsave.saveAirports(connOne, load.buildAirports());
                dbOne.disconnect(connOne);
                //Load all of the content from the database here, and add all airports to the hashmap.
                ObservableList<Airport> airports = adl.loadAirport(connTwo, airportHashMap);
                dbTwo.disconnect(connTwo);
                currentlyLoadedAirports = airports;
                airportTable.setItems(airports);
                setAirportComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /* Method to open up a file chooser for the user to select the Airline Data file with error handling */
    public void addAirlineData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airline datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airline data file
                //ObservableList<Airline> airlines = load.buildAirlines();

                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                AirlineDatabaseLoader adl = new AirlineDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();

                dbsave.saveAirlines(connOne, load.buildAirlines());
                dbOne.disconnect(connOne);

                ObservableList<Airline> airlines = adl.loadAirlines(connTwo, airlineHashMap);
                dbTwo.disconnect(connTwo);

                currentlyLoadedAirlines = airlines;
                airlineTable.setItems(airlines);
                setAirlineComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }

    }

    /* Method to open up a file chooser for the user to select the Route Data file with error handling */
    public void addRouteData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Route datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the route data file
                //ObservableList<Route> routes = load.buildRoutes();

                Database dbOne = new Database();
                Database dbTwo = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                RouteDatabaseLoader rdl = new RouteDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbTwo.connect();

                if (routeIds == 0) {
                    routeIds = dbsave.saveRoutes(connOne, load.buildRoutes());
                } else {
                    routeIds = dbsave.saveRouteWithID(connOne, load.buildRoutes(), routeIds);
                }
                dbOne.disconnect(connOne);
                ObservableList<Route> routes = rdl.loadRoutes(connTwo, routeHashMap, airlineHashMap, airportHashMap);
                dbTwo.disconnect(connTwo);

                currentlyLoadedRoutes = routes;
                routeTable.setItems(routes);
                setRouteComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }

    }

    /*Fills comboboxes used for searching airports and creating routes*/
    public void setAirportComboBoxes() {
        HashSet<String> countries = new HashSet<String>();
        HashSet<String> names = new HashSet<String>();

        for (int i = 0; i < currentlyLoadedAirports.size(); i++) {
            countries.add(currentlyLoadedAirports.get(i).getCountry());
            names.add(currentlyLoadedAirports.get(i).getName());
        }
        List sortedCountries = new ArrayList(countries);
        List sortedNames = new ArrayList(names);

        Collections.sort(sortedNames);
        Collections.sort(sortedCountries);
        sortedCountries.add(0, "ALL COUNTRIES");
        searchAirportViewController.setCountryCombobox(sortedCountries);

        addRouteViewController.addedRouteSource.getItems().clear();
        addRouteViewController.addedRouteSource.getItems().addAll(sortedNames);
        addRouteViewController.addedRouteDestination.getItems().clear();
        addRouteViewController.addedRouteDestination.getItems().addAll(sortedNames);
    }

    /*Fills comboboxes used for searching airlines and creating routes*/
    public void setAirlineComboBoxes() {
        ArrayList<String> activeStatuses = new ArrayList<String>();
        activeStatuses.add("ACTIVE OR INACTIVE");
        activeStatuses.add("Active");
        activeStatuses.add("Inactive");
        searchAirlineViewController.setActiveCombobox(activeStatuses);

        HashSet<String> names = new HashSet<String>();
        HashSet<String> countries = new HashSet<String>();
        for (int i = 0; i < currentlyLoadedAirlines.size(); i++) {
            countries.add(currentlyLoadedAirlines.get(i).getCountry());
            names.add(currentlyLoadedAirlines.get(i).getName());
        }

        List sortedCountries = new ArrayList(countries);
        List sortedAirlines = new ArrayList(names);

        Collections.sort(sortedAirlines);
        Collections.sort(sortedCountries);

        sortedCountries.add(0, "ALL COUNTRIES");

        searchAirlineViewController.setCountryCombobox(sortedCountries);

        addRouteViewController.addedRouteAirline.getItems().clear();
        addRouteViewController.addedRouteAirline.getItems().addAll(sortedAirlines);
    }

    /*Fills comboboxes used for searching routes*/
    public void setRouteComboBoxes() {
        ArrayList<String> codeshareStatuses = new ArrayList<String>();
        codeshareStatuses.add("ALL");
        codeshareStatuses.add("Codeshare");
        codeshareStatuses.add("Non Codeshare");
        searchRouteViewController.setCodeshareCombobox(codeshareStatuses);

        HashSet<String> sources = new HashSet<String>();
        HashSet<String> destinations = new HashSet<String>();
        HashSet<String> equipment = new HashSet<String>();

        int stops = 0;
        for (int i = 0; i < currentlyLoadedRoutes.size(); i++) {
            if (currentlyLoadedRoutes.get(i).getSourceAirport().getIATA() != null) {
                sources.add(currentlyLoadedRoutes.get(i).getSourceAirport().getIATA());
            } else {
                sources.add(currentlyLoadedRoutes.get(i).getSourceAirport().getICAO());
            }
            if (currentlyLoadedRoutes.get(i).getDestinationAirport().getIATA() != null) {
                destinations.add(currentlyLoadedRoutes.get(i).getDestinationAirport().getIATA());
            } else {
                destinations.add(currentlyLoadedRoutes.get(i).getDestinationAirport().getICAO());
            }
            equipment.add(currentlyLoadedRoutes.get(i).getEquipment());
            if (currentlyLoadedRoutes.get(i).getStops() > stops) {
                stops = currentlyLoadedRoutes.get(i).getStops();
            }
        }

        ArrayList<String> stopsList = new ArrayList<String>();
        stopsList.add("ALL");

        for (int i = 0; i <= stops; i++) {
            stopsList.add(Integer.toString(i));
        }

        List sortedSources = new ArrayList(sources);
        List sortedDestinations = new ArrayList(destinations);
        List sortedEquipment = new ArrayList(equipment);

        Collections.sort(sortedSources);
        Collections.sort(sortedDestinations);
        Collections.sort(sortedEquipment);


        sortedSources.add(0, "ALL");
        sortedDestinations.add(0, "ALL");
        sortedEquipment.add(0, "ALL");

        searchRouteViewController.setSourceCombobox(sortedSources);
        searchRouteViewController.setDestinationCombobox(sortedDestinations);
        searchRouteViewController.setEquipmentCombobox(sortedEquipment);
        searchRouteViewController.setStopoverCombobox(stopsList);
    }

    /* Opens a file chooser for the user to select the Flight Data file, then loads the data and switches views*/
    public void addFlightData(ActionEvent e) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open flight datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                Flight flight = load.buildFlight();
                flightViewController.setUpFlightView(flight);
                //Swap panes from raw data to the flight viewer
                resetView();

                if (flightViewController.getIsValid()) {
                    flightViewController.makeVisible();
                    flightViewController.isValid = false;

                } else {
                    backToTableView(e);
                    JOptionPane jp = new JOptionPane();
                    jp.setSize(600, 600);
                    jp.showMessageDialog(null, "Data you are trying to add is invalid", "Error Message", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }
    }

    private void filterAirlinesByName(ActionEvent e) {
        Comparator<Airline> airlineNameComparator = new Comparator<Airline>() {
            public int compare(Airline o1, Airline o2) {
                String airline1 = o1.getName();
                String airline2 = o2.getName();

                return airline1.compareTo(airline2);
            }
        };
        ArrayList<Airline> sortedAirlines = new ArrayList<Airline>();
        Collections.sort(currentlyLoadedAirlines, airlineNameComparator);
        for (Airline str : currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded airlines by country
    Need to display an error message if airlines aren't yet loaded
     */
    @FXML
    private void FilterAirlinesByCountry(ActionEvent e) {

        Comparator<Airline> airlineCountryComparator = new Comparator<Airline>() {
            public int compare(Airline o1, Airline o2) {
                String airline1 = o1.getCountry();
                String airline2 = o2.getCountry();

                return airline1.compareTo(airline2);
            }
        };
        ArrayList<Airline> sortedAirlines = new ArrayList<Airline>();
        Collections.sort(currentlyLoadedAirlines, airlineCountryComparator);
        for (Airline str : currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded airlines by activity
    Need to display an error message if airlines aren't yet loaded
    */
    @FXML
    private void FilterAirlinesByActivity(ActionEvent e) {

        Comparator<Airline> airlineActivityComparator = new Comparator<Airline>() {
            public int compare(Airline o1, Airline o2) {
                boolean airline1 = o1.isActive();
                boolean airline2 = o2.isActive();

                if (airline1 == !airline2) {
                    return 1;
                }
                if (!airline1 == airline2) {
                    return -1;
                }
                return 0;
            }
        };
        ArrayList<Airline> sortedAirlines = new ArrayList<Airline>();
        Collections.sort(currentlyLoadedAirlines, airlineActivityComparator);
        for (Airline str : currentlyLoadedAirlines) {
            sortedAirlines.add(str);
        }
        ObservableList<Airline> sortedObservableAirlines = FXCollections.observableArrayList(sortedAirlines);
        airlineTable.setItems(sortedObservableAirlines);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded airports by country
    Need to display an error message if airports aren't yet loaded
     */
    @FXML
    private void FilterAirportsByCountry(ActionEvent e) {

        Comparator<Airport> airportCountryComparator = new Comparator<Airport>() {
            public int compare(Airport o1, Airport o2) {
                String airport1 = o1.getCountry();
                String airport2 = o2.getCountry();

                return airport1.compareTo(airport2);
            }
        };
        ArrayList<Airport> sortedAirports = new ArrayList<Airport>();
        Collections.sort(currentlyLoadedAirports, airportCountryComparator);
        for (Airport str : currentlyLoadedAirports) {
            sortedAirports.add(str);
        }
        ObservableList<Airport> sortedObservableAirports = FXCollections.observableArrayList(sortedAirports);
        airportTable.setItems(sortedObservableAirports);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by Source Airport
    Need to display an error message if routes aren't yet loaded
     */
    @FXML
    private void FilterRoutesBySourceAirport(ActionEvent e) {

        Comparator<Route> sourceAirportComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                String route1 = o1.getSourceAirportName();
                String route2 = o2.getSourceAirportName();

                return route1.compareTo(route2);
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, sourceAirportComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by Destination Airport
    Need to display an error message if routes aren't yet loaded
     */
    @FXML
    private void FilterRoutesByDestinationAirport(ActionEvent e) {

        Comparator<Route> destinationAirportComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                String route1 = o1.getDestinationAirportName();
                String route2 = o2.getDestinationAirportName();

                return route1.compareTo(route2);
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, destinationAirportComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by number of stops
   Need to display an error message if routes aren't yet loaded
    */
    @FXML
    private void FilterRoutesByStops(ActionEvent e) {

        Comparator<Route> stopsComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                int route1 = o1.getStops();
                int route2 = o2.getStops();

                if (route1 < route2) {
                    return -1;
                } else if (route1 > route2) {
                    return 1;
                }
                return 0;
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, stopsComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }

    /* Method to Filter ALREADY loaded routes by equipment
    Need to display an error message if routes aren't yet loaded
     */
    @FXML
    private void FilterRoutesByEquipment(ActionEvent e) {

        Comparator<Route> equipmentComparator = new Comparator<Route>() {
            public int compare(Route o1, Route o2) {
                String route1 = o1.getEquipment();
                String route2 = o2.getEquipment();

                return route1.compareTo(route2);
            }
        };
        ArrayList<Route> sortedRoutes = new ArrayList<Route>();
        Collections.sort(currentlyLoadedRoutes, equipmentComparator);
        for (Route str : currentlyLoadedRoutes) {
            sortedRoutes.add(str);
        }
        ObservableList<Route> sortedObservableRoutes = FXCollections.observableArrayList(sortedRoutes);
        routeTable.setItems(sortedObservableRoutes);
        resetView();
        tableView.setVisible(true);
    }

    /* Resets and hides most panes, giving the interface a clean slate*/
    public void resetView() {
        tableView.setVisible(false);
        flightViewController.makeInvisible();
        addAirportViewController.makeInvisible();
        addAirlineViewController.makeInvisible();
        addRouteViewController.makeInvisible();
        editRouteViewController.cancelRouteChanges(new ActionEvent());
        editRouteViewController.makeInvisible();
        editAirlineViewController.cancelAirlineChanges(new ActionEvent());
        editAirlineViewController.makeInvisible();
        editAirportViewController.cancelAirportChanges(new ActionEvent());
    }

    /* Clears tables and re-adds currently loaded objects to them*/
    private void resetTables() {
        airportTable.getColumns().clear();
        initAirportTable();
        airportTable.getColumns().addAll(airportName, city, airportCountry);
        airportTable.setItems(currentlyLoadedAirports);

        airlineTable.getColumns().clear();
        initAirlineTable();
        airlineTable.getColumns().addAll(airlineName, alias, country, active);
        airlineTable.setItems(currentlyLoadedAirlines);

        routeTable.getColumns().clear();
        initRouteTable();
        routeTable.getColumns().addAll(routeAirlineName, source, destination);
        routeTable.setItems(currentlyLoadedRoutes);
    }

    /* Switches to raw data table viewing interface*/
    public void backToTableView(ActionEvent e) {
        resetTables();
        airportTable.setVisible(true);
        airlineTable.setVisible(true);
        routeTable.setVisible(true);
        tableView.setVisible(true);
    }

    /*Switches to interface for adding an airport*/
    public void switchToAddAirport(ActionEvent e) {
        resetView();
        addAirportViewController.makeVisible();
    }

    /*Switches to interface for adding an airline*/
    public void switchToAddAirline(ActionEvent e) {
        resetView();
        addAirlineViewController.makeVisible();
    }

    /*Switches to interface for adding a route*/
    public void switchToAddRoute(ActionEvent e) {
        resetView();
        addRouteViewController.makeVisible();
    }

    /*Used during initialisation to set this as main controller of all child controllers*/
    private void setMainControllers(){
        mapViewController.setMainController(this);
        mapViewController.initMap();
        flightViewController.setMainController(this);
        mapViewController.setMainController(this);
        addAirlineViewController.setMainController(this);
        addAirportViewController.setMainController(this);
        addRouteViewController.setMainController(this);
        editAirlineViewController.setMainController(this);
        editAirportViewController.setMainController(this);
        editRouteViewController.setMainController(this);
        searchRouteViewController.setMainController(this);
        searchAirlineViewController.setMainController(this);
        searchAirportViewController.setMainController(this);
    }

    /*Set columns of airline table*/
    private void initAirlineTable(){
        airlineID.setCellValueFactory(new PropertyValueFactory<Airline, String>("airlineID"));
        airlineName.setCellValueFactory(new PropertyValueFactory<Airline, String>("name"));
        alias.setCellValueFactory(new PropertyValueFactory<Airline, String>("alias"));
        IATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        ICAO.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        callsign.setCellValueFactory(new PropertyValueFactory<Airline, String>("callsign"));
        country.setCellValueFactory(new PropertyValueFactory<Airline, String>("country"));
        active.setCellValueFactory(new PropertyValueFactory<Airline, String>("active"));
    }

    /*Set columns of airport table*/
    private void initAirportTable(){
        airportID.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportID"));
        airportName.setCellValueFactory(new PropertyValueFactory<Airport, String>("Name"));
        city.setCellValueFactory(new PropertyValueFactory<Airport, String>("City"));
        airportCountry.setCellValueFactory(new PropertyValueFactory<Airport, String>("Country"));
        FAA.setCellValueFactory(new PropertyValueFactory<Airport, String>("FAA"));
        airportIATA.setCellValueFactory(new PropertyValueFactory<Airport, String>("IATA"));
        airportICAO.setCellValueFactory(new PropertyValueFactory<Airport, String>("ICAO"));
        latitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Latitude"));
        longitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Longitude"));
        altitude.setCellValueFactory(new PropertyValueFactory<Airport, String>("Altitude"));
        timezone.setCellValueFactory(new PropertyValueFactory<Airport, String>("Timezone"));
        DST.setCellValueFactory(new PropertyValueFactory<Airport, String>("DST"));
    }

    /*Set columns of route table*/
    private void initRouteTable(){
        routeAirlineName.setCellValueFactory(new PropertyValueFactory<Route, String>("airlineName"));
        source.setCellValueFactory(new PropertyValueFactory<Route, String>("sourceAirportName"));
        destination.setCellValueFactory(new PropertyValueFactory<Route, String>("destinationAirportName"));
        codeshare.setCellValueFactory(new PropertyValueFactory<Route, String>("codeshareString"));
        stops.setCellValueFactory(new PropertyValueFactory<Route, String>("stops"));
        equipment.setCellValueFactory(new PropertyValueFactory<Route, String>("equipment"));
    }

    //Sets Table Cells in Airline Table Viewer to Airline attributes
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        setMainControllers();
        initAirlineTable();
        initAirportTable();
        initRouteTable();

        // Allows individual cells to be selected as opposed to rows
        //airportTable.getSelectionModel().setCellSelectionEnabled(true);
        airportTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click) {
            //Checks if table is empty then checks for double click
            if (!airportTable.getItems().isEmpty()) {
                if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                    editAirportViewController.setAirportInfo();
                }
            }
            }
        });

        airlineTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click) {
            //Checks if table is empty then checks for double click
            if (!airlineTable.getItems().isEmpty()) {
                if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                    editAirlineViewController.setAirlineInfo();
                }
            }
            }
        });

        routeTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //click event handler for double clicking a table cell.
            public void handle(MouseEvent click) {
            //Checks if table is empty then checks for double click
            if (!routeTable.getItems().isEmpty()) {
                if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                    editRouteViewController.setRouteInfo();
                }
            }
            }
        });

        airportTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        airportTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
               @Override
               public void handle(ContextMenuEvent event) {
                   if(airportTable.getSelectionModel().getSelectedItems().size() != 2){
                       getDistanceMenu.setDisable(true);
                   }else{
                       getDistanceMenu.setDisable(false);
                   }
               }
           }
        );
    }

    @FXML
    public void getDistance(ActionEvent e){
        distanceMenu.hide();
        if(airportTable.getSelectionModel().getSelectedItems().size() == 2){
            Airport airport1 = airportTable.getSelectionModel().getSelectedItems().get(0);
            Airport airport2 = airportTable.getSelectionModel().getSelectedItems().get(1);

            double distance = airport1.calculateDistanceTo(airport2);
            JOptionPane jp = new JOptionPane();
            jp.setSize(700, 600);
            jp.showMessageDialog(null, "From "+airport1.getName()+" to "+airport2.getName() + "\nThe distance is "+distance+" km.", "Calculated Distance", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @FXML
    public void helpAdd(ActionEvent e){
        JOptionPane jp = new JOptionPane();
       // ImageIcon icon = new ImageIcon()
        jp.showMessageDialog(null, "Below the Search and Reset buttons is Add New \n" +
                "Route/Airport/Airline, (Depending on which tab you are in) \n" +
                "which allows for adding a single object to the table. \n\n" +
                "Filling out the fields and clicking save will add the \n" +
                "created object to its corresponding table.", "How to Add Data", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    public void helpEdit(ActionEvent e){
        JOptionPane jp = new JOptionPane();
        jp.showMessageDialog(null, "The objects in the data tables can be double \n" +
                "clicked to display a more detailed pane on that object. \n" +
                "Inside this detailed pane, you can choose to edit an \n" +
                "existing Object by clicking the Edit button, which will allow editing. ", "How to Edit Data", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    public void helpFilter(ActionEvent e){
        JOptionPane jp = new JOptionPane();
        jp.showMessageDialog(null, "The data can be filtered using the Filter dropdown\n" +
                "boxes at the top right of the tables, and will filter \n" +
                "data based on the selected parameter. ", "How to Filter Data", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    public void helpDistance(ActionEvent e){
        JOptionPane jp = new JOptionPane();
        jp.showMessageDialog(null, "If you hold CTRL to select two Airport objects \n" +
                "from the table, then left click, you can choose \n" +
                "to get the distance between those two Airports.", "How to Get the Distance between two Airports", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    public void helpSearch(ActionEvent e){
        JOptionPane jp = new JOptionPane();
        jp.showMessageDialog(null, "On the left hand side of the program is a search pane, \n" +
                "with three tabs for Route, Airline and Airport searches. \n\n" +
                "Here you can input search parameters for the different tables, \n" +
                "and an Advanced Pane with more search options is available by \n" +
                "clicking the Advanced button. Searching begins once Search \n" +
                "is clicked, and Reset will reset the tables and the search inputs. \n" +
                "If nothing satisfies the search parameters, no data will \n\n" +
                "be displayed in the table. Also, if spelling is incorrect, \n" +
                "the desired data will not be displayed until the spelling is corrected.", "How to Get the Distance between two Airports", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    public void helpLoad(ActionEvent e){
        JOptionPane jp = new JOptionPane();
        jp.showMessageDialog(null, "Simply load in data using the \"Load\" menu at the top left \n" +
                "corner of the program and choose the type of data file\n" +
                " you wish to add. \n\n" +
                "Once added, the data will be displayed in either one of the three\n" +
                " tables if Add Route, Airline or Airport was chosen, otherwise\n" +
                " the Fight Pane with be displayed showing added flight data. \n\n" +
                "If the file contains an object that already exists in the table, \n" +
                "the entire file will not be added. ", "How to Get the Distance between two Airports", JOptionPane.INFORMATION_MESSAGE);
    }
}