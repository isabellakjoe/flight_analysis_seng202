package seng202.group8.View;

/**
 * Created by Callum on 22/08/16.
 **/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.Model.DatabaseMethods.Database;

public class Main extends Application {

    public static void main(String[] args) {

        Database db = new Database();
        db.createDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Adds the FXML files to the GUI */
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        /* Set the name of the GUI */
        primaryStage.setTitle("Flight Viewer");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.setMinHeight(550);
        primaryStage.setMinWidth(700);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
