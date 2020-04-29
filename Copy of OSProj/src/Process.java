//import java.util.concurrent.Semaphore;

public class Process extends Thread {

	boolean wasBlocked = false;
	public int processID;
	ProcessState status = ProcessState.New;

	public Process(int m) {
		processID = m;
		System.out.println("Process "+processID+" was created, status = NEW");
	}

	@Override
	public void run() {
		switch (processID) {
		case 1:
			process1();
			break;
		case 2:
			process2();
			break;
		case 3:
			process3();
			break;
		case 4:
			process4();
			break;
		case 5:
			process5();
			break;
		}

	}

	private void process1() {

		OperatingSystem.printSem.semWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.printSem.semSignal();

		OperatingSystem.inputSem.semWait(this);
		String file = OperatingSystem.TakeInput();
		OperatingSystem.inputSem.semSignal();

		OperatingSystem.printSem.semWait(this);
		OperatingSystem.readSem.semWait(this);
		OperatingSystem.printText(OperatingSystem.readFile(file));
		OperatingSystem.readSem.semSignal();
		OperatingSystem.printSem.semSignal();

		OperatingSystem.setProcessState(this,ProcessState.Terminated);
	}

	private void process2() {

		OperatingSystem.printSem.semWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.printSem.semSignal();

		OperatingSystem.inputSem.semWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.inputSem.semSignal();

		OperatingSystem.printSem.semWait(this);
		OperatingSystem.printText("Enter Data: ");
		OperatingSystem.printSem.semSignal();

		OperatingSystem.inputSem.semWait(this);
		String data = OperatingSystem.TakeInput();
		OperatingSystem.inputSem.semSignal();

		OperatingSystem.writeSem.semWait(this);
		OperatingSystem.writefile(filename, data);
		OperatingSystem.writeSem.semSignal();

		OperatingSystem.setProcessState(this,ProcessState.Terminated);
	}

	private void process3() {
		int x = 0;
		while (x < 301) {
			OperatingSystem.printSem.semWait(this);
			OperatingSystem.printText(x + "\n");
			OperatingSystem.printSem.semSignal();
			x++;
		}
		OperatingSystem.setProcessState(this,ProcessState.Terminated);
	}

	private void process4() {

		int x = 500;
		while (x < 1001) {
			OperatingSystem.printSem.semWait(this);
			OperatingSystem.printText(x + "\n");
			OperatingSystem.printSem.semSignal();
			x++;
		}
		OperatingSystem.setProcessState(this,ProcessState.Terminated);
	}

	private void process5() {

		OperatingSystem.printSem.semWait(this);
		OperatingSystem.printText("Enter LowerBound: ");
		OperatingSystem.printSem.semSignal();

		OperatingSystem.inputSem.semWait(this);
		String lower = OperatingSystem.TakeInput();
		OperatingSystem.inputSem.semSignal();

		OperatingSystem.printSem.semWait(this);
		OperatingSystem.printText("Enter UpperBound: ");
		OperatingSystem.printSem.semSignal();

		OperatingSystem.inputSem.semWait(this);
		String upper = OperatingSystem.TakeInput();
		OperatingSystem.inputSem.semSignal();

		int lowernbr = Integer.parseInt(lower);
		int uppernbr = Integer.parseInt(upper);
		String data = "";

		while (lowernbr <= uppernbr) {
			data += lowernbr++ + "\n";
		}

		OperatingSystem.writeSem.semWait(this);
		OperatingSystem.writefile("P5.txt", data);
		OperatingSystem.writeSem.semSignal();

		OperatingSystem.setProcessState(this,ProcessState.Terminated);
	}
}
