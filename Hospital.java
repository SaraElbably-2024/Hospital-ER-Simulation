import java.util.Scanner;

public class Hospital {
    private final int capacity;
    private final int doctors;
    private final int treatmentRoomsCount;
    private final int patients;
    private final TreatmentRooms treatmentRooms;
    private final WaitingRoom waitingRoom;

    public Hospital(int capacity, int doctors, int treatmentRoomsCount, int patients) {
        this.capacity = capacity;
        this.doctors = doctors;
        this.treatmentRoomsCount = treatmentRoomsCount;
        this.patients = patients;
        this.treatmentRooms = new TreatmentRooms(treatmentRoomsCount);
        this.waitingRoom = new WaitingRoom(capacity);
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    public TreatmentRooms getTreatmentRooms() {
        return treatmentRooms;
    }

    public static void simulate(int capacity, int doctors, int treatmentRoomsCount, int patients) throws InterruptedException {
        Hospital hospital = new Hospital(capacity, doctors, treatmentRoomsCount, patients);

        System.out.print("Patients arriving:\n");
        for (int i = 1; i <= patients; i++) {
            System.out.print("P" + i + (i == patients ? "" : " "));
        }
        System.out.println();

        Thread[] doctorThreads = new Thread[doctors];
        for (int i = 1; i <= doctors; i++) {
            Doctor doctor = new Doctor(i, hospital.getWaitingRoom(), hospital.getTreatmentRooms());
            doctorThreads[i - 1] = new Thread(doctor, "Doctor " + i);
            doctorThreads[i - 1].start();
        }

        Thread[] patientThreads = new Thread[patients];
        for (int i = 1; i <= patients; i++) {
            Patient patient = new Patient(i, hospital.getWaitingRoom());
            patientThreads[i - 1] = new Thread(patient, "P" + i);
            patientThreads[i - 1].start();
            Thread.sleep(200);
        }

        for (Thread pt : patientThreads) {
            pt.join();
        }

        Logger.log("All patients have been treated.");
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);

        System.out.print("Waiting room capacity: ");
        int capacity = in.nextInt();
        System.out.print("Number of doctors: ");
        int doctors = in.nextInt();
        System.out.print("Number of treatment rooms: ");
        int treatmentRoomsCount = in.nextInt();
        System.out.print("Number of patients: ");
        int patients = in.nextInt();

        System.out.println();
        simulate(capacity, doctors, treatmentRoomsCount, patients);

        in.close();
    }
}