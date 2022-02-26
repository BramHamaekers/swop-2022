package swop.CarManufactoring;

import java.util.UUID;

public class Order {

	private int buildState; //laten we 0 is nog niet begonnen, 1 means completed belt 1,... tot 3
	private String[] parts;
	private String uniqueID;
	
	public Order(String[] prts, int state) {
		this.parts = prts;
		uniqueID = UUID.randomUUID().toString();
		buildState = state;
	}
	
	public void upBuidState() {
		this.buildState += 1;
	}
	public boolean isCompleted() {
		return buildState == 3;
	}
	
}
