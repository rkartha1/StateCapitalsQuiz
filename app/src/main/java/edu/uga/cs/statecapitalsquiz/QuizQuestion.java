package edu.uga.cs.statecapitalsquiz;

import java.util.List;

/**
 * Represents a quiz question in the State Capitals Quiz application.
 * Each question contains a state name, the correct capital city, and a list of answer choices.
 */
public class QuizQuestion {

    private String state;
    private String correctAnswer;
    private List<String> choices;

    /**
     * Constructs a new QuizQuestion with a state, correct answer, and a list of choices.
     *
     * @param state          The name of the state.
     * @param correctAnswer  The correct capital city for the state.
     * @param choices        A list of possible answers for the quiz question.
     */
    public QuizQuestion(String state, String correctAnswer, List<String> choices) {
        this.state = state;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
    }

    /**
     * Gets the state for this quiz question.
     *
     * @return The state name.
     */
    public String getState() {
        return state;
    }

    /**
     * Gets the correct answer (capital city) for this quiz question.
     *
     * @return The correct capital city for the state.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Gets the list of answer choices for this quiz question.
     *
     * @return A list of possible answer choices (cities).
     */
    public List<String> getChoices() {
        return choices;
    }
}

