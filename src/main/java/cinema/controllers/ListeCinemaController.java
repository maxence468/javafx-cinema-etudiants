package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListeCinemaController extends MenuController implements Initializable {

    @FXML
    private TableView<Cinema> tvCinema;

    @FXML
    private TableColumn<Cinema, String> tcDenomination, tcFranchise;

    @FXML
    private TableColumn<Cinema, Void> tcModif, tcSupp, tcVp;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tcDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        tcFranchise.setCellValueFactory(new PropertyValueFactory<>("nomFranchise"));
        ObservableList<Cinema> data = getCinema();
        tvCinema.setItems(data);

        btnModif();
        btnSupp();
        btnVoirPlus();
    }

    //methode pour recuperer tous les cinémas dans une liste observable
    private ObservableList<Cinema> getCinema() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> mesCinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(mesCinemas);
        return list;
    }
    
    //retourne à la page precedente
    public void bRetourClick(ActionEvent actionEvent) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    
    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Cinema, Void>() {
            //crée un bouton dans chaque cellule de la colonne
            private Button btn = new Button("Modifier");
            {
                //methode à d'action du bouton pour rediriger sur la page de modification
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    Navigation.goTo("/cinema/views/page_modif_cinema.fxml", "cinema", cinema, bRetour.getScene().getWindow());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    //methode pour ajouter un bouton permettant de supprimer un cinéma 
    private void btnSupp() {
        tcSupp.setCellFactory(col -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    //supprime le cinéma de la table et de la BDD
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    tvCinema.getItems().remove(cinema);
                    CinemaDAO cinemaDAO = new CinemaDAO();
                    cinemaDAO.delete(cinema);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    //methode qui redirige sur la liste des salles associées au cinéma selectionné
    private void btnVoirPlus(){
        tcVp.setCellFactory(col -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Voir Les Salles");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    int idCinema = cinema.getIdCinema(); 
                    //enregistre l'id du cinéma pour filtrer les salles
                    Navigation.setParam("idCinema", idCinema);
                    Navigation.goTo("/cinema/views/page_liste_salle.fxml", bRetour.getScene().getWindow());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

}
