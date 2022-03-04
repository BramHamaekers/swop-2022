package swop.CarManufactoring;

import java.util.UUID;

public class Car {
	
	private int buildState; //laten we 0 is nog niet begonnen, 1 means completed belt 1,... tot 3
    private CarModel carModel;
    private String uniqueID;
    private Order order = null;
    
    public Car(CarModel model){
        this.carModel = model;
		uniqueID = UUID.randomUUID().toString();
		buildState = 0;
    }
    
	public void setOrder(Order order) {
		this.order = order;
	}
    
    public Order getOrder() {
    	return this.getOrder();
    }
    
    public void upBuildState() {
		this.buildState += 1;
		//TODO: check if valid buildState
	}
	public boolean isCompleted() {
		return buildState == 3;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}
	public int getBuildState() {
		return buildState;
	}
}
