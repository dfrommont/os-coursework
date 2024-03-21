import java.util.ArrayList;
import java.util.Properties;

/**
 * Ideal Shortest Job First Scheduler
 * 
 * @version 2017
 */
public class IdealSJFScheduler extends AbstractScheduler {

  private ArrayList<Process> readyQueue;

  /**
   * Initializes the scheduler from the given parameters
   */
  @Override public void initialize(Properties parameters) {
    readyQueue = new ArrayList<Process>();
  }

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */
  @Override public void ready(Process process, boolean usedFullTimeQuantum) {
    readyQueue.add(process);
  }

  /**
   * Removes the next process to be run from the ready queue 
   * and returns it. 
   * Returns null if there is no process to run.
   */
  @Override public Process schedule() {
    if (readyQueue.size() > 0) {
      Process best = readyQueue.get(0);
      int bestTime = best.getNextBurst();
      for (Process p : readyQueue) {
        if (p.getNextBurst() <= bestTime) {
          best = p;
          bestTime = p.getNextBurst();
        }
      }
      readyQueue.remove(best);
      return best;
    } else {
      return null;
    }
  }

  /**
   * Returns whether the scheduler is preemptive
   */
  @Override public boolean isPreemptive() {
    return false;
  }
}
