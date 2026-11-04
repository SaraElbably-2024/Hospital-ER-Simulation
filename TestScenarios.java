// Runs the simulation with different inputs to check that every patient is treated
// and that nothing hangs. Only for testing (do not put it in the final ZIP).
public class TestScenarios {

    private static void run(String title, int capacity, int doctors, int patients) throws InterruptedException {
        Logger.log("");
        Logger.log("===== " + title + " (capacity=" + capacity + ", doctors=" + doctors
                + ", patients=" + patients + ") =====");
        Hospital.simulate(capacity, doctors, patients);
    }

    public static void main(String[] args) throws InterruptedException {
        run("Sample input", 2, 3, 5);
        run("Capacity 1", 1, 3, 4);
        run("One doctor", 2, 1, 3);
        run("One patient", 3, 2, 1);
        run("More doctors than rooms", 3, 5, 8);
        run("Many patients, small waiting room", 1, 4, 10);
    }
}
