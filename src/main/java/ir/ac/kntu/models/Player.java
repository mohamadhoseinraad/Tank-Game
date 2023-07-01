package ir.ac.kntu.models;

import java.io.Serializable;

public class Player implements Serializable {
    private final String username;

    private final String password;

    private Level lastLevel = Level.Leve1;

    private int highScore = 0;

    private int countPlay = 0;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void levelUp() {
        switch (lastLevel) {
            case Leve1 -> lastLevel = Level.Leve2;
            case Leve2 -> lastLevel = Level.Leve3;
            case Leve3 -> lastLevel = Level.Leve4;
            case Leve4 -> lastLevel = Level.Leve5;
            case Leve5 -> lastLevel = Level.Leve6;
            default -> brokeFunction(lastLevel);
        }
    }

    private void brokeFunction(Level level) {
        switch (level) {
            case Leve6 -> lastLevel = Level.Leve7;
            case Leve7 -> lastLevel = Level.Leve8;
            case Leve8 -> lastLevel = Level.Leve9;
            case Leve9 -> lastLevel = Level.Leve10;
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

    public boolean login(String username, String password) {
        return username.equals(this.username) && password.equals(this.password);
    }
}
