import java.util.Scanner;

public class Hospital {
    private final int capacity;
    private final int patients;
    private final int doctors;
    private final TreatmentRooms treatmentRooms;
    private final WaitingRoom waitingRoom;

    public Hospital(int capacity, int doctors, int patients) {
        this.capacity = capacity;
        this.doctors = doctors;
        this.patients = patients;
        this.treatmentRooms = new TreatmentRooms(doctors);
        this.waitingRoom = new WaitingRoom(capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumDoctors() {
        return doctors;
    }

    public int getNumPatients() {
        return patients;
    }

    public TreatmentRooms getTreatmentRooms() {
        return treatmentRooms;
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }


public static void simulate(int capacity, int doctors, int patients) throws InterruptedException {
    Hospital hospital = new Hospital(capacity,doctors,patients);

    Logger.log("Waiting Room Capacity: " + hospital.getCapacity());
    Logger.log("Number of Doctors: " + hospital.getNumDoctors());
    Logger.log("Number of Patients: " + hospital.getNumPatients());
    Logger.log("===================================");

    Thread[] patientThreads = new Thread[patients];
            for (int i = 1; i <= patients; i++) {
    Patient patient = new Patient(i, hospital.getWaitingRoom());
    patientThreads[i - 1] = new Thread(patient, "Patient-" + i);
    patientThreads[i - 1].start();
                Thread.sleep(200);
            }
                    for (Thread pt : patientThreads) {
            pt.join();
            }
            Logger.log("=== All Patients Processed. Simulation Finished ===");
        }

public static void main(String[] args) throws InterruptedException {
    Scanner in = new Scanner(System.in);

    int capacity = readPositive(in, "Waiting room capacity: ");
    int doctors = readPositive(in, "Number of doctors: ");
    int patients = readPositive(in, "Number of patients: ");

    System.out.println();
    simulate(capacity, doctors, patients);

    in.close();
}

private static int readPositive(Scanner in, String message) {
    int val = -1;
    while (val <= 0) {
        System.out.print(message);
        if (in.hasNextInt()) {
            val = in.nextInt();
            if (val <= 0) {
                System.out.println("Please enter a positive integer.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            in.next();
        }
    }
    return val;
}}