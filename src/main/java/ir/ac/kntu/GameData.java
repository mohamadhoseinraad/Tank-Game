package ir.ac.kntu;

import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.scenes.SceneHelper;

import java.util.ArrayList;
import java.util.List;

public class GameData {

    private final static GameData instance = new GameData();

    private GameData() {

    }

    public static GameData getInstance() {
        return instance;
    }


    private List<SceneObject> sceneObjects = new ArrayList<>();

    private GameStatus gameStatus = GameStatus.Running;

    private ArrayList<Tank> playersTank = new ArrayList<>();

    private ArrayList<Tank> enemyTank = new ArrayList<>();

    private String[][] map = SceneHelper.readMapFile();

    private int score = 0;

    private boolean enemyFreezing = false;


    public ArrayList<Tank> getPlayersTank() {
        return playersTank;
    }

    public ArrayList<Tank> getEnemyTank() {
        return enemyTank;
    }

    public List<SceneObject> getSceneObjects() {
        return sceneObjects;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isEnemyFreezing() {
        return enemyFreezing;
    }

    public void setEnemyFreezing(boolean enemyFreezing) {
        this.enemyFreezing = enemyFreezing;
    }


    public void setSceneObjects(List<SceneObject> sceneObjects) {
        this.sceneObjects = sceneObjects;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setPlayersTank(ArrayList<Tank> playersTank) {
        this.playersTank = playersTank;
    }

    public void setEnemyTank(ArrayList<Tank> enemyTank) {
        this.enemyTank = enemyTank;
    }

    public String[][] getMap() {
        return map;
    }

    public void setMap(String[][] map) {
        this.map = map;
    }
}
