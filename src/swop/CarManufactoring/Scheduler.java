package swop.CarManufactoring;

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
	T next();
	
	/**
	 * Replaces the old iterated list with a new one
	 * @param l
	 */
	void refreshList(List<T> l);
	
	/**
	 * Sets the algorithm that will be used to retrieve the next element.
	 * @param algo
	 * @param batchOptions
	 */
	void setAlgoritm(String algo, String[] batchOptions);
	String getAlgoritm();
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
    private costumIterator<Car> carIterator;

    public Scheduler(CarManufacturingController carManufacturingController) {

        this.controller = carManufacturingController;
        this.minutes = 0;
        this.workingDayMinutes = 960; // 06:00 -> 22:00
        //create new iterator
        this.carIterator = this.iterator(this.controller.getCarQueue());
        setSchedulingAlgorithm("FIFO");
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
          
          //refresh/reset the iterator
          this.updateIterator();

    
          minutes += this.calculateWaitingTime(car,  workstationCars);

          //reset again
  	      this.updateIterator();

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
    	Car before1 = workstationCars.get(0);
    	Car before2 = workstationCars.get(1);
    	Car before3 = workstationCars.get(2);
   
    	Car current = this.getNextScheduledCar();
    	
    	int time = 0;
    	while(before3 == null || !before3.equals(car)) {
    		before3 = before2;
    		before2 = before1;
    		before1 = current;
    		current = this.getNextScheduledCar();
    		time += this.getMax(Arrays.asList(before1, before2, before3));
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
     * update Iterator List
     * @param l
     */
    public void updateIterator() {
    	this.carIterator.refreshList(this.controller.getCarQueue());
    }

    /**
     * Get amount of minutes that have already past in the day
     * @return this.day
     */
    public int getMinutes() {
        return minutes;
    }

    public int getDay() {
        return this.day;
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
        return this.carIterator.getAlgoritm();
    }

    /**
     * set the current schedulingAlgorithm to the new given algorithms
     * @param schedulingAlgorithm
     */
    public void setSchedulingAlgorithm(String schedulingAlgorithm) {
    	this.carIterator.setAlgoritm(schedulingAlgorithm, null);
    }

    /**
     * Returns the car that is scheduled to be the next car on the assemblyLine
     * @return this.carQueue.removeFirst()
     */
    public Car getNextScheduledCar() {
    	if (this.carIterator.hasNext())
        	return this.carIterator.next();
    	return null;
    }
    
	public costumIterator<Car> iterator(List<Car> l) {
		return new costumIterator<Car>() {
			List<Car> list = new LinkedList<Car>(l);
			String algoritm = "FIFO";
			String[] batchOptions;
			
			public void refreshList(List<Car> l) {
				list = new LinkedList<Car>(l);
			}
			
			public void setAlgoritm(String algo, String[] batchOptions) {
				if(!algo.equals("FIFO") && !algo.equals("BATCH")) throw new IllegalArgumentException("Invalid Scheduling Algoritm");
				this.algoritm = algo;
				if(algo.equals("BATCH")) this.batchOptions = batchOptions;
			}
			
			public String getAlgoritm() {
				return this.algoritm;
			}
			
			public boolean hasNext() {
				return list.size() > 0;
			}
			
			public Car next() {
				if(algoritm.equals("FIFO")) return list.remove(0);
				if(algoritm.equals("BATCH"))
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


