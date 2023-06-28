package ir.ac.kntu.gameObjects;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TankHelper {

    private final static String PLAYER_TANK = "src/main/resources/images/tank-yellow/yellow-tank-up.gif";

    private final static String NORMAL_ENEMY = "src/main/resources/images/tank-white/white-tank-up.gif";

    private final static String STRONG_ENEMY = "src/main/resources/images/tank-green/green-tank-up.gif";

    private final static String RANDOM_ENEMY = "src/main/resources/images/tank-red/red-tank-up.gif";

    public static Image attachImage(TankType tankType) {
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
}
