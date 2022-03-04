package swop.CarManufactoring;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Schedular {
	
	
	private static List<Car> CarbodyPostQueue = new LinkedList<Car>();
	private static List<Car> DrivetrainPostQueue = new LinkedList<Car>();
	private static List<Car> AccessoriesPostQueue = new LinkedList<Car>();

	private static double totalWorkHours; 
	private static boolean test = true;
	
	public static LinkedHashMap<String, List<Car>> returnAllQueuesAsMap() {
		//////TEST//////
		if(test) {
		CarbodyPostQueue.add(new Car(null));
		DrivetrainPostQueue.add(new Car(null));
		CarbodyPostQueue.add(new Car(null));
		test = false;
		}
		/////////////
		LinkedHashMap<String, List<Car>> queueMap = new LinkedHashMap<>();
		queueMap.put("Carbody Post Queue", CarbodyPostQueue);
		queueMap.put("Drivetrain Post Queue", DrivetrainPostQueue);
		queueMap.put("Accessories Post Queue", AccessoriesPostQueue);
		return queueMap;
		
	}
}
