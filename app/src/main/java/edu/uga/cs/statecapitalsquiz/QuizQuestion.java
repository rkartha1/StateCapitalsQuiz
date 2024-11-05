package edu.uga.cs.statecapitalsquiz;

import java.util.List;

public class QuizQuestion {
    private String state;
    private String correctAnswer;
    private List<String> choices;

    public QuizQuestion(String state, String correctAnswer, List<String> choices) {
        this.state = state;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
    }

    public String getState() {
        return state;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getChoices() {
        return choices;
    }
}
