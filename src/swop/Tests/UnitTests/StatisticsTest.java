package swop.Tests.UnitTests;

import org.junit.jupiter.api.Test;
import swop.Miscellaneous.Statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {
    Statistics stats = new Statistics();
    //TODO: how to test listeners

    @Test
    void updateDelay() {
    }

    @Test
    void finishOrder() {
        Statistics stats = new Statistics();
        stats.finishOrder(30,0);
        assertEquals(Map.of(0, new ArrayList<>(List.of(30))),stats.getCarDelayMap());
        stats.finishOrder(40,0);
        assertEquals(Map.of(0, new ArrayList<>(List.of(30,40))),stats.getCarDelayMap());
        stats.finishOrder(300,1);
        assertEquals(Map.of(0, new ArrayList<>(List.of(30,40)), 1, new ArrayList<>(List.of(300))),stats.getCarDelayMap());
    }

    @Test
    void getAvgDelay() {
        Statistics stats = new Statistics();
        assertEquals(stats.getAvgDelay(),0);
    }

    @Test
    void getMdnDelay() {
        Statistics stats = new Statistics();
        assertEquals(stats.getMdnDelay(),0);
    }

    @Test
    void getDelayLast2() {
//        assertEquals(a.getDelayLast2(), new ArrayList<>());
    }

    @Test
    void getAvgOrders() {
        Statistics stats = new Statistics();
        assertEquals(stats.getAvgOrders(),0);
    }

    @Test
    void getMdnOrders() {
        Statistics stats = new Statistics();
        assertEquals(stats.getMdnOrders(),0);
    }

    @Test
    void getOrdersLast2() {
//        assertEquals(a.getOrdersLast2(), new HashMap<>());
    }
}