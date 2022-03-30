package mvh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/**
 * GUI based editor for MonsterVsHero Game simulation
 * @author Abhay Chopra
 * @version 1.1
 * April 1st, 2022
 * TA: 06 (w/ Amir)
 */
public class Main extends Application {

    public static final String version = "1.0";

    /**
     * A program-wide random number generator
     */
    public static Random random = new Random(12345);

    /**
     * Main function
     * @param args CMD args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates stage for GUI portion of MonsterVsHero's
     * @param stage Stage for JavaFx GUI
     * @throws IOException General Exceptions
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        //Students edit here to set up the scene
        stage.setTitle("Monster versus Hero World Editor v1.1");
        stage.setScene(scene);
        stage.show();
    }
}
