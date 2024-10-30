package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionsData {
    public static final String DEBUG_TAG = "QuizQuestionsData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase db;
    private SQLiteOpenHelper quizQuestionsDbHelper;
    private static final String[] allColumns = {
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_ID,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY
    };

    public QuizQuestionsData( Context context ) {
        this.quizQuestionsDbHelper = QuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = quizQuestionsDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizQuestionsData: db open" );
    }

    // Close the database
    public void close() {
        if( quizQuestionsDbHelper != null ) {
            quizQuestionsDbHelper.close();
            Log.d(DEBUG_TAG, "quizQuestionsData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all job leads and return them as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java POJO object) instance and add it to the list.
    public List<QuizQuestions> retrieveAllQuizQuestions() {
        ArrayList<QuizQuestions> quizQuestionsList = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_QUIZ_QUESTIONS, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 5) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_ID);
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE );
                        String state = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY );
                        String capitalCity = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY );
                        String firstCity = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY );
                        String secondCity = cursor.getString( columnIndex );

                        // create a new JobLead object and set its state to the retrieved values
                        QuizQuestions quizQuestions = new QuizQuestions( state, capitalCity, firstCity, secondCity );
                        quizQuestions.setId(id); // set the id (the primary key) of this object
                        // add it to the list
                        quizQuestionsList.add( quizQuestions );
                        Log.d(DEBUG_TAG, "Retrieved JobLead: " + quizQuestions);
                    }
                }
            }
            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved job leads
        return quizQuestionsList;
    }

    // Store a new job lead in the database.
    public QuizQuestions storeJobLead( QuizQuestions quizQuestions ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE, quizQuestions.getState());
        values.put( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY, quizQuestions.getCapitalCity() );
        values.put( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY, quizQuestions.getFirstCity() );
        values.put( QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY, quizQuestions.getSecondCity() );

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( QuizDBHelper.TABLE_QUIZ_QUESTIONS, null, values );

        // store the id (the primary key) in the JobLead instance, as it is now persistent
        quizQuestions.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz question with id: " + String.valueOf( quizQuestions.getId() ) );

        return quizQuestions;
    }

}
