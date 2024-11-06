package edu.uga.cs.statecapitalsquiz;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.viewpager2.widget.ViewPager2;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        new LoadDataAsyncTask(this).execute();
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        ViewPager2 pager = findViewById( R.id.viewpager );
//        AndroidVersionsPagerAdapter avpAdapter = new
//                AndroidVersionsPagerAdapter(
//                getSupportFragmentManager(), getLifecycle() );
//        pager.setOrientation(
//                ViewPager2.ORIENTATION_HORIZONTAL );
//        pager.setAdapter( avpAdapter );
//    }
//
//    public List<QuizQuestions> getQuizQuestions() {
//        List<QuizQuestions> questions = new ArrayList<>();
//        QuizDBHelper dbHelper = QuizDBHelper.getInstance(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        String[] columns = {
//                QuizDBHelper.QUIZ_QUESTIONS_COLUMN_ID,
//                QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE,
//                QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY,
//                QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY,
//                QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY
//        };
//
//        Cursor cursor = db.query(QuizDBHelper.TABLE_QUIZ_QUESTIONS, columns, null, null, null, null, null);
//
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_ID));
//            String state = cursor.getString(cursor.getColumnIndexOrThrow(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_STATE));
//            String capitalCity = cursor.getString(cursor.getColumnIndexOrThrow(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY));
//            String firstCity = cursor.getString(cursor.getColumnIndexOrThrow(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_FIRST_CITY));
//            String secondCity = cursor.getString(cursor.getColumnIndexOrThrow(QuizDBHelper.QUIZ_QUESTIONS_COLUMN_SECOND_CITY));
//
//            questions.add(new QuizQuestions(id, state, capitalCity, firstCity, secondCity));
//        }
//        cursor.close();
//        db.close();
//        return questions;
//    }
//
//
//
//
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import edu.uga.cs.statecapitalsquiz.AndroidVersionsPagerAdapter;
import edu.uga.cs.statecapitalsquiz.LoadDataAsyncTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 pager; // Declare the ViewPager2 variable

    public int wins = 0;
    private static final String TAG = "MAIN";

    public void setWins(int wins){
        wins = wins+1;
    }
    public int getWins () {
        return wins;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "TESTTTTT");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the pager variable
        pager = findViewById(R.id.viewpager);

        new LoadDataAsyncTask(this).execute();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the ViewPager2 with the adapter
        AndroidVersionsPagerAdapter avpAdapter = new AndroidVersionsPagerAdapter(
                getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(avpAdapter);
    }

    public AndroidVersionsPagerAdapter getPagerAdapter() {
        return (AndroidVersionsPagerAdapter) pager.getAdapter(); // Now 'pager' is defined
    }
}

//
//
//
//}