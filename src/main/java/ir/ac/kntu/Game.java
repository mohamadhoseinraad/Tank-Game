package ir.ac.kntu;

import ir.ac.kntu.scenes.SceneHelper;
import ir.ac.kntu.scenes.StartMenu;
import ir.ac.kntu.threads.EnemyTankMovement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Game extends Application {

    private static final Pane PANE = new Pane();

    private static final Scene SCENE = new Scene(PANE);


    public void start(Stage stage) {
        SceneHelper.conformStage(stage, PANE, SCENE);
        enemyTankThread();
        StartMenu.makeMenuScene(stage, PANE, SCENE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void enemyTankThread() {
        EnemyTankMovement enemyTankMover = new EnemyTankMovement();
        Thread enemyTankThread = new Thread(enemyTankMover);
        enemyTankThread.start();
    }


}