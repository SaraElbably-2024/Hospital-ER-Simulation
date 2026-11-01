public class TestPart3 {
    public static void main(String[] args) throws Exception {
        TreatmentRooms rooms = new TreatmentRooms(2);
        Logger.log("Room 1: " + rooms.tryAcquireRoom(3000));
        Logger.log("Room 2: " + rooms.tryAcquireRoom(3000));

        long start = System.currentTimeMillis();
        boolean third = rooms.tryAcquireRoom(3000);   
        Logger.log("Room 3 (should be false): " + third
                + " after ~" + (System.currentTimeMillis() - start) + " ms");

        new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            rooms.releaseRoom();
        }).start();
        start = System.currentTimeMillis();
        Logger.log("After release (should be true): " + rooms.tryAcquireRoom(3000)
                + " after ~" + (System.currentTimeMillis() - start) + " ms");
    }
}
