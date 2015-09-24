package tdd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ProfileTest {
    public static class MatchesCriterion {
        private Profile profile;
        private BooleanQuestion questionIsThereRelocation;
        private Answer answerThereIsRelocation;
        private Answer answerThereIsNotRelocation;
        private BooleanQuestion questionReimbursesTuition;
        private Answer answerDoesNotReimburseTuition;
        private Answer answerReimburseTuition;

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
            answerReimburseTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
            answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);
        }

        @Test
        public void trueWhenMatchesSoleAnswer() {
            profile.add(answerThereIsRelocation);
            Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

            assertTrue(profile.matches(criterion));
        }

        @Test
        public void falseWhenNoMatchingAnswerContained() {
            profile.add(answerThereIsNotRelocation);
            Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

            assertFalse(profile.matches(criterion));
        }

        @Test
        public void trueWhenOneOfMultipleAnswerMatches() {
            profile.add(answerThereIsRelocation);
            profile.add(answerDoesNotReimburseTuition);
            Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

            assertTrue(profile.matches(criterion));
        }

        @Test
        public void trueWhenForAnyDontCare() {
            profile.add(answerDoesNotReimburseTuition);
            Criterion criterion = new Criterion(answerReimburseTuition, Weight.DontCare);

            assertTrue(profile.matches(criterion));
        }
    }

    public static class MatchesCriteria {
        private Profile profile;
        private BooleanQuestion questionIsThereRelocation;
        private Answer answerThereIsRelocation;
        private BooleanQuestion questionReimbursesTuition;
        private Answer answerDoesNotReimburseTuition;
        private Answer answerReimburseTuition;
        private Criteria criteria;

        @Before
        public void createProfile() {
            profile = new Profile();
        }

        @Before
        public void createQuestion() {
            questionIsThereRelocation = new BooleanQuestion(1, "Relocation package ?");
            answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);

            questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition ?");
            answerReimburseTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
            answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);
        }

        @Before
        public void createCriteria() {
            criteria = new Criteria();
        }

        @Test
        public void falseWhenNoneOfMultipleCriteriaMatch() {
            profile.add(answerDoesNotReimburseTuition);
            Criteria criteria = new Criteria();
            criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
            criteria.add(new Criterion(answerReimburseTuition, Weight.Important));

            assertFalse(profile.matches(criteria));
        }

        @Test
        public void trueWhenAnyOfMultipleCriteriaMatch() {
            profile.add(answerThereIsRelocation);
            criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
            criteria.add(new Criterion(answerReimburseTuition, Weight.Important));

            assertTrue(profile.matches(criteria));
        }

        @Test
        public void falseWhenAnyMustMeetCriteriaNotMet() {
            profile.add(answerThereIsRelocation);
            profile.add(answerDoesNotReimburseTuition);
            criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
            criteria.add(new Criterion(answerReimburseTuition, Weight.MustMatch));

            assertFalse(profile.matches(criteria));
        }
    }
}
