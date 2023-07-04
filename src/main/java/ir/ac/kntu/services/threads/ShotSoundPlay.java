package ir.ac.kntu.services.threads;

import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;

public class ShotSoundPlay implements Runnable {

    private static boolean isFired = false;

    private static boolean isVictory = false;

    private static boolean isDefeat = false;

    private static File shot = null;

    private static File victory = null;

    private static File defeat = null;


    public ShotSoundPlay() {
        shot = new File("src/main/resources/sounds/shot.mp3");
        victory = new File("src/main/resources/sounds/win-victory.mp3");
        defeat = new File("src/main/resources/sounds/Lose-defeat.mp3");
    }

    @Override
    public void run() {
        while (true) {
            if (isFired) {
                playShotSound();
                isFired = false;
            }
            if (isVictory) {
                playVictory();
                isVictory = false;
            }
            if (isDefeat) {
                playDefeat();
                isDefeat = false;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setFired(boolean fired) {
        isFired = fired;
    }

    public static void setIsVictory(boolean isVictory) {
        ShotSoundPlay.isVictory = isVictory;
    }

    public static void setIsDefeat(boolean isDefeat) {
        ShotSoundPlay.isDefeat = isDefeat;
    }

    public static void playShotSound() {
        try {
            Player shotSound = new Player(new FileInputStream(shot));
            shotSound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playVictory() {
        try {
            Player victorySound = new Player(new FileInputStream(victory));
            victorySound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playDefeat() {
        try {
            Player defeatSound = new Player(new FileInputStream(defeat));
            defeatSound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
