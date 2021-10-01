package assignment.dino;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DinoController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    @FXML
    private ImageView image;
    @FXML
    private BorderPane DinoPortal;
    @FXML
    private Label title;
    @FXML
    private Label about;
    @FXML
    private Button play;
    @FXML
    private Button puase;
    @FXML
    private ComboBox size;
    @FXML
    private TextField name;
    Media media;
    MediaPlayer player;
    OrderedDictionary database = null;
    DinoRecord dino = null;
    int dinoSize = 1;

    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }

    public void find() {
        DataKey key = new DataKey(this.name.getText(), dinoSize);
        try {
            dino = database.find(key);
            showDino();
        } catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }

    public void delete() {
        DinoRecord previousDino = null;
        try {
            previousDino = database.predecessor(dino.getDataKey());
        } catch (DictionaryException ex) {

        }
        DinoRecord nextDino = null;
        try {
            nextDino = database.successor(dino.getDataKey());
        } catch (DictionaryException ex) {

        }
        DataKey key = dino.getDataKey();
        try {
            database.remove(key);
        } catch (DictionaryException ex) {
            System.out.println("Error in delete "+ ex);
        }
        if (database.isEmpty()) {
            this.DinoPortal.setVisible(false);
            displayAlert("No more dinosaur in the database to show");
        } else {
            if (previousDino != null) {
                dino = previousDino;
                showDino();
            } else if (nextDino != null) {
                dino = nextDino;
                showDino();
            }
        }
    }

    private void showDino() {
        play.setDisable(false);
        puase.setDisable(true);
        if (player != null) {
            player.stop();
        }
        String img = dino.getImage();
        Image dinoImage = new Image("file:src/main/resources/assignment/dino/images/" + img);
        image.setImage(dinoImage);
        title.setText(dino.getDataKey().getBirdName());
        about.setText(dino.getAbout());
    }

    private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/main/resources/assignment/dino/images/UNTIcon.png"));
            stage.setTitle("Dictionary Exception");
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }

    public void getSize() {
        switch (this.size.getValue().toString()) {
            case "Small":
                this.dinoSize = 1;
                break;
            case "Medium":
                this.dinoSize = 2;
                break;
            case "Large":
                this.dinoSize = 3;
                break;
            default:
                break;
        }
    }

    public void first() {
        DinoRecord oneDino;
        try {
            oneDino = database.smallest();
            dino = oneDino;
            showDino();
        }
        catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }

    public void last() {
        DinoRecord oneDino;
        try {
            oneDino = database.largest();
            dino = oneDino;
            showDino();
        }
        catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }

    public void next() {
        DinoRecord oneDino;
        try {
            oneDino = database.successor(dino.getDataKey());
            dino = oneDino;
            showDino();
        }catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }

    public void previous() {
        DinoRecord oneDino;
        try {
            oneDino = database.predecessor(dino.getDataKey());
            dino = oneDino;
            showDino();
        }catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }

    public void play() {
        String filename = "src/main/resources/assignment/dino/sounds/" + dino.getSound();
        media = new Media(new File(filename).toURI().toString());
        player = new MediaPlayer(media);
        play.setDisable(true);
        puase.setDisable(false);
        player.play();
    }

    public void puase() {
        play.setDisable(false);
        puase.setDisable(true);
        if (player != null) {
            player.stop();
        }
    }

    public void loadDictionary() {
        Scanner input;
        int line = 0;
        try {
            String dinoName = "";
            String description;
            int size = 0;
            input = new Scanner(new File("Database.txt"));
            while (input.hasNext()) // read until  end of file
            {
                String data = input.nextLine();
                switch (line % 3) {
                    case 0:
                        size = Integer.parseInt(data);
                        break;
                    case 1:
                        dinoName = data;
                        break;
                    default:
                        description = data;
                        database.insert(new DinoRecord(new DataKey(dinoName, size), description, dinoName + ".mp3", dinoName + ".jpg"));
                        break;
                }
                line++;
            }
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: Database.txt");
            System.out.println(e.getMessage());
        } catch (DictionaryException ex) {
            Logger.getLogger(DinoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.DinoPortal.setVisible(true);
        this.first();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = new OrderedDictionary();
        size.setItems(FXCollections.observableArrayList(
                "Small", "Medium", "Large"
        ));
        size.setValue("Small");
    }

}
