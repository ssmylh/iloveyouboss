package tdd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProfileTest {
    private Profile profile;
    private BooleanQuestion questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNotRelocation;
    private BooleanQuestion questionReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    @Before
    public void createProfile() {
        profile = new Profile();
    }

    @Before
    public void createQuestion() {
        questionIsThereRelocation = new BooleanQuestion(1, "Relocation package ?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);

        questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition ?");
        answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);
    }

    @Test
    public void matchesNothingWhenProfileEmpty() {
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertFalse(result);
    }

    @Test
    public void matchesProfileContainsMatchingAnswer() {
        profile.add(answerThereIsRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertTrue(result);
    }

    @Test
    public void doesNotMatchWhenNoMatchingAnswer() {
        profile.add(answerThereIsNotRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        boolean result = profile.matches(criterion);

        assertFalse(result);
    }

    @Test
    public void matchesWhenContainsMultipleAnswers() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        boolean result = profile.matches(criterion);

        assertTrue(result);
    }
}
