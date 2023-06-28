package ir.ac.kntu.eventHandler;

import ir.ac.kntu.Game;

import ir.ac.kntu.gameObjects.Direction;
import ir.ac.kntu.gameObjects.tank.Tank;
import javafx.scene.input.KeyCode;

import static ir.ac.kntu.GlobalConstance.scale;


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
            player.move(scale, Direction.Down);
        }
        if (keyCode == KeyCode.UP) {
            player.move(scale, Direction.Up);
        }
        if (keyCode == KeyCode.RIGHT) {
            player.move(scale, Direction.Right);
        }
        if (keyCode == KeyCode.LEFT) {
            player.move(scale, Direction.Left);
        }
        if (keyCode == KeyCode.SPACE) {
            player.fire();
        }
    }
}
