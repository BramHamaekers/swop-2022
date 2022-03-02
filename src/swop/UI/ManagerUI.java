package swop.UI;

import java.util.LinkedHashMap;
import java.util.Queue;

import swop.CarManufactoring.Order;

public class ManagerUI implements UI {
	
	// Dummy init
		public static void init(String id) {
			System.out.println("Welcome Manager: " + id);
		}
		
		public static void displayOrderQueues(LinkedHashMap<String, Queue<Order>> queues) {
			System.out.printf("%n============ Queues ============%n");
			queues.forEach((str,queue) ->{
				System.out.println(str + ":");
				if(queue.size() == 0) System.out.print("Empty");
				else queue.forEach((post) -> {
					System.out.print(post.getUniqueID() + " <- ");
				});
				System.out.println();
			});
			System.out.printf("================================%n");
		}
		
		public static String comfirmOrder(Order order) {
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
			
		}

		private static boolean isValidOption(int input) {
			if(input == 0 || input == 1) return true;
			return false;
			
		}
		
		///////////////////Checkers//////////////////
		
		
	
}
