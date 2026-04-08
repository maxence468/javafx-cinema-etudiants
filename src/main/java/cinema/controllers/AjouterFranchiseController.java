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
import javafx.scene.paint.Color;

public class AjouterFranchiseController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Utilisateur> lvGerantFranchise;
    @FXML
    private Label lbFeedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();

        lvGerantFranchise.setItems(utilisateurs);
    }

    //methode pour recuperer tous les utilisateurs dans une liste observable
    private ObservableList<Utilisateur> getUtilisateurList() {

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {

        String nomFranchise = tfNomFranchise.getText();
        String siegeSocial = tfSiegeSocial.getText();

        Utilisateur gerantSelectionne = lvGerantFranchise.getSelectionModel().getSelectedItem();
        //verifie que tous les champs soient remplis
        if(nomFranchise != null && siegeSocial != null && gerantSelectionne != null && !nomFranchise.trim().isEmpty() && !siegeSocial.trim().isEmpty()){
            int idGerant = gerantSelectionne.getIdUtilisateur();
            Franchise franchise = new Franchise(nomFranchise, siegeSocial, idGerant);

            FranchiseDAO franchiseDAO = new FranchiseDAO();
            //si la création reussit la methode renvoie true 
            boolean controle = franchiseDAO.create(franchise);
            if (controle) {
                tfNomFranchise.clear();
                tfSiegeSocial.clear();
                lvGerantFranchise.getSelectionModel().clearSelection();
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
        if (tfNomFranchise != null)
            tfNomFranchise.clear();
        if (tfSiegeSocial != null)
            tfSiegeSocial.clear();
        lvGerantFranchise.getSelectionModel().clearSelection();
    }

    //affiche un message d'erreur en rouge
    public void afficherErreur(){
        lbFeedback.setText("Tous les champs doivent être remplis");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }

    //affiche un message de validation en vert
    public void afficherSuccess(){
        lbFeedback.setText("Création de la franchise réussie");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: green;");
    }

}
