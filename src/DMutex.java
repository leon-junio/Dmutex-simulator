import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DMutex {

    private boolean[][] permissionMatrix;
    private int processLength;
    private Queue<Integer> queueProcessWaiting;
    private Map<Long, Integer> criticalSectionReport;

    public DMutex(int processLength) {
        this.processLength = processLength;
        this.permissionMatrix = new boolean[processLength][processLength];
        this.queueProcessWaiting = new PriorityQueue<>();
        this.criticalSectionReport = new HashMap<>();
        for (int i = 0; i < processLength; i++) {
            for (int j = 0; j < processLength; j++) {
                permissionMatrix[i][j] = false;
            }
        }
    }

    public Map<Long, Integer> getCriticalSectionReport() {
        return criticalSectionReport;
    }

    /**
     * Request to join the critical section
     * 
     * @param id Process id that is requesting to join the critical section
     * @return void
     */
    public synchronized void requestCS(int id) throws InterruptedException{
        queueProcessWaiting.add(id);
        while (queueProcessWaiting.peek() != id || !canEnterCS(id)) {
            try {
                System.out.println("Process " + id + " is waiting to enter the critical section.");
                wait();
            } catch (InterruptedException e) {
                System.out.println("Process " + id + " was interrupted while waiting to enter the critical section.");
                throw e;
            }
        }
        System.out.println("Process " + id + " received permission to enter the critical section.");
        System.out.println("The id: " + queueProcessWaiting.poll() + " was removed from the queue.");
        setCS(id);
    }

    /**
     * Set the critical section for the process
     * 
     * @param id Process id that is in the critical section
     * @return void
     */
    public synchronized void setCS(int id) {
        for (int i = 0; i < processLength; i++) {
            permissionMatrix[id][i] = true;
        }
        this.criticalSectionReport.put(System.currentTimeMillis(), id);
        System.out.println("Process " + id + " is in the critical section.");
    }

    /**
     * Release the critical section for the process (allow other processes to enter
     * the critical section)
     * 
     * @param id Process id that is releasing the critical section
     * @return void
     */
    public synchronized void releaseCS(int id) {
        for (int i = 0; i < processLength; i++) {
            permissionMatrix[id][i] = false;
        }
        System.out.println("Process " + id + " released the critical section.");
        notifyAll();
    }

    /**
     * Check if the process can enter the critical section
     * 
     * @param id Process id that is requesting to enter the critical section
     * @return boolean True if the process can enter the critical section, false
     *         otherwise
     */
    private boolean canEnterCS(int id) {
        for (int i = 0; i < processLength; i++) {
            if (i != id && permissionMatrix[i][id]) {
                return false;
            }
        }
        return true;
    }
}