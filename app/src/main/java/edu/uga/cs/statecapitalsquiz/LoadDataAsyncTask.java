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

public class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private static final String TAG = "LoadDataAsyncTask";

    public LoadDataAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        QuizDBHelper dbHelper = QuizDBHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if table is already populated
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

                // Ensure correct number of fields
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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "Data load task complete.");
    }
}
