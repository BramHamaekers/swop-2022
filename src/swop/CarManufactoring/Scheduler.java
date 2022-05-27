package swop.CarManufactoring;
import swop.Car.Car;
import swop.Car.CarModel.*;
import swop.Miscellaneous.TimeStamp;

import java.util.*;

/**
 * Iterator for the scheduling algorithm to pick the right car for a specified algorithm
 * @param <T> the type of the iterable
 */
interface QueueIterator<T> {
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

	/**
	 * The CarManufacturingController that this Scheduler provides the scheduling for
	 */
    private final CarManufacturingController controller;
	/**
	 * The amount of minutes that have past this day
	 */
    private int minutes;
	/**
	 * The current day the program is at
	 */
    private int day;
	/**
	 * The amount of minutes that CarMechanics will maximally work this day.
	 */
    private int workingDayMinutes;
	/**
	 * The amount of overtime that occurred so fat this day
	 */
    private int overTime;
	/**
	 * Maps the amount of time it takes to complete all tasks at a workstation for every type of CarModel
	 */
    private final Map<Class<? extends CarModel>, Integer> timePerWorkstationMap = new HashMap<>(){{
		put(ModelA.class, 50);
		put(ModelB.class, 70);
		put(ModelC.class, 60);
	}};
	/**
	 * A list of valid scheduling algorithms
	 */
	private final List<String> validAlgorithms = List.of("BATCH", "FIFO");
	/**
	 * The currently active scheduling algorithm
	 */
	private String algorithm;
	/**
	 * The batchOptions that are currently used if the scheduling algorithm is "BATCH"
	 */
	private Map<String,String> batchOptions;

	/**
	 * Initialises the scheduler with his controller, the current time at the beginning, 
	 * the total minutes in a working day and the algorithm.
	 * @param carManufacturingController a given controller that initialised this scheduler
	 */
    public Scheduler(CarManufacturingController carManufacturingController) {
		if (carManufacturingController == null)
			throw new IllegalArgumentException("provided controller is null, provide a controller to make a scheduler");
        this.controller = carManufacturingController;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
        this.setSchedulingAlgorithm("FIFO", null);
    }

	/**
	 * Get the working day minutes -> the total number of minutes the factory can work on a car this day
	 * @return int of the minutes in this day
	 */
	public int getWorkingDayMinutes() {
    	return this.workingDayMinutes;
    }

    /**
     * returns the estimated completion time based on the CarQueue and overtime done on previous days
     * @param car a car for which to calculate the estimated completion time
     * @return a TimeStamp containing the estimated day and minutes the car will be finished. 
     */
    public TimeStamp getEstimatedCompletionTime(Car car) {
		if (car == null)
			throw new IllegalArgumentException("null car object has no estimated completion time");
		int totalMinutes = this.getEstCompletionTimeInMinutes(car);
        return this.createTimeStampForCar(totalMinutes, car);
    }
    
    /**
     * calculates the estimated time it will take to finish the car in minutes
     * @param car from which the estimated time needs to be calculated
     * @return estimated time in minutes
     */
	private int getEstCompletionTimeInMinutes(Car car) {
		if (car == null)
			throw new IllegalArgumentException("null car obeject has no estimated completion time in minutes");
    	List<Car> workstationCars = this.getWorkStationCars();
    	int minutes = this.minutes;
    	minutes += this.calculateWaitingTime(car,  workstationCars);
    	return minutes;
    }
	/**
	 * Creates a TimeStamp based on current car 
	 * and the the estimated time in minutes given as parameter
	 * @param minutes the the estimated time in minutes
	 * @param car the current car
	 * @return a new TimeStamp
	 */
	private TimeStamp createTimeStampForCar(int minutes, Car car) {
		if (minutes < 0)
			throw new IllegalArgumentException("minutes cant be negative");
		if (car == null)
			throw new IllegalArgumentException("car cannot be null");
		if(this.carOnAssembly(car)) return new TimeStamp(this.day, minutes); //needs to be finished today
		int day = this.day;
		int workingDayMinutes = this.workingDayMinutes;
		while (minutes > workingDayMinutes) {
	        day += 1;
	        minutes -= workingDayMinutes;
	        workingDayMinutes = 960;
	    }
		if(day != this.day) 
			return new TimeStamp(day,roundMinutes(minutes, this.timePerWorkstationMap.get(car.getCarModel().getClass())));
		return new TimeStamp(day, minutes);
	}
    
    /**
     * This function will round the given minutes based on avgTimeCarInStation 
     * @param minutes the passed minutes that need to be rounded
     * @param avgTimeCarInStation how long it typical takes to finish a car of this model
     * @return rounded minutes based on the model
     */
    private int roundMinutes(int minutes, int avgTimeCarInStation) {
		if (minutes < 0 || avgTimeCarInStation < 0)
			throw new IllegalArgumentException("minutes cant be negative");
	   	if (minutes < 3 * avgTimeCarInStation) // First car of the new day
	   		return 3 * avgTimeCarInStation;
	   	return (int) (Math.ceil( (float) minutes/60) * 60);// Other cars
	}

