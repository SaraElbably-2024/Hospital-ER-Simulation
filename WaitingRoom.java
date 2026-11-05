public class WaitingRoom {
    private final Patient[] buffer;
    private int in = 0;
    private int out = 0;
    private int count = 0; //number of patients in waitingroom

    private final Semaphore empty;                 // free seats
    private final Semaphore full = new Semaphore(0);   // patients waiting
    private final Semaphore mutex = new Semaphore(1);  // protects the buffer

    public WaitingRoom(int capacity) {
        buffer = new Patient[capacity];
        empty = new Semaphore(capacity);
    }

    // Patient enters the waiting room (waits outside if it is full)
    public void put(Patient p) throws InterruptedException {
        if (!empty.tryAcquire(0)) {
            Logger.log("The waiting room is full.");
            Logger.log(p.getName() + " is waiting outside for an available seat.");
            empty.P();
        }
        mutex.P();
        buffer[in] = p;
        in = (in + 1) % buffer.length;
        count++;
        Logger.logWaitingRoom(
            p.getName() + " entered the waiting room.",
            PrintState() + "\n"
        );
        mutex.V();
        full.V();
    }

    // Doctor sends a patient back (same as entering the waiting room)
    public void putBack(Patient p) throws InterruptedException {
        put(p);
    }

    // Doctor takes a patient (waits while the waiting room is empty)
    public Patient take() throws InterruptedException {
        full.P();
        mutex.P();
        Patient p = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        Logger.logWaitingRoom(
            "Doctor took " + p.getName() + " from the waiting room.",
            PrintState() + "\n"
        );
        mutex.V();
        empty.V();
        return p;
    }

    private String PrintState(){
        StringBuilder sb = new StringBuilder("Waiting room: [");
        for (int i=0;i<count;i++) {
            if(i>0) sb.append(", ");
            sb.append(buffer[(out + i) % buffer.length].getName());
        }
        sb.append("] (").append(count).append("/").append(buffer.length).append(")");
        return sb.toString();
    }
}
