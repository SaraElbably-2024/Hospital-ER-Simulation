public class Doctor extends Thread {
    private final WaitingRoom waitingRoom;
    private final TreatmentRooms treatmentRooms;

    public Doctor(int id, WaitingRoom waitingRoom, TreatmentRooms treatmentRooms) {
        super("Doctor " + id);
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    public void run() {
        try {
            while (true) {
                Patient p = waitingRoom.take();   // idle until a patient is available
                Logger.log(getName() + " is assigned to " + p.getName() + ".");
                Logger.log(getName() + " is trying to acquire a treatment room.");

                if (treatmentRooms.tryAcquireRoom(3000)) {
                    Logger.log(getName() + " acquired a treatment room.");
                    Logger.log(getName() + " begins treating " + p.getName() + ".");
                    Thread.sleep(2000 + (long) (Math.random() * 3000));
                    Logger.log(getName() + " finished treating " + p.getName() + ".");
                    Logger.log(getName() + " released the treatment room.");
                    treatmentRooms.releaseRoom();
                    Logger.log(getName() + " is now available.");
                    p.finishTreatment();
                } else {
                    Logger.log(getName() + " could not acquire a treatment room.");
                    Logger.log(getName() + " is sending " + p.getName() + " back to the waiting room.");
                    waitingRoom.putBack(p);
                }
            }
        } catch (InterruptedException e) {
            // Hospital stops the doctors at the end of the simulation
        }
    }
}