	/**
	 * Checks if the parameter car is on the assembly line
	 * @param car the car to check if it is on the assemblyLine
	 * @return True if the given car is on the assemblyLine.
	 */
	private boolean carOnAssembly(Car car) {
		if (car == null)
			throw new IllegalArgumentException("Provided car is null");
		List<Car> workstationCars = this.getWorkStationCars();
		return workstationCars.contains(car);
	}

	/**
     * Returns how long it will take for the car to be finished
	 * @param car a car for which to return the estimated waiting time
	 * @param workstationCars list of cars currently on the assembly line 
	 * (this list may have elements like car = null)
     * @return time the car has to wait before it is completed
     */
    private int calculateWaitingTime(Car car,List<Car> workstationCars) {
		if (car == null)
			throw new IllegalArgumentException("car is null");
		if (workstationCars == null || workstationCars.size() != 3) // null objects count as car
			throw new IllegalArgumentException("not enough cars on station");
		Car station1Car = workstationCars.get(0);
    	Car station2Car = workstationCars.get(1);
    	Car station3Car = workstationCars.get(2);

    	QueueIterator<Car> carqueue = this.createIterator(this.controller.getCarQueue());
		Car current = carqueue.next(this.algorithm);

		int time = this.getMax(getUnfinishedCars());
    	while(station3Car == null || !station3Car.equals(car)) {
			station3Car = station2Car;
			station2Car = station1Car;
			station1Car = current;
    		current = carqueue.next(this.algorithm);
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
    	if(cars == null)
			throw new IllegalArgumentException("empty list");
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
		if (minutes<0)
			throw new IllegalArgumentException("time can't be negative");
        this.minutes = this.getMinutes() + minutes;
    }
    
    /**
     * Get amount of minutes that has already passed in the day
     * @return the current passed minutes in the day
     */
    public int getMinutes() {
        return this.minutes;
    }

	/**
	 * Get the amount of days that have already passed
	 * @return the current day
	 */
    public int getDay() {
        return this.day;
    }

	/**
	 * Get the current time in a string format
	 * @return {@code TimeStamp} of current time
	 */
	public TimeStamp getTime() {
		return new TimeStamp(this.getDay(), this.getMinutes());
	}

    /**
     * Advances the day based on the overtime
     */
    public void advanceDay() {
        this.day += 1;  // Go to next day
        overTime += (this.getMinutes() - this.workingDayMinutes); // Calculate the amount of overtime this day
        
        while(overTime > 960) { //overtime more than a day
        	this.day += 1;
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
		if (minutes<0)
			throw new IllegalArgumentException("no negative minutes");
		int estTime;
		Car nextCar = this.getNextScheduledCar();
		List<Car> workstationCars = getWorkStationCars();
		Car car1 = workstationCars.get(0);
		Car car2 = workstationCars.get(1);
		if(nextCar == null) {
			estTime = Collections.min(this.timePerWorkstationMap.values());
			if(car1 != null || car2 != null)
				estTime += this.getMax(Arrays.asList(car2, car1));
			else
				estTime += Collections.min(this.timePerWorkstationMap.values());
			if(car1 != null)
				estTime += this.getMax(List.of(car1));
			else
				estTime += Collections.min(this.timePerWorkstationMap.values());
		}
		else {
			estTime = this.getMax(Arrays.asList(nextCar, car1, car2))
					+ this.getMax(Arrays.asList(nextCar, car1)) 
					+ this.getMax(List.of(nextCar));
		}
        return this.minutes + minutes <= this.workingDayMinutes - estTime;
    }

	/**
	 * Function gets all the cars that are currently at workstation position of the assembly line
	 * @return list of cars at workstations
	 */
	private List<Car> getWorkStationCars() {
		List<WorkStation> workstations = this.controller.getAssembly().getWorkStations();
		return new ArrayList<>(workstations.stream().map(WorkStation::getCar).toList());
	}

	/**
     * Returns the current schedulingAlgorithm
     * @return the current scheduling algorithm (Batch or fifo)
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
		if (algorithm.equals("BATCH") && batchOptions == null)
			throw new IllegalArgumentException("invalid batch options");
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
		return algorithm != null && this.getValidAlgorithms().contains(algorithm);
	}

	/**
	 * @return copy of the validAlgorithms list
	 */
	public List<String> getValidAlgorithms() {
		return List.copyOf(this.validAlgorithms);
	}

    /**
     * Returns the car that is scheduled to be the next car on the assemblyLine
     * @return returns the first car on the carqueue
     */
    public Car getNextScheduledCar() {
    	QueueIterator<Car> iter = this.createIterator(this.controller.getCarQueue());
    	if (iter.hasNext())
        	return iter.next(this.algorithm);
    	return null;
    }

	/**
	 * create customIterator used in looping over a given list of cars based on the active scheduling algorithm
	 * @param cars list of cars to loop over
	 * @return new customIterator that loops over cars
	 */
	public QueueIterator<Car> createIterator(List<Car> cars) {
		if (cars == null)
			throw new IllegalArgumentException("List of cars cannot be null");
		return new QueueIterator<>() {
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


