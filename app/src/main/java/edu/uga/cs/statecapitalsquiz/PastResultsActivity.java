package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The {@link PastResultsActivity} class is an {@link AppCompatActivity} that displays the past results
 * of the State Capitals Quiz game. It initializes the activity by setting the content view
 * and loading a fragment to display the past results.
 */
public class PastResultsActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. This method sets the layout for the activity and
     * loads the {@link PastResultsFragment} if this is the first time the activity is created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in
     *                           {@link #onSaveInstanceState(Bundle)}. Otherwise, it is null.
     */
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
