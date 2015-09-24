package tdd;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();

    public boolean matches(Criterion criterion) {
        Answer answer = getMatcingProfileAnswer(criterion);
        return criterion.getAnswer().match(answer);
    }

    private Answer getMatcingProfileAnswer(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        boolean result = false;
        for (Criterion criterion : criteria) {
            if (matches(criterion)) {
                result = true;
                continue;
            }

            if (criterion.getWeight() == Weight.MustMatch) {
                return false;
            }
        }
        return result;
    }
}
