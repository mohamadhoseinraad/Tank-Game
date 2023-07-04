package ir.ac.kntu.services.threads;

import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;

public class ShotSoundPlay implements Runnable {

    private static boolean isFired = false;

    private static boolean isVictory = false;

    private static boolean isDefeat = false;

    private static boolean isShotDown = false;

    private static boolean isLegendary = false;

    private static boolean isBeep = false;


    private static File shot = null;

    private static File victory = null;

    private static File defeat = null;

    private static File legendary = null;

    private static File shotDown = null;

    private static File beep = null;


    public ShotSoundPlay() {
        shot = new File("src/main/resources/sounds/shot.mp3");
        victory = new File("src/main/resources/sounds/win-victory.mp3");
        defeat = new File("src/main/resources/sounds/Lose-defeat.mp3");
        legendary = new File("src/main/resources/sounds/extra-shot-legendary.mp3");
        shotDown = new File("src/main/resources/sounds/freez-shutDown.mp3");
        beep = new File("src/main/resources/sounds/beep.mp3");

    }

    @Override
    public void run() {
        while (true) {
            if (isFired) {
                playShotSound();
                isFired = false;
            }
            checkStartAndEnd();
            checkGift();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkStartAndEnd() {
        if (isVictory) {
            playVictory();
            isVictory = false;
        }
        if (isBeep) {
            playBeep();
            isBeep = false;
        }
        if (isDefeat) {
            playDefeat();
            isDefeat = false;
        }
    }

    private static void checkGift() {
        if (isLegendary) {
            playLegendary();
            isLegendary = false;
        }
        if (isShotDown) {
            playShotDown();
            isShotDown = false;
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

    public static void setIsShotDown(boolean isShotDown) {
        ShotSoundPlay.isShotDown = isShotDown;
    }

    public static void setIsLegendary(boolean isLegendary) {
        ShotSoundPlay.isLegendary = isLegendary;
    }

    public static void setIsBeep(boolean isBeep) {
        ShotSoundPlay.isBeep = isBeep;
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

    public static void playLegendary() {
        try {
            Player legendarySound = new Player(new FileInputStream(legendary));
            legendarySound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playShotDown() {
        try {
            Player shotDownSound = new Player(new FileInputStream(shotDown));
            shotDownSound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBeep() {
        try {
            Player beepSound = new Player(new FileInputStream(beep));
            beepSound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
