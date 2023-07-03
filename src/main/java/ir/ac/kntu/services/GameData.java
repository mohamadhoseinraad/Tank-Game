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

    private GameStatus gameStatus = GameStatus.Stop;

    private ArrayList<Tank> playersTank = new ArrayList<>();

    private ArrayList<Tank> enemyTank = new ArrayList<>();

    private String[][] map = SceneHelper.readMapFile(DEFAULT_MAP_ONE_PLAYER);

    private int score = 0;

    private boolean enemyFreezing = false;

    public boolean sendGift = false;

    public boolean player2Mode = false;

    private Player currentPlayer = null;

    private Level level;

    private int enemyNumber = 4;


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

    public void resetGame(Level level) {
        this.level = level;
        sceneObjects = new ArrayList<>();
        gameStatus = GameStatus.Running;
        playersTank = new ArrayList<>();
        enemyTank = new ArrayList<>();
        score = 0;
        enemyFreezing = true;
        sendGift = false;
        applyLevel(level);
        if (player2Mode && level != null) {
            map = SceneHelper.readMapFile(DEFAULT_MAP_TWO_PLAYER);
        } else {
            map = SceneHelper.readMapFile(DEFAULT_MAP_ONE_PLAYER);
        }
    }

    public void resetAll(Level level) {
        sceneObjects = new ArrayList<>();
        gameStatus = GameStatus.Running;
        playersTank = new ArrayList<>();
        enemyTank = new ArrayList<>();
        score = 0;
        enemyFreezing = true;
        sendGift = false;
        player2Mode = false;
        applyLevel(level);
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

    public int getEnemyNumber() {
        return enemyNumber;
    }

    public void minusEnemyNumber() {
        enemyNumber--;
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

    private void applyLevel(Level level) {
        if (level == null) {
            enemyNumber = 0;
        } else {
            switch (level) {
                case Level_1 -> enemyNumber = 10;
                case Level_2 -> enemyNumber = 14;
                case Level_3 -> enemyNumber = 18;
                case Level_4 -> enemyNumber = 22;
                case Level_5 -> enemyNumber = 26;
                case Level_6 -> enemyNumber = 30;
                case Level_7 -> enemyNumber = 34;
                case Level_8 -> enemyNumber = 38;
                case Level_9 -> enemyNumber = 42;
                case Level_10 -> enemyNumber = 46;
                default -> enemyNumber = 0;
            }
        }
    }
}
