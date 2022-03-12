package swop.Tests;

import org.junit.jupiter.api.Test;
import swop.Users.CarMechanic;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CarMechanicTest {
    String id = "id";
    CarMechanic a = new CarMechanic(id);

    @Test
    void getId() {
        assert Objects.equals(a.getId(), id);
    }

    @Test
    void isValidYesNo() {

    }

    @Test
    void testTaskInfo() {

    }
}