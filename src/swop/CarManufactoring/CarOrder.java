package swop.CarManufactoring;

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
