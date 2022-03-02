package swop.CarManufactoring;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Schedular {
	
	
	private static Queue<Order> waitingOrderQueue = new LinkedList<Order>();
	private static Queue<Order> CarbodyPostQueue = new LinkedList<Order>();
	private static Queue<Order> DrivetrainPostQueue = new LinkedList<Order>();
	private static Queue<Order> AccessoriesPostQueue = new LinkedList<Order>();
	
	private static Queue<Order> UpBuildStateQueue = new LinkedList<Order>();
	private double totalWorkHours; 
	
	//opm: er moet niet rekening gehouden worden bij scheduling met aantal werkeners dus je hebt enkel 2 schiften
	// ^ kan misschien een uitbreiding zijn
	
	public static LinkedHashMap<String, Queue<Order>> returnAllQueuesAsMap() {
		LinkedHashMap<String, Queue<Order>> queueMap = new LinkedHashMap<>();
		queueMap.put("Waiting Orders", waitingOrderQueue);
		queueMap.put("Carbody Post Queue", CarbodyPostQueue);
		queueMap.put("Drivetrain Post Queue", DrivetrainPostQueue);
		queueMap.put("Accessories Post Queue", AccessoriesPostQueue);
		return queueMap;
		
	}
	
	public static Queue<Order> getOrdersToApprove(){
		 return new LinkedList<Order>(UpBuildStateQueue);
	}
	
	public static void requestToMoveOrder(Order order) {
		UpBuildStateQueue.add(order);
		removeFromCurrentPostQueue(order);
	}
	
	private static void removeFromCurrentPostQueue(Order order) {
		
	}
	
	public static void approvedOrder(Order order) {
		if(checkPostStillWorking(order.getBuildState() + 1)) System.out.println("Error, could move 2 next post: Still working");//Should throw error
		else {
			
		}
	}
	private static boolean checkPostStillWorking(int i) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
