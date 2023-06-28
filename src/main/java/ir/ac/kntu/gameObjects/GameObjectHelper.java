package ir.ac.kntu.gameObjects;

import ir.ac.kntu.gameObjects.tank.TankType;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameObjectHelper {

    private final static String PLAYER_TANK = "src/main/resources/images/tank-yellow/yellow-tank-up.gif";

    private final static String NORMAL_ENEMY = "src/main/resources/images/tank-white/white-tank-up.gif";

    private final static String STRONG_ENEMY = "src/main/resources/images/tank-green/green-tank-up.gif";

    private final static String RANDOM_ENEMY = "src/main/resources/images/tank-red/red-tank-up.gif";

    private final static String NORMAL_WALL = "src/main/resources/images/wall.png";

    private final static String IRON_WALL = "src/main/resources/images/wallIron.png";

    public static Image attachTankImage(TankType tankType) {
        String src;
        switch (tankType) {
            case Player -> src = PLAYER_TANK;
            case StrongEnemy -> src = STRONG_ENEMY;
            case RandomEnemy -> src = RANDOM_ENEMY;
            default -> src = NORMAL_ENEMY;
        }
        {
            try {
                return new Image(new FileInputStream(src));
            } catch (FileNotFoundException e) {
                System.out.println(e);
                return null;
            }
        }
    }

    public static Image attachWallImage(WallType wallType) {
        String src;
        switch (wallType) {
            case Iron -> src = IRON_WALL;
            default -> src = NORMAL_WALL;
        }
        {
            try {
                return new Image(new FileInputStream(src));
            } catch (FileNotFoundException e) {
                System.out.println(e);
                return null;
            }
        }
    }
}
