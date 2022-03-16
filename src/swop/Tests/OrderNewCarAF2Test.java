package swop.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swop.Exceptions.CancelException;
import swop.UI.GarageHolderUI;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class OrderNewCarAF2Test {
    @Test
    void leaveDuringOrderTest() {
        Assertions.assertThrows(CancelException.class, () -> {
            InputStream sysInBackup = System.in;
            ByteArrayInputStream in = new ByteArrayInputStream("cancel".getBytes());
            System.setIn(in);
            GarageHolderUI.askOption(0, Arrays.asList("red", "blue", "black", "white").size(), "Color");
            System.setIn(sysInBackup);
        });
    }
}
