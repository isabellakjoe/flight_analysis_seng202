package seng202.group8.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import seng202.group8.Model.FileLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


import seng202.group8.Model.Airline;
import seng202.group8.Model.AirlineParser;
import seng202.group8.Model.FileLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
//import javafx.scene.control.TextField;

/**
 * Created by esa46 on 19/08/16.
 */
public class Controller implements Initializable {
    @FXML
    private MenuItem viewAirportData;
/*
    public void search(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Airport Data File");
        fileChooser.showOpenDialog(stage);
    }
*/
    @FXML
    private TableView<Airline> airlineTable;
    @FXML
    private TableColumn<Airline, String> ID;
    @FXML
    private TableColumn<Airline, String> name;
    @FXML
    private TableColumn<Airline, String> alias;
    @FXML
    private TableColumn<Airline, String> IATA;
    @FXML
    private TableColumn<Airline, String> ICAO;
    @FXML
    private TableColumn<Airline, String> callSign;
    @FXML
    private TableColumn<Airline, String> country;
    @FXML
    private TableColumn<Airline, String> active;
    private MenuItem addAirportData;

    @FXML
    private MenuItem addAirlineData;

    @FXML
    private MenuItem addRouteData;

    /* Method to open up a file chooser for the user to select the Airport Data file  with error handling*/
    public void addAirportData(ActionEvent e){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airport datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the airport data file
                load.getFile(br);
                load.readData(br);
                load.buildAirports();


    private String airlineString = "324,All Nippon Airways,ANA All Nippon Airways,NH,ANA,ALL NIPPON,Japan,Y";
    AirlineParser airlineParser = new AirlineParser();
    Airline airline = airlineParser.createSingleAirline(airlineString);



    //Initialises the controller class. Automatically called after the fxml file has been loaded
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        ID.setCellValueFactory(new PropertyValueFactory<Airline, String>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Airline, String>("Name"));
        alias.setCellValueFactory(new PropertyValueFactory<Airline, String>("Alias"));
        IATA.setCellValueFactory(new PropertyValueFactory<Airline, String>("IATA"));
        ICAO.setCellValueFactory(new PropertyValueFactory<Airline, String>("ICAO"));
        callSign.setCellValueFactory(new PropertyValueFactory<Airline, String>("Call Sign"));
        country.setCellValueFactory(new PropertyValueFactory<Airline, String>("Country"));
        active.setCellValueFactory(new PropertyValueFactory<Airline, String>("Active"));
        airlineTable.getItems().setAll(airline);
            }
        } catch(FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    /* Method to open up a file chooser for the user to select the Airline Data file with error handling */
    public void addAirlineData(ActionEvent e){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airline datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                //Use imported methods from FileLoader to process the airline data file
                FileLoader load = new FileLoader(br);
                load.getFile(br);
                load.readData(br);
                load.buildAirlines();
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    /* Method to open up a file chooser for the user to select the Route Data file with error handling */
    public void addRouteData(ActionEvent e) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Route datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                //Use imported methods from FileLoader to process the route data file
                load.getFile(br);
                load.readData(br);
                load.buildRoute();
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

}
