package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Franchise;
import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Spinner;


public class AjouterSalleController extends MenuController implements Initializable {

    @FXML
    private Button bRetour, bEnregistrer;
    @FXML
    private TextField tfDescription;
    @FXML
    private Spinner spNumero, spNbPlace;
    @FXML 
    ListView<Cinema> lvCinema;
    @FXML
    private Label lbFeedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Cinema> cinemas = getCinemaList();
        lvCinema.setItems(cinemas);

        spNumero.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        spNbPlace.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500));

    }

    private ObservableList<Cinema> getCinemaList() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();

        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        Integer numero = (Integer) spNumero.getValue();
        String description = tfDescription.getText();
        Integer nbPlace = (Integer) spNbPlace.getValue();
        Cinema selected = lvCinema.getSelectionModel().getSelectedItem();
        if (numero != null && description != null && nbPlace != null && selected != null 
             && !description.trim().isEmpty()){
                int idCinema = selected.getIdCinema();
                Salle salle = new Salle(numero, description, nbPlace, idCinema);

                SalleDAO salleDAO = new SalleDAO();
                boolean controle = salleDAO.create(salle);
                if(controle){
                    spNumero.getValueFactory().setValue(0);
                    tfDescription.clear();
                    spNbPlace.getValueFactory().setValue(0);
                    lvCinema.getSelectionModel().clearSelection();
                    afficherSuccess();
                }
        }
        else{
            afficherErreur();
        }

    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfDescription != null)
            tfDescription.clear();
        spNumero.getValueFactory().setValue(0);
        spNbPlace.getValueFactory().setValue(0);
        lvCinema.getSelectionModel().clearSelection();
    }

    public void afficherErreur(){
        lbFeedback.setText("Tous les champs doivent être remplis");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }

    public void afficherSuccess(){
        lbFeedback.setText("Création de la salle réussie");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: green;");
    }

}
