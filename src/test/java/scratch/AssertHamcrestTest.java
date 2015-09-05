package scratch;

//import static org.hamcrest.CoreMatchers.*; // should not import because the `is` conflicts.
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class AssertHamcrestTest {

    @Test
    @Ignore
    public void assertDoublesUsingIs() {
        assertThat(2.32 * 3, is(6.96));
    }

    @Test
    public void assertDoublesUsingAssertEquals() {
        assertEquals(2.32 * 3, 6.96, 0.0005);
    }

    @Test
    public void assertDoublesUsingCloseTo() {
        assertThat(2.32 * 3, is(closeTo(6.96, 0.0005)));
    }
}
