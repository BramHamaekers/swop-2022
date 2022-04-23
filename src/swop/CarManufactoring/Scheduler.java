package swop.CarManufactoring;
import swop.Car.Car;
import java.util.*;

interface costumIterator<T> {
	/**
	 * iterator standard hasnext method
	 * @return whether the iterator has another element to return
	 */
	boolean hasNext();
	
	/**
	 * Returns next element
	 * @param algorithm the algorithm selection (for now fifo or batch)
	 * @return next element in the list for the iterator
	 */
	T next(String algorithm);
}

public class Scheduler {

    private final CarManufacturingController controller;
    private int minutes;
    private int day;
    private int workingDayMinutes;
    private final Map<String, Integer> timePerWorkstationMap = new HashMap<>(){{
		put( "ModelA",50);
		put( "ModelB",70);
		put( "ModelC",60);
	}};
	private final List<String> validAlgorithms = List.of("BATCH", "FIFO", "Cancel selection");
	private String algorithm;
	private Map<String,String> batchOptions;

    public Scheduler(CarManufacturingController carManufacturingController) {

        this.controller = carManufacturingController;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
        this.setSchedulingAlgorithm("FIFO", null);
    }

    /**
     * Calculates the estimated completion time based on the CarQueue and overtime done on previous days
     *
     * @return Time formatted as string
     */
    public String getEstimatedCompletionTime(Car car) {
    	  int day = this.day;
          int minutes = this.minutes;
          int workingDayMinutes = this.workingDayMinutes;
          List<Car> workstationCars = this.getWorkStationCars();
     
    
          minutes += this.calculateWaitingTime(car,  workstationCars);

          // assume no overtime should be made: assignment -> scheduler should minimize overtime
          // scheduling a car to make overtime to complete should not be allowed
          while (minutes > workingDayMinutes & !this.carOnAssembly(car)) {
              day += 1;
              minutes -= workingDayMinutes;
              workingDayMinutes = 960;
          }

          if (day != this.day) {
              if (minutes < 3 * this.timePerWorkstationMap.get(car.getCarModelName())) { // First car of the new day
				  minutes = 3 * this.timePerWorkstationMap.get(car.getCarModelName());
			  }
              else {minutes = (int) (Math.ceil( (float) minutes/60) * 60);} // Other cars

          }
          // Convert to format
          int hours = minutes / 60;
          hours += 6;
          minutes = minutes % 60;

          return String.format("day: %s, time: %02d:%02d%n", day, hours, minutes);
    }

	/**
	 * @param car the car to check if it is on the assemblyLine
	 * @return True if the given car is on the assemblyLine.
	 */
	private boolean carOnAssembly(Car car) {
		List<Car> workstationCars = this.getWorkStationCars();
		return workstationCars.contains(car);
	}

	/**
     * Returns how long it will take for the car to be finished
     * @return time
     */
    private int calculateWaitingTime(Car car,List<Car> workstationCars) {
		Car station1Car = workstationCars.get(0);
    	Car station2Car = workstationCars.get(1);
    	Car station3Car = workstationCars.get(2);

    	costumIterator<Car> iter = this.iterator(this.controller.getCarQueue());
		Car current = iter.next(this.algorithm);

		int time = this.getMax(getUnfinishedCars());
    	while(station3Car == null || !station3Car.equals(car)) {
			station3Car = station2Car;
			station2Car = station1Car;
			station1Car = current;
    		current = iter.next(this.algorithm);
    		time += this.getMax(Arrays.asList(station1Car, station2Car, station3Car));
    	}

    	return time;
    }

	/**
	 * Function returns all unFinishedCars on the assemblyLine
	 * @return all unFinishedCars on the assemblyLine
	 */
	private List<Car> getUnfinishedCars() {
		return this.controller.getAssembly().getUnfinishedCars();
	}

	/**
     * Returns the maximum time of a list of cars, that a car spends in a workstation
     * @param cars all the cars to check for
     * @return the maximum time for the list of cars
     */
    private int getMax(List<Car> cars) {
    	if(cars == null) return 0;
    	List<Integer> l = new LinkedList<>();
    	for(Car c: cars) {
    		if(c != null) l.add(this.timePerWorkstationMap.get(c.getCarModelName()));
    	}
    	if (l.isEmpty()) return 0;
    	return Collections.max(l);
    }

    /**
     * Add time in minutes to this.minutes
     *
     * @param minutes Minutes to add to this.minutes
     */
    public void addTime(int minutes) {
        this.minutes = this.getMinutes() + minutes;
    }
    
