package ir.pint.soltoon.utils.shared.facades.uuid;

import java.util.Random;

public class UUID {
    public static Random random = new Random();

    public static long getLong() {
        long l = random.nextLong();
        return l < 0 ? -l : l;
    }
}
