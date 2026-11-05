public class Logger {
    public static synchronized void log(String message) {
        System.out.println(message);
    }
    public static synchronized void logWaitingRoom(String message, String state) {
        System.out.println(message);
        System.out.println(state);
    }
}
