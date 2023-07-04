package ir.ac.kntu.services.threads;

import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.RandGenerate;
import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.gameObjects.Flag;
import ir.ac.kntu.models.SceneObject;
import ir.ac.kntu.models.gameObjects.operatorGift.OperatorGift;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.models.gameObjects.tank.TankSide;
import ir.ac.kntu.models.gameObjects.tank.TankType;
import ir.ac.kntu.models.gameObjects.wall.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.*;
import static ir.ac.kntu.GlobalConstance.scale;

public class AddEnemy extends Thread {


    public AddEnemy() {
    }

    @Override
    public void run() {
        TankType[] tankTypes = {TankType.NormalEnemy, TankType.RandomEnemy, TankType.StrongEnemy};
        while (true) {
            try {
                sleep(1000); // wait for 1 second
                if (GameData.getInstance().getEnemyTank().size() < 4
                        && GameData.getInstance().getEnemyNumber() > 0
                        && GameData.getInstance().getGameStatus() == GameStatus.Running) {
                    Point point = findEmptyPoint();
                    TankType tankType = tankTypes[RandGenerate.getINSTANCE().getRanBetween(0, tankTypes.length)];
                    Tank sceneObject = new Tank(tankType, TankSide.Enemy, point.x, point.y, scale);
                    synchronized (GameData.getInstance().getSceneObjects()) {
                        GameData.getInstance().getSceneObjects().add(sceneObject);
                        synchronized (GameData.getInstance().getEnemyTank()) {
                            GameData.getInstance().getEnemyTank().add(sceneObject);
                        }
                    }
                    GameData.getInstance().minusEnemyNumber();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Point findEmptyPoint() {
        int[][] nowayMap = new int[mapSize][mapSize];
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            if (sceneObject instanceof Wall wall) {
                applyWall(nowayMap, wall);
            } else if (sceneObject instanceof Tank tank) {
                applyTank(nowayMap, tank);
            } else if (sceneObject instanceof Flag flag) {
                applyFlag(nowayMap, flag);
            } else if (sceneObject instanceof OperatorGift gift) {
                applyGift(nowayMap, gift);
            }
        }
        int x, y;
        do {
            x = RandGenerate.getINSTANCE().getRanEnemyLoc();
            y = 0;
        } while (nowayMap[y][x] == 1);
        x = (int) (MAP_FIRST_X + x * scale);
        y = (int) (MAP_FIRST_Y + y * scale);
        return new Point(x, y);
    }

    private static void applyGift(int[][] nowayMap, OperatorGift gift) {
        int j = (int) ((gift.getX() - MAP_FIRST_X) / scale);
        int i = (int) ((gift.getY() - MAP_FIRST_Y) / scale);
        nowayMap[i][j] = 1;
    }

    private static void applyFlag(int[][] nowayMap, Flag flag) {
        int j = (int) ((flag.getX() - MAP_FIRST_X) / scale);
        int i = (int) ((flag.getY() - MAP_FIRST_Y) / scale);
        nowayMap[i][j] = 1;
    }

    private static void applyTank(int[][] nowayMap, Tank tank) {
        int j = (int) ((tank.getX() - MAP_FIRST_X) / scale);
        int i = (int) ((tank.getY() - MAP_FIRST_Y) / scale);
        nowayMap[i][j] = 1;
    }

    private void applyWall(int[][] nowayMap, Wall wall) {
        if (wall.getX() > MAP_FIRST_X && wall.getX() < MAP_FIRST_X + mapHeight
                && wall.getY() > MAP_FIRST_Y && wall.getY() < MAP_FIRST_Y + mapHeight) {
            int j = (int) ((wall.getX() - MAP_FIRST_X) / scale);
            int i = (int) ((wall.getY() - MAP_FIRST_Y) / scale);
            nowayMap[i][j] = 1;
        }
    }


}