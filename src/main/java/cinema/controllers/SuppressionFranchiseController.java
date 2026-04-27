package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import cinema.BO.Franchise;
import cinema.BO.Cinema;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.CinemaDAO;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;


public class SuppressionFranchiseController implements Initializable {
    @FXML
    private Button ButtonOk, ButtonAnnuler;

    @FXML
    private Text tMessage, tCinemas;

    private Franchise franchise;
    private TableView<Franchise> tvFranchises;

    public void setFranchise(Franchise franchise, TableView<Franchise> tvFranchises){
        this.franchise = franchise;
        this.tvFranchises = tvFranchises;

        String nomFranchise = franchise.getNomFranchise();
        String message = "Voulez-vous vraiment supprimer la franchise "+ nomFranchise +" ? Cela supprimera également le(s) cinéma(s) :";
        tMessage.setText(message);

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findByIdFranchise(franchise.getIdFranchise());
        
        List<String> denominations = new ArrayList<>();
        for(Cinema c: cinemas){
            denominations.add(c.getDenomination());
        }
        String listCinema = String.join(", ", denominations) + " ainsi que toutes les salles de chaque cinéma.";

        if(cinemas.isEmpty()){
            tCinemas.setText("Aucun cinéma associé à cette franchise.");
        }else{
            tCinemas.setText(listCinema.toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                 
    }

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        tvFranchises.getItems().remove(franchise);
        FranchiseDAO franchiseDAO = new FranchiseDAO();
        franchiseDAO.delete(franchise);

        Stage stage = (Stage) ButtonOk.getScene().getWindow();
        stage.close();
    }

    public void ButtonAnnulerOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonAnnuler.getScene().getWindow();
        stage.close();
    }
}
