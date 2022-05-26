package swop.Tests.UnitTests.Users;

import org.junit.jupiter.api.Test;
import swop.Main.AssemAssist;
import swop.UI.Generators.ManagerGenerator;
import swop.Users.Manager;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    AssemAssist assem = new AssemAssist();


    @Test
    void getId() {
        Manager manager = new Manager("id", assem);
        assertEquals(manager.getId(),"id");
    }

    @Test
    void setSchedulingAlgorithm_InvalidAlgorithm() {
        Manager manager = new Manager("id", assem);
        assertThrows(IllegalArgumentException.class, () -> manager.setSchedulingAlgorithm(null, null));
        assertThrows(IllegalArgumentException.class, () -> manager.setSchedulingAlgorithm("INVALID", null));
    }

    @Test
    void setSchedulingAlgorithm_NullBatchOptions() {
        Manager manager = new Manager("id", assem);
        assertThrows(IllegalArgumentException.class, () -> manager.setSchedulingAlgorithm("BATCH", null));
    }


}