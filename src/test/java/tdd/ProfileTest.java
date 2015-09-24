package tdd;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProfileTest {

    @Test
    public void matchesNothingWhenProfileEmpty() {
        Profile profile = new Profile();
        Question question = new BooleanQuestion(1, "Relocation package ?");
        Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertFalse(result);
    }

    @Test
    public void matchesProfileContainsMatchingAnswer() {
        Profile profile = new Profile();
        Question question = new BooleanQuestion(1, "Relocation package ?");
        Answer answer = new Answer(question, Bool.TRUE);
        profile.add(answer);
        Criterion criterion = new Criterion(answer, Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertTrue(result);
    }

}
