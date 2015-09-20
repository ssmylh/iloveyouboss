/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package iloveyouboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfilePool {
    private List<Profile> profiles = new ArrayList<Profile>();

    public void add(Profile profile) {
        profiles.add(profile);
    }

    public List<Profile> ranked(Criteria criteria) {
        Collections.sort(profiles, (p1, p2) -> { // score desc.
            int score1 = p1.getMatchSet(criteria).getScore();
            int score2 = p2.getMatchSet(criteria).getScore();
            return Integer.valueOf(score2).compareTo(Integer.valueOf(score1));
        });
        return profiles;
    }
}
