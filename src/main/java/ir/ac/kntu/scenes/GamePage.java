package ir.ac.kntu.scenes;

import ir.ac.kntu.EnemyTankMovement;
import ir.ac.kntu.Game;
import ir.ac.kntu.GameData;
import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.gameObjects.CountDownTimer;
import ir.ac.kntu.models.gameObjects.SceneObject;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GamePage {

    private static CountDownTimer countDownTimer;


    public static void countDownTimer(Stage stage, Pane pane, Scene scene, GameData gameData) {
        SceneHelper.conformStage(stage, pane, scene);
        SceneHelper.makeGameScene(pane);
        SceneHelper.readMap(gameData.getMap(), gameData.getSceneObjects());
        countDownTimer = new CountDownTimer(gameData.getSceneObjects());
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    update(gameData);
                    pane.getChildren().clear();
                    SceneHelper.makeGameScene(pane);
                    for (SceneObject sceneObject : gameData.getSceneObjects()) {
                        pane.getChildren().add(sceneObject.getNode());
                    }
                    if (countDownTimer.isEnd()) {
                        this.stop();
                        gameData.setGameStatus(GameStatus.Running);
                        gameLoop(pane, gameData);
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void gameLoop(Pane pane, GameData gameData) {
        enemyTankThread(gameData);
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update(gameData);
                if (currentNanoTime - lastUpdate >= 100_000) {
                    pane.getChildren().clear();
                    SceneHelper.makeGameScene(pane);
                    draw(gameData, pane);
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

    private static void enemyTankThread(GameData gameData) {
        EnemyTankMovement enemyTankMover = new EnemyTankMovement(gameData.getEnemyTank());

        // Start a new thread with the enemyTankMover instance
        Thread enemyTankThread = new Thread(enemyTankMover);
        //enemyTankThread.setDaemon(true);
        enemyTankThread.start();
    }

    private static void draw(GameData gameData, Pane pane) {
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(gameData.getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            pane.getChildren().add(sceneObject.getNode());
        }
    }


    private static void update(GameData gameData) {
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
