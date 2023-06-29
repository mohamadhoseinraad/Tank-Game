package ir.ac.kntu.gameObjects;

import ir.ac.kntu.gameObjects.tank.TankType;
import ir.ac.kntu.gameObjects.wall.WallType;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameObjectHelper {

    private static Image playerTank;

    private static Image normalTank = null;

    private static Image randomTank = null;

    private static Image strongTank = null;

    private static Image normalWall = null;

    public static Image normalWallDamaged = null;

    private static Image ironWall = null;

    private static Image shot = null;


    static {
        try {
            playerTank = new Image(new FileInputStream("src/main/resources/images/tank-yellow/yellow-tank-up.gif"));
            normalTank = new Image(new FileInputStream("src/main/resources/images/tank-white/white-tank-up.gif"));
            strongTank = new Image(new FileInputStream("src/main/resources/images/tank-green/green-tank-up.gif"));
            randomTank = new Image(new FileInputStream("src/main/resources/images/tank-red/red-tank-up.gif"));
            normalWall = new Image(new FileInputStream("src/main/resources/images/wall.png"));
            normalWallDamaged = new Image(new FileInputStream("src/main/resources/images/wall-damaged.png"));
            ironWall = new Image(new FileInputStream("src/main/resources/images/wallIron.png"));
            shot = new Image(new FileInputStream("src/main/resources/images/shot/shot-up.gif"));
        } catch (FileNotFoundException e) {
            System.out.println("Error get image " + e);
        }
    }

    public static Image attachTankImage(TankType tankType) {
        switch (tankType) {
            case Player -> {
                return playerTank;
            }
            case StrongEnemy -> {
                return strongTank;
            }
            case RandomEnemy -> {
                return randomTank;
            }
            default -> {
                return normalTank;
            }
        }
    }

    public static Image attachWallImage(WallType wallType) {
        switch (wallType) {
            case Iron -> {
                return ironWall;
            }
            default -> {
                return normalWall;
            }
        }
    }

    public static Image attachShotImage() {
        return shot;
    }
}
