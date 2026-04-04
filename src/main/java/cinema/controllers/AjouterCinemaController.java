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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;
    @FXML
    private Button bRetour, bEnregistrer;
    @FXML
    private ListView<Franchise> lvFranchise;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Franchise> franchises = getFranchiseList();
        lvFranchise.setItems(franchises);

    }

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
        if (denomination != null && adresse != null && ville != null && selected != null 
            && !denomination.trim().isEmpty() && !adresse.trim().isEmpty() && !ville.trim().isEmpty()){
                int idFranchise = selected.getIdFranchise();
                Cinema cinema = new Cinema(denomination, adresse, ville, idFranchise);

                CinemaDAO cinemaDAO = new CinemaDAO();
                boolean controle = cinemaDAO.create(cinema);
                if(controle){
                    tfDenomination.clear();
                    tfAdresse.clear();
                    tfVille.clear();
                    lvFranchise.getSelectionModel().clearSelection();
                }
            }

    }

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

}
