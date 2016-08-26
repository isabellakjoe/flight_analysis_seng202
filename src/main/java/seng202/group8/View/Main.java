package seng202.group8.View;

/**
 * Created by Callum on 22/08/16.
 **/

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group8.Model.Flight;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Adds the FXML files to the GUI */
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        /* Set the name of the GUI */
        primaryStage.setTitle("Flight Viewer");
        primaryStage.setScene(new Scene(root, 1100, 680));
        primaryStage.show();
    }

}
