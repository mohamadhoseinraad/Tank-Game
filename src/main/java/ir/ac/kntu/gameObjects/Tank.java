package ir.ac.kntu.gameObjects;

import ir.ac.kntu.Game;
import ir.ac.kntu.SceneObject;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Tank implements SceneObject {

    public final static int TANK_SIZE = 50;

    private Image image;

    {
        try {
            image = new Image(new FileInputStream("src/main/resources/images/tank-yellow/yellow-tank-up.gif"));
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }


    private ImageView imageView = new ImageView(image);

    private TankType tankType;

    private TankSide tankSide;

    private int health;

    private Direction direction = Direction.Up;

    private int x;

    private int y;


    public Tank(TankType tankType, TankSide tankSide, int x, int y) {
        imageView.setFitWidth(TANK_SIZE);
        imageView.setFitHeight(TANK_SIZE);
        this.tankType = tankType;
        this.tankSide = tankSide;
        switch (tankType) {
            case Player -> {
                health = 3;
            }
            case NormalEnemy -> {
                health = 1;
            }
            case StrongEnemy -> {
                health = 2;
            }
            default -> {
                health = (new Random().nextInt(1, 2));
            }
        }
        this.x = x;
        this.y = y;
        Game.sceneObjects.add(this);
    }

    public void move() {

    }

    public void handleInput() {

    }

    public void fire() {

    }

    public boolean collidesWith(SceneObject object) {
//            if (object instanceof Shot) {
//                Shot shot = (Shot) object;
//                double tankLeft = x - TANK_WIDTH / 2;
//                double tankRight = x + TANK_WIDTH / 2;
//                double tankTop = y - TANK_HEIGHT / 2;
//                double tankBottom = y + TANK_HEIGHT / 2;
//                double shotLeft = shot.getX() - SHOT_WIDTH / 2;
//                double shotRight = shot.getX() + SHOT_WIDTH / 2;
//                double shotTop = shot.getY() - SHOT_HEIGHT / 2;
//                double shotBottom = shot.getY() + SHOT_HEIGHT / 2;
//                return tankLeft < shotRight && tankRight > shotLeft && tankTop < shotBottom && tankBottom > shotTop;
//            }
        return false;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        if (health <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        imageView.setX(x);
        imageView.setY(y);
    }

    @Override
    public Node getNode() {
        return imageView;
    }
}
