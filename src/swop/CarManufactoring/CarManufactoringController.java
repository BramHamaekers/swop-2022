package swop.CarManufactoring;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import swop.Exceptions.NotAllTasksCompleteException;

public class CarManufactoringController {
	private LinkedList<Car> carQueue; //msschien static maken, dan hoeft de schedular this niet meer bij te houden
	private AssemblyLine assemblyLine;
	private final Scheduler scheduler;
	
	
	
	public CarManufactoringController() {
		this.carQueue = new LinkedList<>();
		this.assemblyLine = new AssemblyLine(createWorkStations());
		this.scheduler = new Scheduler(this);
	}
	
	/**
	 * Function creates all the workstations that are part of the assemblyLine as a linked list so that they have the
	 * right order.
	 * @return LinkedList<WorkStation> of all the workstations of this assemblyLine
	 */
	private LinkedList<WorkStation> createWorkStations() {
		LinkedList<WorkStation> workStations = new LinkedList<>();
		workStations.add(new WorkStation("Car Body Post"));
		workStations.add(new WorkStation("Drivetrain Post"));
		workStations.add(new WorkStation("Accessories Post"));
		return workStations;
	}
	
	/**
	 * ads a new car to waiting car queue
	 * @param car
	 */
	public void addToCarQueue(Car car) {
		if(car == null) throw new IllegalArgumentException("car is null");
		this.carQueue.add(car);
		
	}
	
	/**
	 * Returns how many cars are still waiting
	 * @return carQueue.size()
	 */
	public int getCarQueuesize() {
		return carQueue.size();
	}

	public AssemblyLine getAssembly() {
		return this.assemblyLine;
	}
	
	public void advanceAssembly(int minutes) throws NotAllTasksCompleteException {
		if(!carQueue.isEmpty()) {
			this.assemblyLine.advance(carQueue.get(0), minutes);
		}
		this.updateScheduleTime(minutes);
		
	}

	private void updateScheduleTime(int minutes) {
		this.scheduler.addTime(minutes);
		
	}
	
	/*schedular stuff
	workStation.getCar().setCompletionTime(this.scheduler.getMinutes());
	advanceAssemblyTime(minutes);
	

	private void advanceNextFromQueue() {
		// Set first workstation to first element from the queue
		try {
			if (!canFinishNewCar()) {
				this.workStations.getFirst().setCar(null);
				return;
			}
			Car car = this.getCarQueue().removeFirst();
			this.workStations.getFirst().setCar(car);}
		catch (NoSuchElementException e) {this.workStations.getFirst().setCar(null);}
	}


	private boolean canFinishNewCar() {
		return this.scheduler.canAddCarToAssemblyLine();
	}
	
		advanceNextFromQueue();
		// check if the assemblyLine is done for the day
		if (!canFinishNewCar() && isEmptyAssemblyLine()) {
			this.scheduler.advanceDay();
		}
	
	*/
	
}
