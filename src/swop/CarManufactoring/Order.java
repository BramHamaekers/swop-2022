package swop.CarManufactoring;

import java.util.List;
import java.util.UUID;

public class Order {

	private int buildState; //laten we 0 is nog niet begonnen, 1 means completed belt 1,... tot 3
	private List<String> parts;
	private String uniqueID;
	private String userID; // ID of user that ordered this order
	
	public Order(List<String> prts) {
		this.parts = prts;
		uniqueID = UUID.randomUUID().toString();
		buildState = 0;
	}
	
	public void upBuildState() {
		this.buildState += 1;
		//TODO: check if valid buildState
	}
	public boolean isCompleted() {
		return buildState == 3;
	}

	public List<String> getParts() {
		return parts;
	}

	public String getUniqueID() {
		return uniqueID;
	}
	public int getBuildState() {
		return buildState;
	}
}
