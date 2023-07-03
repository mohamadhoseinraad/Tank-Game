package ir.ac.kntu.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Player implements Serializable, Comparable {
    private final String username;

    private final String password;

    private Level lastLevel = Level.Level_1;

    private int highScore = 0;

    private int countPlay = 0;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void levelUp() {
        switch (lastLevel) {
            case Level_1 -> lastLevel = Level.Level_2;
            case Level_2 -> lastLevel = Level.Level_3;
            case Level_3 -> lastLevel = Level.Level_4;
            case Level_4 -> lastLevel = Level.Level_5;
            case Level_5 -> lastLevel = Level.Level_6;
            default -> brokeFunction(lastLevel);
        }
    }

    private void brokeFunction(Level level) {
        switch (level) {
            case Level_6 -> lastLevel = Level.Level_7;
            case Level_7 -> lastLevel = Level.Level_8;
            case Level_8 -> lastLevel = Level.Level_9;
            case Level_9 -> lastLevel = Level.Level_10;
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

    @Override
    public int compareTo(@NotNull Object o) {
        Player secP = (Player) o;
        if (secP.getHighScore() > highScore) {
            return -1;
        } else if (secP.getHighScore() < highScore) {
            return 1;
        }
        return 0;
    }

    public void setLastLevel(Level lastLevel) {
        this.lastLevel = lastLevel;
    }
}
