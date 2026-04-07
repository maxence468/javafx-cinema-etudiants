package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.DAO.CinemaDAO;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModifierCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;
    //private TextArea taLibSec;
    @FXML 
    ListView<Franchise> lvFranchise;
    @FXML
    private Label lbFeedback;

    private int idCinema;

    @FXML
    private Button bRetour, bEnregistrer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Franchise> franchises = getFranchiseList();
        lvFranchise.setItems(franchises);

        Cinema cinema = Navigation.getParam("cinema");

        tfDenomination.setText(cinema.getDenomination());
        tfAdresse.setText(cinema.getAdresse());
        tfVille.setText(cinema.getVille());

        for (Franchise franchise : lvFranchise.getItems()) {
            if (franchise.getIdFranchise() == cinema.getIdFranchise()) {
                lvFranchise.getSelectionModel().select(franchise);
                break;
            }
        }
        //lvFranchise.getSelectionModel().select(cinema.getIdFranchise() - 1);

        this.idCinema = cinema.getIdCinema();

    }

    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        return list;
    }


    // public void setAttrinuts() {
    //     CinemaDAO sectionDAO = new CinemaDAO();
    //     Cinema sec = sectionDAO.find(idSec);
    //     taLibSec.setText(sec.getDenomination());
    // }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());

        // Stage stageP = (Stage) bRetour.getScene().getWindow();
        // stageP.close();
        // try {

        //     FXMLLoader fxmlLoader = new FXMLLoader(
        //             getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
        //     Parent root = fxmlLoader.load();

        //     ListeCinemaController listeCinemaController = fxmlLoader.getController();
        //     listeCinemaController.setName(nameUti);

        //     Stage stage = new Stage();
        //     stage.setTitle("Liste franchises");
        //     stage.setScene(new Scene(root));

        //     stage.initModality(Modality.APPLICATION_MODAL);

        //     stage.show();

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();
        Franchise selected = lvFranchise.getSelectionModel().getSelectedItem();
        if (denomination != null && adresse != null && ville != null && selected != null 
            && !denomination.trim().isEmpty() && !adresse.trim().isEmpty() && !ville.trim().isEmpty()){
                int idFranchise = selected.getIdFranchise();
                Cinema cinema = new Cinema(this.idCinema, denomination, adresse, ville, idFranchise);

                CinemaDAO cinemaDAO = new CinemaDAO();
                boolean controle = cinemaDAO.update(cinema);
                if(controle){
                    Navigation.goTo("/cinema/views/page_liste_cinema.fxml", bRetour.getScene().getWindow());
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
