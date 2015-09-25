package iloveyouboss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ProfileMatcher {
    private Map<String, Profile> profiles = new HashMap<>();
    private static final int DEFAULT_POOL_SIZE = 4;
    ExecutorService es = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

    public void add(Profile profile) {
        profiles.put(profile.getName(), profile);
    }

    public void gatherMatchingProfiles(Criteria criteria, MatchListener listener, List<MatchSet> sets,
            BiConsumer<MatchListener, MatchSet> process) {
        try {
            for (MatchSet set : sets) {
                Runnable runnable = () -> process.accept(listener, set);
                es.execute(runnable);
            }
        } finally {
            es.shutdown();
        }
    }

    public void findMatchingProfiles(Criteria criteria, MatchListener listener) {
        gatherMatchingProfiles(criteria, listener, collectMatchSets(criteria), this::process);
    }

    List<MatchSet> collectMatchSets(Criteria criteria) {
        return profiles.values().stream().map(profile -> profile.getMatchSet(criteria)).collect(Collectors.toList());
    }

    void process(MatchListener listener, MatchSet set) {
        if (set.matches()) {
            listener.foundMatch(profiles.get(set.getProfileName()), set);
        }
    }
}
