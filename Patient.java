public class Patient extends Thread {
    private final WaitingRoom waitingRoom;
    private final Semaphore treated = new Semaphore(0);

    public Patient(int id, WaitingRoom waitingRoom) {
        super("P" + id);
        this.waitingRoom = waitingRoom;
    }

    public void run() {
        try {
            Logger.log("\n" + getName() + " arrived.");
            waitingRoom.put(this);
            treated.P();                 // wait until the doctor finishes
            Logger.log(getName() + " left the clinic.\n");
        } catch (InterruptedException e) {
        }
    }

    // called by the doctor when the treatment is finished
    public void finishTreatment() {
        treated.V();
    }
}
