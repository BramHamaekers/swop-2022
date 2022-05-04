package swop.Car;

import swop.Car.CarModel.CarModel;
import swop.Miscellaneous.RandomID;
import swop.Miscellaneous.TimeStamp;

/**
 * A car order made by a garage holder for a specific {@code Car}
 */
public class CarOrder implements Comparable<CarOrder> {
	private final Car car;
	private final String ID;
	private TimeStamp orderTime;

	/**
	 * Creates a new order based on given carModel
	 * @param carModel the given carModel
	 */
	public CarOrder(CarModel carModel) {
		this.car = new Car(carModel);
		this.ID = RandomID.random(7);
	}

	/**
	 * @return the car for this car order
	 */
	public Car getCar() {
		return this.car;
	}

	/**
	 * Returns boolean based on completion of cars
	 * @return true if all the cars of the order are completed
	 */
	public boolean isCompleted() {
		return this.car.isCompleted();
	}

	/**
	 * @return the id for this order
	 */
	public String getID() {
		return this.ID;
	}

	/**
	 * EstEstimated time of completion for this car.
	 * @return Estimated time of completion
	 */
	public TimeStamp getEstimatedCompletionTime() {
		return this.getCar().getEstimatedCompletionTime();
	}

	/**
	 * get the time that this carOrder was completed at
	 * @return this.getCar().getCompletionTime()
	 */
	public TimeStamp getCompletionTime() {
		return this.getCar().getCompletionTime();
	}

	@Override
	public int compareTo(CarOrder carOrder) {
		if (!carOrder.isCompleted()) {
			return this.getEstimatedCompletionTime().compareTo(carOrder.getEstimatedCompletionTime());
		}
		return this.getCompletionTime().compareTo(carOrder.getCompletionTime());
	}

	/**
	 * Set the time of Ordering to a new time
	 * @param time the time of ordering
	 */
	public void setOrderTime(TimeStamp time) {
		this.orderTime = time;
	}

	/**
	 * Returns the time that this car was ordered at
	 * @return this.orderTime
	 */
	public TimeStamp getOrderTime() {
		return this.orderTime;
	}

	@Override
	public String toString() {
		if (!isCompleted()) {
			return String.format("specification: %s %n" +
					"timestamp of ordering: %s %n" +
					"Estimated Completion Time: %s",
					this.getCar().getCarModel().getCarModelSpecification().getAllParts(),
					this.getOrderTime(),
					this.getCar().getEstimatedCompletionTime());
		} else return String.format("specification: %s %n" +
						"timestamp of ordering: %s %n" +
						"Completion Time: %s",
				this.getCar().getCarModel().getCarModelSpecification().getAllParts(),
				this.getOrderTime(),
				this.getCompletionTime());

	}
}
