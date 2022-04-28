package swop.Tests.UnitTests.Users;

import org.junit.jupiter.api.Test;
import swop.CarManufactoring.WorkStation;
import swop.Main.AssemAssist;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;
import swop.UI.Generators.ManagerGenerator;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.Manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    Manager man = new Manager("naam");
    InputStream input;
    AssemAssist assem = new AssemAssist();
    private static final ManagerGenerator managerGenerator = new ManagerGenerator();

    @Test
    void getId() {
        assertEquals(man.getId(),"naam");
    }

    @Test
    void load() {
        assertThrows(IllegalArgumentException.class, () -> man.load(null));
//        man.load(assem);

    }

    @Test
    void selectAction() {
        assertThrows(IllegalArgumentException.class, () -> man.selectAction(null));
//        man.selectAction(assem);
    }
}