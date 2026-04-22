package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Franchise;
import cinema.BO.Cinema;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.CinemaDAO;
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

public class AjouterCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;
    @FXML
    private Button bRetour, bEnregistrer, bAjtFranchise;
    @FXML
    private ListView<Franchise> lvFranchise;
    @FXML
    private Label lbFeedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Franchise> franchises = getFranchiseList();
        lvFranchise.setItems(franchises);
    }

    //methode pour recuperer toutes les franchises dans une liste observable
    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {

        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();
        Franchise selected = lvFranchise.getSelectionModel().getSelectedItem();
        //verifie que tous les champs soient remplis 
        if (denomination != null && adresse != null && ville != null && selected != null 
            && !denomination.trim().isEmpty() && !adresse.trim().isEmpty() && !ville.trim().isEmpty()){
                int idFranchise = selected.getIdFranchise();
                Cinema cinema = new Cinema(denomination, adresse, ville, idFranchise);

                CinemaDAO cinemaDAO = new CinemaDAO();
                //si la création reussit la methode renvoie true 
                boolean controle = cinemaDAO.create(cinema);
                if(controle){
                    tfDenomination.clear();
                    tfAdresse.clear();
                    tfVille.clear();
                    lvFranchise.getSelectionModel().clearSelection();
                    afficherSuccess();
                }
        }
        else{
            afficherErreur();
        }

    }

    //reinitialise les champs du formulaire
    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfDenomination != null)
            tfDenomination.clear();
        if (tfAdresse != null)
            tfAdresse.clear();
        if (tfVille != null)
            tfVille.clear();
        lvFranchise.getSelectionModel().clearSelection();
    }

    @FXML
    public void bAjtFranchiseClick(ActionEvent event){
        Navigation.openPopUp("/cinema/views/page_ajout_franchise_popup.fxml");
        lvFranchise.setItems(getFranchiseList());
    }

    //affiche un message d'erreur en rouge
    public void afficherErreur(){
        lbFeedback.setText("Tous les champs doivent être remplis");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }
    //affiche un message de validation en vert
    public void afficherSuccess(){
        lbFeedback.setText("Création du cinéma réussie");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: green;");
    }

}
