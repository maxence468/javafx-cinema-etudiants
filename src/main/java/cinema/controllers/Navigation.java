package cinema.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Navigation {

    private static Stage primaryStage;
    private static final Stack<String> historique = new Stack<>();
    private static final Map<String, Object> params = new HashMap<>();

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void goTo(String fxmlPath) {
        try {
            if (primaryStage != null) {
                if (!historique.isEmpty() && !historique.peek().equals(fxmlPath)) {
                    historique.push(fxmlPath);
                } else if (historique.isEmpty()) {
                    historique.push(fxmlPath);
                }

                Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlPath));
                primaryStage.setScene(new Scene(root));

                // ✅ Réassigner l’icône
                primaryStage.getIcons().clear();
                primaryStage.getIcons()
                        .add(new Image(Navigation.class.getResourceAsStream("/cinema/images/cinema_logo.png")));

                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goTo(String fxmlPath, String key, Object value) {
        setParam(key, value);
        goTo(fxmlPath);
    }

    public static void goTo(String fxmlPath, Window currentWindow) {
        try {
            if (historique.isEmpty() || !historique.peek().equals(fxmlPath)) {
                historique.push(fxmlPath);
            }

            Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlPath));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            newStage.setTitle(getTitre(fxmlPath));

            // ✅ Ajouter l’icône à la nouvelle fenêtre
            newStage.getIcons()
                    .add(new Image(Navigation.class.getResourceAsStream("/cinema/images/cinema_logo.png")));

            newStage.show();

            if (currentWindow != null) {
                currentWindow.hide();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goTo(String fxmlPath, String key, Object value, Window currentWindow) {
        setParam(key, value);
        goTo(fxmlPath, currentWindow);
    }

    public static void goBack(Window currentWindow) {
        if (historique.size() >= 2) {
            historique.pop();
            String previous = historique.peek();
            goTo(previous, currentWindow);
        }
    }

    public static void clearHistory() {
        historique.clear();
    }

    public static void setParam(String key, Object value) {
        params.put(key, value);
    }

    public static <T> T getParam(String key) {
        return (T) params.get(key);
    }

    public static void clearParams() {
        params.clear();
    }

    // Méthode qui associe un titre à chaque page
    private static String getTitre(String fxmlPath) {
        switch (fxmlPath) {
            case "/cinema/views/page_accueil.fxml":         return "Accueil Gestion de franchises";
            case "/cinema/views/page_connexion.fxml":       return "Connexion";
            case "/cinema/views/page_liste_franchise.fxml": return "Liste des franchises";
            case "/cinema/views/page_ajout_franchise.fxml": return "Ajouter une franchise";
            case "/cinema/views/page_modif_franchise.fxml": return "Modifier une franchise";
            case "/cinema/views/page_modif_cinema.fxml": return "Modifier un cinéma";
            case "/cinema/views/page_liste_cinema.fxml":    return "Liste des cinémas";
            case "/cinema/views/page_ajout_cinema.fxml":    return "Ajouter un cinéma";
            case "/cinema/views/page_liste_salle.fxml":     return "Liste des salles";
            case "/cinema/views/page_ajout_salle.fxml":     return "Ajouter une salle";
            case "/cinema/views/page_modif_salle.fxml": return "Modifier une salle";
            default: return "Gestion de franchises";
    }
}
}
