package swop.Tests;

import org.junit.jupiter.api.Assertions;
import swop.Exceptions.CancelException;
import swop.UI.CarMechanicUI;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class PerformAssemblyTasksAFTest {
    void leaveAssemblyTest() {
        Assertions.assertThrows(CancelException.class, () -> {
            InputStream sysInBackup = System.in;
            ByteArrayInputStream in = new ByteArrayInputStream("cancel".getBytes());
            System.setIn(in);
            CarMechanicUI.askOption("Test", 5);
            System.setIn(sysInBackup);
        });
    }
}
