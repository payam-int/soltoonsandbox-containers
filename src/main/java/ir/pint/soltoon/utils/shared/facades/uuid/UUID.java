package ir.pint.soltoon.utils.shared.facades.uuid;

import java.util.Random;

public class UUID {
    public static Random random = new Random();

    public static long getLong() {
        return random.nextLong();
    }
}
