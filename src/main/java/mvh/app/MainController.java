package mvh.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import mvh.world.*;

import java.io.File;
import java.util.Optional;

/**
 * Controller that handles all connections between stage and Main.fxml
 * @author Abhay Chopra
 * @version v1.0
 */
public class MainController {

    //Store the data of editor
    private World world;

    //Alert for when Help clicked (info about program)
    @FXML
    public void helpPopUp(ActionEvent ignoredEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About this Application");
        alert.setContentText("Author: Abhay Chopra\nEmail: abhay.chopra1@ucalgary.ca\nVersion: v1.0\nTA: Amir (Tutorial 06)\nThis is a world editor for Monster Versus Heroes!");
        alert.show();
    }

    @FXML
    void createNewWorld(ActionEvent ignoredEvent) {
        try {
            int rows = Integer.parseInt(worldRows.getText());
            int columns = Integer.parseInt(worldColumns.getText());
            if (rows > 20 || columns > 20) {
                throw new IndexOutOfBoundsException();
            }
            world = new World(rows, columns);
            updateWorldInfo();
            //Cleaning Up Text Fields
            worldRows.clear();
            worldColumns.clear();
            //Updating Status
            rightStatus.setText("World Drawn!");
            leftStatus.setText("");
        } catch (IndexOutOfBoundsException e) {
            //Updating Status
            leftStatus.setText("Exceeds max world size!");
            rightStatus.setText("");
        } catch (IllegalArgumentException e) {
            //Updating Status
            leftStatus.setText("Invalid world properties!");
            rightStatus.setText("");
        }

    }

