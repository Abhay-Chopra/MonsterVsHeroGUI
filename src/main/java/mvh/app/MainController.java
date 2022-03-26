package mvh.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import mvh.world.*;

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
            //worldMap.setText(String.format("Successfully created new world of size %sX%s!%n%nAdd Entity's to the world!", rows, columns));
            worldMap.setText(world.worldString());
            worldMap.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.REGULAR, 14));
            //Cleaning Up Text Fields
            worldRows.clear();
            worldColumns.clear();
        }catch (IndexOutOfBoundsException e){
            //TODO Update Status
            System.out.println("Too Big World Size");
        }
        catch (IllegalArgumentException e){
            //TODO Update Status
            System.out.println("Didn't give int!");
        }

    }

    @FXML
    private Button addEntity;

    @FXML
    private TextField entityColumn;

    @FXML
    private TextField entityRow;

    @FXML
    private MenuItem helpMenuItem;

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
    }

    @FXML
    void saveFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("world.txt");
        //TODO Handle save's and cancel
    }

    @FXML
    void quitWindow(ActionEvent event) {
    }

    //Should get details from world, at detailRow, detailColumn
    @FXML
    void getDetails(ActionEvent event) {
    }

    @FXML
    private RadioButton selectHero;

    @FXML
    private RadioButton selectMonster;

    @FXML
    private ToggleGroup entityGroup;
}
