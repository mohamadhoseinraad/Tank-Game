package ir.ac.kntu;

import ir.ac.kntu.constants.GlobalConstants;
import ir.ac.kntu.gamecontroller.EventHandler;
import ir.ac.kntu.gameobjects.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.Random;

import static ir.ac.kntu.constants.GlobalConstants.*;

public class Game extends Application {

    private static Player player;

    private static Player computer;
    //private static Ball ball;

    private GameState gameState;

    private int playerScore = 0;

    private int computerScore = 0;

    public static void main(String[] args) {
        player = new Player(false);
        computer = new Player(true);
        //ball = new Ball(1, 1);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pong-Game");
        Canvas canvas = new Canvas(CANVAS_WIDTH, GlobalConstants.CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10),
                e -> start(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        Scene scene = new Scene(new Pane(canvas));
        canvas.setOnMouseClicked(e -> gameState = GameState.RUNNING);
        //TODO attach EventHandler
        EventHandler.getInstance().attachEventHandlers(scene);

        stage.setScene(scene);
        stage.show();
        tl.play();
    }

    public void start(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 255, 255));
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        gc.strokeText("Playe 1 score : " + playerScore, 20, 20);
        gc.strokeText("Playe 2 score : " + computerScore, CANVAS_WIDTH - 250, 20);
        if (gameState == GameState.RUNNING) {
            System.out.println("");
//            ball.setXPos(ball.getPositionX() + ball.getXSpeed());
//            ball.setYPos(ball.getPositionY() + ball.getYSpeed());
//            ball.draw(gc);
//            if (ball.getPositionX() < CANVAS_WIDTH - CANVAS_WIDTH / 4) {
//                computer.setYPos(ball.getPositionY() - PLAYER_HEIGHT / 2);
//            } else {
//                computer.setYPos(ball.getPositionY() > computer.getPositionY() + PLAYER_HEIGHT / 2 ?
//                        computer.getPositionY() + 1 : computer.getPositionY() - 1);
//            }
        } else {
            resetPos();
            gc.strokeText("Click", CANVAS_WIDTH / 2 - 30,
                    GlobalConstants.CANVAS_HEIGHT / 2);
        }
        checkScore();
        //reflectionBall();

        player.draw(gc);
        computer.draw(gc);
    }

    private void checkScore() {
//        if (ball.getPositionX() < player.getPositionX() - PLAYER_WIDTH) {
//            computerScore++;
//            gameState = GameState.FINISHED;
//        }
//        if (ball.getPositionX() > computer.getPositionX() + PLAYER_WIDTH) {
//            playerScore++;
//            gameState = GameState.FINISHED;
//        }
    }

    private void resetPos() {
//        ball.setXPos(BALL_FIRST_POS_X);
//        ball.setYPos(BALL_FIRST_POS_Y);
        player.setXPos(PLAYER_FIRST_POS_X);
        player.setYPos(PLAYER_FIRST_POS_Y);
//        ball.setXSpeed((int) (1 * Math.pow(-1, RandomHelper.nextInt())));
//        ball.setYSpeed((int) (1 * Math.pow(-1, RandomHelper.nextInt())));
    }

    public static Player getPlayer() {
        return player;
    }
}