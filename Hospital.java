import java.util.Scanner;

public class Hospital {

    public static void simulate(int capacity, int numDoctors, int numPatients) throws InterruptedException {
        WaitingRoom waitingRoom = new WaitingRoom(capacity);
        TreatmentRooms treatmentRooms = new TreatmentRooms(2);

        // create and start the doctors
        Doctor[] doctors = new Doctor[numDoctors];
        for (int i = 0; i < numDoctors; i++) {
            doctors[i] = new Doctor(i + 1, waitingRoom, treatmentRooms);
            doctors[i].start();
        }

        // create the patients
        Patient[] patients = new Patient[numPatients];
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < numPatients; i++) {
            patients[i] = new Patient(i + 1, waitingRoom);
            names.append(patients[i].getName()).append(" ");
        }
        Logger.log("Patients arriving:");
        Logger.log(names.toString().trim());

        // patients arrive one after another
        for (Patient p : patients) {
            p.start();
            Thread.sleep((long) (Math.random() * 800));
        }

        // wait until every patient has been treated and has left
        for (Patient p : patients) {
            p.join();
        }

        // stop the doctors (they are idle, waiting for a patient)
        for (Doctor d : doctors) {
            d.interrupt();
        }
        for (Doctor d : doctors) {
            d.join();
        }

        Logger.log("All patients have been treated.");
        Logger.log("Simulation completed.");
    }

    private static int readPositive(Scanner in, String message) {
        while (true) {
            System.out.print(message);
            String line = in.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n > 0) {
                    return n;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter a positive whole number.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int capacity = readPositive(in, "Waiting room capacity: ");
        int numDoctors = readPositive(in, "Number of doctors: ");
        int numPatients = readPositive(in, "Number of patients: ");
        System.out.println();
        simulate(capacity, numDoctors, numPatients);
    }
}
