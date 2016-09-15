package seng202.group8.Controller;


import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by esa46 on 15/09/16.
 */
public class MapViewController {

    //Google Maps initialization
    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private MainController mainController;

    public void initMap() {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("maps.html").toExternalForm());
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
