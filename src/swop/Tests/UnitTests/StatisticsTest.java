package swop.Tests.UnitTests;

import org.junit.jupiter.api.Test;
import swop.Miscellaneous.Statistics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {
    Statistics stats = new Statistics();
    //TODO: how to test listeners

    @Test
    void updateDelay() {
    	stats.addTimeToDelayMap(30,0);
    	stats.addTimeToDelayMap(30,1);
    	stats.addTimeToDelayMap(30,2);
    	assertEquals(3,stats.getCarDelayMap().size());
    	stats.addTimeToDelayMap(30,2);
    	assertEquals(2,stats.getCarDelayMap().get(2).size());
    }

    @Test
    void finishOrder() {
        Statistics stats = new Statistics();
        stats.addTimeToDelayMap(30,0);
        assertEquals(Map.of(0, new ArrayList<>(List.of(30))),stats.getCarDelayMap());
        stats.addTimeToDelayMap(40,0);
        assertEquals(Map.of(0, new ArrayList<>(List.of(30,40))),stats.getCarDelayMap());
        stats.addTimeToDelayMap(300,1);
        assertEquals(Map.of(0, new ArrayList<>(List.of(30,40)), 1, new ArrayList<>(List.of(300))),stats.getCarDelayMap());
    }

    @Test
    void getAvgDelay() {
        Statistics stats = new Statistics();
        assertEquals(stats.getAvgDelay(),0);

        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(300,1);

        assertEquals(120, stats.getAvgDelay());
        stats.addTimeToDelayMap(50,9);
        assertEquals((double) 410 / 4, stats.getAvgDelay());
    }

    @Test
    void getMdnDelay_EvenAmount() {
        Statistics stats = new Statistics();
        assertEquals(stats.getMdnDelay(),0);

        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(40,0);
        stats.addTimeToDelayMap(300,1);
        stats.addTimeToDelayMap(60,2);

        assertEquals(50, stats.getMdnDelay());
    }

    @Test
    void getMdnDelay_UnEvenAmount() {
        Statistics stats = new Statistics();
        assertEquals(stats.getMdnDelay(),0);

        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(40,2);
        stats.addTimeToDelayMap(300,1);

        assertEquals(40, stats.getMdnDelay());
    }

    @Test
    void getDelayLast2() {
        Statistics stats = new Statistics();
        assertTrue(stats.getDelayLast2().isEmpty());
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(40,2);
        stats.addTimeToDelayMap(300,1);
        stats.addTimeToDelayMap(50,4);
        // 2 different days but spaced first element is the most recent, second the second most recent
        assertEquals(new LinkedHashMap<Integer, List<Integer>>(){{
        	put(4,List.of(50));
        	put(2,List.of(40));
        }}, stats.getDelayLast2());
        stats.addTimeToDelayMap(1,4);
        // 2 delays on same day
        assertEquals(new LinkedHashMap<Integer, List<Integer>>(){{
        	put(4,List.of(1,50));
        }}, stats.getDelayLast2());
    }
    
    @Test
    void getDelayLast2NoDelayLast() {
        Statistics stats = new Statistics();
        assertTrue(stats.getDelayLast2().isEmpty());
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(40,2);
        // 2 different days but spaced first element is the most recent, second the second most recent
        assertEquals(new LinkedHashMap<Integer, List<Integer>>(){{
        	put(2,List.of(40));
        	put(0,List.of(30));
        }}, stats.getDelayLast2());
        stats.addTimeToDelayMap(0,4);
        // 2 delays on same day
        assertEquals(new LinkedHashMap<Integer, List<Integer>>(){{
        	put(2,List.of(40));
        	put(0,List.of(30));
        }}, stats.getDelayLast2());
    }
    
    @Test
    void getDelayLast2NoDelayMiddle() {
        Statistics stats = new Statistics();
        assertTrue(stats.getDelayLast2().isEmpty());
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(0,4);
        // 2 different days but spaced first element is the most recent, second the second most recent
        assertEquals(new LinkedHashMap<Integer, List<Integer>>(){{
        	put(0,List.of(30));
        }}, stats.getDelayLast2());
        stats.addTimeToDelayMap(40,6);
        // 2 delays on same day
        assertEquals(new LinkedHashMap<Integer, List<Integer>>(){{
        	put(6,List.of(40));
        	put(0,List.of(30));
        }}, stats.getDelayLast2());
    }


    @Test
    void getAvgOrders() {
        Statistics stats = new Statistics();
        assertEquals(stats.getAvgOrders(),0);
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(60,2);
        stats.addTimeToDelayMap(90,1);
        // 3 orders on different days
        assertEquals(1, stats.getAvgOrders());
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(60,2);
        stats.addTimeToDelayMap(60,2);
        // orders on the same day
        assertEquals(2, stats.getAvgOrders());
        // some days no order
        stats.addTimeToDelayMap(60,9);
        assertEquals(0.7, stats.getAvgOrders());
    }

    @Test
    void getMdnOrders() {
        Statistics stats = new Statistics();
        assertEquals(stats.getMdnOrders(),0);
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(60,2);
        stats.addTimeToDelayMap(90,1);
        assertEquals(1,stats.getMdnOrders());
        stats.addTimeToDelayMap(30,0);
        stats.addTimeToDelayMap(30,1);
        assertEquals(2,stats.getMdnOrders());
        stats.addTimeToDelayMap(30,9);
        assertEquals(0,stats.getMdnOrders());
        stats.addTimeToDelayMap(30,5);
        assertEquals(0.5,stats.getMdnOrders());
    }

    @Test
    void getOrdersLast2() {
    	//TODO Don't know how to test the order
    	assertEquals(new LinkedHashMap<Integer, Integer>(), stats.getOrdersLast2());
        stats.addTimeToDelayMap(30,0);
        // day 0 and 1 order
        assertEquals(new LinkedHashMap<Integer, Integer>(){{
        	put(0,1);
        }}, stats.getOrdersLast2());
        stats.addTimeToDelayMap(30,0);
        // day 0 and 2 orders
        assertEquals(new LinkedHashMap<Integer, Integer>(){{
        	put(0,2);
        }}, stats.getOrdersLast2());
        stats.addTimeToDelayMap(30,1);
        // day 1, 1 order + day 0, 2 orders
        assertEquals(new LinkedHashMap<Integer, Integer>(){{
        	put(1,1);
        	put(0,2);
        }}, stats.getOrdersLast2());
        stats.addTimeToDelayMap(30,2);
        stats.addTimeToDelayMap(30,2);
        // day 2, 2 orders + day 1, 1 order
        assertEquals(new LinkedHashMap<Integer, Integer>(){{
        	put(2,2);
        	put(1,1);
        }}, stats.getOrdersLast2());
        stats.addTimeToDelayMap(30,501);
        // day 501, 1 order + day 500, 0 orders
        assertEquals(new LinkedHashMap<Integer, Integer>(){{
        	put(501,1);
        	put(500,0);
        }}, stats.getOrdersLast2());
    }
}