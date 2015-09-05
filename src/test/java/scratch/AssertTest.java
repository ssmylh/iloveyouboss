package scratch;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void expectedExceptionRule() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("runtime");
        thrown.expectCause(instanceOf(NullPointerException.class));

        try {
            throw null;
        } catch (NullPointerException e) {
            throw new RuntimeException("runtime", e);
        }
    }
}
