package ir.pint.soltoon.soltoongame.shared.communication;

public class Comminucation {
    public static final int CLIENT_ROUND_TIME = 1000;
    public static final int CLIENT_RECIEVE_TIME = (int) (CLIENT_ROUND_TIME * 1.5);
    public static final int SERVER_ACCEPT_TIMEOUT = 10000;
    public static final int CLIENT_ROUND_EXTRA = (int) (CLIENT_ROUND_TIME * 1.5);
}
