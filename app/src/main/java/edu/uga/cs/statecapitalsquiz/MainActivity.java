package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import androidx.fragment.app.Fragment;

/**
 * {@link MainActivity} is the main activity of the State Capitals Quiz app.
 * It initializes and manages the user interface, sets up the ViewPager2 for fragment navigation,
 * and handles the back button press behavior to navigate between fragments.
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager2 pager;

    private int wins = 0;
    private static final String TAG = "MAIN";

    /**
     * Sets the number of wins by incrementing the current wins count.
     *
     * @param wins The number of wins to set.
     */
    public void setWins(int wins){
        this.wins = wins + 1;
    }

    /**
     * Gets the current number of wins.
     *
     * @return The current number of wins.
     */
    public int getWins () {
        return wins;
    }

    /**
     * Called when the activity is first created. Sets up the user interface, initializes the
     * ViewPager2, and starts the asynchronous task to load quiz data from a CSV file.
     *
     * @param savedInstanceState A bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "TESTTTTT");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        pager = findViewById(R.id.viewpager);

        new LoadDataAsyncTask(this).execute();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AndroidVersionsPagerAdapter avpAdapter = new AndroidVersionsPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), getApplicationContext());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(avpAdapter);
    }

    /**
     * Handles the back button press. If the current fragment is {@link ResultsFragment},
     * it starts a new {@link SplashActivity}. Otherwise, it behaves as a normal back press.
     */
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof ResultsFragment) {
            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Retrieves the current {@link AndroidVersionsPagerAdapter} associated with the ViewPager2.
     *
     * @return The current {@link AndroidVersionsPagerAdapter}.
     */
    public AndroidVersionsPagerAdapter getPagerAdapter() {
        return (AndroidVersionsPagerAdapter) pager.getAdapter(); // Return the pager adapter
    }
}

