package swop.Tests.UnitTests.Users;

import org.junit.jupiter.api.Test;
import swop.Main.AssemAssist;
import swop.UI.Generators.ManagerGenerator;
import swop.Users.Manager;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    AssemAssist assem = new AssemAssist();
    Manager man = new Manager("id", assem);

    @Test
    void getId() {
        assertEquals(man.getId(),"id");
    }


}