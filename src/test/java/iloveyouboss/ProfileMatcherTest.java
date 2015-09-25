package iloveyouboss;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class ProfileMatcherTest {
    private BooleanQuestion question;
    private Criteria criteria;
    private Profile matchingProfile;
    private Profile nonMatchingProfile;

    @Before
    public void create() {
        question = new BooleanQuestion(1, "");
        criteria = new Criteria();

        Answer matchingAnswer = new Answer(question, Bool.TRUE);
        criteria.add(new Criterion(matchingAnswer, Weight.MustMatch));

        Answer nonMatchingAnswer = new Answer(question, Bool.FALSE);

        matchingProfile = createProfile("matching", matchingAnswer);
        nonMatchingProfile = createProfile("nonMatching", nonMatchingAnswer);
    }

    private Profile createProfile(String name, Answer answer) {
        Profile profile = new Profile(name);
        profile.add(answer);
        return profile;
    }

    private ProfileMatcher sut;

    @Before
    public void createMatcher() {
        sut = new ProfileMatcher();
    }

    @Test
    public void collectMatchSets() {
        sut.add(matchingProfile);
        sut.add(nonMatchingProfile);

        List<MatchSet> sets = sut.collectMatchSets(criteria);

        Set<String> actual = sets.stream().map(MatchSet::getProfileName).collect(Collectors.toSet());
        Set<String> expected = new HashSet<>(Arrays.asList(matchingProfile.getName(), nonMatchingProfile.getName()));
        assertThat(actual, is(expected));
    }

    private MatchListener listener;

    @Before
    public void createMatchListener() {
        listener = mock(MatchListener.class);
    }

    @Test
    public void processNotifiesListenerOnMatch() {
        sut.add(matchingProfile);
        MatchSet set = matchingProfile.getMatchSet(criteria);

        sut.process(listener, set);

        verify(listener).foundMatch(matchingProfile, set);
    }

    @Test
    public void processDoesNotNotifyListenerWhenNoMatch() {
        sut.add(nonMatchingProfile);
        MatchSet set = nonMatchingProfile.getMatchSet(criteria);

        sut.process(listener, set);

        verify(listener, never()).foundMatch(nonMatchingProfile, set);
    }

    @Test
    public void gatherMatchingProfiles() {
        Set<String> processed = Collections.synchronizedSet(new HashSet<>());
        BiConsumer<MatchListener, MatchSet> process = (listener, set) -> {
            processed.add(set.getProfileName());
        };
        List<MatchSet> sets = createMatchSets(100);

        sut.gatherMatchingProfiles(criteria, listener, sets, process);

        while (sut.es.isTerminated() == false) {
            // wait
        }

        Set<String> expected = sets.stream().map(MatchSet::getProfileName).collect(Collectors.toSet());
        assertThat(processed, is(expected));
    }

    private List<MatchSet> createMatchSets(int count) {
        List<MatchSet> sets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sets.add(new MatchSet(String.valueOf(i), null, null));
        }
        return sets;
    }
}
