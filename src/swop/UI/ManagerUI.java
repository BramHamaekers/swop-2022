package swop.UI;

import swop.Exceptions.CancelException;
import swop.Exceptions.NotAllTasksCompleteException;

import java.util.List;

public class ManagerUI implements UI {

    public static void init(String id) {
        System.out.println("Welcome Manager: " + id);
    }

    public static String indicateAdvance() throws CancelException {
        return UI.indicateYesNo("advance the assembly line");
    }

    public static void displayAssemblyLine(List<String> assemLineCurrent, List<String> assemLineAdvanced) {
        System.out.printf("%n============ Assembly Line ============%n");
        if(assemLineCurrent != null) {
        	System.out.println("----Current----");
        	for(String line: assemLineCurrent) {
        		System.out.println(line);
        	}
        }
        if( assemLineAdvanced != null) {
        	System.out.println("----Advanced----");
        	for(String line: assemLineAdvanced) {
       	 		System.out.println(line);
        	}
        }
        System.out.printf("================================%n");
    }

    public static int askTime() throws CancelException {
        System.out.println("How much time has passed? (in min)");
        return scanner.scanNextLineOfTypeInt();
    }

	public static void printException(NotAllTasksCompleteException e) {
		 System.out.println(e.getMessage() + e.getWorkstations());
		 System.out.println("Press 'Enter' to exit");
		 inputScanner.nextLine();
		
	}

	public static String confirmAdvance() {
		System.out.println("Please confirm change: Yes [y] | No [n]");
        return inputScanner.nextLine();
	}

	public static void exit(List<String> assemLineCurrent) {
		System.out.printf("%n============ Advanced Assembly Line ============%n");
        if(assemLineCurrent != null) {
        	for(String line: assemLineCurrent) {
        		System.out.println(line);
        	}
        }
        System.out.printf("================================%n");
        System.out.println("Press enter 2 exit");
		inputScanner.nextLine();
	}
    


}
