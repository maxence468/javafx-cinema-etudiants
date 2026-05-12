package cinema.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import cinema.controllers.Navigation;

import java.io.IOException;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Rediriger les erreurs vers un fichier log
    try {
        PrintStream ps = new PrintStream(new FileOutputStream("cineforall.log", true));
        System.setErr(ps);
        System.setOut(ps);
    } catch (Exception e) {
        e.printStackTrace();
    }


        // On enregistre le stage principal dans Navigation
        Navigation.setPrimaryStage(primaryStage);
        //nom de la page de connexion
        primaryStage.setTitle("Application de gestion de franchise - Authentification");
        //empeche de changer la taille de l'ecran
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        //affiche la fenetre au premier plan 
        primaryStage.setAlwaysOnTop(true);
        // Navigation gère le reste (chargement FXML + icône + show)
        Navigation.goTo("/cinema/views/page_connexion.fxml");
        //desactive l'affichage forcé au premier plan
        primaryStage.setAlwaysOnTop(false);
    }
}
