package ir.ac.kntu.services;

import ir.ac.kntu.GlobalConstance;
import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.Level;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.operatorGift.GifType;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.scenes.SceneHelper;

import java.util.ArrayList;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.*;

public class GameData {

    private final static GameData INSTANCE = new GameData();

    private GameData() {

    }

    public static GameData getInstance() {
        return INSTANCE;
    }


    private List<SceneObject> sceneObjects = new ArrayList<>();

    private GameStatus gameStatus = GameStatus.Running;

    private ArrayList<Tank> playersTank = new ArrayList<>();

    private ArrayList<Tank> enemyTank = new ArrayList<>();

    private String[][] map = SceneHelper.readMapFile(DEFAULT_MAP_ONE_PLAYER);

    private int score = 0;

    private boolean enemyFreezing = false;

    public boolean sendGift = false;

    public boolean player2Mode = false;

    private Player currentPlayer = null;

    private Level level;


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

    public void resetGame() {
        sceneObjects = new ArrayList<>();
        gameStatus = GameStatus.Running;
        playersTank = new ArrayList<>();
        enemyTank = new ArrayList<>();
        score = 0;
        enemyFreezing = true;
        sendGift = false;
        if (player2Mode) {
            map = SceneHelper.readMapFile(DEFAULT_MAP_TWO_PLAYER);
        } else {
            map = SceneHelper.readMapFile(DEFAULT_MAP_ONE_PLAYER);
        }
    }

    public void resetAll() {
        sceneObjects = new ArrayList<>();
        gameStatus = GameStatus.Running;
        playersTank = new ArrayList<>();
        enemyTank = new ArrayList<>();
        score = 0;
        enemyFreezing = true;
        sendGift = false;
        player2Mode = false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void updateUser() {
        if (currentPlayer != null) {
            currentPlayer.setHighScore(score);
            PlayerService.getINSTANCE().update();
        }
    }

    public void applyGift(GifType gifType) {
        switch (gifType) {
            case Enemy_Freezing -> enemyFreezing = true;
            case Extra_Shot -> GlobalConstance.applyPowerPlayerShotDamage();
            default -> {
                for (Tank tank : playersTank) {
                    tank.setHealth(tank.getHealth() + 1);
                }
            }
        }
    }
}
