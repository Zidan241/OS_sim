import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class OperatingSystem {

	public static Queue<Thread> ProcessTable;
	public static Semaphore writeSem = new Semaphore("Writer");
	public static Semaphore inputSem = new Semaphore("Scanner");
	public static Semaphore printSem = new Semaphore("Printer");
	public static Semaphore readSem = new Semaphore("Reader");
	public static Thread schedulerThread = Thread.currentThread();

	public OperatingSystem() {
		ProcessTable = new LinkedList<Thread>();
	}

	public static String readFile(String name) {
		String Data = "";
		File file = new File(name);
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				Data += scan.nextLine() + "\n";
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return Data;
	}

	// 2- Write into file
	@SuppressWarnings("unused")
	public static void writefile(String name, String data) {
		try {
			BufferedWriter BW = new BufferedWriter(new FileWriter(name));
			BW.write(data);
			BW.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// 3- print to console
	@SuppressWarnings("unused")
	public static void printText(String text) {
		System.out.println(text);
	}

	// 4- take input
	@SuppressWarnings("unused")
	public static String TakeInput() {
		Scanner in = new Scanner(System.in);
		String data = in.nextLine();
		return data;
	}

	private static void createProcess(int processID) {
		Process p = new Process(processID);
		ProcessTable.add(p);
		setProcessState(p,ProcessState.Ready);
		System.out.println("Process " + processID + " was added to Process Table, status = READY");
	}

	@SuppressWarnings("deprecation")
	public static void scheduler() {
		while (!ProcessTable.isEmpty()) {
			
			Process currProcess = (Process) ProcessTable.peek();
			setProcessState(currProcess,ProcessState.Running);
			if (!currProcess.wasBlocked) {
				System.out.println("Process " + currProcess.processID + " was started, status = RUNNING");
				currProcess.start();
			} else {
				System.out.println("Process " + currProcess.processID + " was resumed, status = RUNNING");
				currProcess.resume();
			}
			schedulerThread.suspend();		
		}
	}
	
	public static void setProcessState(Process p , ProcessState s) {
		p.status = s;

		if (s == ProcessState.Terminated) {
			OperatingSystem.ProcessTable.remove();
			System.out.println("Process " + p.processID + " was terminated, status = TERMINATED");
			OperatingSystem.schedulerThread.resume();
		}
	}
	
	public static ProcessState getProcessState(Process p) {
		return p.status;
	}

	public static void main(String[] args) {
		OperatingSystem os = new OperatingSystem();
		createProcess(1);
		createProcess(2);
		createProcess(3);
		createProcess(4);
		createProcess(5);

		scheduler();

	}
}
