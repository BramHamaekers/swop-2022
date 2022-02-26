package swop.UI;

import java.util.LinkedList;
import java.util.Queue;

import swop.CarManufactoring.Order;

public class Schedular {
	
	
	private  Queue<Order> waitingOrderQueue = new LinkedList<Order>();
	private  Queue<Order> CarBodyPostQueue = new LinkedList<Order>();
	private  Queue<Order> DrivetrainPostQueue = new LinkedList<Order>();
	private  Queue<Order> AccessoriesPostQueue = new LinkedList<Order>();
	private double totalWorkHours; 
	
	//opm: er moet niet rekening gehouden worden bij scheduling met aantal werkeners dus je hebt enkel 2 schiften 
	
	public Schedular() {
		waitingOrderQueue = this.getToCompleteOrderList(); //get not yet completed order list
	}

	private Queue<Order> getToCompleteOrderList() {
		// TODO Auto-generated method stub
		return null;
	}

	private void getOrderList() {
		// TODO Auto-generated method stub
		
	}
	
}
