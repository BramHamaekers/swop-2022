package swop.Tests.UnitTests.Users;

import org.junit.jupiter.api.Test;
import swop.Main.AssemAssist;
import swop.UI.Generators.ManagerGenerator;
import swop.Users.Manager;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    InputStream input;
    AssemAssist assem = new AssemAssist();
    Manager man = new Manager("naam", assem);
    private static final ManagerGenerator managerGenerator = new ManagerGenerator();

    @Test
    void getId() {
        assertEquals(man.getId(),"naam");
    }

//    @Test
//    void load() {
//        assertThrows(IllegalArgumentException.class, () -> man.load(null));
////        man.load(assem);
//
//    }

//    @Test
//    void selectAction() {
//        assertThrows(IllegalArgumentException.class, () -> man.selectAction(null));
////        man.selectAction(assem);
//    }
}