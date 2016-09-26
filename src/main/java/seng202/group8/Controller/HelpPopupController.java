package seng202.group8.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

/**
 * Created by ikj11 on 23/09/16.
 */
public class HelpPopupController {

    @FXML
    public ImageView helpImage;
    @FXML
    public Text helpPopupTextOne;
    @FXML
    public Text helpPopupTextTwo;
    @FXML
    public Button helpPopupOKButton;

    @FXML
    public void closePopup(ActionEvent e){
        Stage stage = (Stage) helpPopupOKButton.getScene().getWindow();
        stage.close();
    }

    public void setUp(Stage stage, String type){
        if (type.equals("add")) {
            stage.setTitle("How to Add Data");
            helpPopupTextOne.setText("Below the Search and Reset buttons is Add New Route/Airport/Airline, " +
                    "(Depending on which tab you are in) which allows for adding a single object to the table.");
            helpPopupTextTwo.setText("Filling out the fields and clicking save will add the created object to its corresponding table.");

        } else if(type.equals("edit")){
            stage.setTitle("How to Edit Data");
            helpPopupTextOne.setText("The objects in the data tables can be double clicked to display a more detailed pane on that object.");
            helpPopupTextTwo.setText( "Inside this detailed pane, you can choose to edit an existing Object by clicking the Edit button, which will allow editing. ");
        } else if(type.equals("filter")){
            stage.setTitle("How to Filter Data");
            helpPopupTextOne.setText("");
            helpPopupTextTwo.setText("The data can be filtered using the Filter dropdown boxes at the top right of the tables, and will filter data based on the selected parameter.");
        } else if(type.equals("distance")){
            stage.setTitle("How to to get the distance between two airports");
            helpPopupTextOne.setText("");
            helpPopupTextTwo.setText("If you hold CTRL to select two Airport objects from the table, then left click, you can choose to get the distance between those two Airports.");
        } else if(type.equals("search")){
            stage.setTitle("How to Search Data");
            helpPopupTextOne.setText("On the left hand side of the program is a search pane, with three tabs for Route, Airline and Airport searches." +
                    "Here you can input search parameters for the different tables, and an Advanced Pane with more search options is available by clicking the Advanced button.");
            helpPopupTextTwo.setText("Searching begins once Search is clicked, and Reset will reset the tables and the search inputs." +
                    "If nothing satisfies the search parameters, no data will be displayed in the table. " +
                    "Also, if spelling is incorrect, the desired data will not be displayed until the spelling is corrected.");
        } else if(type.equals("load")){
            stage.setTitle("How to Load Data");
            helpPopupTextOne.setText("Simply load in data using the \\\"Load\\\" menu at the top left corner of the program and choose the type of data file you wish to add.");
            helpPopupTextTwo.setText( "Once added, the data will be displayed in either one of the three" +
                    " tables if Add Route, Airline or Airport was chosen, otherwise the Fight Pane with be displayed showing added flight data." +
                    "If the file contains an object that already exists in the table, the entire file will not be added. ");
        }
//        Image image = new Image(getClass().getClassLoader().getResource("addAirport.png").toExternalForm());
//        helpImage.setImage(image);
    }

}
