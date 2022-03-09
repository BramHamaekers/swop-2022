package swop.CarManufactoring;

import java.util.List;

import swop.Database.RandomID;

public class CarOrder {
	private Car car;
	
	public CarOrder(CarModel carModel) {
		this.car = new Car(carModel);

	}
	

	public Car getCar() {
		return car;
	}
	
	
	public boolean isCompleted() {
		return car.isCompleted();
	}
}
