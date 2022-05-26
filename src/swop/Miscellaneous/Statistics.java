package swop.Miscellaneous;

import swop.Car.Car;
import swop.Listeners.StatisticsListener;
import swop.Records.AllStats;

import java.util.*;
import java.util.Map.Entry;

/**
 * A class containing all delays and orders for a controller
 */
public class Statistics { 
    private final Map<Integer, List<Integer>> carDelayMap = new HashMap<>();
    public final StatisticsListener statisticsListener = this::updateDelay;

    /**
     * Updates the delay map given a Car
     * @param car of the type Car
     */
    public void updateDelay(Car car) {
        if (car == null)
            throw new IllegalArgumentException("updateDelay of null object not possible");
    	int[] dayAndMinutes = getDayAndDelayMinutesCar(car);
        this.addTimeToDelayMap(dayAndMinutes[1], dayAndMinutes[0]);
    }

    /**
     * returns a array with in [0] the finished day and on [1] the total delay in minutes
     * @param car
     * @return int[] {finishedDay, delayMinutes}
     */
    private int[] getDayAndDelayMinutesCar(Car car) {
    	if (car == null) throw new IllegalArgumentException("Error trying to update the delay: Car can not be null");
        int minutes = calculateDelayMinutes(car);
		return new int[] {car.getCompletionTime().getDay(), minutes};
	}

    /**
     * Will calculate the total delay in minutes (if negative delay -> return 0 delay minutes)
     * @param car the finished car
     * @return total delay in minutes
     */
	private int calculateDelayMinutes(Car car) {
		if (car == null) throw new IllegalArgumentException("Error trying to update the delay: Car can not be null");
		int delayedDays = car.getCompletionTime().getDay() - car.getInitialCompletionTime().getDay();
        int delayedMinutes = car.getCompletionTime().getMinutes() - car.getInitialCompletionTime().getMinutes();
        int minutes = 0;
        minutes += delayedDays * 3600 + delayedMinutes;
        if(minutes < 0) minutes = 0;
		return minutes;
	}

	/**
     * update the delays map when a car is finished
     * @param minutes the given day that the car was finished on
     * @param day the given delay in minutes
     */
    public void addTimeToDelayMap(int minutes, int day) {
        if (this.carDelayMap.containsKey(day)) {
            this.carDelayMap.get(day).add(minutes);
        } else {
            this.carDelayMap.put(day, new ArrayList<>(List.of(minutes)));
        }
    }

    /**
     * Get the average delay over all the days
     * @return the average of the delays for all finished cars
     */
    public double getAvgDelay() {
        double totalDelay = 0;
        double numberOfDelays = 0;
        for (List<Integer> delays : this.getCarDelayMap().values())
            for (int delay : delays) {
            	totalDelay += delay;
            	numberOfDelays += 1;}
        return this.getCarDelayMap().size() > 0 ? totalDelay / numberOfDelays : 0;
    }

    /**
     * Get the median delay over all the days
     * @return the median of the delays for all finished cars
     */
    public double getMdnDelay() {
        List<Integer> total = new ArrayList<>();
        for (List<Integer> delays : this.getCarDelayMap().values())
            total.addAll(delays);
        Collections.sort(total);
        if (total.size() > 0) {
            if (total.size() % 2 == 0)
                return ((double) total.get(total.size() / 2) + (double) total.get(total.size() / 2 - 1)) / 2;
            else
                return (double) total.get(total.size() / 2);
        } else return 0;
    }

    /**
     * get the last 2 delays
     * @return a list containing the last two delays
     */
    public Map<Integer, List<Integer>> getDelayLast2(){
    	Map<Integer, List<Integer>> result = new LinkedHashMap<>();
    	Map<Integer, List<Integer>> filteredDelayMap = getFilteredMap(this.getCarDelayMap());
    	Set<Integer> dayset = new LinkedHashSet<>(filteredDelayMap.keySet());
    	int total = 0;
    	while (total < 2 && !dayset.isEmpty()) {
    		int day = Collections.max(dayset);
    		List<Integer> delays = filteredDelayMap.get(day);
    		result.put(day,new ArrayList<>());
    		for(int i = 1; i<=delays.size() && i <= 2 ;i++)
    			result.get(day).add(delays.get(delays.size()-i));
    		total += delays.size();
    		dayset.remove(day);
    	}
        return result;
    }

