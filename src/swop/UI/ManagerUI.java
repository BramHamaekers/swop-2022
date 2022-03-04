package swop.UI;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;

import swop.CarManufactoring.Car;
import swop.CarManufactoring.Order;

public class ManagerUI implements UI {
	
	// Dummy init
		public static void init(String id) {
			System.out.println("Welcome Manager: " + id);
		}
		
		public static void displayOrderQueues(LinkedHashMap<String, List<Car>> queues, int[] index) {
			System.out.printf("%n============ Queues ============%n");
			int i = 0;
			queues.forEach((str,list) ->{
				System.out.println(str + ":");
				if(list.size() == 0) System.out.print("Empty");
				else {
					for(int j = 0; j < list.size(); j++) {
						if(j<index[i]) System.out.print(list.get(j).getUniqueID()+ " (Ready), ");
						else if(j == index[i]) System.out.print("| "+list.get(j).getUniqueID()+ " (Building) | ");
						else System.out.print(list.get(j).getUniqueID()+ " (Pending), ");
						//TODO implementeren dat er orders ready zijn en aan het wachten, maar niks aant builden.
					}
				}
				System.out.println();
			});
			System.out.printf("================================%n");
		}
		
		public static String advanceAssemblyLine() {
			System.out.println("Do you want to advance the assembly line?");
			System.out.println("Yes [y] | No [n]");
			return inputScanner.nextLine();	
		}
		
		
		//niet nodig, had opgave niet tegoei gelezen.
		/*public static String comfirmOrder(Order order) {
			if(order == null) {
				System.out.println("No orders need approval! (Enter 2 continue)");
				inputScanner.nextLine();
			}
			else {
				System.out.println("Move order: " + order.getUniqueID() + " from " + order.getBuildState() + " -> " + Integer.toString(order.getBuildState()+1));
					System.out.println("Yes [y] | No [n]");
					return inputScanner.nextLine();
				}	
			return null;
			
		}*/
		
		
	
}
