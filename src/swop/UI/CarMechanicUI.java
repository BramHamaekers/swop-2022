package swop.UI;

import java.util.List;
import java.util.Set;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;

public class CarMechanicUI implements UI {

		private static final CarMechanicGenerator carMechanicGenerator = new CarMechanicGenerator();

		public static void init(String id) {
			System.out.println("Welcome Car Mechanic: " + id + " (You can cancel any action by typing: CANCEL)");
		}

		/**
	 	* Asks which action the user wants to take
	 	* @return int indicating the chosen action
	 	* @param actions available actions for the user
	 	* @throws CancelException when the user types 'Cancel'
	 	*/
		public static int selectAction(List<String> actions) throws CancelException {
			return UI.selectAction(carMechanicGenerator, actions);
		}
		
		public static void displayAvailableStations(List<WorkStation> stations){
			DisplayStatus builder = new DisplayStatus();
			carMechanicGenerator.generateMechanic(builder, stations);
			System.out.print(builder.getDisplay());
		}
	
		public static int askOption(String s, int numberOfOptions) throws CancelException {
			return scanner.scanNextLineOfTypeInt(0, numberOfOptions);
			
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

		public static void displayTaskInfo(List<String> info) throws CancelException {
			System.out.println("-----------Info For Task----------");
			for(String part:info) System.out.println(part);
			System.out.println("----------------------------------");
			System.out.println("Press enter when you are finished");
			scanner.scanNextLineOfTypeString();
		}

		public static void displayStationStatus(WorkStation workStation, Set<Task> pendingTasks, Set<Task> completedTask) {
			DisplayStatus builder = new DisplayStatus();
			carMechanicGenerator.generateWorkStationStatus(builder, workStation.getName(), pendingTasks, completedTask);
			System.out.print(builder.getDisplay());
		}
		
		public static int askTimeToCompleteTask() throws CancelException {
	        System.out.println("How much time did it took to finish the task? (in min)");
	        return scanner.scanNextLineOfTypeInt();
		}
}
