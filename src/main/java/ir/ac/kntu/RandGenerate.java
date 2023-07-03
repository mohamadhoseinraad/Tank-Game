package ir.ac.kntu;

import java.util.Random;

public class RandGenerate {

    private final static RandGenerate INSTANCE = new RandGenerate();

    private Random random;

    private RandGenerate() {
        random = new Random();
    }

    public int getRanBetween(int from, int to) {
        int result;
        return random.nextInt(from, to);
//        do {
//            result = random.nextInt();
//        } while (!(result >= from && result < to));
//        return result;
    }

    public static RandGenerate getINSTANCE() {
        return INSTANCE;
    }
}
