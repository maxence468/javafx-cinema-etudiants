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
import cinema.BO.Salle;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;


public class SuppressionCinemaController implements Initializable {
    @FXML
    private Button ButtonOk, ButtonAnnuler;

    @FXML
    private Text tMessage, tSalles;

    private Cinema cinema;
    private TableView<Cinema> tvCinema;

    public void setCinema(Cinema cinema, TableView<Cinema> tvCinema){
        this.cinema = cinema;
        this.tvCinema = tvCinema;

        String nomCinema = cinema.getDenomination();
        String message = "Voulez-vous vraiment supprimer le cinéma "+ nomCinema +" ? Cela supprimera egalement la/les salle(s) :";
        tMessage.setText(message);

        SalleDAO salleDAO = new SalleDAO();
        List<Salle> salles = salleDAO.findByIdCinema(cinema.getIdCinema());
        List<String> numeroSalles = new ArrayList<>();
        for(Salle salle: salles){
            numeroSalles.add(salle.getNumero()+"");
        }
        String listSalle = String.join(", ", numeroSalles);
        
        if(numeroSalles.isEmpty()){
            tSalles.setText("Aucune salle associée à ce cinéma.");
        }else{
            tSalles.setText(listSalle.toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                 
    }

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        //supprime le cinéma de la table et de la BDD
        tvCinema.getItems().remove(cinema);
        CinemaDAO cinemaDAO = new CinemaDAO();
        cinemaDAO.delete(cinema);

        Stage stage = (Stage) ButtonOk.getScene().getWindow();
        stage.close();
    }

    public void ButtonAnnulerOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonAnnuler.getScene().getWindow();
        stage.close();
    }
}
