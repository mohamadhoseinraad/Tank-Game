package ir.ac.kntu.models.gameObjects.tank;

import ir.ac.kntu.GameData;
import ir.ac.kntu.models.gameObjects.Shot;
import ir.ac.kntu.models.gameObjects.Direction;
import ir.ac.kntu.models.gameObjects.GameObjectHelper;
import ir.ac.kntu.models.gameObjects.SceneObject;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ir.ac.kntu.GlobalConstance.*;

public class Tank implements SceneObject {

    private final ImageView imageView;

    private final TankType tankType;

    private final TankSide tankSide;

    private int health;

    private int firstHealth;

    private Direction direction = Direction.Up;

    private double x;

    private double y;

    private final double scale;


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
            case Player -> health = PLAYER_TANK_HEALTH;
            case NormalEnemy -> health = NORMAL_TANK_HEALTH;
            case StrongEnemy -> health = STRONG_TANK_HEALTH;
            default -> health = (new Random().nextInt(NORMAL_TANK_HEALTH, STRONG_TANK_HEALTH));
        }
        firstHealth = health;
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

    public boolean move(double speed, Direction direction) {
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
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            if (sceneObject.collidesWith(this) && this != sceneObject) {
                y = oldY;
                x = oldX;
                return false;
            }
        }
        return true;
    }

    public void fire() {
        GameData.getInstance().getSceneObjects().add(new Shot(tankSide, x + scale / 2, y + scale / 2,
                scale / 3, playerShotDamage, direction));
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
                GameData.getInstance().getPlayersTank().remove(this);
            } else {
                if (firstHealth == NORMAL_TANK_HEALTH) {
                    GameData.getInstance().setScore(GameData.getInstance().getScore() + 100);
                } else {
                    GameData.getInstance().setScore(GameData.getInstance().getScore() + 200);
                }
                GameData.getInstance().getEnemyTank().remove(this);
            }
            return false;
        }
        return true;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return (health <= 0);
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }


    public TankSide getTankSide() {
        return tankSide;
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
