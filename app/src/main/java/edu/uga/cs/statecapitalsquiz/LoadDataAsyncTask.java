package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * {@link LoadDataAsyncTask} is an AsyncTask that loads data from a CSV file into the app's database.
 * It runs in the background and checks whether the database is already populated.
 * If not, it loads the quiz questions from the "state_capitals.csv" file into the SQLite database.
 */
public class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private static final String TAG = "LoadDataAsyncTask";

    /**
     * Constructs a new {@link LoadDataAsyncTask} with the provided context.
     *
     * @param context The application context to access resources and the database.
     */
    public LoadDataAsyncTask(Context context) {
        this.context = context;
    }

    /**
     * Performs the background operation of checking the database and loading data from the CSV file
     * if the database is empty.
     *
     * This method ensures that the CSV file is read only once, by checking whether the database
     * table is already populated.
     *
     * @param voids The parameters for the task, not used in this case.
     * @return null
     */
    @Override
    protected Void doInBackground(Void... voids) {
        QuizDBHelper dbHelper = QuizDBHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT 1 FROM " + QuizDBHelper.TABLE_QUIZ_QUESTIONS + " LIMIT 1", null);
        boolean isPopulated = cursor.moveToFirst();
        cursor.close();

        if (isPopulated) {
            Log.d(TAG, "Database already populated. Skipping CSV load.");
            return null;
        }

        try (InputStream is = context.getAssets().open("state_capitals.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length < 3) continue;

                ContentValues values = new ContentValues();
                values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE, fields[0].trim());
                values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY, fields[1].trim());
                values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY, fields[2].trim());
                values.put(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY, fields[3].trim());

                db.insert(QuizDBHelper.TABLE_QUIZ_QUESTIONS, null, values);
            }
            Log.d(TAG, "Database populated successfully from CSV.");

        } catch (IOException e) {
            Log.e(TAG, "Error reading CSV file", e);
        } finally {
            db.close();
        }

        return null;
    }

    /**
     * Called when the background task is complete. Logs a message indicating the task completion.
     *
     * @param aVoid The result of the background operation, not used in this case.
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "Data load task complete.");
    }
}

