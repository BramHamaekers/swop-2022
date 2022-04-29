package swop.Miscellaneous;

import swop.Car.Car;
import swop.Listeners.StatisticsListener;
import swop.Records.AllStats;

import java.util.*;

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
    void updateDelay(Car car) {
        int finishDay = car.getCompletionTime().getDay();
        int delayedDays = finishDay - car.getInitialCompletionTime().getDay();
        int delayedMinutes = car.getCompletionTime().getMinutes() - car.getInitialCompletionTime().getMinutes();

        int minutes = 0;

        minutes += delayedDays * 3600;
        minutes += delayedMinutes;
        if(minutes < 0) minutes = 0;

        this.finishOrder(minutes, finishDay);
    }

    /**
     * update the delays map when a car is finished
     * @param delayedMinutes the given delay in minutes
     * @param finishDay the given day that the car was finished on
     */
    public void finishOrder(int delayedMinutes, int finishDay) {
    	int day_I = finishDay;
        if (this.getCarDelayMap().containsKey(day_I)) {
            this.getCarDelayMap().get(day_I).add(delayedMinutes);
        } else {
            this.getCarDelayMap().put(day_I, new ArrayList<>(List.of(delayedMinutes)));
        }
    }

    /**
     * Get the average delay over all the days
     * @return the average of the delays for all finished cars
     */
    public double getAvgDelay() {
        double total = 0;
        double amount = 0;
        for (List<Integer> delays : this.getCarDelayMap().values())
            for (int delay : delays) {
                total += delay;
                amount += 1;
            }
        return this.getCarDelayMap().size() > 0 ? total / amount : 0;
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
    	Map<Integer, List<Integer>> result = new LinkedHashMap<Integer, List<Integer>>();
        int max = 0;
        Map<Integer, List<Integer>> filteredDelayMap = getFilteredMap(this.getCarDelayMap());
        if (filteredDelayMap.isEmpty()) return result;
        Set<Integer> dayset = new LinkedHashSet<>(filteredDelayMap.keySet());
        max = Collections.max(dayset);
        List<Integer> delays = filteredDelayMap.get(max);
        if(delays.size() > 1) {
        	if(!delays.get(delays.size()-1).equals(delays.get(delays.size()-2))) {
        		result.put(max,List.of(delays.get(delays.size()-1), delays.get(delays.size()-2)));
        	}
        	else {
        		result.put(max,List.of(delays.get(delays.size()-1)));
            	result.put(max,List.of(delays.get(delays.size()-2)));
        	}
        	return result;
        }
        result.put(max, filteredDelayMap.get(max));
        dayset.remove(max);
        if(dayset.isEmpty()) return result;
        max = Collections.max(dayset);
        delays = filteredDelayMap.get(max);
        result.put(max, List.of(delays.get(delays.size()-1)));
        return result;
    }

    /**
     * filtering out all 0 delays
     * @param carDelayMap the delayMap mapping day to number of delays (with zero delays)
     * @return map without zero delays
     */
    private Map<Integer, List<Integer>> getFilteredMap(Map<Integer, List<Integer>> carDelayMap) {
    	Map<Integer, List<Integer>> filteredMap = new LinkedHashMap<Integer, List<Integer>>();
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
        List<Integer> orders = new ArrayList<>();
        for(List<Integer> delays: this.getCarDelayMap().values()){
            orders.add(delays.size());
        }
        if (orders.isEmpty()) return 0;
    	int max =  Collections.max(this.getCarDelayMap().keySet()) + 1;
    	int initialSize = orders.size();
    	for(int i = 0; i < Math.abs(max - initialSize);i++)
    			orders.add(0);
        Collections.sort(orders);
        if (orders.size() % 2 == 0)
        	return ((double) orders.get(orders.size() / 2) + (double) orders.get(orders.size() / 2 - 1)) / 2;
        else
            return (double) orders.get(orders.size() / 2);
    }

    /**
     * get the number of orders for the last two days
     * @return a map containing the day and the number of orders for that day for the last 2 days
     */
    public Map<Integer, Integer> getOrdersLast2(){
        Map<Integer, Integer> result = new LinkedHashMap<>(); //first int is the day + second the number of orders      
        if (this.getCarDelayMap().isEmpty()) return result;
        Map<Integer, List<Integer>> daymap = this.getCarDelayMap();
        int max = Collections.max(daymap.keySet());
        result.put(max, this.getCarDelayMap().get(max).size());
        if(!(daymap.size() > 1)) {
        	return result;
        }
        if(!daymap.containsKey(max-1)) {
        	result.put(max-1, 0);
        	return result;
        }
        result.put(max-1, this.getCarDelayMap().get(max-1).size());
        return result;
    }
    
    /**
     * Returns all the statistics.
     * @return a map containing the stat as a string and the value
     */
    public AllStats getOrderStats(){
    	
    	return new AllStats(getAvgOrders(), getMdnOrders(), getOrdersLast2(), getAvgDelay(),getMdnDelay(),getDelayLast2());
    }

    /**
     * Returns a map containing the day and the list of delays occurred that day
     * @return map of delays
     */
    public Map<Integer, List<Integer>> getCarDelayMap() {
        return this.carDelayMap;
    }
}