    private void updateWorldInfo() {
        worldMap.setText(world.worldString());
        worldMap.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.REGULAR, 14));
    }

    @FXML
    private Label rightStatus;

    @FXML
    public Label leftStatus;

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
    private ComboBox<WeaponType> monsterWeapon;

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

    @FXML
    private TextField rowDetailLocation;

    @FXML
    private TextField columnDetailLocation;

    @FXML
    private TextField deleteColumnLocation;

    @FXML
    private TextField deleteRowLocation;

    //Methods for loading, saving, and quitting from file

    /**
     * Uses FileChooser to load a file and uses reader to read from file to create a new world
     * @param ignoredEvent Event on load clicked
     */
    @FXML
    void loadFile(ActionEvent ignoredEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load World File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                world = Reader.loadWorld(file);
                updateWorldInfo();
                //Updating Status to reflect change
                rightStatus.setText("Loaded World!");
                leftStatus.setText("");
            }catch (RuntimeException e){
                leftStatus.setText(e.getMessage());
            }
        }
    }

    @FXML
    void saveFile(ActionEvent ignoredEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save World File");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                Reader.saveFile(file, world);
                leftStatus.setText("");
                rightStatus.setText("Saved World!");
            }catch (RuntimeException e){
                leftStatus.setText(e.toString());
                rightStatus.setText("");
            }
        }
    }

    @FXML
    void quitWindow(ActionEvent ignoredEvent) {
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
    void getDetails(ActionEvent ignoredEvent) {
        try{
            int row = Integer.parseInt(rowDetailLocation.getText());
            int column = Integer.parseInt(columnDetailLocation.getText());
            if(world.getEntity(row, column) != null){
                String locationDetail = "";
                if(world.getEntity(row, column).getClass() == Hero.class) {
                    locationDetail = String.format("Type: Hero%nSymbol: %s%nHealth: %s%nWeapon Strength: %s%nArmor Strength: %s", world.getEntity(row, column).getSymbol(), world.getEntity(row, column).getHealth(), world.getEntity(row, column).weaponStrength(), world.getEntity(row, column).armorStrength());
                }else if(world.getEntity(row, column).getClass() == Monster.class){
                    //Getting Weapon Type for Monster
                    WeaponType weaponType;
                    int weaponStrength = world.getEntity(row, column).weaponStrength();
                    if(weaponStrength == 2){
                        weaponType = WeaponType.CLUB;
                    }else if(weaponStrength == 3){
                        weaponType = WeaponType.AXE;
                    }else{
                        weaponType = WeaponType.SWORD;
                    }
                    locationDetail = String.format("Type: Monster%nSymbol: %s%nHealth: %s%nWeapon: %s%nWeapon Strength: %s", world.getEntity(row, column).getSymbol(), world.getEntity(row, column).getHealth(), weaponType.name(), world.getEntity(row, column).weaponStrength());
                }
                worldDetailsOutput.setText(locationDetail);
            }else{
                worldDetailsOutput.setText("No existing entity at location!");
            }
            leftStatus.setText("");
            rightStatus.setText("Details Updated!");
        }catch (IllegalArgumentException e){
            leftStatus.setText("Row, Column should be integers!");
            rightStatus.setText("");
        }catch (ArrayIndexOutOfBoundsException e){
            leftStatus.setText("Location outside world!");
            rightStatus.setText("");
        }catch (NullPointerException e){
            leftStatus.setText("No Created World!");
            rightStatus.setText("");
        }
    }

    @FXML
    void heroSelected(ActionEvent ignoredEvent) {
        removeMonsterStyling();
        //Adding Styling so that borders are outlined red of various text fields
        heroSymbol.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        heroHealth.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        heroArmor.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        heroWeapon.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityRow.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityColumn.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
    }

    @FXML
    void monsterSelected(ActionEvent ignoredEvent) {
        removeHeroStyling();
        //Adding Styling so that borders are outlined red of various text fields
        monsterHealth.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        monsterSymbol.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityRow.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityColumn.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
    }

    private void removeMonsterStyling() {
        monsterHealth.setStyle(null);
        monsterSymbol.setStyle(null);
        monsterWeapon.setStyle(null);
        entityRow.setStyle(null);
        entityColumn.setStyle(null);
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

    @FXML
    public void addEntityToWorld(ActionEvent ignoredEvent) {
        leftStatus.setText("");
        try {
            if (selectMonster.isSelected()) {
                try {
                    Monster monster = new Monster(Integer.parseInt(monsterHealth.getText()), monsterSymbol.getText().charAt(0), monsterWeapon.getValue());
                    world.addEntity(Integer.parseInt(entityRow.getText()), Integer.parseInt(entityColumn.getText()), monster);
                } catch (ArrayIndexOutOfBoundsException e) {
                    leftStatus.setText("Outside World!");
                    rightStatus.setText("");
                }

            } else if (selectHero.isSelected()) {
                try {
                    Hero hero = new Hero(Integer.parseInt(heroHealth.getText()), heroSymbol.getText().charAt(0), Integer.parseInt(heroWeapon.getText()), Integer.parseInt(heroArmor.getText()));
                    world.addEntity(Integer.parseInt(entityRow.getText()), Integer.parseInt(entityColumn.getText()), hero);
                } catch (ArrayIndexOutOfBoundsException e) {
                    leftStatus.setText("Outside World!");
                    rightStatus.setText("");
                }
            } else {
                leftStatus.setText("Select Entity Type!");
                rightStatus.setText("");
            }
            updateWorldInfo();
            rightStatus.setText("Added Entity!");
        } catch (NullPointerException e) {
            leftStatus.setText("No Existing world!");
            rightStatus.setText("");
        } catch (IllegalArgumentException e) {
            leftStatus.setText("Invalid Entries!");
            rightStatus.setText("");
        }
        //Removing Styling
        removeHeroStyling();
        removeMonsterStyling();
    }

    @FXML
    private void initialize() {
        ObservableList<WeaponType> weapons = FXCollections.observableArrayList(WeaponType.CLUB, WeaponType.AXE, WeaponType.SWORD);
        monsterWeapon.setValue(WeaponType.CLUB);
        monsterWeapon.setItems(weapons);
    }

    @FXML
    void deleteEntity(ActionEvent ignoredEvent) {
        try {
            world.addEntity(Integer.parseInt(deleteRowLocation.getText()), Integer.parseInt(deleteColumnLocation.getText()),null);
            updateWorldInfo();
            rightStatus.setText("Deleted Entity!");
            leftStatus.setText("");
        }catch (IndexOutOfBoundsException e) {
            leftStatus.setText("Deletion locations outside world!");
            rightStatus.setText("");
        }catch (IllegalArgumentException e){
            leftStatus.setText("Deletion locations should be integers!");
            rightStatus.setText("");
        }catch (NullPointerException e){
            leftStatus.setText("No World created!");
            rightStatus.setText("");
        }
    }
}
