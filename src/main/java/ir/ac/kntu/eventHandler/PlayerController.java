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
        Tank[] tanks = Game.getPlayersTank();
        if (tanks.length == 1) {
            Tank player = Game.getPlayersTank()[0];
            handlePlayer1Movements(keyCode, player);
        } else if (tanks.length == 2) {
            Tank player = Game.getPlayersTank()[0];
            handlePlayer1Movements(keyCode, player);
            Tank player2 = Game.getPlayersTank()[1];
            handlePlayer1Movements(keyCode, player2);
        }
    }

    private void handlePlayer1Movements(KeyCode keyCode, Tank player) {
        if (keyCode == KeyCode.DOWN) {
            player.move(scale / 2, Direction.Down);
        }
        if (keyCode == KeyCode.UP) {
            player.move(scale / 2, Direction.Up);
        }
        if (keyCode == KeyCode.RIGHT) {
            player.move(scale / 2, Direction.Right);
        }
        if (keyCode == KeyCode.LEFT) {
            player.move(scale / 2, Direction.Left);
        }
        if (keyCode == KeyCode.SPACE) {
            player.fire();
        }
    }

    private void handlePlayer2Movements(KeyCode keyCode, Tank player) {
        if (keyCode == KeyCode.S) {
            player.move(scale / 2, Direction.Down);
        }
        if (keyCode == KeyCode.W) {
            player.move(scale / 2, Direction.Up);
        }
        if (keyCode == KeyCode.D) {
            player.move(scale / 2, Direction.Right);
        }
        if (keyCode == KeyCode.A) {
            player.move(scale / 2, Direction.Left);
        }
        if (keyCode == KeyCode.Q) {
            player.fire();
        }
    }
}
