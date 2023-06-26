package ir.ac.kntu.gameobjects;

import ir.ac.kntu.constants.Direction;
import ir.ac.kntu.constants.GlobalConstants;
import ir.ac.kntu.gameobjects.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static ir.ac.kntu.constants.GlobalConstants.*;

public class Player implements GameObject {
    private int height;

    private int width;

    private boolean isComputer;

    private double xPos;

    private double yPos;

    private Direction currentDir;

    private int layer;

    private int scale;

    private Direction rotation = Direction.UP;

    private String imageSrcUP = "src/main/resources/images/tank-red/red-tank-up.gif";

    private String imageSrcDown = "src/main/resources/images/tank-red/red-tank-down.gif";

    private String imageSrcRight = "src/main/resources/images/tank-red/red-tank-right.gif";

    private String imageSrcLeft = "src/main/resources/images/tank-red/red-tank-left.gif";

    private String imageSrcCurrent = imageSrcUP;


    public Player(boolean isComputer) {
        this.isComputer = isComputer;
        this.height = PLAYER_HEIGHT;
        this.width = PLAYER_WIDTH;
        if (isComputer) {
            this.xPos = GlobalConstants.COMPUTER_FIRST_POS_X;
            this.yPos = GlobalConstants.COMPUTER_FIRST_POS_Y;
        } else {
            this.xPos = GlobalConstants.PLAYER_FIRST_POS_X;
            this.yPos = GlobalConstants.PLAYER_FIRST_POS_Y;
        }
        this.currentDir = Direction.DOWN;
        this.layer = 1;
        this.scale = 1;
    }

    public void move(int step, Direction direction) {
        if (direction == Direction.DOWN) {
            imageSrcCurrent = imageSrcDown;
            if (yPos < 600) {
                yPos += step;
            }
        }
        if (direction == Direction.UP) {
            imageSrcCurrent = imageSrcUP;
            if (yPos > 0) {
                yPos -= step;
            }

        }
        //TODO complete

    }

    @Override
    public boolean isColliding(GameObject b) {
        if (b.getPositionX() == getPositionX()) {
            if (b.getPositionY() >= getPositionY() && b.getPositionY() <= getPositionY() + PLAYER_HEIGHT) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(imageSrcCurrent));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setLayoutX(300);
        imageView.setLayoutY(400);
        Image img = imageView.snapshot(null, null);
        gc.drawImage(img, xPos, yPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        //TODO complete

    }

    @Override
    public void removeFromScene() {

    }

    @Override
    public double getPositionX() {
        return xPos;
    }

    @Override
    public double getPositionY() {
        return yPos;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public int getScale() {
        return scale;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }
}
