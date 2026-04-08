package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.DAO.UtilisateurDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnexionController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfMDP;
    @FXML
    private Button bConnexion;

    @FXML
    public void bConnexionClick(ActionEvent event) {
        String login = tfLogin.getText();
        String mdp = tfMDP.getText();
        
        //si un des champs est vide, affiche la fenetre d'erreur
        if(login == null || login.trim().isEmpty() ||
            mdp == null || mdp.trim().isEmpty()){
            showError();
            return;
        }
        

        UtilisateurDAO userDAO = new UtilisateurDAO();
        Utilisateur user = userDAO.authenticate(login, mdp);
        //verifie si l'utilisateur a été trouvé 
        if(user != null){
            showAccueil(user.getNom());
        }
        else{
            showError();
        }
    }

    //affiche la page d'accueil
    private void showAccueil(String name) {
        Navigation.setParam("nameUti", name);
        Navigation.goTo("/cinema/views/page_accueil.fxml", bConnexion.getScene().getWindow());
    }

    //affiche une fenetre d'erreur
    @FXML
    private void showError() {
        try {
            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/ErreurConnexion.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la pop-up
            ErrorController errorController = fxmlLoader.getController();

            // Passer la variable au contrôleur de la pop-up
            // errorController.setMajLabel(Integer.toString(compteur));

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Error Window");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(Navigation.class.getResourceAsStream("/cinema/images/cinema_logo.png")));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
