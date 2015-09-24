package tdd;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnswerTest {

    @Test
    public void matchAgainstNullAnswerReturnFalse() {
        assertFalse(new Answer(new BooleanQuestion(0, ""), Bool.TRUE).match(null));
    }

}
