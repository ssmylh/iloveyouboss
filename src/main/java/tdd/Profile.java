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
}
