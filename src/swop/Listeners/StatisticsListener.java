package swop.Listeners;

import swop.Car.Car;

/**
 * Listener for statistics on finished cars
 */
public interface StatisticsListener {
    /**
     * update the statistics for a finished car
     * @param car a finished car
     */
    void updateDelay(Car car);
}
