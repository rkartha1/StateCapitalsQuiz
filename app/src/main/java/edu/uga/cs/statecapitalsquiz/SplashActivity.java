package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * SplashActivity displays a splash screen when the app is launched.
 * It contains a button that, when clicked, navigates the user to
 * the MainActivity of the app.
 */
public class SplashActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting. This is where most initialization
     * should go. It sets the content view and initializes the continue button
     * to navigate to the MainActivity when clicked.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains
     *                           the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button continueButton = findViewById(R.id.continue_button);
        Button viewPastResultsButton = findViewById(R.id.continue_button2);
        continueButton.setOnClickListener(v -> {
            // Start MainActivity when the continue button is clicked
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
        viewPastResultsButton.setOnClickListener(v -> {
            Intent pastResultsIntent = new Intent(SplashActivity.this, PastResultsActivity.class);
            startActivity(pastResultsIntent);
            finish();
        });
    }
}
