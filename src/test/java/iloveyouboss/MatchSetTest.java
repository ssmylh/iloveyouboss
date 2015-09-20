package iloveyouboss;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MatchSetTest {
    private Criteria criteria;
    private AnswerCollection answers;

    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    @Before
    public void createAnswers() {
        answers = new AnswerCollection();
    }

    @Before
    public void createCriteria() {
        criteria = new Criteria();
    }

    @Before
    public void createQuestionAndAnswers() {
        questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);
    }

    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        answers.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));

        assertFalse(createMatchSet().matches());
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        answers.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.DontCare));

        assertTrue(createMatchSet().matches());
    }

    private MatchSet createMatchSet() {
        return new MatchSet(answers, criteria);
    }
}
