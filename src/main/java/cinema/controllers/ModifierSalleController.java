package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import cinema.DAO.FranchiseDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;

public class ModifierSalleController extends MenuController implements Initializable {

    @FXML
    private TextField tfDescription;

    @FXML
    private Spinner spNumero, spNbPlace;

    @FXML 
    ListView<Cinema> lvCinema;

    @FXML
    private Label lbFeedback;

    private int idSalle;

    @FXML
    private Button bRetour, bEnregistrer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Cinema> cinemas = getCinemaList();
        lvCinema.setItems(cinemas);

        Salle salle = Navigation.getParam("salle");

        spNumero.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, salle.getNumero()));
        tfDescription.setText(salle.getDescription());
        spNbPlace.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, salle.getNbPlace()));
        
        for (Cinema cinema : lvCinema.getItems()) {
            if (cinema.getIdCinema() == salle.getIdCinema()) {
                lvCinema.getSelectionModel().select(cinema);
                break;
            }
        }

        this.idSalle = salle.getIdSalle();

    }

    private ObservableList<Cinema> getCinemaList() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();

        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
        return list;
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        Integer numero = (Integer) spNumero.getValue();
        String description = tfDescription.getText();
        Integer nbPlace = (Integer) spNbPlace.getValue();
        Cinema selected = lvCinema.getSelectionModel().getSelectedItem();
        if (numero != null && description != null && nbPlace != null && selected != null 
             && !description.trim().isEmpty()){
                int idCinema = selected.getIdCinema();
                Salle salle = new Salle(this.idSalle, numero, description, nbPlace, idCinema);

                SalleDAO salleDAO = new SalleDAO();
                boolean controle = salleDAO.update(salle);
                if(controle){
                    Navigation.goTo("/cinema/views/page_liste_salle.fxml", bRetour.getScene().getWindow());
                }
        }
        else{
            afficherErreur();
        }
    }
    

    public void afficherErreur(){
            lbFeedback.setText("Tous les champs doivent être remplis");
            lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }
}
