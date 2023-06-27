package ir.ac.kntu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.WINDOWS_HEIGHT;
import static ir.ac.kntu.GlobalConstance.WINDOWS_WIDTH;


public class Game extends Application {

    public static List<SceneObject> sceneObjects = new ArrayList<>();

    public static GameStatus gameStatus = GameStatus.Stop;

    public Pane pane = new Pane();

    public Scene scene = new Scene(pane);

    public void start(Stage stage) {
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        //StartMenu.start(pane);
        makeScene();
        conformStage(stage);
        stage.show();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {

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

    private void makeScene() {
        Rectangle square = new Rectangle(500, 500);
        square.setFill(Color.BLACK);
        square.setStroke(Color.WHITE);
        pane.getChildren().add(square);
        square.setX(50);
        square.setY(25);
    }
}