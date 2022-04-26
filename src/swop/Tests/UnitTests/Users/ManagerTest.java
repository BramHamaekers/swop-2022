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

    private ListIterator<String> setupUITest(String inputString, int skips) {
        this.assem = new AssemAssist();
        this.man = (Manager) this.assem.getUserMap().get("c");

        this.input = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(input);
        LoginUI.scanner.updateScanner();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assem.run();

        ListIterator<String> output = Arrays.asList(outContent.toString().split(String.format("%n")))
                .listIterator();

        for(int i =0; i < skips; i++)
            output.next();

        return output;
    }
}