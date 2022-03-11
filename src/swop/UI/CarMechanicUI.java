package swop.UI;

import java.util.List;
import java.util.Set;

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
	
		public static String askNumber(String s) {
			System.out.println(s);
			return inputScanner.nextLine();
			
		}

		public static void displayAvailableTasks(List<String> taskList) {
			System.out.printf("%n============ Available Tasks ============%n");
			int number = -1;
			for(String s: taskList){
				number+=1;
				System.out.println(s + " [" + number + "] ");
			}
			System.out.println("=======================================");
			
		}

		public static void displayTaskInfo(String info) {
			if(info == null) {
				System.out.println("Press enter 2 exit");
			}
			else {
			System.out.println("-----------Info For Task----------");
			System.out.println(info);
			System.out.println("----------------------------------");
			System.out.println("Press enter when you are finished");
			}
			inputScanner.nextLine();
		}
}
