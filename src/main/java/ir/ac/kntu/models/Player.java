package ir.ac.kntu.models;

import java.io.Serializable;

public class Player implements Serializable {
    private String username;

    private String password;

    private Level lastLevel = Level.Leve1;

    private int highScore = 0;

    private int countPlay = 0;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void levelUp() {
        switch (lastLevel) {
            case Leve1 -> {
                lastLevel = Level.Leve2;
                break;
            }
            case Leve2 -> {
                lastLevel = Level.Leve3;
                break;
            }
            case Leve3 -> {
                lastLevel = Level.Leve4;
                break;
            }
            case Leve4 -> {
                lastLevel = Level.Leve5;
                break;
            }
            case Leve5 -> {
                lastLevel = Level.Leve6;
                break;
            }
            default -> {
                brokeFunction(lastLevel);
            }
        }
    }

    private void brokeFunction(Level level) {
        switch (level) {
            case Leve6 -> {
                lastLevel = Level.Leve7;
                break;
            }
            case Leve7 -> {
                lastLevel = Level.Leve8;
                break;
            }
            case Leve8 -> {
                lastLevel = Level.Leve9;
                break;
            }
            case Leve9 -> {
                lastLevel = Level.Leve10;
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

    public Level getLastStage() {
        return lastLevel;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCountPlay() {
        return countPlay;
    }
}
