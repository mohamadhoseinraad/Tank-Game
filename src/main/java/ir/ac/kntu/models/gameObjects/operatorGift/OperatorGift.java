package ir.ac.kntu.models.gameObjects.operatorGift;

import ir.ac.kntu.services.GameData;
import ir.ac.kntu.models.gameObjects.GameObjectHelper;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.models.gameObjects.tank.TankSide;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class OperatorGift implements SceneObject {

    private final ImageView imageView;


    private final GifType gifType;


    private final double x;

    private final double y;

    private final double scale;

    private boolean isCollision = false;

    private int startTime = 5;

    private boolean expired = false;


    public OperatorGift(double x, double y, double scale, GifType gifType) {
        imageView = new ImageView(GameObjectHelper.attachGiftImage(gifType));
        this.scale = scale;
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
        imageView.setY(y);
        imageView.setX(x);
        this.x = x;
        this.y = y;
        this.gifType = gifType;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public void update() {

    }

    @Override
    public Node getNode() {
        return imageView;
    }

    @Override
    public boolean collidesWith(SceneObject object) {
        double shotLeft = x;
        double shotRight = x + scale;
        double shotTop = y;
        double shotBottom = y + scale;
        Tank tank = (Tank) object;
        if (tank.getTankSide() != TankSide.Player) {
            return false;
        }
        double tankScale = tank.getScale();
        double tankLeft = tank.getX();
        double tankRight = tank.getX() + tankScale;
        double tankTop = tank.getY();
        double tankBottom = tank.getY() + tankScale;
        if (shotLeft < tankRight && shotRight > tankLeft && shotTop < tankBottom && shotBottom > tankTop) {
            isCollision = true;
            GameData.getInstance().applyGift(gifType);
            return true;
        }
        return false;
    }

    @Override
    public boolean isVisible() {
        if (isCollision) {
            return false;
        }
        return !expired;
    }
}
