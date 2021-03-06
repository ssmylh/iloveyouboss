package iloveyouboss;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProfileTest {
    private Profile profile;

    @Before
    public void create() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    int[] ids(Collection<Answer> answers) {
        return answers.stream().mapToInt(a -> a.getQuestion().getId()).sorted().toArray();
    }

    @Test
    public void findsAnswersBasedOnPredicate() {
        profile.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        profile.add(new Answer(new PercentileQuestion(2, "2", new String[] {}), 0));
        profile.add(new Answer(new PercentileQuestion(3, "3", new String[] {}), 0));

        List<Answer> answers = profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);
        assertThat(ids(answers), is(new int[] { 2, 3 }));

        List<Answer> complementAnswers = profile.find(a -> a.getQuestion().getClass() != PercentileQuestion.class);
        ArrayList<Answer> allAnswers = new ArrayList<>();
        allAnswers.addAll(answers);
        allAnswers.addAll(complementAnswers);
        assertThat(ids(allAnswers), is(new int[] { 1, 2, 3 }));
    }
}
