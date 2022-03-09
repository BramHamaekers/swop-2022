package swop.CarManufactoring;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AssemblyLine {


	private List<Car> carQueue; // Queue of cars that still have to be assembled but are not yet on the assembly line
	private final List<WorkStation> workStations;


	public AssemblyLine() {
		this.carQueue = new ArrayList<>();
		this.workStations = createWorkStations();

	}

	private List<WorkStation> createWorkStations () {
		List<WorkStation> workStations = new LinkedList<>();
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
		// add first car from carQueue to first workstation
		// advance all other workstations
		// IMPORTANT! -> check if assemblyline can be advanced!
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
