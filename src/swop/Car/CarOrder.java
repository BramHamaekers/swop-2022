package swop.Car;

import swop.Car.CarModel.CarModel;
import swop.Miscellaneous.RandomID;

import java.util.Map;

public class CarOrder implements Comparable<CarOrder> {
	private final Car car;
	private final String ID;
	private String orderTime;

	/**
	 * Creates a new order based on given carModel
	 * @param carModel ... TODO
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
	public Map<String, Integer> getEstimatedCompletionTime() {
		return this.getCar().getEstimatedCompletionTime();
	}

	public Map<String, Integer> getCompletionTime() {
		return this.getCar().getCompletionTime();
	}

	@Override
	public int compareTo(CarOrder carOrder) {
		int day1 = this.getCompletionTime().get("day");
		int day2 = carOrder.getCompletionTime().get("day");
		if (day1 != day2) return Integer.compare(day1, day2);
		return Integer.compare(this.getCompletionTime().get("minutes"), carOrder.getCompletionTime().get("minutes"));
	}

	/**
	 * Set the time of Ordering to a new time
	 * @param timeAsString the time of ordering as string
	 */
	public void setOrderTime(String timeAsString) {
		// TODO maybe do this in garageholder
		this.orderTime = timeAsString;
	}

	/**
	 * Returns the time that this car was ordered at
	 * @return this.orderTime
	 */
	public String getOrderTime() {
		return this.orderTime;
	}

	@Override
	public String toString() {
		if (!isCompleted()) {
			return String.format("specification: %s %n" +
					"timestamp of ordering: %s" +
					"Estimated Completion Time: %s",
					this.getCar().getCarModel().getCarModelSpecification().getAllParts(),
					this.getOrderTime(),
					this.getCar().getEstimatedCompletionTime());
		}
		return String.format("specification: %s%n" +
						"timestamp of ordering: %s%n" +
						"Completion Time: %s",
				this.getCar().getCarModel().getCarModelSpecification().getAllParts(),
				this.getOrderTime(),
				this.getCompletionTime());

	}
}
