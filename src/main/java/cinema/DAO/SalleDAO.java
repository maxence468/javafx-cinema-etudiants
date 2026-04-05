package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Salle;

public class SalleDAO extends DAO<Salle> {

    @Override
    public boolean create(Salle obj) {
        boolean result = false;
        try {
            String query = "INSERT INTO salle (numero, description, nb_places, id_cinema) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setInt(1, obj.getNumero());
            preparedStatement.setString(2, obj.getDescription());
            preparedStatement.setInt(3, obj.getNbPlace());
            preparedStatement.setInt(4, obj.getIdCinema());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Salle obj) {
        boolean result = false;
        String query = "DELETE FROM salle WHERE id_salle = ?;";

        try (PreparedStatement preparedStatement = this.connect.prepareStatement(query)) {
            preparedStatement.setInt(1, obj.getIdSalle());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean update(Salle obj) {
        boolean result = false;
        String query = "UPDATE salle SET numero = ?, description = ?, nb_places = ?, id_cinema = ? WHERE id_salle = ?;";
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setInt(1, obj.getNumero());
            preparedStatement.setString(2, obj.getDescription());
            preparedStatement.setInt(3, obj.getNbPlace());
            preparedStatement.setInt(4, obj.getIdCinema());
            preparedStatement.setInt(5, obj.getIdSalle());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Salle find(int id) {
        Salle salle = null;
        String query = "SELECT * FROM salle WHERE id_salle = ?;";
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                salle = new Salle(
                        resultSet.getInt("id_salle"),
                        resultSet.getInt("numero"),
                        resultSet.getString("description"),
                        resultSet.getInt("nb_places"),
                        resultSet.getInt("id_cinema"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salle;
    }

    @Override
    public List<Salle> findAll() {
        List<Salle> salles = new ArrayList<Salle>();
        String query = "SELECT * FROM salle s inner join cinema c on s.id_cinema = c.id_cinema;";

        try (PreparedStatement preparedStatement = this.connect.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Salle salle = new Salle(
                        resultSet.getInt("id_salle"),
                        resultSet.getInt("numero"),
                        resultSet.getString("description"),
                        resultSet.getInt("nb_places"),
                        resultSet.getInt("id_cinema"));
                        
                        salle.setDenomination(resultSet.getString("denomination"));

                salles.add(salle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salles;
    }

}
