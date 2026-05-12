package cinema.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static String url = "jdbc:postgresql://172.16.102.20:5432/bdd_groupe8?sslmode=disable";

    private static String user = "maxence";

    private static String pass = "maxence";

    private static Connection connect;

    public static Connection getInstance() {
        if (connect == null) {
            try {
                Class.forName("org.postgresql.Driver"); 
                connect = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connect;
    }

    public static void setUtilisateurSession(int idUtilisateur) {
        try {
            Statement st = connect.createStatement();
            st.execute("SET app.id_utilisateur = " + idUtilisateur);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
