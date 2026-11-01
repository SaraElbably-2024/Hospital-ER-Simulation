public class TreatmentRooms {
    private final Semaphore rooms;

    public TreatmentRooms(int numberOfRooms) {
        rooms = new Semaphore(numberOfRooms);
    }

    public boolean tryAcquireRoom(long timeoutMillis) throws InterruptedException {
        return rooms.tryAcquire(timeoutMillis);
    }

    public void releaseRoom() {
        rooms.V();
    }
}
