package swop.CarManufactoring;

import swop.Car.Car;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

interface costumIterator<T> {
	/**
	 * checks if the iterator has another element to return 
	 * @return
	 */
	boolean hasNext();
	
	/**
	 * Returns next element
	 * @return
	 */
	T next(String algorithm);
	
	/**
	 * Replaces the old iterated list with a new one
	 * @param l
	 */
	void refreshList(List<T> l);
	
}

public class Scheduler {

    private final CarManufacturingController controller;
    private int minutes;
    private int day;
    private int workingDayMinutes;
    private Map<String, Integer> timePerWorkstationMap = new HashMap<String, Integer>(){{
		put( "a",50);
		put( "b",70);
		put( "c",60);
	}};
	private String algorithm = "FIFO";
	private String[] batchOptions;

    public Scheduler(CarManufacturingController carManufacturingController) {

        this.controller = carManufacturingController;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
        //create new iterator
        setSchedulingAlgorithm("FIFO", null);
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
          List<Car> workstationCars = new LinkedList<Car>(Arrays.asList(this.controller.getAssembly().getWorkStations().get(0).getCar()
        		  , this.controller.getAssembly().getWorkStations().get(1).getCar()
        		  , this.controller.getAssembly().getWorkStations().get(2).getCar()));
     
    
          minutes += this.calculateWaitingTime(car,  workstationCars);

          // assume no overtime should be made: assignment -> scheduler should minimize overtime
          // scheduling a car to make overtime to complete should not be allowed
          while (minutes > workingDayMinutes) {
              day += 1;
              minutes -= workingDayMinutes;
              workingDayMinutes = 960;
          }

          if (day != this.day) {
              if (minutes < 180) {minutes = 180;} // First car of the new day
              else {minutes = (int) (Math.ceil( (float) minutes/60) * 60);} // Other cars

          }
          // Convert to format
          int hours = minutes / 60;
          hours += 6;
          minutes = minutes % 60;

          return String.format("day: %s, time: %02d:%02d%n", day, hours, minutes);
    }
    
    /**
     * Returns how long it will take for the car to be finished
     * @return
     */
    private int calculateWaitingTime(Car car,List<Car> workstationCars) {
    	Car station1Car = workstationCars.get(0);
    	Car station2Car = workstationCars.get(1);
    	Car station3Car = workstationCars.get(2);

    	costumIterator<Car> iter = this.iterator(this.controller.getCarQueue());
		Car current = iter.next(this.algorithm);
    	
    	int time = 0;
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
     * Returns the maximum time of a list of cars, that a car spends in a workstation
     * @param cars
     * @return
     */
    private int getMax(List<Car> cars) {
    	List<Integer> l = new LinkedList<Integer>();
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

    //TODO this function needs to be rewritten.
    public boolean canAddCarToAssemblyLine() {
        return this.minutes <= this.workingDayMinutes - 3 * 60;
    }

    /**
     * Returns the current schedulingAlgorithm
     * @return this.schedulingAlgorithm
     */
    private String getSchedulingAlgorithm() {
        return this.algorithm;
    }

    /**
     * set the current schedulingAlgorithm to the new given algorithms
     * @param algorithm
     */
    public void setSchedulingAlgorithm(String algorithm, String[] batchOptions) {
		if(!algorithm.equals("FIFO") && !algorithm.equals("BATCH")) throw new IllegalArgumentException("Invalid Scheduling Algoritm");
		this.algorithm = algorithm;
		if(algorithm.equals("BATCH")) this.batchOptions = batchOptions;
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
		return new costumIterator<Car>() {
			List<Car> list = new LinkedList<Car>(l);
			
			public void refreshList(List<Car> l) {
				list = new LinkedList<Car>(l);
			}
			
			public boolean hasNext() {
				return list.size() > 0;
			}
			
			public Car next(String algorithm) {
				if (!hasNext()) return null;
				if(algorithm.equals("FIFO")) return list.remove(0);
				if(algorithm.equals("BATCH"))
					for(int i = 0; i<list.size();i++) {
						if(list.get(i).getPartsMap().values().contains(batchOptions))
							return list.remove(i);
					}
				//if there is no more element that is comform the batch, return to fifo
				return list.remove(0);
			}
		};
	}    
   

}


