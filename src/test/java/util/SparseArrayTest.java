package util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SparseArrayTest {
    private SparseArray<Object> array;

    @Before
    public void create() {
        array = new SparseArray<>();
    }

    @Test
    public void handlesInsertionInDescendingOrder() {
        array.put(7, "seven");
        array.checkInvariants();
        array.put(6, "six");
        array.checkInvariants();
        assertThat(array.get(6), is("six"));
        assertThat(array.get(7), is("seven"));
    }

    @Test
    public void handlesInsertionInAscendingOrder() {
        array.put(3, "three");
        array.checkInvariants();
        array.put(5, "five");
        array.checkInvariants();
        assertThat(array.get(3), is("three"));
        assertThat(array.get(5), is("five"));
    }

    @Test
    public void handlesInsertionAtSameIndex() {
        array.put(7, "seven-1");
        array.checkInvariants();
        array.put(7, "seven-2");
        array.checkInvariants();
        assertThat(array.get(7), is("seven-2"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsOnInsertionAtNegativeIndex() {
        array.put(-1, "seven");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsOnGetAtNegativeIndex() {
        array.put(7, "seven");
        array.checkInvariants();
        array.get(-1);
    }

    @Test
    public void canStoreMaximumNumberOfElements() {
        for (int i = 0; i < SparseArray.MAXIMUM_NUMBER_OF_ELEMENTS; i++) {
            array.put(i, i);
        }
        assertThat(0, is(0));
        assertThat(500, is(500));
        assertThat(SparseArray.MAXIMUM_NUMBER_OF_ELEMENTS, is(SparseArray.MAXIMUM_NUMBER_OF_ELEMENTS));
    }

    @Test(expected = RuntimeException.class)
    public void throwsOnOverMaximumNumberOfElements() {
        for (int i = 0; i < SparseArray.MAXIMUM_NUMBER_OF_ELEMENTS; i++) {
            array.put(i, i);
        }
        array.put(1000, 1000);
    }
}
