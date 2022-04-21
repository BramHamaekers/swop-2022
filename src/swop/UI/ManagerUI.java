package swop.UI;

import swop.Exceptions.CancelException;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.UI.Generators.GarageHolderGenerator;
import swop.UI.Generators.ManagerGenerator;

import java.util.List;

public class ManagerUI implements UI {

	private static final ManagerGenerator managerGenerator = new ManagerGenerator();

    public static void init(String id) {
        System.out.println("Welcome Manager: " + id + " (You can cancel any action by typing: CANCEL)");
    }

    public static String indicateAdvance() throws CancelException {
        return UI.indicateYesNo("advance the assembly line");
    }

	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static int selectAction(List<String> actions) throws CancelException {
		return UI.selectAction(managerGenerator, actions);
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
		 try {
			scanner.scanNextLineOfTypeString();
		} catch (CancelException e1) {
			 e1.printMessage();
		}
		
	}

	public static String confirmAdvance() throws CancelException {
		System.out.println("Please confirm change: Yes [y] | No [n]");
		return scanner.scanNextLineOfTypeString();

	}

	public static void exit(List<String> assemLineCurrent) {
		System.out.printf("%n============ Advanced Assembly Line ============%n");
        if(assemLineCurrent != null) {
        	for(String line: assemLineCurrent) {
        		System.out.println(line);
        	}
        }
        System.out.printf("================================%n");
        System.out.println("Press 'Enter' to exit");
		 try {
			scanner.scanNextLineOfTypeString();
		} catch (CancelException e1) {
			 e1.printMessage();
		}
	}
}
