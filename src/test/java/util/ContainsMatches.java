package util;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ContainsMatches extends TypeSafeMatcher<List<Match>> {
    private Match[] expected;

    public ContainsMatches(Match[] expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description arg0) {
        arg0.appendText("<" + expected.toString() + ">");
    }

    @Override
    protected boolean matchesSafely(List<Match> arg0) {
        if (arg0.size() != expected.length)
            return false;

        for (int i = 0; i < expected.length; i++) {
            if (!equals(expected[i], arg0.get(i)))
                return false;
        }
        return true;
    }

    private boolean equals(Match exptected, Match actual) {
        return exptected.searchString.equals(actual.searchString)
                && exptected.surroundingContext.equals(actual.surroundingContext);
    }

    public static Matcher<List<Match>> containsMatches(Match[] expected) {
        return new ContainsMatches(expected);
    }
}
