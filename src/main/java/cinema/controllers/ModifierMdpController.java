package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
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
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class ModifierMdpController extends MenuController implements Initializable {

    @FXML
    private PasswordField tfOldMdp, tfNewMdp, tfNewConfirm;
    @FXML
    private Button bRetour;
    @FXML
    private Label lbFeedback;
    
    private int idUser;

    private int longueurMdp = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {      
        //recupere l'utilisateur à modifier
        idUser = Navigation.getParam("idUser");        
    }

    //verifie si les données sont valides et enregistre les modifications
    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String oldMdp = tfOldMdp.getText();
        String newMdp = tfNewMdp.getText();
        String newConfirm = tfNewConfirm.getText();

        if (oldMdp != null && newMdp != null && newConfirm != null && !oldMdp.trim().isEmpty()
                && !newMdp.trim().isEmpty() && !newConfirm.trim().isEmpty()) {

            UtilisateurDAO userDAO = new UtilisateurDAO();
            Utilisateur user = userDAO.find(idUser);
            String mdpHash = userDAO.getMdpBylogin(user.getLogin());

            if(!BCrypt.checkpw(oldMdp, mdpHash)){
                wrongOldMdp();
                return;
            }
            
            if(!newMdp.equals(newConfirm)){
                erreur2MdpDiff();
                return;
            }

            if(newMdp.length() < this.longueurMdp){
                erreurLongueurMdp();
                return;
            }

            user.setMdp(newMdp);

            boolean controle = userDAO.updateMdp(user);
            if (controle) {
                afficherSuccess();
                tfOldMdp.clear();
                tfNewMdp.clear();
                tfNewConfirm.clear();
            }
        }
        else{
            afficherErreur();
        }
    }

    //retourne à la page precedente
    @FXML
    private void bRetourClick(ActionEvent event) {
        Navigation.goBack(bRetour.getScene().getWindow());
    }

    //affiche un message d'erreur en rouge
    public void afficherErreur(){
        lbFeedback.setText("Tous les champs doivent être remplis");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }

    //affiche un message de validation en vert
    public void afficherSuccess(){
        lbFeedback.setText("Mot de passe modifié avec succès");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: green;");
    }

    public void erreur2MdpDiff(){
        lbFeedback.setText("Les nouveaux mots de passe ne correspondent pas");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }

    public void wrongOldMdp(){
        lbFeedback.setText("L'ancien mot de passe est incorrect");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }
    
    public void erreurLongueurMdp(){
        lbFeedback.setText("Le mot de passe doit contenir au moins"+ this.longueurMdp +"caractères");
        lbFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
    }

}
