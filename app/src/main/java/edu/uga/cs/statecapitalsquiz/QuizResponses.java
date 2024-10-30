package edu.uga.cs.statecapitalsquiz;

public class QuizResponses {
    private long id;
    private long quizID;
    private long quizQuestionID; // Change this to long
    private String userAnswer;

    public QuizResponses() {
        this.id = -1;
        this.quizID = -1;
        this.quizQuestionID = -1; // Change this to long
        this.userAnswer = null;
    }

    public QuizResponses(long quizID, long quizQuestionID, String userAnswer) {
        this.id = -1;
        this.quizID = quizID;
        this.quizQuestionID = quizQuestionID; // <-- This should be a long, but you are passing a String here
        this.userAnswer = userAnswer;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuizID() {
        return this.quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public long getQuizQuestionID() {
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
