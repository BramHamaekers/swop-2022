package swop.CarManufactoring;

import swop.Database.RandomID;

public class CarOrder implements Comparable<CarOrder>{
	private final Car car;
	private final String ID;
	
	/**
	 * Creates a new order based on given carModel
	 * @param carModel
	 */
	public CarOrder(CarModel carModel) {
		this.car = new Car(carModel);
		this.ID = RandomID.random(7);
	}

	public Car getCar() {
		return car;
	}

	/**
	 * Returns boolean based on completion of cars
	 * @return true if all the cars of the order are completed
	 */
	public boolean isCompleted() {
		return car.isCompleted();
	}

	public String getID() {
		return this.ID;
	}

	/**
	 * Est. value of completion for this car.
	 * @return est. value
	 */
	public String getEstimatedCompletionTime() {
		return this.getCar().getEstimatedCompletionTime();
	}

	public int getCompletionTime() {
		return this.getCar().getDeliveryTime();
	}

	@Override
	public int compareTo(CarOrder carOrder) {
		return Integer.compare(this.getCompletionTime(), carOrder.getCompletionTime());
	}
}
