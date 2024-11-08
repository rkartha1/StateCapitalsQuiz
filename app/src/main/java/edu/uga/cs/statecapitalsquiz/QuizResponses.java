package edu.uga.cs.statecapitalsquiz;

/**
 * The {@code QuizResponses} class represents a response from a user to a quiz question.
 * It stores the user's answer along with the associated quiz and quiz question identifiers.
 *
 */
public class QuizResponses {

    private long id;
    private long quizID;
    private long quizQuestionID; // Change this to long
    private String userAnswer;

    /**
     * Default constructor for the {@code QuizResponses} class.
     * Initializes the {@code id}, {@code quizID}, {@code quizQuestionID} to -1,
     * and {@code userAnswer} to null.
     */
    public QuizResponses() {
        this.id = -1;
        this.quizID = -1;
        this.quizQuestionID = -1; // Change this to long
        this.userAnswer = null;
    }

    /**
     * Constructor for the {@code QuizResponses} class that initializes a new response
     * with the given {@code quizID}, {@code quizQuestionID}, and {@code userAnswer}.
     *
     * @param quizID the identifier for the quiz.
     * @param quizQuestionID the identifier for the quiz question.
     * @param userAnswer the user's answer to the quiz question.
     */
    public QuizResponses(long quizID, long quizQuestionID, String userAnswer) {
        this.id = -1;
        this.quizID = quizID;
        this.quizQuestionID = quizQuestionID;
        this.userAnswer = userAnswer;
    }

    /**
     * Gets the unique identifier for this response.
     *
     * @return the {@code id} of this response.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier for this response.
     *
     * @param id the new {@code id} to assign to this response.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the identifier for the quiz associated with this response.
     *
     * @return the {@code quizID} of this response.
     */
    public long getQuizID() {
        return this.quizID;
    }

    /**
     * Sets the identifier for the quiz associated with this response.
     *
     * @param quizID the new {@code quizID} to assign to this response.
     */
    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    /**
     * Gets the identifier for the quiz question associated with this response.
     *
     * @return the {@code quizQuestionID} of this response.
     */
    public long getQuizQuestionID() {
        return this.quizQuestionID;
    }

    /**
     * Sets the identifier for the quiz question associated with this response.
     *
     * @param quizQuestionID the new {@code quizQuestionID} to assign to this response.
     */
    public void setQuizQuestionID(int quizQuestionID) {
        this.quizQuestionID = quizQuestionID;
    }

    /**
     * Gets the user's answer to the quiz question.
     *
     * @return the {@code userAnswer} of this response.
     */
    public String getUserAnswer() {
        return userAnswer;
    }

    /**
     * Sets the user's answer to the quiz question.
     *
     * @param userAnswer the new {@code userAnswer} to assign to this response.
     */
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    /**
     * Returns a string representation of this {@code QuizResponses} object.
     *
     * @return a string representation of this response.
     */
    @Override
    public String toString() {
        return id + ":" + quizID + " " + quizQuestionID + " " + userAnswer;
    }
}

