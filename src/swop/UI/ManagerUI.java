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
		}
		
		public static boolean comfirmOrder(Order order) {
			return false;
			
		}
		
	
}
