package seng202.group8.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.group8.Controller.AddObjectControllers.AddAirlineViewController;
import seng202.group8.Controller.AddObjectControllers.AddAirportViewController;
import seng202.group8.Controller.AddObjectControllers.AddRouteViewController;
import seng202.group8.Controller.EditObjectControllers.EditAirlineViewController;
import seng202.group8.Controller.EditObjectControllers.EditAirportViewController;
import seng202.group8.Controller.EditObjectControllers.EditRouteViewController;
import seng202.group8.Controller.SearchObjectControllers.SearchAirlineViewController;
import seng202.group8.Controller.SearchObjectControllers.SearchAirportViewController;
import seng202.group8.Controller.SearchObjectControllers.SearchRouteViewController;
import seng202.group8.Main;
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
    @FXML
    private MenuItem helpAddButton;
    @FXML
    private MenuItem helpEditButton;
    @FXML
    private MenuItem helpFilterButton;
    @FXML
    private MenuItem helpDistanceButton;
    @FXML
    private MenuItem helpLoadButton;
    @FXML
    private MenuItem helpSearchButton;

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

    public static HashMap<String, Airline> getAirlineHashMap() {return airlineHashMap;}

    public static HashMap<String, Airport> getAirportHashMap() {return airportHashMap;}

    public static HashMap<Integer, Route> getRouteHashMap() {return routeHashMap;}

    public void showAirlines() {
        try {
            airlineTable.setItems(currentlyLoadedAirlines);
            setAirlineComboBoxes();
        } catch (NullPointerException np) {
            System.out.println("Error Loading Airlines");
        }
    }

    public void showAirports() {
        try {
            airportTable.setItems(currentlyLoadedAirports);
            setAirportComboBoxes();
        } catch (NullPointerException np) {
            System.out.println("Error Loading Airports");
        }
    }

    public void showRoutes() {
        try {
            routeTable.setItems(currentlyLoadedRoutes);
            setRouteComboBoxes();
        } catch (NullPointerException np) {
            System.out.println("Error Loading Routes");
        }
    }

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

    /** Method to implement Airport data into the application with error handling
     *
     * @param file: Contains Airport data
     */
    public void airportFile(File file) {
        try {
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airport data file
                //ObservableList<Airport> airports = load.buildAirports();
                //Create the database objects and connections here
                Database dbOne = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                AirportDatabaseLoader adl = new AirportDatabaseLoader();
                Connection connOne = dbOne.connect();
                Connection connTwo = dbOne.connect();
                //Save all of the loaded files to the database here
                dbsave.saveAirports(connOne, load.buildAirports());
                dbOne.disconnect(connOne);
                //Load all of the content from the database here, and add all airports to the hashmap.
                ObservableList<Airport> airports = adl.loadAirport(connTwo, airportHashMap);
                dbOne.disconnect(connTwo);
                currentlyLoadedAirports = airports;
                airportTable.setItems(airports);
                setAirportComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        }catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /** Method to open up a file chooser for the user to select the Airport Data file  with error handling
     */
    public void addAirportData(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Airport datafile"); //Text in the window header
        File file = fileChooser.showOpenDialog(new Stage());
        airportFile(file);
    }


    /** Method to implement Airline data into the application with error handling
     *
     * @param file: Contains Airline data
     */
    public void airlineFile(File file) {
        try {
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airline data file
                //ObservableList<Airline> airlines = load.buildAirlines();

                Database dbOne = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                AirlineDatabaseLoader adl = new AirlineDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbOne.connect();

                dbsave.saveAirlines(connOne, load.buildAirlines());
                dbOne.disconnect(connOne);

                ObservableList<Airline> airlines = adl.loadAirlines(connTwo, airlineHashMap);
                dbOne.disconnect(connTwo);

                currentlyLoadedAirlines = airlines;
                airlineTable.setItems(airlines);
                setAirlineComboBoxes();
                resetView();
                tableView.setVisible(true);
            }
        }catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /** Method to open up a file chooser for the user to select the Airline Data file
     */
    public void addAirlineData(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Airline datafile");
        File file = fileChooser.showOpenDialog(new Stage());
        airlineFile(file);
    }

    /** Method to implement Route data into the application with error handling
     *
     * @param file: Contains Route data
     */
    public void routeFile(File file) {
        try {
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the route data file
                //ObservableList<Route> routes = load.buildRoutes();

                Database dbOne = new Database();
                DatabaseSaver dbsave = new DatabaseSaver();
                RouteDatabaseLoader rdl = new RouteDatabaseLoader();

                Connection connOne = dbOne.connect();
                Connection connTwo = dbOne.connect();

                ObservableList<Route> loadedRoutes = load.buildRoutes(airlineHashMap, airportHashMap);

                for (Route route: loadedRoutes) {
                    route.setRouteID(routeIds);
                    routeIds += 1;
                }

                dbsave.saveRoutes(connOne, loadedRoutes);

                ObservableList<Route> routes = rdl.loadRoutes(connOne, routeHashMap, airlineHashMap, airportHashMap);
                dbOne.disconnect(connOne);

                System.out.println(routes.size());

                currentlyLoadedRoutes = routes;
                routeTable.setItems(routes);
                setRouteComboBoxes();
                resetView();
                tableView.setVisible(true);

            }
        }catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /** Method to open up a file chooser for the user to select the Route Data file with error handling
     */
    public void addRouteData(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Route datafile");
        File file = fileChooser.showOpenDialog(new Stage());
        routeFile(file);
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

            sources.add(currentlyLoadedRoutes.get(i).getSourceAirport().getName());
            destinations.add(currentlyLoadedRoutes.get(i).getDestinationAirport().getName());

            /**
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
             */
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
        editAirportViewController.makeInvisible();
    }

    /* Clears tables and re-adds currently loaded objects to them*/
    private void resetTables() {
        airportTable.getColumns().clear();
        initAirportTable();
        airportTable.getColumns().addAll(airportID, airportName, city, airportCountry);
        airportTable.setItems(currentlyLoadedAirports);

        airlineTable.getColumns().clear();
        initAirlineTable();
        airlineTable.getColumns().addAll(airlineID, airlineName, alias, country, active);
        airlineTable.setItems(currentlyLoadedAirlines);

        routeTable.getColumns().clear();
        initRouteTable();
        routeTable.getColumns().addAll(routeAirlineName, source, destination);
        routeTable.setItems(currentlyLoadedRoutes);
    }

    /* Switches to raw data table viewing interface*/
    public void backToTableView(ActionEvent e) {
        resetView();
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

    //------------------------------Needs to be moved to GraphViewController eventually-----------------------------------

    @FXML
    private BarChart<String, Integer> routesPerAirport;
    @FXML
    private PieChart equipmentPerRoute;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private Slider routeNum;
    @FXML
    private TabPane dataTabs;
    @FXML
    private Tab graphTab;
    @FXML
    private ComboBox graphCombo;

    public void setGraphCombo() {
        graphCombo.getItems().addAll("Routes per Airport", "Equipment per Routes");
        graphCombo.setValue("Choose a Graph");
    }

    @FXML
    public void setGraph(){
        String graph = graphCombo.getValue().toString();
        if (graph == "Routes per Airport"){
            routesPerAirport.setVisible(true);
            equipmentPerRoute.setVisible(false);
        } else if (graph == "Equipment per Routes"){
            routesPerAirport.setVisible(false);
            equipmentPerRoute.setVisible(true);
        }
    }

    @FXML
    /** Calls the setRoutesPerAirport method with the selected Airports in the Airports table
     *
     */
    public void addToRoutesPerAirport() {
        routesPerAirport.setVisible(true);
        equipmentPerRoute.setVisible(false);
        if (!airportTable.getItems().isEmpty() || !airportTable.getSelectionModel().isEmpty()) {
            ObservableList<Airport> airports = FXCollections.observableArrayList();
            ObservableList<Airport> airportsFromTable = airportTable.getSelectionModel().getSelectedItems();
            airports.addAll(airportsFromTable);
            setRoutesPerAirport(airports);
            dataTabs.getSelectionModel().select(graphTab);
        }
    }

    /** Fills the routesPerAirport Graph with data from an ObeservableList of airports and sets xAxis labels to
     *  Airport names
     *
     * @param loadedAirports: ObservableList of Airport Objects
     */
    public void setRoutesPerAirport(ObservableList<Airport> loadedAirports){
        routesPerAirport.getData().clear();
        if (!loadedAirports.isEmpty()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
            ArrayList<String> airportNames = new ArrayList<String>();
            airportNames.clear();

            Collections.sort(loadedAirports, new Comparator<Airport>() {
                @Override
                public int compare(Airport o1, Airport o2) {
                    return o1.getNumRoutes() - o2.getNumRoutes();
                }
            }.reversed());
            if (routeNum.getValue() > loadedAirports.size()){
                routeNum.setValue(loadedAirports.size());
            }
            for (int i = 0; i < routeNum.getValue(); i++) {
                int routes = loadedAirports.get(i).getNumRoutes();
                String name = loadedAirports.get(i).getName() + " : " + Integer.toString(routes);
                airportNames.add(name);
                series.getData().add(new XYChart.Data<String, Integer>(name, routes));
            }
            xAxis.getCategories().clear();
            if (routeNum.getValue() > 0){
                xAxis.setCategories(FXCollections.observableArrayList(airportNames));
                routesPerAirport.getData().add(series);
            }
        }
    }

    @FXML
    public void addToEquipmentPerRoute(){
        equipmentPerRoute.setVisible(true);
        routesPerAirport.setVisible(false);
        if (!routeTable.getItems().isEmpty() || !routeTable.getSelectionModel().isEmpty()) {
            ObservableList<Route> routes = FXCollections.observableArrayList();
            ObservableList<Route> routesFromTable = routeTable.getSelectionModel().getSelectedItems();
            routes.addAll(routesFromTable);
            setEquipmentPerRoute(routes);
            dataTabs.getSelectionModel().select(graphTab);
        }
    }

    public void setEquipmentPerRoute(ObservableList<Route> routes) {
        equipmentPerRoute.getData().clear();
        if (!routes.isEmpty()) {
            ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
            HashMap<String, Integer> equipment = new HashMap<String, Integer>();
            for (Route route : routes) {
                for (String equip : route.getEquipment().split(" ")) {
                    if (equipment.containsKey(equip)) {
                        equipment.put(equip, equipment.get(equip) + 1);
                    } else {
                        equipment.put(equip, 1);
                    }
                }
            }
            for (Map.Entry<String, Integer> map : equipment.entrySet()) {
                chartData.add(new PieChart.Data(map.getKey() + " : " + Integer.toString(map.getValue()), map.getValue()));
            }
            equipmentPerRoute.getData().addAll(chartData);
        }
    }



    //-----------------------------------------------------------------------------------------------------------------

    //Sets Table Cells in Airline Table Viewer to Airline attributes
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        setMainControllers();
        initAirlineTable();
        initAirportTable();
        initRouteTable();
        setGraphCombo();


        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New State");
        alert.setHeaderText("There is data availible to be loaded.");
        alert.setContentText("Would you like to load initial data?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES){
            airportFile(new File("src/main/resources/airports.dat"));
            airlineFile(new File("src/main/resources/airlines.dat"));
            routeFile(new File("src/main/resources/routes.dat"));
        }*/
        // Allows individual cells to be selected as opposed to rows
        //airportTable.getSelectionModel().setCellSelectionEnabled(true);
        //Setting up Chart

        airportTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        routeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

        setRouteIds(currentlyLoadedRoutes.size());
        showAirlines();
        showAirports();
        showRoutes();
    }


    /** Switches to the more info pane for Airports
     *
     * @param click: The click event
     */
    public void airportInfo(MouseEvent click) {
        //Checks if table is empty then checks for double click
        if (!airportTable.getItems().isEmpty()) {
            if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                editAirportViewController.setAirportInfo();
            }
        }
    }

    /** Switches to the more info pane for Airports
     *
     * @param click: The click event
     */
    public void airlineInfo(MouseEvent click) {
        //Checks if table is empty then checks for double click
        if (!airlineTable.getItems().isEmpty()) {
            if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                editAirlineViewController.setAirlineInfo();
            }
        }
    }

    /** Switches to the more info pane for Airports
     *
     * @param click: The click event
     */
    public void routeInfo(MouseEvent click) {
        //Checks if table is empty then checks for double click
        if (!routeTable.getItems().isEmpty()) {
            if (click.getClickCount() >= 2 && !click.isControlDown() && !click.getTarget().toString().startsWith("TableColumnHeader")) {
                editRouteViewController.setRouteInfo();
            }
        }
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
    public void showHelpPopup(ActionEvent e){
        String type = null;

        if(e.getSource() == helpAddButton){
            type = "add";
        } else if(e.getSource() == helpEditButton){
            type = "edit";
        }else if(e.getSource() == helpFilterButton){
            type = "filter";
        } else if(e.getSource() == helpDistanceButton){
            type = "distance";
        } else if(e.getSource() == helpLoadButton){
            type = "load";
        } else if(e.getSource() == helpSearchButton){
            type = "search";
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/helpPopup.fxml"));

            Parent root = loader.load();

            Stage stage = new Stage();

            HelpPopupController cont = loader.getController();
            cont.setUp(stage, type);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(new Scene(root));

            stage.show();


        }catch(IOException io){
            io.printStackTrace();
        }



    }
}