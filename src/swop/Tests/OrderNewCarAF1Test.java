package swop.Tests;

import org.junit.jupiter.api.Test;
import swop.Exceptions.CancelException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class OrderNewCarAF1Test {
    @Test
    void leaveInstantlyTest() throws CancelException {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("n".getBytes());
        System.setIn(in);
        swop.UI.GarageHolderUI.indicatePlaceOrder();
        System.setIn(sysInBackup);
    }
}
