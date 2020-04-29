import java.util.LinkedList;
import java.util.Queue;

public class Semaphore {
	private Queue<Process> BlockedQueue = new LinkedList<Process>();
	private boolean ResourceAvailable = true;
	private String Resource;

	public Semaphore(String Resource) {
		this.Resource = Resource;
	}

	@SuppressWarnings("deprecation")
	public void semWait(Process p) {
		System.out.println("Process " + p.processID + " asked for the " + Resource);
		if (ResourceAvailable) {
			ResourceAvailable = false;
			System.out.println("Process " + p.processID + " acquired the " + Resource);
		} else {
			
			System.out.println("Process " + p.processID + " was Blocked because the " + Resource + " is already in use");
			
			BlockedQueue.add(p);
			p.wasBlocked=true;
			
			OperatingSystem.setProcessState(p,ProcessState.Waiting);
			System.out.println("Process " + p.processID + " was suspended, status = WAITING");
			
			OperatingSystem.ProcessTable.remove();
			OperatingSystem.schedulerThread.resume();
			p.suspend();
		}
	}

	public void semSignal() {
		System.out.println("The " + Resource + " was released");
		if (BlockedQueue.isEmpty()) {
			ResourceAvailable = true;
		} else {
			Process unblockedProcess = BlockedQueue.remove();
			OperatingSystem.setProcessState(unblockedProcess,ProcessState.Ready);
			System.out.println("Process " + unblockedProcess.processID + " was Unblocked because the " + Resource
					+ " Semaphore was signaled, status = READY");
			OperatingSystem.ProcessTable.add(unblockedProcess);
		}
	}
}
