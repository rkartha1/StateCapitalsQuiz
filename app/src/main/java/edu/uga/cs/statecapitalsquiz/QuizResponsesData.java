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
 * The {@code QuizResponsesData} class is responsible for interacting with the database
 * to store, retrieve, and manage quiz responses. It provides methods for opening
 * and closing the database, checking if the database is open, retrieving all quiz responses,
 * and storing new quiz responses.
 * The data is persisted in an SQLite database using {@link SQLiteDatabase} and
 * {@link SQLiteOpenHelper} to manage the database schema.
 */
public class QuizResponsesData {
    public static final String DEBUG_TAG = "QuizResponsesData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizResponsesDbHelper;

    private static final String[] allColumns = {
            QuizDBHelper.QUIZ_RESPONSES_COLUMN_ID,
            QuizDBHelper.QUIZ_RESPONSES_COLUMN_QUIZ_ID,
            QuizDBHelper.QUIZ_RESPONSES_COLUMN_QUESTION_ID,
            QuizDBHelper.QUIZ_RESPONSES_COLUMN_USER_ANSWER
    };

    /**
     * Constructs a {@code QuizResponsesData} object with a given context,
     * initializing the database helper for accessing the database.
     *
     * @param context the context used to access the database.
     */
    public QuizResponsesData(Context context) {
        this.quizResponsesDbHelper = QuizDBHelper.getInstance(context);
    }

    /**
     * Opens a writable database connection to enable reading and writing.
     * This method should be called before any database operations.
     */
    public void open() {
        db = quizResponsesDbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "QuizResponsesData: db open");
    }

    /**
     * Closes the database connection and releases the resources.
     * This should be called when the database is no longer needed.
     */
    public void close() {
        if (quizResponsesDbHelper != null) {
            quizResponsesDbHelper.close();
            Log.d(DEBUG_TAG, "quizResponsesData: db closed");
        }
    }

    /**
     * Checks whether the database connection is currently open.
     *
     * @return {@code true} if the database is open, {@code false} otherwise.
     */
    public boolean isDBOpen() {
        return db.isOpen();
    }

    /**
     * Retrieves all quiz responses from the database and returns them as a list of
     * {@link QuizResponses} objects. Each row in the database corresponds to a quiz response.
     *
     * @return a list of all {@code QuizResponses} objects from the database.
     */
    public List<QuizResponses> retrieveAllQuizResponses() {
        ArrayList<QuizResponses> quizResponsesList = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(QuizDBHelper.TABLE_QUIZ_RESPONSES, allColumns,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    if (cursor.getColumnCount() >= 5) {
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_RESPONSES_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_RESPONSES_COLUMN_QUIZ_ID);
                        int quizID = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_RESPONSES_COLUMN_QUESTION_ID);
                        int questionID = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZ_RESPONSES_COLUMN_USER_ANSWER);
                        String userAnswer = cursor.getString(columnIndex);

                        QuizResponses quizResponses = new QuizResponses(quizID, questionID, userAnswer);
                        quizResponses.setId(id);

                        quizResponsesList.add(quizResponses);
                        Log.d(DEBUG_TAG, "Retrieved QuizResponse: " + quizResponses);
                    }
                }
            }

            if (cursor != null) {
                Log.d(DEBUG_TAG, "Number of records from DB: " + cursor.getCount());
            } else {
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
            }
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return quizResponsesList;
    }

    /**
     * Stores a new {@link QuizResponses} object in the database. The object's
     * attributes are persisted as a new row in the quiz responses table.
     *
     * @param quizResponses the {@code QuizResponses} object to be stored.
     * @return the {@code QuizResponses} object with the generated database ID set.
     */
    public QuizResponses storeQuizResponses(QuizResponses quizResponses) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZ_RESPONSES_COLUMN_QUIZ_ID, quizResponses.getQuizID());
        values.put(QuizDBHelper.QUIZ_RESPONSES_COLUMN_QUESTION_ID, quizResponses.getQuizQuestionID());
        values.put(QuizDBHelper.QUIZ_RESPONSES_COLUMN_USER_ANSWER, quizResponses.getUserAnswer());

        long id = db.insert(QuizDBHelper.TABLE_QUIZ_RESPONSES, null, values);

        quizResponses.setId(id);

        Log.d(DEBUG_TAG, "Stored new quiz response with id: " + String.valueOf(quizResponses.getId()));

        return quizResponses;
    }
}

