package iloveyouboss;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScoreCollectionTest {
    ScoreCollection collection;

    @Before
    public void create() {
        collection = new ScoreCollection();
    }

    @Test
    public void answersArithmeticMeanOfTwoNumbers() {
        collection.add(() -> 5);
        collection.add(() -> 7);

        int actual = collection.arithmeticMean();
        assertThat(actual, is(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenAddingNull() {
        collection.add(null);
    }

    @Test
    public void answersZeroWhenNoElementsAdded() {
        assertThat(collection.arithmeticMean(), is(0));
    }

    @Test
    public void dealsWithIntegerOverflow() {
        collection.add(() -> Integer.MAX_VALUE);
        collection.add(() -> 1);

        assertThat(collection.arithmeticMean(), is(1073741824));
    }
}
