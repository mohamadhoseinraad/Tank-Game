package ir.ac.kntu.gameObjects;

import ir.ac.kntu.Game;
import ir.ac.kntu.GameStatus;
import ir.ac.kntu.gameObjects.SceneObject;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.WINDOWS_HEIGHT;
import static ir.ac.kntu.GlobalConstance.WINDOWS_WIDTH;

public class CountDownTimer implements SceneObject {
    private Text textField = new Text("5");

    private int secondHalf = 5;

    private int secondStart;

    private boolean isEnd = false;

    public CountDownTimer(List<SceneObject> sceneObjects) {
        secondStart = LocalTime.now().getSecond();
        textField.setFill(Color.WHITE);
        textField.setFont(Font.font(100));
        textField.setLayoutX(WINDOWS_WIDTH / 2 - 50);
        textField.setLayoutY(WINDOWS_HEIGHT / 2 - 50);
        sceneObjects.add(this);
    }


    @Override
    public void update() {
        if (!isEnd) {
            secondHalf -= 1;
            textField.setText(String.valueOf(secondHalf));
        }
        if (secondHalf == 0) {
            textField.setText("Go");
            textField.setFill(Color.GREEN);
            isEnd = true;
            Game.gameStatus = GameStatus.Start;
        }
    }

    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public Node getNode() {
        return textField;
    }

    @Override
    public boolean collidesWith(SceneObject object) {
        return false;
    }

    @Override
    public boolean isVisible() {
        if (isEnd) {
            return false;
        }
        return true;
    }
}
