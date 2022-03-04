package swop.CarManufactoring;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Schedular {
	
	
	private static List<Car> CarbodyPostQueue = new LinkedList<Car>();
	private static List<Car> DrivetrainPostQueue = new LinkedList<Car>();
	private static List<Car> AccessoriesPostQueue = new LinkedList<Car>();
	private static int indexCarbodyPost = 0;
	private static int indexDrivetrainPost = 0;
	private static int indexAccessoriesPost = 0;
	private static double totalWorkHours; 
	
	
	public static LinkedHashMap<String, List<Car>> returnAllQueuesAsMap() {
		LinkedHashMap<String, List<Car>> queueMap = new LinkedHashMap<>();
		queueMap.put("Carbody Post Queue", CarbodyPostQueue);
		queueMap.put("Drivetrain Post Queue", DrivetrainPostQueue);
		queueMap.put("Accessories Post Queue", AccessoriesPostQueue);
		return queueMap;
		
	}
	public static int[] getCurrentIndex() {
		return new int [] {indexCarbodyPost, indexDrivetrainPost, indexAccessoriesPost};
	}
 	
}
