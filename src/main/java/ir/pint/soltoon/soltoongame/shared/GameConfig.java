package ir.pint.soltoon.soltoongame.shared;

public class GameConfig {
    public static final int CLIENT_ROUND_TIME = 1000;
    public static final int CLIENT_RECIEVE_TIME = (int) (CLIENT_ROUND_TIME * 1.5);
    public static final int SERVER_ACCEPT_TIMEOUT = 10000;
    public static final int CLIENT_ROUND_EXTRA = (int) (CLIENT_ROUND_TIME * 1.5);


    public static final int NUMBER_OF_PLAYERS = 2;
    public static final int HEIGHT = 10, WIDTH = 10;
    public static final int STEPS = 10;
}
