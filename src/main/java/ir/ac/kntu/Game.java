package ir.ac.kntu;

import ir.ac.kntu.gameObjects.Tank;
import ir.ac.kntu.gameObjects.TankSide;
import ir.ac.kntu.gameObjects.TankType;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.WINDOWS_HEIGHT;
import static ir.ac.kntu.GlobalConstance.WINDOWS_WIDTH;


public class Game extends Application {

    public static List<SceneObject> sceneObjects = new ArrayList<>();

    public static GameStatus gameStatus = GameStatus.Stop;

    private int score = 0;

    public Pane pane = new Pane();

    public Scene scene = new Scene(pane);

    public void start(Stage stage) {
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        //StartMenu.start(pane);
        makeGameScene();
        makeEndGame();
        conformStage(stage);
        stage.show();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                for (SceneObject sceneObject : sceneObjects) {
                    sceneObject.update();
                }
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    pane.getChildren().clear();
                    makeGameScene();
                    for (SceneObject sceneObject : sceneObjects) {
                        pane.getChildren().add(sceneObject.getNode());
                    }

                    lastUpdate = currentNanoTime;
                }
            }
        }.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void conformStage(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("Tank Game");
        stage.setHeight(WINDOWS_HEIGHT);
        stage.setWidth(WINDOWS_WIDTH);
        stage.setMaxHeight(WINDOWS_HEIGHT);
        stage.setMaxWidth(WINDOWS_WIDTH);
        stage.setMinHeight(WINDOWS_HEIGHT);
        stage.setMinWidth(WINDOWS_WIDTH);
    }

    private void makeGameScene() {
        Rectangle square = new Rectangle(500, 500);
        square.setFill(Color.BLACK);
        square.setStroke(Color.WHITE);
        pane.getChildren().add(square);
        square.setX(50);
        square.setY(25);
        Text scoreTitle = new Text("Score : ");
        scoreTitle.setFont(new Font(40));
        scoreTitle.setX(WINDOWS_WIDTH - 175);
        scoreTitle.setY(WINDOWS_HEIGHT - 100);
        Text currentScore = new Text(String.valueOf(score));
        currentScore.setFont(new Font(40));
        currentScore.setX(WINDOWS_WIDTH - 50);
        currentScore.setY(WINDOWS_HEIGHT - 100);
        pane.getChildren().add(currentScore);
        pane.getChildren().add(scoreTitle);
        Tank player = new Tank(TankType.Player, TankSide.Player, 250, 475);
    }

    private void makeEndGame() {
        Text gameOver = new Text("Game Over");
        gameOver.setFont(new Font(50));
        gameOver.setX(WINDOWS_WIDTH / 2 - 200);
        gameOver.setY(WINDOWS_HEIGHT / 2);
        gameOver.setFill(Color.RED);
        pane.getChildren().add(gameOver);
        gameStatus = GameStatus.Stop;
    }
}