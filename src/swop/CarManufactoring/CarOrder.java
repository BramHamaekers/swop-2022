package swop.CarManufactoring;

import swop.Database.RandomID;

public class CarOrder {
	private Car car;
	private String completionTime;
	private String ID;
	
	public CarOrder(CarModel carModel) {
		this.car = new Car(carModel);
		this.ID = RandomID.random(7);

	}
	

	public Car getCar() {
		return car;
	}
	
	
	public boolean isCompleted() {
		return car.isCompleted();
	}


	public void setComplitionTime(String time) {
		this.completionTime = time;
		
	}


	public String getComplitionTime() {
		return this.completionTime;
	}


	public String getID() {
		return this.ID;
	}
}
