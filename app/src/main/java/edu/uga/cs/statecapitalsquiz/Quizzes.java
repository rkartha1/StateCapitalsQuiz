package edu.uga.cs.statecapitalsquiz;

/**
 * Represents a quiz record with its properties such as date, result, and number of answered questions.
 * This class is used to hold information related to individual quizzes taken by the user.
 */
public class Quizzes {

    private long id;
    private String date;
    private int result;
    private int answered;

    /**
     * Default constructor for creating a new instance of the Quizzes class.
     * Initializes all fields to default values.
     */
    public Quizzes() {
        this.id = -1;
        this.date = null;
        this.result = -1;
        this.answered = -1;
    }

    /**
     * Constructor to create a Quizzes object with the specified date, result, and number of answered questions.
     * The id will be set later by a setter method.
     *
     * @param date     The date the quiz was taken.
     * @param result   The result of the quiz (e.g., score).
     * @param answered The number of questions answered in the quiz.
     */
    public Quizzes(String date, int result, int answered) {
        this.id = -1;  // The primary key id will be set by a setter method.
        this.date = date;
        this.result = result;
        this.answered = answered;
    }

    /**
     * Gets the unique identifier of the quiz.
     *
     * @return The quiz's unique identifier.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the quiz.
     *
     * @param id The new unique identifier for the quiz.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the date when the quiz was taken.
     *
     * @return The date of the quiz.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for when the quiz was taken.
     *
     * @param date The new date of the quiz.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the result (e.g., score) of the quiz.
     *
     * @return The result of the quiz.
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the result for the quiz (e.g., score).
     *
     * @param result The new result for the quiz.
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * Gets the number of questions answered in the quiz.
     *
     * @return The number of questions answered in the quiz.
     */
    public int getAnswered() {
        return answered;
    }

    /**
     * Sets the number of questions answered in the quiz.
     *
     * @param answered The new number of questions answered in the quiz.
     */
    public void setAnswered(int answered) {
        this.answered = answered;
    }

    /**
     * Returns a string representation of the Quizzes object, including its id, date, result, and answered questions.
     *
     * @return A string representation of the quiz.
     */
    @Override
    public String toString() {
        return id + ": " + date + " " + result + " " + answered;
    }
}
