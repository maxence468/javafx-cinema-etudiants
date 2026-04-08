package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
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

public class ListeSalleController extends MenuController implements Initializable {

    @FXML
    private TableView<Salle> tvSalle;

    @FXML
    private TableColumn<Salle, String> tcNumero, tcDescription, tcNbPlace, tcDenominationDuCinema;

    @FXML
    private TableColumn<Salle, Void> tcModif, tcSupp;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcNbPlace.setCellValueFactory(new PropertyValueFactory<>("nbPlace"));
        tcDenominationDuCinema.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        
        ObservableList<Salle> data = getSalle();
        tvSalle.setItems(data);

        btnModif();
        btnSupp();
    }

    //methode pour recuperer toutes les salles dans une liste observable
    private ObservableList<Salle> getSalle() {
        SalleDAO salleDAO = new SalleDAO();
        List<Salle> mesSalles = salleDAO.findAll();

        Integer idCinema = Navigation.getParam("idCinema");
        if(idCinema != null){
            mesSalles.removeIf(salle -> salle.getIdCinema() != idCinema);
        }
        
        ObservableList<Salle> list = FXCollections.observableArrayList(mesSalles);
        return list;
    }

    //retourne à la page precedente
    public void bRetourClick(ActionEvent actionEvent) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    //methode pour ajouter un bouton permettant de modifier une salle
    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Salle, Void>() {
            private Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    Navigation.goTo("/cinema/views/page_modif_salle.fxml", "salle", salle, bRetour.getScene().getWindow());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    //methode pour ajouter un bouton permettant de supprimer une salle
    private void btnSupp() {
        tcSupp.setCellFactory(col -> new TableCell<Salle, Void>() {
            private Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    tvSalle.getItems().remove(salle);
                    SalleDAO salleDAO = new SalleDAO();
                    salleDAO.delete(salle);
                    
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
