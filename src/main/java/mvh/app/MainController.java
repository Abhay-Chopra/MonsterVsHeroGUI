package mvh.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvh.util.Reader;
import mvh.world.*;

import java.io.File;
import java.util.Optional;

public class MainController {

    //Store the data of editor
    private World world;

    //Alert for when Help clicked (info about program)
    @FXML
    public void helpPopUp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About this Application");
        alert.setContentText("Author: Abhay Chopra\nEmail: abhay.chopra1@ucalgary.ca\nVersion: v1.0\nThis is a world editor for Monster Versus Heroes!");
        alert.show();
    }

    @FXML
    void createNewWorld(ActionEvent event) {
        try {
            int rows = Integer.parseInt(worldRows.getText());
            int columns = Integer.parseInt(worldColumns.getText());
            if(rows > 20 || columns > 20){throw new IndexOutOfBoundsException();}
            world = new World(rows, columns);
            updateWorldInfo();
            //Cleaning Up Text Fields
            worldRows.clear();
            worldColumns.clear();
            //Updating Status
            leftStatus.setText("World Drawn!");
            rightStatus.setText("");
        }catch (IndexOutOfBoundsException e){
            //TODO Change font color to red. Change alignment of text
            //Updating Status
            rightStatus.setText("Exceeds max world size!");
            leftStatus.setText("");
        }
        catch (IllegalArgumentException e){
            //Updating Status
            rightStatus.setText("Invalid world properties!");
            leftStatus.setText("");
        }

    }

    private void updateWorldInfo() {
        worldMap.setText(world.worldString());
        worldMap.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.REGULAR, 14));
    }

    @FXML
    private Label rightStatus;

    @FXML
    private Label leftStatus;

    @FXML
    private Button addEntity;

    @FXML
    private TextField entityColumn;

    @FXML
    private TextField entityRow;

    @FXML
    private TextField heroArmor;

    @FXML
    private TextField heroHealth;

    @FXML
    private TextField heroSymbol;

    @FXML
    private TextField heroWeapon;

    @FXML
    private TextField monsterHealth;

    @FXML
    private TextField monsterSymbol;

    @FXML
    private ComboBox<?> monsterWeapon;

    @FXML
    private TextField worldColumns;

    @FXML
    private TextArea worldDetails;

    @FXML
    private TextArea worldMap;

    //Number of Rows in new World
    @FXML
    private TextField worldRows;

    //Text Area for World Details
    @FXML
    private TextArea worldDetailsOutput;

    //Methods for loading, saving, and quitting from file
    @FXML
    void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load World File");
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            world = Reader.loadWorld(file);
            updateWorldInfo();
            //Updating Status to reflect change
            leftStatus.setText("Loaded World!");
            rightStatus.setText("");
        }
    }

    @FXML
    void saveFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save World File");
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
            Reader.saveFile(file);
            leftStatus.setText("Saved World to file!");
            rightStatus.setText("");
        }
    }

    @FXML
    void quitWindow(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Should get details from world, at detailRow, detailColumn
    @FXML
    void getDetails(ActionEvent event) {
    }

    @FXML
    void heroSelected(ActionEvent event) {
        removeMonsterStyling();
        //Adding Styling so that borders are outlined red of various text fields
        heroSymbol.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        heroHealth.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        heroArmor.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        heroWeapon.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        entityRow.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        entityColumn.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
    }

    private void removeMonsterStyling() {
        monsterHealth.setStyle(null);
        monsterSymbol.setStyle(null);
        monsterWeapon.setStyle(null);
        entityRow.setStyle(null);
        entityColumn.setStyle(null);
    }

    @FXML
    void monsterSelected(ActionEvent event) {
        removeHeroStyling();
        //Adding Styling so that borders are outlined red of various text fields
        monsterHealth.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        monsterSymbol.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        entityRow.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
        entityColumn.setStyle("-fx-text-box-border: #d45a5a; -fx-focus-color: #d45a5a;");
    }

    private void removeHeroStyling() {
        heroSymbol.setStyle(null);
        heroHealth.setStyle(null);
        heroWeapon.setStyle(null);
        heroArmor.setStyle(null);
        entityRow.setStyle(null);
        entityColumn.setStyle(null);
    }

    @FXML
    private RadioButton selectHero;

    @FXML
    private RadioButton selectMonster;

    @FXML
    private ToggleGroup entityGroup;

    public void addEntityToWorld(ActionEvent actionEvent) {
        //TODO add Entity to World

        //Removing Styling
        removeHeroStyling();
        removeMonsterStyling();
    }
}
