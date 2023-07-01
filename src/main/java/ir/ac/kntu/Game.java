package ir.ac.kntu;

import ir.ac.kntu.scenes.GamePage;
import ir.ac.kntu.scenes.SceneHelper;
import ir.ac.kntu.scenes.StartMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Game extends Application {

    private static final Pane PANE = new Pane();

    private static final Scene SCENE = new Scene(PANE);


    private final GameData gameData = GameData.getInstance();


    public void start(Stage stage) {
        SceneHelper.conformStage(stage, PANE, SCENE);
        enemyTankThread(gameData);
        StartMenu.makeMenuScene(stage, PANE, SCENE);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void enemyTankThread(GameData gameData) {
        EnemyTankMovement enemyTankMover = new EnemyTankMovement();

        // Start a new thread with the enemyTankMover instance
        Thread enemyTankThread = new Thread(enemyTankMover);
        //enemyTankThread.setDaemon(true);
        enemyTankThread.start();
    }


}