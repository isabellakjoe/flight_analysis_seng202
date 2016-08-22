package seng202.group8.View;/**
 * Created by Callum on 22/08/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Adds the FXML files to the GUI */
        //Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        /* Set the name of the GUI */
        primaryStage.setTitle("Flight Viewer");
        //primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}