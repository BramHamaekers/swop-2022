package swop.CarManufactoring;

import java.util.List;
import java.util.UUID;

import swop.Database.RandomID;

public class Order {
	private List<Car> cars;
	private String userID; // ID of user that ordered this order
	private String uniqueID;
	
	public Order(List<Car> cars) {
		uniqueID = RandomID.random(5);
		this.cars = cars;

	}
	

	public List<Car> getCars() {
		return cars;
	}
	
	
	public boolean isCompleted() {
		for(Car car: cars) {
			if(!car.isCompleted()) return false;
		}
		return true;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}
}
