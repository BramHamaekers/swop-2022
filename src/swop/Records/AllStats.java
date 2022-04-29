package swop.Records;

import java.util.List;
import java.util.Map;

public record AllStats(double avgOrders, double mdnOrders, Map<Integer, Integer> ordersLast2, double avgDelay,
		double mdnDelay, Map<Integer, List<Integer>> delayLast2) {}
