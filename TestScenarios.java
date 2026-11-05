// Runs the simulation with different inputs to check that every patient is treated
// and that nothing hangs. Only for testing (do not put it in the final ZIP).
public class TestScenarios {

    private static void run(String title, int capacity, int doctors,int treatmentRooms, int patients) throws InterruptedException {
        Logger.log("");
        Logger.log("===== " + title + " (capacity=" + capacity + ", doctors=" + doctors
                + ", treatmentRooms=" + treatmentRooms + ", patients=" + patients + ") =====");
        Hospital.simulate(capacity, doctors, treatmentRooms, patients);
    }

    public static void main(String[] args) throws InterruptedException {
        run("Sample input", 2, 3, 2, 5);
        run("Capacity 1", 1, 3, 2, 4);
        run("One doctor", 2, 1, 2, 3);
        run("One patient", 3, 2, 2, 1);
        run("More doctors than rooms", 3, 5, 2, 8);
        run("Many patients, small waiting room", 1, 4, 2, 10);
    }
}
