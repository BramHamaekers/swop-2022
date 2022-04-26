package swop.Miscellaneous;

import java.util.*;

public class Statistics {
    private final Map<Integer, List<Integer>> delays = new HashMap<>();

    public Statistics() {}

    /**
     * update the delays map when a car is finished
     * @param delay the given delay in minutes
     * @param day the given day int (0-*)
     */
    public void finishOrder(int delay, int day) {
        Integer day_I = day;
        if (this.delays.containsKey(day_I)) {
            this.delays.get(day_I).add(delay);
        } else {
            this.delays.put(day_I, new ArrayList<>(delay));
        }
    }

    /**
     * Get the average delay over all the days
     * @return the average of the delays for all finished cars
     */
    public double getAvgDelay() {
        double total = 0;
        for (List<Integer> delays : this.delays.values())
            for (Integer delay : delays) {
                total += delay;
            }
        return this.delays.size() > 0 ? total / (double) this.delays.size() : 0;
    }

    /**
     * Get the median delay over all the days
     * @return the median of the delays for all finished cars
     */
    public double getMdnDelay() {
        List<Integer> total = new ArrayList<>();
        for (List<Integer> delays : this.delays.values())
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
     * get all the delays for the last two days
     * @return a list containing all the delays from the last two days
     */
    public List<Integer> getDelayLast2(){
        List<Integer> result = new ArrayList<>();
        int max = Collections.max(this.delays.keySet());
        //todo: no seperation between 2 days
        result.addAll(this.delays.get(max-1));
        result.addAll(this.delays.get(max));
        return result;
    }

    /**
     * Get the average number of orders over all the days
     * @return the average number of orders for all finished cars
     */
    public double getAvgOrders(){
        double total = 0;
        for(List<Integer> delays: this.delays.values())
            total+= delays.size();
        return this.delays.size()>0 ? total/(double) this.delays.size(): 0;
    }

    /**
     * Get the median number of orders over all the days
     * @return the median number of orders for all finished cars
     */
    public double getMdnOrders(){
        List<Integer> orders = new ArrayList<>();
        for(List<Integer> delays: this.delays.values()){
            orders.add(delays.size());
        }
        Collections.sort(orders);
        if (orders.size() > 0) {
            if (orders.size() % 2 == 0)
                return ((double) orders.get(orders.size() / 2) + (double) orders.get(orders.size() / 2 - 1)) / 2;
            else
                return (double) orders.get(orders.size() / 2);
        } else return 0;
    }


    /**
     * get the number of orders for the last two days
     * @return a map containing the day and the number of orders for that day for the last 2 days
     */
    public Map<Integer, Integer> getOrdersLast2(){
        Map<Integer, Integer> result = new HashMap<>();
        int max = Collections.max(this.delays.keySet());
        result.put(max-1, this.delays.get(max-1).size());
        result.put(max, this.delays.get(max).size());
        return result;
    }
}
