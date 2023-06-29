package ir.ac.kntu.gameObjects.tank;

import ir.ac.kntu.Game;
import ir.ac.kntu.gameObjects.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

import static ir.ac.kntu.GlobalConstance.scale;

public class Tank implements SceneObject {

    private ImageView imageView;

    private TankType tankType;

    private TankSide tankSide;

    private int health;

    private Direction direction = Direction.Up;

    private double x;

    private double y;

    private double scale;


    public Tank(TankType tankType, TankSide tankSide, double x, double y, double scale) {
        imageView = new ImageView(GameObjectHelper.attachTankImage(tankType));
        this.scale = scale;
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

    public double getScale() {
        return scale;
    }

    public void move(double speed, Direction direction) {
        this.direction = direction;
        double oldY = y;
        double oldX = x;
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
            Tank tank = (Tank) object;
            double tankLeft = x - scale / 2;
            double tankRight = x + scale / 2;
            double tankTop = y - scale / 2;
            double tankBottom = y + scale / 2;
            double shotLeft = tank.getX() - scale / 2;
            double shotRight = tank.getX() + scale / 2;
            double shotTop = tank.getY() - scale / 2;
            double shotBottom = tank.getY() + scale / 2;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public TankSide getTankSide() {
        return tankSide;
    }

    public void setTankSide(TankSide tankSide) {
        this.tankSide = tankSide;
    }

    @Override
    public void update() {
        if (!isDead()) {
            imageView.setX(x);
            imageView.setY(y);
            switch (direction) {
                case Down -> imageView.setRotate(180);
                case Right -> imageView.setRotate(90);
                case Left -> imageView.setRotate(270);
                default -> imageView.setRotate(0);
            }
        } else {
            Game.sceneObjects.remove(this);
        }
    }

    @Override
    public Node getNode() {
        return imageView;
    }
}
