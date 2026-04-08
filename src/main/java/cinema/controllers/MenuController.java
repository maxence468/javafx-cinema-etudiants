package cinema.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import cinema.controllers.Navigation;
import javafx.stage.Window;

public class MenuController {

    @FXML
    protected MenuItem bListeFranchise, bAjouterFranchise, bListeCinema, bAjouterCinema, bQuitter, bAccueil,
            bListeSalle,
            bAjouterSalle;

    protected String nameUti;

    @FXML
    public void bQuitterClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void bAccueilClick(ActionEvent event) {
        Window window = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        Navigation.goTo("/cinema/views/page_accueil.fxml",  window);

    }

    @FXML
    public void bListFranchiseClick(ActionEvent event) {
        Window window = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        Navigation.goTo("/cinema/views/page_liste_franchise.fxml", window);
    }

    @FXML
    public void bAjouterFranchiseClick(ActionEvent event) {
        Window window = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        Navigation.goTo("/cinema/views/page_ajout_franchise.fxml", window);
    }

    @FXML
    public void bListeCinemaClick(ActionEvent event) {
        Window window = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        Navigation.goTo("/cinema/views/page_liste_cinema.fxml", window);
    }

    @FXML
    public void bAjouterCinemaClick(ActionEvent event) {
        Window window = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        Navigation.goTo("/cinema/views/page_ajout_cinema.fxml", window);
    }

    @FXML
    public void bListeSalleClick(ActionEvent event) {
        Navigation.removeParam("idCinema");
        Window window = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        Navigation.goTo("/cinema/views/page_liste_salle.fxml", window);
    }

    @FXML
    public void bAjouterSalleClick(ActionEvent event) {
        Window window = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        Navigation.goTo("/cinema/views/page_ajout_salle.fxml", window);
    }
}
