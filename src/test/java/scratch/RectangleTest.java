package scratch;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static scratch.ConstrainsSidesTo.*;

import org.junit.After;
import org.junit.Test;

public class RectangleTest {
    private Rectangle rectangle;

    @After
    public void ensureInvariant() {
        assertThat(rectangle, is(constrainsSidesTo(100)));
    }

    @Test
    public void answerArea() {
        rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
        assertThat(rectangle.area(), is(50));
    }

    @Test
    public void allowsDynamicallyChangingSize() {
        rectangle = new Rectangle(new Point(5, 5));
        rectangle.setOppositeCorner(new Point(105, 105));
        assertThat(rectangle.area(), is(10000));
    }
}
