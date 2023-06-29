package ir.ac.kntu.gameObjects;

import ir.ac.kntu.Game;
import ir.ac.kntu.gameObjects.tank.Tank;
import ir.ac.kntu.gameObjects.tank.TankSide;
import ir.ac.kntu.gameObjects.wall.Wall;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Shot implements SceneObject {

    private ImageView imageView;

    private TankSide tankSide;

    private int damage;

    private Direction direction = Direction.Up;

    private double x;

    private double y;

    private double scale;

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
        Game.sceneObjects.add(this);
    }

    public double getScale() {
        return scale;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isCollision() {
        return isCollision;
    }

    public void setCollision(boolean collision) {
        isCollision = collision;
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
        for (SceneObject sceneObject : Game.sceneObjects) {
            this.collidesWith(sceneObject);
        }
    }

    public void handleInput() {

    }


    public boolean collidesWith(SceneObject object) {

        if (object instanceof Tank) {
            return collisionWithTank(object);
        }
        if (object instanceof Wall) {
            return collisionWithWall(object);
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
        if (isCollision) {
            return false;
        }
        return true;
    }

    public boolean isDead() {
        if (damage <= 0) {
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
