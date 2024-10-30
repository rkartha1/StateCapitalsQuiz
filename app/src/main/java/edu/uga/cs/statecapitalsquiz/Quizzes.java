package edu.uga.cs.statecapitalsquiz;

public class Quizzes {

    private long id;
    private String date;
    private int result;
    private int answered;

    public Quizzes()
    {
        this.id = -1;
        this.date = null;
        this.result = -1;
        this.answered = -1;
    }

    public Quizzes( String date, int result, int answered) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.date = date;
        this.result = result;
        this.answered = answered;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }


    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public int getAnswered()
    {
        return answered;
    }

    public void setAnswered(int answered)
    {
        this.answered = answered;
    }

    public String toString()
    {
        return id + ": " + date + " " + result + " " + answered;
    }
}