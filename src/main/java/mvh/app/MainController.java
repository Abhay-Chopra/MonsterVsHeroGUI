package mvh.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mvh.world.*;

public class MainController {

    //Store the data of editor
    private World world;

    @FXML
    public void helpPopUp(ActionEvent actionEvent) {
        System.out.println("clicked on help");
    }

    @FXML
    private Button addEntity;

    @FXML
    private Button createWorld;

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
    private RadioButton selectHero;

    @FXML
    private RadioButton selectMonster;

    @FXML
    private TextField worldColumns;

    @FXML
    private TextArea worldDetails;

    @FXML
    private TextArea worldMap;

    @FXML
    private TextField worldRows;
}
