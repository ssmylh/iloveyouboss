package scratch;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConstrainsSidesTo extends TypeSafeMatcher<Rectangle> {
    private int length;

    public ConstrainsSidesTo(int length) {
        this.length = length;
    }

    @Override
    public void describeTo(Description arg0) {
        arg0.appendText("both sides must be <= " + length);
    }

    @Override
    protected boolean matchesSafely(Rectangle arg0) {
        return Math.abs(arg0.origin().x - arg0.opposite().x) <= length
                && Math.abs(arg0.origin().y - arg0.opposite().y) <= length;
    }

    public static Matcher<Rectangle> constrainsSidesTo(int length) {
        return new ConstrainsSidesTo(length);
    }
}
