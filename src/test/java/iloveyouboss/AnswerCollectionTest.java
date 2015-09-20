package iloveyouboss;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AnswerCollectionTest {
    private AnswerCollection answers;

    @Before
    public void create() {
        answers = new AnswerCollection();
    }

    int[] ids(Collection<Answer> answers) {
        return answers.stream().mapToInt(a -> a.getQuestion().getId()).sorted().toArray();
    }

    @Test
    public void findsAnswersBasedOnPredicate() {
        answers.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        answers.add(new Answer(new PercentileQuestion(2, "2", new String[] {}), 0));
        answers.add(new Answer(new PercentileQuestion(3, "3", new String[] {}), 0));

        List<Answer> result = answers.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);
        assertThat(ids(result), is(new int[] { 2, 3 }));

        List<Answer> complementResult = answers.find(a -> a.getQuestion().getClass() != PercentileQuestion.class);
        ArrayList<Answer> allAnswers = new ArrayList<>();
        allAnswers.addAll(result);
        allAnswers.addAll(complementResult);
        assertThat(ids(allAnswers), is(new int[] { 1, 2, 3 }));
    }

}