    /**
     * Filtering out all 0 delays
     * @param carDelayMap the delayMap mapping day to number of delays (with zero delays)
     * @return map containing only the nonzero delays
     */
    private Map<Integer, List<Integer>> getFilteredMap(Map<Integer, List<Integer>> carDelayMap) {
        if (carDelayMap == null)
            throw new IllegalArgumentException("provided delayMap is null");
    	Map<Integer, List<Integer>> filteredMap = new LinkedHashMap<>();
    	for(Map.Entry<Integer, List<Integer>> v: carDelayMap.entrySet()) {
    		List<Integer> notZero = v.getValue().stream().filter(e -> !e.equals(0)).toList();
    		if(!notZero.isEmpty())
    			filteredMap.put(v.getKey(), notZero);
    	}
		return filteredMap;
	}

	/**
     * Get the average number of orders over all the days
     * @return the average number of orders for all finished cars
     */
    public double getAvgOrders(){
        double total = 0;
        for(List<Integer> delays: this.getCarDelayMap().values())
            total+= delays.size();
        if(this.getCarDelayMap().size()>0) {
        	int max =  Collections.max(this.getCarDelayMap().keySet()) + 1;
        	return total/(double)max;
        }
        return 0;
    }

    /**
     * Get the median number of orders over all the days
     * @return the median number of orders for all finished cars
     */
    public double getMdnOrders(){ 
    	List<Integer> ordersDailyFinished = getOrdersDailyFinished();
        if (ordersDailyFinished.isEmpty()) return 0;
        if (ordersDailyFinished.size() % 2 == 0)
        	return ((double) ordersDailyFinished.get(ordersDailyFinished.size() / 2) + (double) ordersDailyFinished.get(ordersDailyFinished.size() / 2 - 1)) / 2;
        else
            return (double) ordersDailyFinished.get(ordersDailyFinished.size() / 2);
    }

    /**
     * calculates the orders finished each day and stores them in a list sorted by day
     * @return  list with orders finished for each day
     */
	private List<Integer> getOrdersDailyFinished() {
		List<Integer> ordersDailyFinished = new ArrayList<>();
		if (this.getCarDelayMap().isEmpty()) return ordersDailyFinished;
		for(List<Integer> delays: this.getCarDelayMap().values()){
            ordersDailyFinished.add(delays.size());
        }
    	int max =  Collections.max(this.getCarDelayMap().keySet()) + 1;
    	int initialSize = ordersDailyFinished.size();
    	for(int i = 0; i < Math.abs(max - initialSize);i++)
    		ordersDailyFinished.add(0);
        Collections.sort(ordersDailyFinished);
        return ordersDailyFinished;
	}

    /**
     * get the number of orders for the last two days
     * @return a map containing the day and the number of orders for that day for the last 2 days
     */
    public Map<Integer, Integer> getOrdersLast2(){
        Map<Integer, Integer> result = new LinkedHashMap<>(); //key is the day, value the number of orders      
        Set<Integer> daySet = this.getCarDelayMap().keySet();
        if (daySet.isEmpty()) return result;
    	int max = Collections.max(daySet);
        for(int i = 0; i<2;i++) {
        	if(daySet.contains(max)) result.put(max, this.getCarDelayMap().get(max).size());
        	else if(max > 0) result.put(max, 0);
        	max--;
        }
        return result;
    }
    
    /**
     * Returns all the statistics.
     * @return a record containing all the statistics
     */
    public AllStats getOrderStats(){ 	
    	return new AllStats(getAvgOrders(), getMdnOrders(), getOrdersLast2(), getAvgDelay(),getMdnDelay(),getDelayLast2());
    }

    /**
     * Returns a map containing the day and the list of delays occurred that day
     * @return map of delays
     */
    public Map<Integer, List<Integer>> getCarDelayMap() {
        return new HashMap<>(this.carDelayMap);
    }
}
