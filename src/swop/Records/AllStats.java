package swop.Records;

import java.util.List;
import java.util.Map;

/**
 * a record containing all the stats from the {@code Statistics}
 * @param avgOrders the average orders for all the days
 * @param mdnOrders the median orders for all the days
 * @param ordersLast2 the orders from the last 2 days
 * @param avgDelay the average delay for all the days
 * @param mdnDelay the median delay for all the days
 * @param delayLast2 the delay of the last two orders
 */
public record AllStats(double avgOrders, double mdnOrders, Map<Integer, Integer> ordersLast2, double avgDelay,
					   double mdnDelay, List<Integer> delayLast2) {}
