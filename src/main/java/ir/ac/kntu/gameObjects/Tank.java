package ir.ac.kntu.gameObjects;

import ir.ac.kntu.Game;
import ir.ac.kntu.GlobalConstance;
import ir.ac.kntu.SceneObject;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import static ir.ac.kntu.GlobalConstance.scale;

public class Tank implements SceneObject {

    private ImageView imageView;

    private TankType tankType;

    private TankSide tankSide;

    private int health;

    private Direction direction = Direction.Up;

    private int x;

    private int y;


    public Tank(TankType tankType, TankSide tankSide, int x, int y) {
        imageView = new ImageView(TankHelper.attachImage(tankType));
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
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

    public void move(int speed, Direction direction) {
        this.direction = direction;
        int oldY = y;
        int oldX = x;
        if (direction == Direction.Up) {
            y -= speed;
        }
        if (direction == Direction.Down) {
            y += speed;
        }
        if (direction == Direction.Right) {
            x += speed;
        }
        if (direction == Direction.Left) {
            x -= speed;
        }
        for (SceneObject sceneObject : Game.sceneObjects) {
            if (sceneObject.collidesWith(this) && this != sceneObject) {
                y = oldY;
                x = oldX;
            }
        }
    }

    public void handleInput() {

    }

    public void fire() {

    }

    public boolean collidesWith(SceneObject object) {
        if (object instanceof Tank) {
            Tank shot = (Tank) object;
            double tankLeft = x - scale / 2;
            double tankRight = x + scale / 2;
            double tankTop = y - scale / 2;
            double tankBottom = y + scale / 2;
            double shotLeft = shot.getX() - scale / 2;
            double shotRight = shot.getX() + scale / 2;
            double shotTop = shot.getY() - scale / 2;
            double shotBottom = shot.getY() + scale / 2;
            return tankLeft < shotRight && tankRight > shotLeft && tankTop < shotBottom && tankBottom > shotTop;
        }
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void update() {
        imageView.setX(x);
        imageView.setY(y);
        switch (direction) {
            case Down -> imageView.setRotate(180);
            case Right -> imageView.setRotate(90);
            case Left -> imageView.setRotate(270);
            default -> imageView.setRotate(0);
        }
    }

    @Override
    public Node getNode() {
        return imageView;
    }
}
