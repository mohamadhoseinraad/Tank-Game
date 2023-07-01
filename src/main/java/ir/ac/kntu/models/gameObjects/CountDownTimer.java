package ir.ac.kntu.models.gameObjects;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static ir.ac.kntu.GlobalConstance.WINDOWS_HEIGHT;
import static ir.ac.kntu.GlobalConstance.WINDOWS_WIDTH;

public class CountDownTimer implements SceneObject {
    private final Text textField = new Text("5");

    private int secondHalf = 4;

    private boolean isEnd = false;

    public CountDownTimer(List<SceneObject> sceneObjects) {
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
        }
        if (secondHalf == -1) {
            isEnd = true;
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
        return !isEnd;
    }
}
