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

public class ModifierFranchiseController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Utilisateur> lvGerantFranchise;
    @FXML
    private Label lbFeedback;
    
    private int idFranchise;
    private int idGerant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();
        lvGerantFranchise.setItems(utilisateurs);
        
        //recupere la franchise à modifier
        Franchise franchise = Navigation.getParam("franchise");
        
        tfNomFranchise.setText(franchise.getNomFranchise());
        tfSiegeSocial.setText(franchise.getSiegeSocial());
        
        //Selectionne le gerant de la franchise
        for (Utilisateur gerant : lvGerantFranchise.getItems()) {
            if (gerant.getIdUtilisateur() == franchise.getIdGerant()) {
                lvGerantFranchise.getSelectionModel().select(gerant);
                break;
            }
        }
        this.idFranchise = franchise.getIdFranchise();
    }

    //methode pour recuperer tous les utilisateurs dans une liste observable
    private ObservableList<Utilisateur> getUtilisateurList() {
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    //verifie si les données sont valides et enregistre les modifications
    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String nom = tfNomFranchise.getText();
        String siegeSocial = tfSiegeSocial.getText();
        Utilisateur selected = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (nom != null && siegeSocial != null && selected != null && !nom.trim().isEmpty()
                && !siegeSocial.trim().isEmpty()) {
            int idGerant = selected.getIdUtilisateur();
            Franchise newFranchise = new Franchise(this.idFranchise, nom, siegeSocial, idGerant);

            FranchiseDAO franchiseDAO = new FranchiseDAO();
            boolean controle = franchiseDAO.update(newFranchise);
            if (controle) {
                Navigation.goTo("/cinema/views/page_liste_franchise.fxml", bRetour.getScene().getWindow());
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

}
