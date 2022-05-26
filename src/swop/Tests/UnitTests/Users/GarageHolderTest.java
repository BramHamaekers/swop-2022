package swop.Tests.UnitTests.Users;

import org.junit.jupiter.api.Test;
import swop.Main.AssemAssist;
import swop.Users.GarageHolder;

import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GarageHolderTest {
    AssemAssist assem = new AssemAssist();

    @Test
    void newGarageHolder_InvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new GarageHolder(null, assem));
        assertThrows(IllegalArgumentException.class, () -> new GarageHolder("a", null));
    }

    @Test
    void getOrders_NewGarageHolder() {
        GarageHolder garageHolder = new GarageHolder("a", assem);
        assertEquals(new HashSet<>(), garageHolder.getOrders());
    }

    @Test
    void placeOrder_NullConfig() {
        GarageHolder garageHolder = new GarageHolder("a", assem);
        assertThrows(IllegalArgumentException.class, () -> garageHolder.placeOrder(null, null));
    }

    @Test
    void placeOrder_NullModel() {
        GarageHolder garageHolder = new GarageHolder("a", assem);
        assertThrows(IllegalArgumentException.class, () -> garageHolder.placeOrder(Map.of("Test", 0), null));
    }

    @Test
    void addOrder_NullOrder() {
        GarageHolder garageHolder = new GarageHolder("a", assem);
        assertThrows(IllegalArgumentException.class, () -> garageHolder.addOrder(null));
    }

    @Test
    void getValidOptions_NullModel() {
        GarageHolder garageHolder = new GarageHolder("a", assem);
        assertThrows(IllegalArgumentException.class, () -> garageHolder.getValidOptions(null));
    }

    @Test
    void createCarModel_UnexpectedChoice() {
        GarageHolder garageHolder = new GarageHolder("a", assem);
        assertThrows(IllegalArgumentException.class, () -> garageHolder.createCarModel(5));
    }
}


