package swop.UI;

import java.util.List;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Exceptions.CancelException;
import swop.UI.Generators.CarMechanicGenerator;

public class CarMechanicUI implements UI {
	
		// Dummy init
		public static void init(String id) {
			System.out.println("Welcome Car Mechanic: " + id + " (You can cancel any action by typing: CANCEL)");
		}
		
		public static void displayAvailableStations(List<WorkStation> stations){
//			System.out.printf("%n============ Current Stations ============%n");
//			int number = -1;
//			for(String s: stations){
//				number+=1;
//				System.out.println(s + " [" + number + "] ");
//			}
//			System.out.println("=======================================");
			CarMechanicGenerator generator = new CarMechanicGenerator();
			DisplayStatus builder = new DisplayStatus();
			generator.generateMechanic(builder, stations);
			System.out.print(builder.getDisplay());
		}
	
		public static int askOption(String s, int numberOfOptions) throws CancelException {
			// TODO: implement in displayAvailable or in DisplayStatus
//			System.out.println(s);
			return scanner.scanNextLineOfTypeInt(0, numberOfOptions); //only place where inputscanner class is used.
			
		}

		public static void displayAvailableTasks(List<Task> taskList) {
			System.out.printf("%n============ Available Tasks ============%n");
			int number = -1;
			if(taskList.isEmpty()) System.out.println("There are no tasks!");
			else for(Task s: taskList){
				number+=1;
				System.out.println(s.getName() + " [" + number + "] ");
			}
			System.out.println("=======================================");
			
		}

		public static void displayTaskInfo(String info) throws CancelException {
			System.out.println("-----------Info For Task----------");
			System.out.println(info);
			System.out.println("----------------------------------");
			System.out.println("Press enter when you are finished");
			scanner.scanNextLineOfTypeString();

		}
}
