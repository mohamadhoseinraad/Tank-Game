package ir.ac.kntu.scenes;

import ir.ac.kntu.services.GameData;
import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.Level;
import ir.ac.kntu.models.gameObjects.CountDownTimer;
import ir.ac.kntu.models.gameObjects.SceneObject;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.customMap;

public class GamePage {

    private static CountDownTimer countDownTimer;


    public static void countDownTimer(Level level, Stage stage, Pane pane, Scene scene, GameData gameData, int health) {
        SceneHelper.conformStage(stage, pane, scene);
        SceneHelper.makeGameScene(pane);
        gameData.resetGame(level);
        gameData.setGameStatus(GameStatus.Start);
        if (level == null) {
            gameData.setMap(SceneHelper.readMapFile(customMap));
        }
        SceneHelper.readMap(gameData.getMap(), gameData.getSceneObjects(), health);
        countDownTimer = new CountDownTimer(gameData.getSceneObjects());
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    update(gameData);
                    draw(gameData, pane);
                    if (countDownTimer.isEnd()) {
                        this.stop();
                        gameData.setEnemyFreezing(false);
                        gameLoop(pane, gameData, stage, scene);
                        gameData.setGameStatus(GameStatus.Running);
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void gameLoop(Pane pane, GameData gameData, Stage stage, Scene scene) {
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update(gameData);
                if (currentNanoTime - lastUpdate >= 100_000) {
                    draw(gameData, pane);
                    if (gameData.getGameStatus() == GameStatus.Stop) {
                        gameData.setEnemyFreezing(true);
                        GameData.getInstance().updateUser();
                        this.stop();
                        if (gameData.getEnemyNumber() == 0 && gameData.getEnemyTank().size() == 0) {
                            SceneHelper.makeEndGameWin(pane, stage, scene, gameData.getPlayersTank().get(0).getHealth());
                        } else {
                            SceneHelper.makeEndGameLose(pane, stage, scene);
                        }
                    }

                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void draw(GameData gameData, Pane pane) {
        pane.getChildren().clear();
        SceneHelper.makeGameScene(pane);
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
        if (gameData.getPlayersTank().size() == 0 ||
                !GameData.getInstance().getPlayersFlag().isVisible() ||
                (gameData.getEnemyTank().size() == 0
                        && gameData.getEnemyNumber() == 0)) {
            gameData.setGameStatus(GameStatus.Stop);
        }
    }

}
