package ir.ac.kntu.models;

import java.io.Serializable;

public class Player implements Serializable {
    private String username;

    private String password;

    private Stage lastStage = Stage.Leve1;

    private int highScore = 0;

    private int countPlay = 0;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void levelUp() {
        switch (lastStage) {
            case Leve1 -> {
                lastStage = Stage.Leve2;
                break;
            }
            case Leve2 -> {
                lastStage = Stage.Leve3;
                break;
            }
            case Leve3 -> {
                lastStage = Stage.Leve4;
                break;
            }
            case Leve4 -> {
                lastStage = Stage.Leve5;
                break;
            }
            case Leve5 -> {
                lastStage = Stage.Leve6;
                break;
            }
            default -> {
                brokeFunction(lastStage);
            }
        }
    }

    private void brokeFunction(Stage stage) {
        switch (stage) {
            case Leve6 -> {
                lastStage = Stage.Leve7;
                break;
            }
            case Leve7 -> {
                lastStage = Stage.Leve8;
                break;
            }
            case Leve8 -> {
                lastStage = Stage.Leve9;
                break;
            }
            case Leve9 -> {
                lastStage = Stage.Leve10;
                break;
            }
            default -> {
            }
        }
    }

    public void addPlay() {
        countPlay++;
    }

    public void setHighScore(int highScore) {
        if (highScore > this.highScore) {
            this.highScore = highScore;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Stage getLastStage() {
        return lastStage;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCountPlay() {
        return countPlay;
    }
}
