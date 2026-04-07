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

    private ObservableList<Cinema> getCinema() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> mesCinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(mesCinemas);
        return list;
    }

    public void bRetourClick(ActionEvent actionEvent) {
        Navigation.goBack(bRetour.getScene().getWindow());

        // Stage stageP = (Stage) bRetour.getScene().getWindow();
        // stageP.close();

        // try {
        //     FXMLLoader fxmlLoader = new FXMLLoader(
        //             getClass().getResource("/cinema/views/page_accueil.fxml"));
        //     Parent root = fxmlLoader.load();

        //     AccueilController accueilController = fxmlLoader.getController();
        //     accueilController.setName(nameUti);
        //     accueilController.setBienvenue();

        //     // Créer une nouvelle fenêtre (Stage)
        //     Stage stage = new Stage();
        //     stage.setTitle("Liste franchises");
        //     stage.setScene(new Scene(root));

        //     // Configurer la fenêtre en tant que modal
        //     stage.initModality(Modality.APPLICATION_MODAL);

        //     // Afficher la fenêtre et attendre qu'elle se ferme
        //     stage.show();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    Navigation.goTo("/cinema/views/page_modif_cinema.fxml", "cinema", cinema, bRetour.getScene().getWindow());
                    // Stage stageP = (Stage) bRetour.getScene().getWindow();
                    // stageP.close();

                    // try {
                    //     FXMLLoader fxmlLoader = new FXMLLoader(
                    //             getClass().getResource("/cinema/views/page_modif_cinema.fxml"));
                    //     Parent root = fxmlLoader.load();

                    //     Stage stage = new Stage();
                    //     stage.setTitle("Modification cinema");
                    //     stage.setScene(new Scene(root));

                    //     stage.initModality(Modality.APPLICATION_MODAL);

                    //     stage.show();
                    // } catch (Exception e) {
                    //     e.printStackTrace();
                    // }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void btnSupp() {
        tcSupp.setCellFactory(col -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
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

    private void btnVoirPlus(){
        tcVp.setCellFactory(col -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Voir Les Salles");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    int idCinema = cinema.getIdCinema(); 
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
