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
	
		public static String askNumber() {
			System.out.println("Select Station: ");
			return inputScanner.nextLine();
			
		}

		public static void displayAvailableTasks(Set<String> tasks) {
			System.out.printf("%n============ Available Tasks ============%n");
			int number = -1;
			for(String s: tasks){
				number+=1;
				System.out.println(s + " [" + number + "] ");
			}
			System.out.println("=======================================");
			
		}
}
