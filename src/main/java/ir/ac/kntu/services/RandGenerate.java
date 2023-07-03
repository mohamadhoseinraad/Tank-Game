package ir.ac.kntu.services;

import java.util.Random;

public class RandGenerate {

    private final static RandGenerate INSTANCE = new RandGenerate();

    private final Random random;

    private RandGenerate() {
        random = new Random();
    }

    public int getRanBetween(int from, int to) {
        int result;
        do {
            result = random.nextInt(to);
        } while (!(result >= from && result < to));
        return result;
    }

    public static RandGenerate getINSTANCE() {
        return INSTANCE;
    }
}
