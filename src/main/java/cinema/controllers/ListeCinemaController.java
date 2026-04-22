package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.Map;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
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
import javafx.scene.image.Image;
import javafx.beans.property.SimpleStringProperty;

public class ListeCinemaController extends MenuController implements Initializable {

    @FXML
    private TableView<Cinema> tvCinema;

    @FXML
    private TableColumn<Cinema, String> tcDenomination, tcFranchise, tcAdresse, tcVille;

    @FXML
    private TableColumn<Cinema, Void> tcModif, tcSupp, tcVp;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FranchiseDAO franchiseDAO = new FranchiseDAO();
        //permet de retrouver rapidement un gérant par son id
        Map<Integer, Franchise> franchises = franchiseDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Franchise::getIdFranchise, u -> u));

        //pour chaque ligne du tableau, récupère le gérant depuis la Map via idGerant
        tcFranchise.setCellValueFactory(cellData -> {
            Franchise franchise = franchises.get(cellData.getValue().getIdFranchise());
            return new SimpleStringProperty(
                    franchise != null ? franchise.getNomFranchise() : "Aucune franchise");
        });


        tcDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        tcAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tcVille.setCellValueFactory(new PropertyValueFactory<>("ville"));   
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
                    Cinema cinema = getTableView().getItems().get(getIndex());

                    try {
                        // Charger le fichier FXML pour la pop-up
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cinema/views/popupSuppressionCinema.fxml"));
                        Parent root = fxmlLoader.load();

                        // Obtenir le contrôleur de la pop-up
                        SuppressionCinemaController supprController = fxmlLoader.getController();

                        // Passer le cinema et la liste au contrôleur de la pop-up
                        supprController.setCinema(cinema, tvCinema);

                        // Créer une nouvelle fenêtre (Stage)
                        Stage stage = new Stage();
                        stage.setTitle("Validation suppression");
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image(Navigation.class.getResourceAsStream("/cinema/images/cinema_logo.png")));
                        stage.setResizable(false);

                        // Configurer la fenêtre en tant que modal
                        stage.initModality(Modality.APPLICATION_MODAL);

                        // Afficher la fenêtre et attendre qu'elle se ferme
                        stage.showAndWait();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
