package cinema.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import cinema.controllers.Navigation;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // On enregistre le stage principal dans Navigation
        Navigation.setPrimaryStage(primaryStage);
        
        primaryStage.setTitle("Application de gestion de franchise - Authentification");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setAlwaysOnTop(true);
            
        // Navigation gère le reste (chargement FXML + icône + show)
        Navigation.goTo("/cinema/views/page_connexion.fxml");
        
        primaryStage.setAlwaysOnTop(false);
    }
}
