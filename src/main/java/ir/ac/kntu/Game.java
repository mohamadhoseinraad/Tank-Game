package ir.ac.kntu;

import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.gameObjects.CountDownTimer;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.scenes.SceneHelper;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Game extends Application {

    private static Pane pane = new Pane();

    private static Scene scene = new Scene(pane);

    private static CountDownTimer countDownTimer;

    private final GameData gameData = GameData.getInstance();


    public void start(Stage stage) {
        SceneHelper.conformStage(stage, pane, scene);
        //StartMenu.makeMenuScene(stage);
        Button button = new Button("Start");
        pane.getChildren().add(button);
        countDownTimer(stage, button);
        stage.show();


    }

    public void countDownTimer(Stage stage, Button start) {
        start.setOnAction(actionEvent -> {
            SceneHelper.conformStage(stage, pane, scene);
            SceneHelper.makeGameScene(pane);
            SceneHelper.readMap(gameData.getMap(), gameData.getSceneObjects());
            countDownTimer = new CountDownTimer(gameData.getSceneObjects());
            new AnimationTimer() {
                private long lastUpdate = 0;

                public void handle(long currentNanoTime) {
                    if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                        update();
                        pane.getChildren().clear();
                        SceneHelper.makeGameScene(pane);
                        for (SceneObject sceneObject : gameData.getSceneObjects()) {
                            pane.getChildren().add(sceneObject.getNode());
                        }
                        if (countDownTimer.isEnd()) {
                            this.stop();
                            gameData.setGameStatus(GameStatus.Running);
                            startGame(stage);
                        }
                        lastUpdate = currentNanoTime;
                    }
                }
            }.start();
        });
    }

    private void startGame(Stage stage) {
        //SceneHelper.conformStage(stage, pane, scene);
        //SceneHelper.readMap(map);
        stage.show();

        EnemyTankMovement enemyTankMover = new EnemyTankMovement(gameData.getEnemyTank());

        // Start a new thread with the enemyTankMover instance
        Thread enemyTankThread = new Thread(enemyTankMover);
        //enemyTankThread.setDaemon(true);
        enemyTankThread.start();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update();
                if (currentNanoTime - lastUpdate >= 100_000) {
                    pane.getChildren().clear();
                    SceneHelper.makeGameScene(pane);
                    draw();
                    if (gameData.getGameStatus() == GameStatus.Stop) {
                        gameData.setEnemyFreezing(true);
                        if (gameData.getEnemyTank().size() == 0) {
                            SceneHelper.makeEndGameWin(pane);
                        } else {
                            SceneHelper.makeEndGameLose(pane);
                        }
                    }

                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private void draw() {
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(gameData.getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            pane.getChildren().add(sceneObject.getNode());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void update() {
//        Iterator<SceneObject> iterator = sceneObjects.iterator();
//        while (iterator.hasNext()) {
//            SceneObject sceneObject = iterator.next();

        List<SceneObject> copyOfSceneObjects = new ArrayList<>(gameData.getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            if (sceneObject.isVisible()) {
                sceneObject.update();
            } else {
                gameData.getSceneObjects().remove(sceneObject);
            }
        }
        if (gameData.getPlayersTank().size() == 0 || gameData.getEnemyTank().size() == 0) {
            gameData.setGameStatus(GameStatus.Stop);
        }
    }


}