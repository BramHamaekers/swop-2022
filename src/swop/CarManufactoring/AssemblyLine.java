package swop.CarManufactoring;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class AssemblyLine {


	private LinkedList<Car> carQueue; // Queue of cars that still have to be assembled but are not yet on the assembly line
	private final LinkedList<WorkStation> workStations;


	public AssemblyLine() {
		this.carQueue = new LinkedList<>();
		this.workStations = createWorkStations();

	}

	private LinkedList<WorkStation> createWorkStations () {
		LinkedList<WorkStation> workStations = new LinkedList<>();
		workStations.add(new WorkStation("Car Body Post"));
		workStations.add(new WorkStation("Drivetrain Post"));
		workStations.add(new WorkStation("Accessories Post"));
		return workStations;
	}

	public void addToAssembly(CarOrder carOrder) {
		this.carQueue.add(carOrder.getCar());
		System.out.println(carQueue.get(0).getCarModel().getParts());
	}

	public void advanceAssemblyLine() {
		//TODO check if assemblyLine can advance

		// Move all cars on assembly by 1 position
		for (int i = this.workStations.size() - 1; i > 0; i--) {
			this.workStations.get(i).setCar(this.workStations.get(i-1).getCar());
		}
		// Set first workstation to first element from the queue
		try {this.workStations.getFirst().setCar(this.carQueue.removeFirst());}
		catch (NoSuchElementException e) {this.workStations.getFirst().setCar(null);}
	}
}

class WorkStation {
	private final String name;
	private Car car;

	public WorkStation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}
