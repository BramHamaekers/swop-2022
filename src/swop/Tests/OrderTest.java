package swop.Tests;

import swop.CarManufactoring.Order;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderTest {

    Order order = new Order(List.of("a"),0);

    @Test
    void constructor() {
        assertEquals(order.getParts(), List.of("a"));
        assertEquals(order.getBuildState(), 0);
    }

    @Test
    void upBuildState() {
        assert order.getBuildState()==0;
        order.upBuildState();
        assert order.getBuildState()==1;
    }

    @Test
    void isCompleted() {
        assertEquals(new Order(List.of(" "),3).isCompleted(),true);
        assertEquals(order.isCompleted(),false);
    }
}