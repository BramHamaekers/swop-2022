package swop.UI;

import java.util.List;

import swop.Exceptions.CancelException;

public class CarMechanicUI implements UI {
	
	// Dummy init
		public static void init(String id) {
			System.out.println("Welcome Car Mechanic: " + id);
		}
		
		public static void displayAvailableStations(List<String> stations){
			System.out.printf("%n============ Current Stations ============%n");
			int number = -1;
			for(String s: stations){
				number+=1;
				System.out.println(s + " [" + number + "] ");
			}
			System.out.println("=======================================");
		}
	
		public static int askOption(String s, int numberOfOptions) throws CancelException {
			System.out.println(s);
			return scanner.scanNextLineOfTypeInt(0, numberOfOptions); //only place where inputscanner class is used.
			
		}

		public static void displayAvailableTasks(List<String> taskList) {
			System.out.printf("%n============ Available Tasks ============%n");
			int number = -1;
			if(taskList.isEmpty()) System.out.println("There are no tasks!");
			else for(String s: taskList){
				number+=1;
				System.out.println(s + " [" + number + "] ");
			}
			System.out.println("=======================================");
			
		}

		public static void displayTaskInfo(String info) {
			System.out.println("-----------Info For Task----------");
			System.out.println(info);
			System.out.println("----------------------------------");
			System.out.println("Press enter when you are finished");
			inputScanner.nextLine();
		}
}
