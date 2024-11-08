package edu.uga.cs.statecapitalsquiz;

/**
 * Represents a quiz question with state, capital city, and two other city choices.
 * This class is typically used to store the details of a question in the quiz database.
 */
public class QuizQuestions {

    private long id;
    private String state;
    private String capitalCity;
    private String firstCity;
    private String secondCity;
    public int used;

    /**
     * Default constructor for QuizQuestions. Initializes fields to default values.
     */
    public QuizQuestions()
    {
        this.id = -1;
        this.state = null;
        this.capitalCity = null;
        this.firstCity = null;
        this.secondCity = null;
        this.used = 0;
    }

    /**
     * Constructs a new QuizQuestions instance with the provided values.
     *
     * @param id           The unique identifier for the question (usually set by a database).
     * @param state        The name of the state.
     * @param capitalCity  The correct capital city for the state.
     * @param firstCity    The first incorrect city choice.
     * @param secondCity   The second incorrect city choice.
     * @param used         Indicates whether the question has been used (1 if used, 0 if not).
     */
    public QuizQuestions(Integer id, String state, String capitalCity, String firstCity, String secondCity, int used) {
        this.id = id;  // the primary key id will be set by a setter method
        this.state = state;
        this.capitalCity = capitalCity;
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        this.used = used;
    }

    /**
     * Gets the unique identifier for this quiz question.
     *
     * @return The id of the quiz question.
     */
    public long getId()
    {
        return id;
    }

    /**
     * Sets the unique identifier for this quiz question.
     *
     * @param id The id to be set.
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Gets the state for this quiz question.
     *
     * @return The state name.
     */
    public String getState()
    {
        return state;
    }

    /**
     * Sets the state for this quiz question.
     *
     * @param state The state name to be set.
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Gets the correct capital city for this quiz question.
     *
     * @return The correct capital city.
     */
    public String getCapitalCity()
    {
        return capitalCity;
    }

    /**
     * Sets the correct capital city for this quiz question.
     *
     * @param capitalCity The correct capital city to be set.
     */
    public void setCapitalCity(String capitalCity)
    {
        this.capitalCity = capitalCity;
    }

    /**
     * Gets the first incorrect city choice for this quiz question.
     *
     * @return The first incorrect city.
     */
    public String getFirstCity()
    {
        return firstCity;
    }

    /**
     * Sets the first incorrect city choice for this quiz question.
     *
     * @param firstCity The first incorrect city to be set.
     */
    public void setFirstCity(String firstCity)
    {
        this.firstCity = firstCity;
    }

    /**
     * Gets the second incorrect city choice for this quiz question.
     *
     * @return The second incorrect city.
     */
    public String getSecondCity()
    {
        return secondCity;
    }

    /**
     * Sets the second incorrect city choice for this quiz question.
     *
     * @param secondCity The second incorrect city to be set.
     */
    public void setSecondCity(String secondCity)
    {
        this.secondCity = secondCity;
    }

    /**
     * Gets the "used" status of this quiz question. This indicates whether the question has been used (1 if used, 0 if not).
     *
     * @return The used status of the question.
     */
    public int getUsed() {
        return used;
    }

    /**
     * Sets the "used" status of this quiz question.
     *
     * @param used The used status to be set (1 for used, 0 for not used).
     */
    public void setUsed(int used) {
        this.used = used;
    }

    /**
     * Returns a string representation of the QuizQuestions object.
     *
     * @return A string containing the id, state, capital city, first city, second city, and used status.
     */
    @Override
    public String toString()
    {
        return id + ": " + state + " " + capitalCity + " " + firstCity + " " + secondCity + " " + used;
    }
}
