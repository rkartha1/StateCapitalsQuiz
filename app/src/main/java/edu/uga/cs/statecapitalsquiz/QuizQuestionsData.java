package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations for storing, retrieving, and updating quiz questions
 * in the SQLite database.
 */
public class QuizQuestionsData {

    public static final String DEBUG_TAG = "QuizQuestionsData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizQuestionsDbHelper;

    private static final String[] allColumns = {
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_ID,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY,
            QuizDBHelper.QUIZ_QUESTIONS_COLUMN_USED
    };

    /**
     * Constructor to initialize the database helper instance.
     *
     * @param context the context used to initialize the database helper
     */
    public QuizQuestionsData(Context context) {
        this.quizQuestionsDbHelper = QuizDBHelper.getInstance(context);
    }

    /**
     * Opens the database in write mode.
     */
    public void open() {
        db = quizQuestionsDbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "QuizQuestionsData: db open");
    }

    /**
     * Closes the database.
     */
    public void close() {
        if (quizQuestionsDbHelper != null) {
            quizQuestionsDbHelper.close();
            Log.d(DEBUG_TAG, "quizQuestionsData: db closed");
        }
    }

    /**
     * Checks if the database is currently open.
     *
     * @return true if the database is open, false otherwise
     */
    public boolean isDBOpen() {
        return db.isOpen();
    }

    /**
     * Retrieves all quiz questions from the database and returns them as a list.
     * Each row is mapped to a QuizQuestions object.
     *
     * @return a list of QuizQuestions objects
     */
    public List<QuizQuestions> retrieveAllQuizQuestions() {
        ArrayList<QuizQuestions> quizQuestionsList = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(QuizDBHelper.TABLE_QUIZ_QUESTIONS, allColumns,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= 5) {
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_ID);
                        Integer id = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE);
                        String state = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY);
                        String capitalCity = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY);
                        String firstCity = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY);
                        String secondCity = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_USED);
                        int used = cursor.getInt(columnIndex);

                        QuizQuestions quizQuestions = new QuizQuestions(id, state, capitalCity, firstCity, secondCity, used);
                        quizQuestions.setId(id);

                        quizQuestionsList.add(quizQuestions);
                        Log.d(DEBUG_TAG, "Retrieved QuizQuestion: " + quizQuestions);
                    }
                }
            }
            if (cursor != null)
                Log.d(DEBUG_TAG, "Number of records from DB: " + cursor.getCount());
            else
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return quizQuestionsList;
    }

    /**
     * Updates a quiz question in the database.
     *
     * @param question the QuizQuestions object containing the updated data
     */
    public void updateQuizQuestion(QuizQuestions question) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_USED, question.getUsed());
        db.update(QuizDBHelper.TABLE_QUIZ_QUESTIONS, values,
                QuizDBHelper.QUIZ_QUESTIONS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(question.getId())});
    }

    /**
     * Stores a new quiz question in the database.
     *
     * @param quizQuestions the QuizQuestions object to be stored
     * @return the stored QuizQuestions object with the assigned ID
     */
    public QuizQuestions storeQuizQuestions(QuizQuestions quizQuestions) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE, quizQuestions.getState());
        values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY, quizQuestions.getCapitalCity());
        values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY, quizQuestions.getFirstCity());
        values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY, quizQuestions.getSecondCity());
        values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_USED, quizQuestions.getUsed());

        long id = db.insert(QuizDBHelper.TABLE_QUIZ_QUESTIONS, null, values);

        quizQuestions.setId(id);

        Log.d(DEBUG_TAG, "Stored new quiz question with id: " + String.valueOf(quizQuestions.getId()));

        return quizQuestions;
    }
}

