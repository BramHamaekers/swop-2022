package swop.CarManufactoring;
import swop.Car.Car;
import swop.Car.CarModel.*;
import swop.Miscellaneous.TimeStamp;

import java.util.*;

/**
 * Iterator for the scheduling algorithm to pick the right car for a specified algorithm
 * @param <T> the type of the iterable
 */
interface customIterator<T> {
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

/**
 * A scheduler manages the time and order of the car queue
 */
public class Scheduler {

    private final CarManufacturingController controller;
    private int minutes;
    private int day;
    private int workingDayMinutes;
    private int overTime;
    private final Map<Class<? extends CarModel>, Integer> timePerWorkstationMap = new HashMap<>(){{
		put(ModelA.class, 50);
		put(ModelB.class, 70);
		put(ModelC.class, 60);
	}};
	private final List<String> validAlgorithms = List.of("BATCH", "FIFO", "Cancel selection");
	private String algorithm;
	private Map<String,String> batchOptions;

	/**
	 * Initialises the scheduler with his controller, the current time at the beginning, 
	 * the total minutes in a working day and the algorithm.
	 * @param carManufacturingController a given controller that initialised this scheduler
	 */
    public Scheduler(CarManufacturingController carManufacturingController) {
        this.controller = carManufacturingController;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
        this.setSchedulingAlgorithm("FIFO", null);
    }

    /**
     * returns the estimated completion time based on the CarQueue and overtime done on previous days
     * @param car a car for which to calculate the estimated completion time
     * @return a TimeStamp containing the estimated day and minutes the car will be finished. 
     */
    public TimeStamp getEstimatedCompletionTime(Car car) {
          int totalMinutes = this.getEstCompletionTimeInMinutes(car);
          if(this.carOnAssembly(car)) return new TimeStamp(this.day, totalMinutes);
          return this.createTimeStamp(totalMinutes, car);
    }
    
    /**
     * calculates the estimated time it will take to finish the car in minutes
     * @param car from which the estimated time needs to be calculated
     * @return estimated time in minutes
     */
	private int getEstCompletionTimeInMinutes(Car car) {
    	List<Car> workstationCars = this.getWorkStationCars();
    	int minutes = this.minutes;
    	minutes += this.calculateWaitingTime(car,  workstationCars);
    	return minutes;
    }
    
	/**
	 * Creates a TimeStamp based on current car 
	 * and the the estimated time in minutes
	 * @param the the estimated time in minutes
	 * @param current car
	 * @return a new TimeStamp
	 */
    private TimeStamp createTimeStamp(int minutes, Car car) {
    	int day = this.day;
    	int workingDayMinutes = this.workingDayMinutes;
    	while (minutes > workingDayMinutes) {
            day += 1;
            minutes -= workingDayMinutes;
            workingDayMinutes = 960;
        }
    	return new TimeStamp(day,roundMinutes(minutes, day, this.timePerWorkstationMap.get(car.getCarModel().getClass())));
    }
    
    /**
     * This function will round the given minutes based on avgTimeCarInStation 
     * and parameter day != current day
     * @param minutes that need to be rounded
     * @param day on which the car estimated to be finished
     * @param avgTimeCarInStation how long it typical takes to finish a car of this model
     * @return rounded minutes
     */
    private int roundMinutes(int minutes,int day, int avgTimeCarInStation) {
	   	 if (day != this.day) {
	   		 if (minutes < 3 * avgTimeCarInStation) // First car of the new day
	   			 return 3 * avgTimeCarInStation;
	   		 return (int) (Math.ceil( (float) minutes/60) * 60);// Other cars
	    }
	   	return minutes;
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
	 * @param car a car for which to return the estimated waiting time
	 * @param workstationCars the cars currently on the assembly line
     * @return time
     */
    private int calculateWaitingTime(Car car,List<Car> workstationCars) {
		Car station1Car = workstationCars.get(0);
    	Car station2Car = workstationCars.get(1);
    	Car station3Car = workstationCars.get(2);

    	customIterator<Car> iter = this.iterator(this.controller.getCarQueue());
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
    		if(c != null) l.add(this.timePerWorkstationMap.get(c.getCarModel().getClass()));
    	}
    	if (l.isEmpty()) return 0;
    	return Collections.max(l);
    }

    /**
     * Add time in minutes to this.minutes
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
	public TimeStamp getTime() {
		return new TimeStamp(this.getDay(), this.getMinutes());
	}

    /**
     * advances day based on the overtime
     */
    public void advanceDay() {
        this.day += 1;  // Go to next day
        overTime += (this.getMinutes() - this.workingDayMinutes); // Calculate the amount of overtime this day
        
        while(overTime > 960) { //overtime more than a day
        	this.day += 1;
        	this.workingDayMinutes = 0;
        	overTime -= 960;
        }
        if (overTime > 0){ //overtime less than a day and positive
        	this.workingDayMinutes = 960 - overTime;
        	overTime = 0;
        }
        else { //negative overtime
        	overTime = 0; 
        	this.workingDayMinutes = 960;
        }
        this.minutes = 0; // Reset amount of minutes that have past this day
    }

	/**
	 * Function checks if there is enough time left in a working day to complete the next car.
	 * @param minutes the minutes that need to be added to the time before checking
	 * @return whether there is enough time in the day to add a car to the assembly line
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
			// check if throws NullpointerException
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
	 * @param batchOptions a map of the selected batch option
     */
    public void setSchedulingAlgorithm(String algorithm, Map<String,String> batchOptions) {
		if(!this.isValidSchedulingAlgorithm(algorithm)) throw new IllegalArgumentException("Invalid Scheduling Algorithm");
		this.algorithm = algorithm;
		if(algorithm.equals("BATCH")) this.batchOptions = batchOptions;
		//update the est. completionTime from the car in the queue
		this.controller.updateEstimatedCompletionTime();
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
    	customIterator<Car> iter = this.iterator(this.controller.getCarQueue());
    	if (iter.hasNext())
        	return iter.next(this.algorithm);
    	return null;
    }

	/**
	 * create customIterator used in looping over a given list of cars based on the active scheduling algorithm
	 * @param cars list of cars to loop over
	 * @return new customIterator that loops over cars
	 */
	public customIterator<Car> iterator(List<Car> cars) {
		return new customIterator<>() {
			final List<Car> list = new LinkedList<>(cars);

			public boolean hasNext() {
				return list.size() > 0;
			}
			
			public Car next(String algorithm) {
				if (!hasNext()) return null;
				if(algorithm.equals("FIFO")) return list.remove(0);
				if(algorithm.equals("BATCH"))
					for(int i = 0; i<list.size();i++) {
						for(Map.Entry<String,String> prioSelection : batchOptions.entrySet())
							if(list.get(i).getPartsMap().get(prioSelection.getKey()) != null)
									if(list.get(i).getPartsMap().get(prioSelection.getKey()).equals(prioSelection.getValue()))
										return list.remove(i);
					}
				//if there is no more element that is conform with the batch, return to FiFo
				return list.remove(0);
			}
		};
	}    
   

}


