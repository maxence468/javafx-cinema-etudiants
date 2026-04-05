package cinema.BO;

public class Salle{

    private int idSalle;
    private int numero;
    private String description;
    private int nbPlace;
    private int idCinema;

    private String denomination;

    public Salle(int numero, String description, int nbPlace, int idCinema){
        this.numero = numero;
        this.description = description;
        this.nbPlace = nbPlace;
        this.idCinema = idCinema;
    }

     public Salle(int idSalle, int numero, String description, int nbPlace, int idCinema) {
        this.idSalle = idSalle;
        this.numero = numero;
        this.description = description;
        this.nbPlace = nbPlace;
        this.idCinema = idCinema;
    }

    public String getDenomination(){
        return denomination;
    }

    public void setDenomination(String denomination){
        this.denomination = denomination;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public int getIdCinema() {
        return idCinema;
    }

    public void setIdCinema(int idCinema) {
        this.idCinema = idCinema;
    }


}