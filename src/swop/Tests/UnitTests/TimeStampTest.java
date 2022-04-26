package swop.Tests.UnitTests;

import org.junit.jupiter.api.Test;
import swop.Miscellaneous.TimeStamp;

import static org.junit.jupiter.api.Assertions.*;

class TimeStampTest {

    TimeStamp ts = new TimeStamp(0 , 20);

    @Test
    void getDay() {
        assertEquals(ts.getDay(),0);
    }

    @Test
    void getMinutes() {
        assertEquals(ts.getMinutes(), 20);
    }

    @Test
    void testToString() {
        assertEquals(ts.toString(), "day: 0, time: 06:20");
    }

    @Test
    void compareTo() {
        TimeStamp a = new TimeStamp(0,10);
        TimeStamp b = new TimeStamp(0,20);
        TimeStamp c = new TimeStamp(1,0);
        assertEquals(1, ts.compareTo(a));
        assertEquals(0, ts.compareTo(b));
        assertEquals(-1, ts.compareTo(c));
    }

    @Test
    void testEquals() {
        TimeStamp a = new TimeStamp(0,10);
        TimeStamp b = new TimeStamp(0,20);
        assertNotEquals(ts, a);
        assertEquals(ts, b);
    }
}