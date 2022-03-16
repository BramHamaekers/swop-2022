package swop.CarManufactoring;

import org.jetbrains.annotations.NotNull;
import swop.Database.RandomID;

public class CarOrder implements Comparable<CarOrder>{
	private final Car car;
	private final String ID;
	
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

	public String getID() {
		return this.ID;
	}

	public String getEstimatedCompletionTime() {
		return this.getCar().getEstimatedCompletionTime();
	}

	public int getCompletionTime() {
		return this.getCar().getCompletionTime();
	}

	@Override
	public int compareTo(@NotNull CarOrder carOrder) {
		return Integer.compare(this.getCompletionTime(), carOrder.getCompletionTime());
	}
}
