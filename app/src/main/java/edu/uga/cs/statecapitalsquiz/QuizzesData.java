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
 * This class provides the interface to interact with the quizzes database.
 * It handles opening and closing the database, as well as retrieving and storing quiz records.
 */
public class QuizzesData {

    public static final String DEBUG_TAG = "QuizzesData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizzesDbHelper;

    private static final String[] allColumns = {
            QuizDBHelper.QUIZZES_COLUMN_ID,
            QuizDBHelper.QUIZZES_COLUMN_DATE,
            QuizDBHelper.QUIZZES_COLUMN_RESULT,
            QuizDBHelper.QUIZZES_COLUMN_ANSWERED
    };

    /**
     * Constructor for QuizzesData.
     * Initializes the database helper to interact with the quizzes database.
     *
     * @param context The context used to get the database helper instance.
     */
    public QuizzesData(Context context) {
        this.quizzesDbHelper = QuizDBHelper.getInstance(context);
    }

    /**
     * Opens a writable database to allow for querying and modifying the quizzes data.
     * Logs the successful opening of the database.
     */
    public void open() {
        db = quizzesDbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "QuizzesData: db open");
    }

    /**
     * Closes the database if it is open.
     * Logs the successful closing of the database.
     */
    public void close() {
        if (quizzesDbHelper != null) {
            quizzesDbHelper.close();
            Log.d(DEBUG_TAG, "quizzesData: db closed");
        }
    }

    /**
     * Checks if the database is currently open.
     *
     * @return true if the database is open, false otherwise.
     */
    public boolean isDBOpen() {
        return db.isOpen();
    }

    /**
     * Retrieves all quizzes stored in the database.
     * For each quiz, a new Quizzes object is created and added to the list.
     *
     * @return A List of Quizzes objects, each representing a quiz stored in the database.
     */
    public List<Quizzes> retrieveAllQuizzes() {
        ArrayList<Quizzes> quizzesList = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(QuizDBHelper.TABLE_QUIZZES, allColumns,
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= 5) {

                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_DATE);
                        String date = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_RESULT);
                        int result = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_ANSWERED);
                        int answered = cursor.getInt(columnIndex);

                        Quizzes quizzes = new Quizzes(date, result, answered);
                        quizzes.setId(id);
                        quizzesList.add(quizzes);
                        Log.d(DEBUG_TAG, "Retrieved Quiz: " + quizzes);
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

        return quizzesList;
    }

    /**
     * Stores a new quiz in the database.
     * The quiz's details are provided, and the id (primary key) is set after insertion.
     *
     * @param quizzes The Quizzes object containing the quiz details.
     * @return The same Quizzes object, but with the id set after insertion.
     */
    public Quizzes storeQuizzes(Quizzes quizzes) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZZES_COLUMN_DATE, quizzes.getDate());
        values.put(QuizDBHelper.QUIZZES_COLUMN_RESULT, quizzes.getResult());
        values.put(QuizDBHelper.QUIZZES_COLUMN_ANSWERED, quizzes.getAnswered());

        long id = db.insert(QuizDBHelper.TABLE_QUIZZES, null, values);

        quizzes.setId(id);

        Log.d(DEBUG_TAG, "Stored new quiz with id: " + quizzes.getId());

        return quizzes;
    }
}


