package ir.ac.kntu;

import ir.ac.kntu.scenes.SceneHelper;
import ir.ac.kntu.scenes.start.StartMenu;
import ir.ac.kntu.services.threads.AddEnemy;
import ir.ac.kntu.services.threads.EnemyTankMovement;
import ir.ac.kntu.services.threads.GiveGift;
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
        giveGift();
        addEnemy();
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

    public static void giveGift() {
        GiveGift giveGift = new GiveGift();
        Thread giveGiftThread = new Thread(giveGift);
        giveGiftThread.start();
    }

    public static void addEnemy() {
        AddEnemy addEnemy = new AddEnemy();
        Thread addEnemyThread = new Thread(addEnemy);
        addEnemyThread.start();
    }

}