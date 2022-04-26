package swop.Tests.UnitTests.Users;

import org.junit.jupiter.api.Test;
import swop.Users.GarageHolder;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GarageHolderTest {

    @Test
    void getOrders_NewGarageHolder() {
        GarageHolder garageHolder = new GarageHolder("a");
        assertEquals(new HashSet<>(), garageHolder.getOrders());
    }
}
