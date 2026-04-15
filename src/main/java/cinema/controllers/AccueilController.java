package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import cinema.DAO.UtilisateurDAO;
import cinema.BO.Utilisateur;


public class AccueilController extends MenuController implements Initializable {

    @FXML
    private Label bienvenue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //recupere l'id de l'utilisateur connecté
        int idUser = Navigation.getParam("idUser");
        UtilisateurDAO userDAO = new UtilisateurDAO();
        //recupere les information de cet utilisateur
        Utilisateur user = userDAO.find(idUser);
        String nameUti = user.getNom();
        //definit le texte du label bienvenue
        bienvenue.setText("BONJOUR " + nameUti.toUpperCase());
    }
}
