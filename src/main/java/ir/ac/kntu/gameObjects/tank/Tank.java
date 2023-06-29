package ir.ac.kntu.gameObjects.tank;

import ir.ac.kntu.Game;
import ir.ac.kntu.gameObjects.*;
import ir.ac.kntu.gameObjects.wall.Wall;
import ir.ac.kntu.gameObjects.wall.WallType;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

import static ir.ac.kntu.GlobalConstance.*;

public class Tank implements SceneObject {

    private ImageView imageView;

    private TankType tankType;

    private TankSide tankSide;

    private int health;

    private int firstHealth;

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
        this.x = x;
        this.y = y;
        switch (tankType) {
            case Player -> {
                health = PLAYER_TANK_HEALTH;
                Game.getPlayersTank().add(this);
            }
            case NormalEnemy -> {
                health = NORMAL_TANK_HEALTH;
                Game.getEnemyTank().add(this);
            }
            case StrongEnemy -> {
                Game.getEnemyTank().add(this);
                health = STRONG_TANK_HEALTH;
            }
            default -> {
                Game.getEnemyTank().add(this);
                health = (new Random().nextInt(NORMAL_TANK_HEALTH, STRONG_TANK_HEALTH));
            }
        }
        firstHealth = health;
        Game.sceneObjects.add(this);
    }

    public double getScale() {
        return scale;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        firstHealth = health;
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
        new Shot(tankSide, x + scale / 2, y + scale / 2, scale / 3, 1, direction);
    }

    public boolean collidesWith(SceneObject object) {
        if (object == this) {
            return false;
        }
        if (object instanceof Tank) {
            return collisionWithTank(object);
        }
        return false;
    }

    public boolean collisionWithTank(SceneObject object) {
        Tank tank = (Tank) object;
        double tankScale = tank.getScale();
        double thisLeft = x;
        double thisRight = x + scale;
        double thisTop = y;
        double thisBottom = y + scale;
        double tankLeft = tank.getX();
        double tankRight = tank.getX() + tankScale;
        double tankTop = tank.getY();
        double tankBottom = tank.getY() + tankScale;
        return thisLeft < tankRight && thisRight > tankLeft && thisTop < tankBottom && thisBottom > tankTop;
    }

    @Override
    public boolean isVisible() {
        if (isDead()) {
            if (tankSide == TankSide.Player) {
                Game.getPlayersTank().remove(this);
            } else {
                if (firstHealth == NORMAL_TANK_HEALTH) {
                    Game.score += 100;
                } else {
                    Game.score += 200;
                }
                Game.getEnemyTank().remove(this);
            }
            return false;
        }
        return true;
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
