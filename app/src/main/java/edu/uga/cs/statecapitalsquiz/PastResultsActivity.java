package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PastResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_results);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PastResultsFragment())
                    .commit();
        }
    }
}