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

        stats.finishOrder(30,0);
        stats.finishOrder(40,0);
        stats.finishOrder(300,1);

        assertEquals((double) 370 / 3, stats.getAvgDelay());
    }

    @Test
    void getMdnDelay_EvenAmount() {
        Statistics stats = new Statistics();
        assertEquals(stats.getMdnDelay(),0);

        stats.finishOrder(30,0);
        stats.finishOrder(40,0);
        stats.finishOrder(300,1);
        stats.finishOrder(60,2);

        assertEquals(50, stats.getMdnDelay());
    }

    @Test
    void getMdnDelay_UnEvenAmount() {
        Statistics stats = new Statistics();
        assertEquals(stats.getMdnDelay(),0);

        stats.finishOrder(30,0);
        stats.finishOrder(40,2);
        stats.finishOrder(300,1);

        assertEquals(40, stats.getMdnDelay());
    }

    @Test
    void getDelayLast2() {
        Statistics stats = new Statistics();
        stats.finishOrder(30,0);
        stats.finishOrder(40,2);
        stats.finishOrder(300,1);
        stats.finishOrder(50,4);
        assertEquals("test", stats.getDelayLast2());
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