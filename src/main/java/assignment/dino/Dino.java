package assignment.dino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Ouda
 */
public class Dino extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("birds-view.fxml"));

        Scene scene = new Scene(root);

        stage.getIcons().add(new Image("file:src/main/resources/assignment/dino/images/UNTIcon.png"));
        stage.setTitle("Dino Portal");

        stage.setScene(scene);
        stage.show();


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