    /**
     * Get amount of minutes that have already passed in the day
     * @return this.minutes
     */
    public int getMinutes() {
        return this.minutes;
    }

	/**
	 * Get amount of days that have already passed
	 * @return this.day
	 */
    public int getDay() {
        return this.day;
    }

	/**
	 * Get the current time in a string format
	 * @return string of current time
	 */
	public String getTimeAsString() {
		int hours = this.getMinutes() / 60;
		hours += 6;
		int minutes = this.getMinutes() % 60;

		return String.format("day: %s, time: %02d:%02d%n", this.getDay(), hours, minutes);
	}

    /**
     * advances day by 1 and calculates the length of next day
     */
    public void advanceDay() {
        this.day += 1;  // Go to next day
        int overTime = this.getMinutes() - this.workingDayMinutes; // Calculate the amount of overtime this day
        this.workingDayMinutes = 960 - overTime;    // Calculate how long the shifts of next day will be
        this.minutes = 0; // Reset amount of minutes that have past this day
    }

	/**
	 * Function checks if there is enough time left in a working day to complete the next car.
	 * @param minutes the minutes that need to be added to the time before checking
	 * @return this.minutes <= this.workingDayMinutes - estTime
	 */
	public boolean canAddCarToAssemblyLine(int minutes) {
		Car nextCar = this.getNextScheduledCar();
		List<Car> workstationCars = getWorkStationCars();
		Car car1 = workstationCars.get(0);
		Car car2 = workstationCars.get(1);
		int estTime = this.getMax(Arrays.asList(nextCar, car1, car2))
				+ this.getMax(Arrays.asList(nextCar, car1));
		if(nextCar != null) estTime += this.getMax(List.of(nextCar));
        return this.minutes + minutes <= this.workingDayMinutes - estTime;
    }

	/**
	 * Function gets all the cars that are currently at workstation position of the assembly line
	 * @return list of cars at workstations
	 */
	private List<Car> getWorkStationCars() {
		ListIterator<WorkStation> workstations = this.controller.getAssembly().getWorkStations().listIterator();
		ArrayList<Car> workstationCars = new ArrayList<>();
		while (workstations.hasNext()){
			// check if throws nullpointerexception
			workstationCars.add(workstations.next().getCar());
		}
		return workstationCars;
	}

	/**
     * Returns the current schedulingAlgorithm
     * @return this.schedulingAlgorithm
     */
	public String getSchedulingAlgorithm() {
        return this.algorithm;
    }

    /**
     * set the current schedulingAlgorithm to the new given algorithms
     * @param algorithm selected priority algorithm
     */
    public void setSchedulingAlgorithm(String algorithm, Map<String,String> batchOptions) {
		if(!this.isValidSchedulingAlgorithm(algorithm)) throw new IllegalArgumentException("Invalid Scheduling Algorithm");
		this.algorithm = algorithm;
		if(algorithm.equals("BATCH")) this.batchOptions = batchOptions;
	}

	/**
	 * Check if the given algorithm is a valid algorithm
	 * @param algorithm the given algorithm
	 * @return True if the given algorithm is valid
	 */
	private boolean isValidSchedulingAlgorithm(String algorithm) {
		return this.getValidAlgorithms().contains(algorithm);
	}

	/**
	 * @return copy of the validAlgorithms list
	 */
	public List<String> getValidAlgorithms() {
		return List.copyOf(this.validAlgorithms);
	}

    /**
     * Returns the car that is scheduled to be the next car on the assemblyLine
     * @return this.carQueue.removeFirst()
     */
    public Car getNextScheduledCar() {
    	costumIterator<Car> iter = this.iterator(this.controller.getCarQueue());
    	if (iter.hasNext())
        	return iter.next(this.algorithm);
    	return null;
    }
    
	public costumIterator<Car> iterator(List<Car> l) {
		return new costumIterator<>() {
			final List<Car> list = new LinkedList<>(l);

			public boolean hasNext() {
				return list.size() > 0;
			}
			
			public Car next(String algorithm) {
				if (!hasNext()) return null;
				if(algorithm.equals("FIFO")) return list.remove(0);
				if(algorithm.equals("BATCH"))
					for(int i = 0; i<list.size();i++) {
						for(Map.Entry<String,String> prioSelection : batchOptions.entrySet())
							if(list.get(i).getPartsMap().get(prioSelection.getKey()).equals(prioSelection.getValue()))
								return list.remove(i);
					}
				//if there is no more element that is conform with the batch, return to fifo
				return list.remove(0);
			}
		};
	}    
   

}


