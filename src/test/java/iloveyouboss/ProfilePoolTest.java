package iloveyouboss;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProfilePoolTest {
    private ProfilePool pool;
    private Profile langrsoft;
    private Profile smeltInc;
    private BooleanQuestion doTheyReimburseTuition;

    @Before
    public void create() {
        pool = new ProfilePool();
        langrsoft = new Profile("Langrsonf");
        smeltInc = new Profile("Smelt Inc.");
        doTheyReimburseTuition = new BooleanQuestion(1, "Reimburses tuition ?");
    }

    @Test
    public void answersResultsInScoredOrder() {
        smeltInc.add(new Answer(doTheyReimburseTuition, Bool.FALSE));
        pool.add(smeltInc);
        langrsoft.add(new Answer(doTheyReimburseTuition, Bool.TRUE));
        pool.add(langrsoft);

        List<Profile> ranked = pool.ranked(soleNeed(doTheyReimburseTuition, Bool.TRUE, Weight.Important));

        assertThat(ranked.toArray(), is(new Profile[] { langrsoft, smeltInc }));
    }

    private Criteria soleNeed(Question question, int value, Weight weight) {
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(new Answer(question, value), weight));
        return criteria;
    }
}
