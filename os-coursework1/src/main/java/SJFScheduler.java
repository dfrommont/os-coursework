import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Properties;

class EstimateItem {
  private int estimate;
  private Process process;

  public EstimateItem(int estimate, Process process) {
    this.estimate = estimate;
    this.process = process;
  }

  public int getPriority() {
    return estimate;
  }

  public Process getProcess() {
    return process;
  }
}

class EstimateComparator implements Comparator<EstimateItem> {
  @Override public int compare(EstimateItem estimate1, EstimateItem estimate2) {
    return Integer.compare(estimate2.getPriority(), estimate1.getPriority());
  }
}

/**
 * Shortest Job First Scheduler
 * 
 * @version 2017
 */
public class SJFScheduler extends AbstractScheduler {

  private PriorityQueue<EstimateItem> readyQueue;
  private int initialBurstEstimate;
  private int alphaBurstEstimate;

  private int exponentialAverage(int initialBurstEstimate, int alphaBurstEstimate, Process p) {
    return (initialBurstEstimate * (1 - alphaBurstEstimate)) + (alphaBurstEstimate * p.getRecentBurst());
  }

  /**
   * Initializes the scheduler from the given parameters
   */
  @Override public void initialize(Properties parameters) {
    readyQueue = new PriorityQueue<>(new EstimateComparator());
    initialBurstEstimate = Integer.parseInt(parameters.getProperty("initialBurstEstimate"));
    alphaBurstEstimate = Integer.parseInt(parameters.getProperty("alphaBurstEstimate"));
  }

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */
  @Override public void ready(Process process, boolean usedFullTimeQuantum) {

    readyQueue.add(new EstimateItem(exponentialAverage(initialBurstEstimate, alphaBurstEstimate, process), process));

  }

  /**
   * Removes the next process to be run from the ready queue 
   * and returns it. 
   * Returns null if there is no process to run.
   */
  @Override public Process schedule() {
    if (readyQueue.isEmpty()) {
      return null;
    } else {
      return readyQueue.poll().getProcess();
    }
  }

  /**
   * Returns whether the scheduler is preemptive
   */
  @Override public boolean isPreemptive() {
    return false;
  }
}
