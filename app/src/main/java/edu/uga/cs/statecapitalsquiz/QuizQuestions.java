package edu.uga.cs.statecapitalsquiz;

public class QuizQuestions {

    private long id;
    private String state;
    private String capitalCity;
    private String firstCity;
    private String secondCity;

    public QuizQuestions()
    {
        this.id = -1;
        this.state = null;
        this.capitalCity = null;
        this.firstCity = null;
        this.secondCity = null;
    }

    public QuizQuestions( String state, String capitalCity, String firstCity, String secondCity ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.state = state;
        this.capitalCity = capitalCity;
        this.firstCity = firstCity;
        this.secondCity = secondCity;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCapitalCity()
    {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity)
    {
        this.capitalCity = capitalCity;
    }

    public String getFirstCity()
    {
        return firstCity;
    }

    public void setFirstCity(String firstCity)
    {
        this.firstCity = firstCity;
    }

    public String getSecondCity()
    {
        return secondCity;
    }

    public void setSecondCity(String secondCity)
    {
        this.secondCity = secondCity;
    }

    public String toString()
    {
        return id + ": " + state + " " + capitalCity + " " + firstCity + " " + secondCity;
    }
}

