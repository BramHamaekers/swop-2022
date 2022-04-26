package swop.Tests.UnitTests;

import org.junit.jupiter.api.Test;
import swop.Miscellaneous.Statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {
    Statistics a = new Statistics();
    //TODO: how to test listeners

    @Test
    void updateDelay() {
    }

    @Test
    void finishOrder() {
    }

    @Test
    void getAvgDelay() {
        assertEquals(a.getAvgDelay(),0);
    }

    @Test
    void getMdnDelay() {
        assertEquals(a.getMdnDelay(),0);
    }

    @Test
    void getDelayLast2() {
//        assertEquals(a.getDelayLast2(), new ArrayList<>());
    }

    @Test
    void getAvgOrders() {
        assertEquals(a.getAvgOrders(),0);
    }

    @Test
    void getMdnOrders() {
        assertEquals(a.getMdnOrders(),0);
    }

    @Test
    void getOrdersLast2() {
//        assertEquals(a.getOrdersLast2(), new HashMap<>());
    }
}