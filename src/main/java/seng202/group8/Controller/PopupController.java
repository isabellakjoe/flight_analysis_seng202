package seng202.group8.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.Controller.MapViewControllers.MapViewController;

import java.util.List;

/**
 * Created by ikj11 on 2/10/16.
 */
public class PopupController {

    @FXML
    public GridPane popupPane;
    @FXML
    public Text popupText1;
    @FXML
    public Text popupText2;
    @FXML
    public Button popupOkButton;
    @FXML
    public Button popupCancelButton;
    @FXML
    public Button popupYesButton;


    private MainController mainController;
    private MapViewController mapViewController;

    private boolean isAirport = false;
    private boolean isRoute = false;
    private boolean isFlight = false;

    @FXML
    private void resetPopup(){

        popupCancelButton.setVisible(false);
        popupYesButton.setVisible(false);
        popupOkButton.setVisible(true);
    }
    @FXML
    public void closePopup(ActionEvent e){
        Stage stage = (Stage) popupOkButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void yesPressed(ActionEvent e){
        Stage stage = (Stage) popupYesButton.getScene().getWindow();
        if(isAirport) {
            mapViewController.showAirportMarkers(mainController.getCurrentlyLoadedAirports());
            isAirport = false;
        } else if(isRoute){
            mapViewController.createMapRoutes(mainController.getCurrentlyLoadedRoutes());
            isRoute = false;
        } else if(isFlight){
            mapViewController.createFlightPath();
        }
        stage.close();

    }




    public void setUpYesNo(Stage stage, List list, String type){
        resetPopup();
        stage.setTitle("WARNING!");

        popupOkButton.setVisible(false);
        popupYesButton.setVisible(true);
        popupCancelButton.setVisible(true);
        if(type == "airport") {
            popupText1.setText("Are you sure you want to display " + list.size() + " airports? ");
            popupText2.setText("This may take a while...");
            isAirport = true;
        } else if(type == "route"){
            popupText1.setText("Are you sure you want to display " + list.size() + " routes? ");
            popupText2.setText("This may take a while...");
            isRoute = true;
        } else if(type == "flight"){
            popupText1.setText("Are you sure you want to display a Flight Path with " +  list.size() + " stops?");
            popupText2.setText("This may take a while...");
            isFlight = true;
        }
    }


    public void setUpEmptyRoutes(Stage stage){
        resetPopup();
        stage.setTitle("Error Message");

        popupText1.setText("No Routes to display.");
        popupPane.setPrefHeight(100);

    }

    public void setUpEmptyAirports(Stage stage){
        resetPopup();
        stage.setTitle("Error Message");

        popupText1.setText("No Airports to display.");
        popupPane.setPrefHeight(100);
    }


    public void setUpEmptyFlights(Stage stage){
        resetPopup();
        stage.setTitle("Error Message");
        popupText1.setText("No Flight Path to display.\nPlease Load Flight Data");
        popupPane.setPrefHeight(100);
    }


    public void setUpDistance(Stage stage, String airport1, String airport2, Double distance){
        resetPopup();
        stage.setTitle("Get Distance");

        popupText1.setText("The distance between " + airport1 + " and " + airport2 + " is ");
        popupText2.setText(distance + " km.");
    }

}
