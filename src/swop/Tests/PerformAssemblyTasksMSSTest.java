package swop.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swop.Exceptions.CancelException;
import swop.UI.CarMechanicUI;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PerformAssemblyTasksMSSTest {
    @Test
    void askWorkPostTest(){
        CarMechanicUI.displayAvailableStations(List.of("Car Body Post", "Drivetrain Post", "Accessories Post"));
    }

    @Test
    void askOptionSuccesTest() throws CancelException {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());
        System.setIn(in);
        Assertions.assertEquals(CarMechanicUI.askOption("Test:",5),4);
        System.setIn(sysInBackup);
    }

    @Test
    void askOptionFailTest() throws CancelException {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("6".getBytes());
        System.setIn(in);
        CarMechanicUI.askOption("Test:",5);
        System.setIn(sysInBackup);
    }

    @Test
    void displayTaskInfoAndFinishTest() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("\r".getBytes());
        System.setIn(in);
        CarMechanicUI.displayTaskInfo("Test info");
        System.setIn(sysInBackup);
    }
}
