package ir.ac.kntu.GUI;

import ir.ac.kntu.models.Level;
import ir.ac.kntu.GUI.game.GamePage;
import ir.ac.kntu.GUI.start.StartMenu;
import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.threads.ShotSoundPlay;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static ir.ac.kntu.GlobalConstance.*;

public class EndGameScene {
    public static void makeEndGameLose(Pane pane, Stage stage, Scene scene) {
        createLoseMassage(pane);
        new AnimationTimer() {
            int timer = 3;
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    timer--;

                    if (timer == 0) {
                        this.stop();
                        GameData.getInstance().resetAll(null);
                        StartMenu.makeMenuScene(stage, pane, scene);
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void createLoseMassage(Pane pane) {
        Text gameOver = new Text("!DEFEAT!");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + (double) mapHeight / 2 - 150);
        gameOver.setY(MAP_FIRST_Y + (double) mapHeight / 2);
        gameOver.setFill(Color.RED);
        pane.getChildren().add(gameOver);
        ShotSoundPlay.setIsDefeat(true);
    }

    public static void endGameWinMassage(Pane pane, Stage stage, Scene scene, int playerHealth) {
        createMassageWin(pane);
        updateLevel();
        new AnimationTimer() {
            int timer = 3;
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    timer--;

                    if (timer == 0) {
                        this.stop();
                        if (GameData.getInstance().getLevel() == null ||
                                GameData.getInstance().getLevel() == Level.Level_10) {
                            GameData.getInstance().resetAll(null);
                            StartMenu.makeMenuScene(stage, pane, scene);
                        } else {
                            GamePage.countDownTimer(GameData.getInstance().getCurrentPlayer().getLastStage(), stage,
                                    pane, scene, GameData.getInstance(), playerHealth);
                        }
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void createMassageWin(Pane pane) {
        Text gameOver = new Text("VICTORY");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + (double) mapHeight / 2);
        gameOver.setY(MAP_FIRST_Y + (double) mapHeight / 2);
        gameOver.setFill(Color.GREEN);
        pane.getChildren().add(gameOver);
        ShotSoundPlay.setIsVictory(true);
    }

    private static void updateLevel() {
        if (GameData.getInstance().getLevel() != null &&
                GameData.getInstance().getLevel() == GameData.getInstance().getCurrentPlayer().getLastStage()) {
            GameData.getInstance().getCurrentPlayer().levelUp();
        }
    }
}
