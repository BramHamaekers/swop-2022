package swop.CarManufactoring;

import java.util.List;
import java.util.UUID;

public class Order {

	private int buildState; //laten we 0 is nog niet begonnen, 1 means completed belt 1,... tot 3
	private List<String> parts;
	private String uniqueID;
	
	public Order(List<String> prts, int state) {
		this.parts = prts;
		uniqueID = UUID.randomUUID().toString();
		buildState = state;
	}
	
	public void upBuildState() {
		this.buildState += 1;
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
