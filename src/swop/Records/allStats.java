package swop.Records;

import java.util.List;
import java.util.Map;

public record allStats(double avgOrders, double mdnOrders, Map<Integer, Integer> ordersLast2, double avgDelay,
		double mdnDelay, List<Integer> delayLast2) {}
