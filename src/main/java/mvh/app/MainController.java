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
import mvh.util.Writer;
import mvh.world.*;

import java.io.File;
import java.util.Optional;

/**
 * Controller that handles all connections between stage and Main.fxml
 * @author Abhay Chopra
 * @version v1.0
 * TA: 06 (w/ Amir)
 * April 1st, 2022
 */
public class MainController {

    //Store the data of editor
    private World world;

    /**
     * Creates a new world given row,column size
     * @param ignoredEvent Event when createWorld button clicked
     */
    @FXML
    void createNewWorld(ActionEvent ignoredEvent) {
        try {
            //Getting world size from user
            int rows = Integer.parseInt(worldRows.getText());
            int columns = Integer.parseInt(worldColumns.getText());
            //Confirming world size within intended limits
            if (rows > 25 || columns > 25) {
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
            //Updating Status when handling errors
            leftStatus.setText("World size out of bounds!");
            rightStatus.setText("");
        } catch (IllegalArgumentException e) {
            //Updating Status when handling errors
            leftStatus.setText("Invalid world properties!");
            rightStatus.setText("");
        }

    }

    /**
     * Prints world string to map view of the world (i.e. updates the world view)
     */
    private void updateWorldInfo() {
        worldMap.setText(world.worldString());
        worldMap.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.REGULAR, 14));
    }

    /**
     * Text field for status at bottom right
     */
    @FXML
    private Label rightStatus;

    /**
     * Text field for status at bottom left
     */
    @FXML
    public Label leftStatus;

    /**
     * Button for adding entity to world
     */
    @FXML
    private Button addEntity;

    /**
     * Text field for where entity should be added (column)
     */
    @FXML
    private TextField entityColumn;

    /**
     * Text field for where entity should be added (row)
     */
    @FXML
    private TextField entityRow;

    /**
     * Text field for assigning hero armor value
     */
    @FXML
    private TextField heroArmor;

    /**
     * Text field for assigning hero health value
     */
    @FXML
    private TextField heroHealth;

    /**
     * Text field for assigning hero symbol
     */
    @FXML
    private TextField heroSymbol;

    /**
     * Text field for assigning hero weapon strength
     */
    @FXML
    private TextField heroWeapon;

    /**
     * Text field for assigning monster health value
     */
    @FXML
    private TextField monsterHealth;

    /**
     * Text field for assigning monster symbol
     */
    @FXML
    private TextField monsterSymbol;

    /**
     * Combo Box for getting monster weapon type
     */
    @FXML
    private ComboBox<WeaponType> monsterWeapon;

    /**
     * Text Field intended to get details about world's column size
     */
    @FXML
    private TextField worldColumns;

    /**
     * Text Area intended to print details about world details
     */
    @FXML
    private TextArea worldDetails;

    /**
     * Text Area that is intended to be for showing current world map view
     */
    @FXML
    private TextArea worldMap;

    /**
     * Number of Rows in new World
     */
    @FXML
    private TextField worldRows;

    /**
     * Text Area for World Details
     */
    @FXML
    private TextArea worldDetailsOutput;

    /**
     * Text Field for row location of where to get details for
     */
    @FXML
    private TextField rowDetailLocation;

    /**
     * Text Field for column location of where to get details for
     */
    @FXML
    private TextField columnDetailLocation;

    /**
     * Text Field for column location of where to delete entity
     */
    @FXML
    private TextField deleteColumnLocation;

    /**
     * Text Field for row location of where to delete entity
     */
    @FXML
    private TextField deleteRowLocation;

    /**
     * Uses FileChooser to load a file and uses reader to read from file to create a new world
     * @param ignoredEvent Event on load clicked
     */
    @FXML
    void loadFile(ActionEvent ignoredEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load World File");
        File file = fileChooser.showOpenDialog(new Stage());
        //Handling non-null files, i.e. ignoring when no file chosen
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

    /**
     * Uses FileChooser to save to a file and uses writer to write to file using information from world
     * @param ignoredEvent Event on save clicked
     */
    @FXML
    void saveFile(ActionEvent ignoredEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save World File");
        File file = fileChooser.showSaveDialog(new Stage());
        //Handling non-null files, i.e. ignoring when no file chosen
        if (file != null) {
            try {
                Writer.saveFile(file, world);
                leftStatus.setText("");
                rightStatus.setText("Saved World!");
            }catch (RuntimeException e){
                leftStatus.setText(e.toString());
                rightStatus.setText("");
            }
        }
    }
    /**
     * Alert for when Help clicked (info about program)
     * @param ignoredEvent Event when "About" clicked from menu
     */
    @FXML
    public void helpPopUp(ActionEvent ignoredEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About this Application");
        alert.setContentText("Author: Abhay Chopra\nEmail: abhay.chopra1@ucalgary.ca\nVersion: v1.0\nTA: Amir (Tutorial 06)\nThis is a world editor for Monster Versus Heroes!");
        alert.show();
    }

    /**
     * Handles quit button within menu items
     * @param ignoredEvent event when quit clicked
     */
    @FXML
    void quitWindow(ActionEvent ignoredEvent) {
        //Adding a confirmation to quit program
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Gets information for entity from world given row,column for entity location
     * @param ignoredEvent Event when getDetails button clicked
     */
    @FXML
    void getDetails(ActionEvent ignoredEvent) {
        try{
            //Getting row,column location of where to access details about entity
            int row = Integer.parseInt(rowDetailLocation.getText());
            int column = Integer.parseInt(columnDetailLocation.getText());
            if(world.getEntity(row, column) != null){
                String locationDetail = "";
                //Handling Hero
                if(world.getEntity(row, column).getClass() == Hero.class) {
                    locationDetail = String.format("Type: Hero%nSymbol: %s%nHealth: %s%nWeapon Strength: %s%nArmor Strength: %s", world.getEntity(row, column).getSymbol(), world.getEntity(row, column).getHealth(), world.getEntity(row, column).weaponStrength(), world.getEntity(row, column).armorStrength());
                }
                //Handling Monsters
                else if(world.getEntity(row, column).getClass() == Monster.class){
                    //Getting Weapon Type for monster
                    WeaponType weaponType;
                    int weaponStrength = world.getEntity(row, column).weaponStrength();
                    //WeaponType based on weapon strength
                    if(weaponStrength == 2){
                        weaponType = WeaponType.CLUB;
                    } else if(weaponStrength == 3){
                        weaponType = WeaponType.AXE;
                    } else{
                        weaponType = WeaponType.SWORD;
                    }
                    locationDetail = String.format("Type: Monster%nSymbol: %s%nHealth: %s%nWeapon: %s%nWeapon Strength: %s", world.getEntity(row, column).getSymbol(), world.getEntity(row, column).getHealth(), weaponType.name(), world.getEntity(row, column).weaponStrength());
                }
                worldDetailsOutput.setText(locationDetail);
            }else{
                worldDetailsOutput.setText("No existing entity at location!");
            }
            rightStatus.setText("Details Updated!");
            leftStatus.setText("");
        }
        //Catching exceptions caused by non-ints given for row,column locations
        catch (IllegalArgumentException e){
            leftStatus.setText("Row, Column should be integers!");
            rightStatus.setText("");
        }
        //Catching exceptions caused by location outside current world
        catch (ArrayIndexOutOfBoundsException e){
            leftStatus.setText("Location outside world!");
            rightStatus.setText("");
        }
        //Catching exception caused by no world created
        catch (NullPointerException e){
            leftStatus.setText("No Created World!");
            rightStatus.setText("");
        }
    }

    /**
     * Adding styling to text boxes when hero radio button selected
     * @param ignoredEvent Event when hero radio button selected
     */
    @FXML
    void heroSelected(ActionEvent ignoredEvent) {
        removeMonsterStyling();
        //Adding Styling so that borders are outlined red of various text fields pertaining to hero
        heroSymbol.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        heroHealth.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        heroArmor.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        heroWeapon.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityRow.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityColumn.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
    }
    /**
     * Adding styling to text boxes when monster radio button selected
     * @param ignoredEvent Event when monster radio button selected
     */
    @FXML
    void monsterSelected(ActionEvent ignoredEvent) {
        removeHeroStyling();
        //Adding Styling so that borders are outlined red of various text fields
        monsterHealth.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        monsterSymbol.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityRow.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
        entityColumn.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
    }

    /**
     * Resets styling elements for anything pertaining to monster radio button clicked
     */
    private void removeMonsterStyling() {
        monsterHealth.setStyle(null);
        monsterSymbol.setStyle(null);
        monsterWeapon.setStyle(null);
        entityRow.setStyle(null);
        entityColumn.setStyle(null);
    }

    /**
     * Resets styling elements for anything pertaining to hero radio button clicked
     */
    private void removeHeroStyling() {
        heroSymbol.setStyle(null);
        heroHealth.setStyle(null);
        heroWeapon.setStyle(null);
        heroArmor.setStyle(null);
        entityRow.setStyle(null);
        entityColumn.setStyle(null);
    }

    /**
     * Radio button for hero selection
     */
    @FXML
    private RadioButton selectHero;

    /**
     * Radio button for monster selection
     */
    @FXML
    private RadioButton selectMonster;

    /**
     * Toggle group that restricts one radio button clicked at a time
     */
    @FXML
    private ToggleGroup entityGroup;

    /**
     * Adding entity to world given row/column location, and all relevant info about entity
     * @param ignoredEvent Event when addEntity button clicked
     */
    @FXML
    public void addEntityToWorld(ActionEvent ignoredEvent) {
        try {
            //Handling Monsters
            if (selectMonster.isSelected()) {
                try {
                    //Creating a monster entity using info provided and adding to world
                    Monster monster = new Monster(Integer.parseInt(monsterHealth.getText()), monsterSymbol.getText().charAt(0), monsterWeapon.getValue());
                    world.addEntity(Integer.parseInt(entityRow.getText()), Integer.parseInt(entityColumn.getText()), monster);
                    rightStatus.setText("Added Entity!");
                    leftStatus.setText("");
                }
                //Handling locations outside of world
                catch (ArrayIndexOutOfBoundsException e) {
                    leftStatus.setText("Location Outside World!");
                    rightStatus.setText("");
                }
                catch (StringIndexOutOfBoundsException e){
                    leftStatus.setText("No Symbol Provided!");
                    rightStatus.setText("");
                }
            }
            //Handling Hero
            else if (selectHero.isSelected()) {
                try {
                    //Creating a hero entity using info provided and adding to world
                    Hero hero = new Hero(Integer.parseInt(heroHealth.getText()), heroSymbol.getText().charAt(0), Integer.parseInt(heroWeapon.getText()), Integer.parseInt(heroArmor.getText()));
                    world.addEntity(Integer.parseInt(entityRow.getText()), Integer.parseInt(entityColumn.getText()), hero);
                    rightStatus.setText("Added Entity!");
                    leftStatus.setText("");
                }
                //Handling locations outside of world
                catch (ArrayIndexOutOfBoundsException e) {
                    leftStatus.setText("Location Outside World!");
                    rightStatus.setText("");
                }
                catch (StringIndexOutOfBoundsException e){
                    leftStatus.setText("No Symbol Provided!");
                    rightStatus.setText("");
                }
            }
            //When no given entity type
            else {
                leftStatus.setText("Select Entity Type!");
                rightStatus.setText("");
            }
            updateWorldInfo();
        }
        //Handling exceptions that arise from no world created or invalid information given
        catch (NullPointerException e) {
            leftStatus.setText("No Existing world!");
            rightStatus.setText("");
        } catch (IllegalArgumentException e) {
            leftStatus.setText("Invalid Entries!");
            rightStatus.setText("");
        }
        //Removing styling from fields
        removeHeroStyling();
        removeMonsterStyling();
    }

    /**
     * Adds options to Combo Box for monster weapons
     */
    @FXML
    private void initialize() {
        ObservableList<WeaponType> weapons = FXCollections.observableArrayList(WeaponType.CLUB, WeaponType.AXE, WeaponType.SWORD);
        monsterWeapon.setValue(WeaponType.CLUB);
        monsterWeapon.setItems(weapons);
    }

    /**
     * Deletes entity from world given row,column location
     * @param ignoredEvent Event on deleteEntity button clicked
     */
    @FXML
    void deleteEntity(ActionEvent ignoredEvent) {
        try {
            //Adding entity to given location
            world.addEntity(Integer.parseInt(deleteRowLocation.getText()), Integer.parseInt(deleteColumnLocation.getText()),null);
            //Updating statues and the world map
            updateWorldInfo();
            rightStatus.setText("Deleted Entity!");
            leftStatus.setText("");
        }
        //Handling exceptions generated if location outside world, world not initialized, or invalid row/column given
        catch (IndexOutOfBoundsException e) {
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
