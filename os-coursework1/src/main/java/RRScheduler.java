import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

/**
 * Round Robin Scheduler
 * 
 * @version 2017
 */
public class RRScheduler extends AbstractScheduler {

  private Queue<Process> readyQueue;
  private int timeQuantum;

  /**
   * Initializes the scheduler from the given parameters
   */
  @Override public void initialize(Properties parameters) {
    readyQueue = new LinkedList<Process>();
    timeQuantum = Integer.parseInt(parameters.getProperty("timeQuantum"));
  }

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */
  @Override public void ready(Process process, boolean usedFullTimeQuantum) {
    if (usedFullTimeQuantum) readyQueue.offer(process);

  }

  /**
   * Removes the next process to be run from the ready queue 
   * and returns it. 
   * Returns null if there is no process to run.
   */
  @Override public Process schedule() {
    System.out.println("Scheduler selects process "+readyQueue.peek());
    return readyQueue.poll();
  }

  /**
   * Returns whether the scheduler is preemptive
   */
  @Override public boolean isPreemptive() {
    return false;
  }

  /**
   * Returns the time quantum of this scheduler
   * or -1 if the scheduler does not require a timer interrupt.
   */
  @Override public int getTimeQuantum() {
    return timeQuantum;
  }
}
