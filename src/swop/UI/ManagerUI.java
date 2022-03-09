package swop.UI;

import java.util.LinkedHashMap;
import java.util.List;

import swop.CarManufactoring.Car;

public class ManagerUI implements UI {

    public static void init(String id) {
        System.out.println("Welcome Manager: " + id);
    }

    public static String indicateAdvance() {
        return UI.indicateYesNo("advance the assembly line");
    }

    /**
    public static void displayOrderQueues(LinkedHashMap<String, List<Car>> queues) {
        System.out.printf("%n============ Queues ============%n");
        for (var entry : queues.entrySet()) {
            System.out.println(entry.getKey() + ":");
            List<Car> list = entry.getValue();
            if (list.size() == 0) System.out.print("Empty");
            else {
                for (int j = 0; j < list.size(); j++) {
                    System.out.print(list.get(j).getUniqueID() + " (status not implemented), ");
                    //TODO nog kunnen teruggeven wat de status is van een auto in een bepaalde post.
                }
            }
            System.out.println();
        }
        System.out.printf("================================%n");
    }
     */

    public static String advanceAssemblyLine() {
        System.out.println("Do you want to advance the assembly line?");
        System.out.println("Yes [y] | No [n]");
        return inputScanner.nextLine();
    }

    public static String confirmadvance() {
        System.out.println("Please comfirm change: Yes [y] | No [n]");
        return inputScanner.nextLine();
    }

    public static int askTime() {
        System.out.println("How much time has passed? (in min)");
        int t = inputScanner.nextInt();
        return t;
    }


    //niet nodig, had opgave niet tegoei gelezen.
		/*public static String comfirmOrder(Order order) {
			if(order == null) {
				System.out.println("No carOrders need approval! (Enter 2 continue)");
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
