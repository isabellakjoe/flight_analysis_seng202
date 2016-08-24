package seng202.group8.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import seng202.group8.Model.FileLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;



/**
 * Created by esa46 on 19/08/16.
 */
public class Controller {
    @FXML
    private MenuItem addAirportData;

    @FXML
    private MenuItem addAirlineData;

    @FXML
    private MenuItem addRouteData;

    /* Method to open up a file chooser for the user to select the Airport Data file */
    public void addAirportData(ActionEvent e){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airport datafile"); //Text in the window header
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                load.getFile(br);
                load.readData(br);
                load.buildAirports();


            }
        } catch(FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    /* Method to open up a file chooser for the user to select the Airline Data file */
    public void addAirlineData(ActionEvent e){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Airline datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                load.getFile(br);
                load.readData(br);
                load.buildAirlines();
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

    /* Method to open up a file chooser for the user to select the Route Data file */
    public void addRouteData(ActionEvent e) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Route datafile");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                FileLoader load = new FileLoader(br);
                load.getFile(br);
                load.readData(br);
                load.buildRoute();
            }
        } catch (FileNotFoundException ex){
            System.out.println("FILE NOT FOUND");
        }

    }

}
