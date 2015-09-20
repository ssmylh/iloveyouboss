package scratch;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import iloveyouboss.BearingOutOfRangeException;

public class BearingTest {

    @Test(expected = BearingOutOfRangeException.class)
    public void throwsOnNegativeNumber() {
        new Bearing(-1);
    }

    @Test(expected = BearingOutOfRangeException.class)
    public void throwsWhenBearingTooLarge() {
        new Bearing(Bearing.MAX + 1);
    }

    @Test
    public void answesValidBearing() {
        assertThat(new Bearing(Bearing.MAX).value(), is(Bearing.MAX));
    }

    @Test
    public void answesAngleBetweenItAndAnotherBearing() {
        assertThat(new Bearing(15).angleBetween(new Bearing(12)), is(3));
    }
}
