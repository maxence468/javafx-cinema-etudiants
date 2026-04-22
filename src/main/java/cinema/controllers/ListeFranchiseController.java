package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Franchise;
import cinema.BO.Cinema;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.CinemaDAO;
import cinema.DAO.UtilisateurDAO;
import cinema.DAO.FranchiseDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class ListeFranchiseController extends MenuController implements Initializable {
    @FXML
    private TableView<Franchise> tvFranchises;

    @FXML
    private TableColumn<Franchise, String> tcNomFranchise;

    @FXML
    private TableColumn<Franchise, String> tcSiegeSocial;

    @FXML
    private TableColumn<Franchise, String> tcGerant;

    @FXML
    private TableColumn<Franchise, Void> tcModifier;

    @FXML
    private TableColumn<Franchise, Void> tcSupprimer;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtilisateurDAO gerantDAO = new UtilisateurDAO();

        // Programmation fonctionnelle
        // Collecteur de flux :
        // https://www.ionos.fr/digitalguide/sites-internet/developpement-web/les-collectors-de-streams-en-java/
        // toMap :
        // https://www.geeksforgeeks.org/java/collectors-tomap-method-in-java-with-examples/
        //
        
        //permet de retrouver rapidement un gérant par son id
        Map<Integer, Utilisateur> gerants = gerantDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Utilisateur::getIdUtilisateur, u -> u));

        //pour chaque ligne du tableau, récupère le gérant depuis la Map via idGerant
        tcGerant.setCellValueFactory(cellData -> {
            Utilisateur gerant = gerants.get(cellData.getValue().getIdGerant());
            return new SimpleStringProperty(
                    gerant != null ? gerant.getNom() + " " + gerant.getPrenom() : "Aucun gérant");
        });
        tcNomFranchise.setCellValueFactory(new PropertyValueFactory<>("nomFranchise"));
        tcSiegeSocial.setCellValueFactory(new PropertyValueFactory<>("siegeSocial"));
        ObservableList<Franchise> data = getFranchiseList();
        tvFranchises.setItems(data);

        addButtonModifierToTable();
        addButtonSupprimerToTable();
    }

    //methode pour recuperer toutes les franchises dans une liste observable
    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList();
        if (franchises != null) {
            list.addAll(franchises);
        }
        return list;
    }

    //retourne à la page precedente
    @FXML
    private void bRetourClick() {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    //methode pour ajouter un bouton permettant de modifier une franchise
    private void addButtonModifierToTable() {
        tcModifier.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("");
            {
                final Image image = new Image(getClass().getResource("/cinema/images/edit_16x16.png").toExternalForm()); 
                final ImageView icon = new ImageView(image); 
                btn.setGraphic(icon);

                btn.setOnAction(event -> {
                    Franchise franchise = getTableView().getItems().get(getIndex());
                    Navigation.goTo("/cinema/views/page_modif_franchise.fxml", "franchise", franchise, bRetour.getScene().getWindow());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    StackPane pane = new StackPane(btn);
                    pane.setAlignment(Pos.CENTER); // centre parfaitement
                    setGraphic(pane);
                }
            }
        });
    }
    //methode pour ajouter un bouton permettant de supprimer une franchise 
    private void addButtonSupprimerToTable() {
        tcSupprimer.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("");
            {
                final Image image = new Image(getClass().getResource("/cinema/images/delete_16x16.png").toExternalForm()); 
                final ImageView icon = new ImageView(image); 
                btn.setGraphic(icon);

                btn.setOnAction(event -> {
                    Franchise franchise = getTableView().getItems().get(getIndex());

                    try {
                        // Charger le fichier FXML pour la pop-up
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cinema/views/popupSuppressionFranchise.fxml"));
                        Parent root = fxmlLoader.load();

                        // Obtenir le contrôleur de la pop-up
                        SuppressionFranchiseController supprController = fxmlLoader.getController();

                        // Passer la franchise et la liste au contrôleur de la pop-up
                        supprController.setFranchise(franchise, tvFranchises);

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
                if (empty) {
                    setGraphic(null);
                } else {
                    StackPane pane = new StackPane(btn);
                    pane.setAlignment(Pos.CENTER); // centre parfaitement
                    setGraphic(pane);
                }
            }
        });
    }

}
