package ir.ac.kntu.models.gameObjects;

import ir.ac.kntu.GameData;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.models.gameObjects.tank.TankSide;
import ir.ac.kntu.models.gameObjects.wall.Wall;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Shot implements SceneObject {

    private final ImageView imageView;

    private final TankSide tankSide;

    private final int damage;

    private Direction direction;

    private double x;

    private double y;

    private final double scale;

    private boolean isCollision = false;


    public Shot(TankSide tankSide, double x, double y, double scale, int damage, Direction direction) {
        this.direction = direction;
        imageView = new ImageView(GameObjectHelper.attachShotImage());
        this.scale = scale;
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
        this.tankSide = tankSide;
        this.x = x;
        this.y = y;
        this.damage = damage;
    }

    public void move(double speed, Direction direction) {
        this.direction = direction;
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
            this.collidesWith(sceneObject);
        }
    }


    public boolean collidesWith(SceneObject object) {

        if (object instanceof Tank) {
            return collisionWithTank(object);
        }
        if (object instanceof Wall) {
            return collisionWithWall(object);
        }
        if (object instanceof Flag) {
            return collisionWithFlag(object);
        }
        return false;
    }

    private boolean collisionWithFlag(SceneObject object) {
        double shotLeft = x;
        double shotRight = x + scale;
        double shotTop = y;
        double shotBottom = y + scale;
        Flag flag = (Flag) object;
        if (tankSide == TankSide.Player) {
            return false;
        }
        double flagScale = flag.getScale();
        double tankLeft = flag.getX();
        double tankRight = flag.getX() + flagScale;
        double tankTop = flag.getY();
        double tankBottom = flag.getY() + flagScale;
        if (shotLeft < tankRight && shotRight > tankLeft && shotTop < tankBottom && shotBottom > tankTop) {
            isCollision = true;
            flag.takeDamage(damage);
            return true;
        }
        return false;
    }

    private boolean collisionWithTank(SceneObject object) {
        double shotLeft = x;
        double shotRight = x + scale;
        double shotTop = y;
        double shotBottom = y + scale;
        Tank tank = (Tank) object;
        if (tank.getTankSide() == tankSide) {
            return false;
        }
        double tankScale = tank.getScale();
        double tankLeft = tank.getX();
        double tankRight = tank.getX() + tankScale;
        double tankTop = tank.getY();
        double tankBottom = tank.getY() + tankScale;
        if (shotLeft < tankRight && shotRight > tankLeft && shotTop < tankBottom && shotBottom > tankTop) {
            isCollision = true;
            tank.takeDamage(damage);
            return true;
        }
        return false;
    }

    private boolean collisionWithWall(SceneObject object) {
        double shotLeft = x;
        double shotRight = x + scale;
        double shotTop = y;
        double shotBottom = y + scale;
        Wall wall = (Wall) object;
        double wallScale = wall.getScale();
        double wallLeft = wall.getX();
        double wallRight = wall.getX() + wallScale;
        double wallTop = wall.getY();
        double wallBottom = wall.getY() + wallScale;
        if (shotLeft < wallRight && shotRight > wallLeft && shotTop < wallBottom && shotBottom > wallTop) {
            isCollision = true;
            wall.takeDamage(damage);
            return true;
        }
        return false;
    }

    @Override
    public boolean isVisible() {
        return !isCollision;
    }

    @Override
    public void update() {
        move(5, direction);
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
