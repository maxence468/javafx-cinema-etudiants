package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.UtilisateurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierInfoPersoController extends MenuController implements Initializable {

    @FXML
    private TextField tfLoginUtilisateur, tfPrenomUtilisateur, tfNomUtilisateur;
    @FXML
    private Button bRetour;
    @FXML
    private Label lbFeedback;
    
    private int idUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {      
        //recupere l'utilisateur à modifier
        idUser = Navigation.getParam("idUser");
        UtilisateurDAO userDAO = new UtilisateurDAO();
        Utilisateur user = userDAO.find(idUser);

        //remplis les champs du formulaire
        tfNomUtilisateur.setText(user.getNom());
        tfPrenomUtilisateur.setText(user.getPrenom());
        tfLoginUtilisateur.setText(user.getLogin());
    }

    //verifie si les données sont valides et enregistre les modifications
    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String nom = tfNomUtilisateur.getText();
        String prenom = tfPrenomUtilisateur.getText();
        String login = tfLoginUtilisateur.getText();

        if (nom != null && prenom != null && login != null && !nom.trim().isEmpty()
                && !prenom.trim().isEmpty() && !login.trim().isEmpty()) {
            Utilisateur newUser = new Utilisateur(this.idUser, nom, prenom, login);

            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            boolean controle = utilisateurDAO.update(newUser);
            if (controle) {
                afficherSuccess();
            }
        }
        else{
            afficherErreur();
        }
    }

    //retourne à la page precedente
    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    //affiche un message d'erreur en rouge
    public void afficherErreur(){
        lbFeedback.setText("Tous les champs doivent être remplis");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }

    //affiche un message de validation en vert
    public void afficherSuccess(){
        lbFeedback.setText("Informations modifiées avec succès");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: green;");
    }

}
