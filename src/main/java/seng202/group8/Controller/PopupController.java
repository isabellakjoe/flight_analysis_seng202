package seng202.group8.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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


    @FXML
    public void closePopup(ActionEvent e){
        Stage stage = (Stage) popupOkButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void yesPressed(ActionEvent e){
        Stage stage = (Stage) popupYesButton.getScene().getWindow();
        mapViewController.showAirportMarkers(mainController.getCurrentlyLoadedAirports());
        stage.close();
    }



    public void setUpYesNo(Stage stage, List airports){

        stage.setTitle("WARNING!");

        popupOkButton.setVisible(false);
        popupYesButton.setVisible(true);
        popupCancelButton.setVisible(false);
        popupText1.setText("Are you sure you want to display " +  airports.size() + " airports? ");
        popupText2.setText("This may take a while...");


    }



    public void setUpNoAirports(Stage stage){
        stage.setTitle("Error Message");

        popupText1.setText("No Airports to display.");

    }

    public void setUpDistance(Stage stage, String airport1, String airport2, Double distance){
        stage.setTitle("Get Distance");

        popupText1.setText("The distance between " + airport1 + " and " + airport2 + " is ");
        popupText2.setText(distance + " km.");
    }
}
