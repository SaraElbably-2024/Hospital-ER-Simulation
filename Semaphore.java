public class Semaphore {
    protected int value = 0;

    public Semaphore() {
        value = 0;
    }

    public Semaphore(int initial) {
        value = initial;
    }
 
    public synchronized void P() throws InterruptedException {
        while (value <= 0) {
            wait();
        }
        value--;
    }
 
    public synchronized boolean tryAcquire(long timeoutMillis) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMillis;
        while (value <= 0) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) {
                return false;
            }
            wait(remaining);
        }
        value--;
        return true;
    }

   
    public synchronized void V() {
        value++;
        notifyAll();
    }
}
