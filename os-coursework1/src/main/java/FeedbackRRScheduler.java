import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Properties;

class ProcessItem {
  private int priority;
  private Process process;

  public ProcessItem(int priority, Process process) {
    this.priority = priority;
    this.process = process;
  }

  public int getPriority() {
    return priority;
  }

  public Process getProcess() {
    return process;
  }
}

class ProcessComparator implements Comparator<ProcessItem> {
  @Override public int compare(ProcessItem process1, ProcessItem process2) {
    return Integer.compare(process2.getPriority(), process1.getPriority());
  }
}

/**
 * Feedback Round Robin Scheduler
 * 
 * @version 2017
 */
public class FeedbackRRScheduler extends AbstractScheduler {

  private PriorityQueue<ProcessItem> readyQueue;

  /**
   * Initializes the scheduler from the given parameters
   */
  @Override public void initialize(Properties parameters) {
    readyQueue = new PriorityQueue<>(new ProcessComparator());
  }

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */
  @Override public void ready(Process process, boolean usedFullTimeQuantum) {
    if (usedFullTimeQuantum) {
      readyQueue.add(new ProcessItem(process.getPriority()+1, process));
    } else {
      readyQueue.add(new ProcessItem(process.getPriority(), process));
    }
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
    return true;
  }
}
