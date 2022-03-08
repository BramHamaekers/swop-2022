package swop.CarManufactoring;

import java.util.ArrayList;
import java.util.List;

public class AssemblyLine {

	private List<Car> carQueue;

	public AssemblyLine() {
		this.carQueue = new ArrayList<>();
	}

	public void addToAssembly(Order order) {
		for (Car car: order.getCars()) {
			carQueue.add(car);
		}
	}
}
