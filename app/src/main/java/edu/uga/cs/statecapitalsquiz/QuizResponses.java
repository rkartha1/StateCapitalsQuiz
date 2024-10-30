package edu.uga.cs.statecapitalsquiz;

public class QuizResponses {
    private long id;
    private int quizID;
    private int quizQuestionID;
    private String userAnswer;


    public QuizResponses() {
        this.id = -1;
        this.quizID = -1;
        this.quizQuestionID = -1;
        this.userAnswer = null;
    }

    public QuizResponses(int quizID, int quizQuestionID, String userAnswer) {
        this.id = -1;
        this.quizID = quizID;
        this.quizQuestionID = quizQuestionID;
        this.userAnswer = userAnswer;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuizID() {
        return this.quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getQuizQuestionID() {
        return this.quizQuestionID;
    }

    public void setQuizQuestionID(int quizQuestionID) {
        this.quizQuestionID = quizQuestionID;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String toString() {
        return id + ":" + quizID + " " + quizQuestionID + " " + userAnswer;
    }

}
