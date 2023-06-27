package ir.ac.kntu;

import ir.ac.kntu.Game;

import ir.ac.kntu.gameObjects.Direction;
import ir.ac.kntu.gameObjects.Tank;
import javafx.scene.input.KeyCode;


public class PlayerController {

    private static PlayerController instance = new PlayerController();

    public static PlayerController getInstance() {
        return instance;
    }

    private PlayerController() {
    }

    public void handlePlayerMovements(KeyCode keyCode) {
        //TODO set controller
        Tank player = Game.getPlayer();
        if (keyCode == KeyCode.DOWN) {
            player.move(50, Direction.Down);
        }
        if (keyCode == KeyCode.UP) {
            player.move(50, Direction.Up);
        }
        if (keyCode == KeyCode.RIGHT) {
            player.move(50, Direction.Right);
        }
        if (keyCode == KeyCode.LEFT) {
            player.move(50, Direction.Left);
        }
    }
}
