/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package util;

import java.util.Arrays;
import java.util.Objects;

public class SparseArray<T> {
    public static final int MAXIMUM_NUMBER_OF_ELEMENTS = 1000;
    private int[] keys = new int[MAXIMUM_NUMBER_OF_ELEMENTS];
    private Object[] values = new Object[MAXIMUM_NUMBER_OF_ELEMENTS];
    private int size = 0;

    public void put(int key, T value) {
        if (value == null)
            return;
        if (key < 0)
            throw new IndexOutOfBoundsException();
        if (size == MAXIMUM_NUMBER_OF_ELEMENTS)
            throw new RuntimeException("the maximum number of elements are stored.");

        int index = binarySearch(key, keys, size);
        if (index != -1 && keys[index] == key) {
            values[index] = value;
        } else {
            insertAfter(key, value, index);
        }
    }

    public int size() {
        return size;
    }

    private void insertAfter(int key, T value, int index) {
        int[] newKeys = new int[MAXIMUM_NUMBER_OF_ELEMENTS];
        Object[] newValues = new Object[MAXIMUM_NUMBER_OF_ELEMENTS];

        copyFromBefore(index, newKeys, newValues);

        int newIndex = index + 1;
        newKeys[newIndex] = key;
        newValues[newIndex] = value;

        if (size - newIndex != 0)
            copyFromAfter(index, newKeys, newValues);

        keys = newKeys;
        values = newValues;
        ++size;
    }

    public void checkInvariants() throws InvariantException {
        long nonNullValues = Arrays.stream(values).filter(Objects::nonNull).count();
        if (nonNullValues != size)
            throw new InvariantException("size " + size + " does not match value count of " + nonNullValues);
    }

    private void copyFromAfter(int index, int[] newKeys, Object[] newValues) {
        int start = index + 1;
        System.arraycopy(keys, start, newKeys, start + 1, size - start);
        System.arraycopy(values, start, newValues, start + 1, size - start);
    }

    private void copyFromBefore(int index, int[] newKeys, Object[] newValues) {
        System.arraycopy(keys, 0, newKeys, 0, index + 1);
        System.arraycopy(values, 0, newValues, 0, index + 1);
    }

    @SuppressWarnings("unchecked")
    public T get(int key) {
        if (key < 0)
            throw new IndexOutOfBoundsException();

        int index = binarySearch(key, keys, size);
        if (index != -1 && keys[index] == key)
            return (T) values[index];
        return null;
    }

    int binarySearch(int n, int[] nums, int size) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int midIndex = (low + high) / 2;
            if (n > nums[midIndex])
                low = midIndex + 1;
            else if (n < nums[midIndex])
                high = midIndex - 1;
            else
                return midIndex;
        }
        return low - 1;
    }
}
