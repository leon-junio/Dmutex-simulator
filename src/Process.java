import java.util.Random;

public class Process implements Runnable {
    private int id;
    private DMutex dmutex;
    private long totalExecutedOperations = 0;
    private Long totalWaitTime = 0l;
    private static final int WAIT_TIME = 2000;
    private static final Random random = new Random();

    public int getId() {
        return id;
    }

    public long getTotalExecutedOperations() {
        return totalExecutedOperations;
    }

    public Long getWaitTime() {
        return totalWaitTime;
    }

    public Process(int id, DMutex dmutex) {
        this.id = id;
        this.dmutex = dmutex;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Process " + id + " is doing operations before join critical section");
            var startTime = System.currentTimeMillis();
            try {
                dmutex.requestCS(id);
                totalWaitTime += System.currentTimeMillis() - startTime;
                System.out.println("Process " + id + " joined critical section.");
                Thread.sleep(random.nextInt(WAIT_TIME));
                totalExecutedOperations++;
            } catch (InterruptedException e) {
                System.out.println("Process " + id + " was interrupted.");
                break;
            }
            System.out.println("Process " + id + " exited critical section.");
            dmutex.releaseCS(id);
        }
        System.out.println("Process " + id + " finished.");
    }
}