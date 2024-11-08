package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A {@link Fragment} that displays the past quiz results. It shows the previous scores stored
 * in a static list and allows the user to return to the splash screen.
 */
public class PastResultsFragment extends Fragment {

    private TextView scoresTextView;
    private static ArrayList<String> al = new ArrayList<>();

    /**
     * Default constructor for the fragment.
     */
    public PastResultsFragment() {
        // Default empty constructor
    }

    /**
     * Adds a new score to the static list of past results.
     *
     * @param s The score to be added to the list of past results.
     */
    public static void setString(String s) {
        al.add(s);
    }

    /**
     * Called when the fragment's view is created. Inflates the layout and sets up the view.
     *
     * @param inflater           The LayoutInflater object to inflate views.
     * @param container          The container (if any) for the fragment's view.
     * @param savedInstanceState If the fragment is being re-initialized, this contains the data
     *                           most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     *                           Otherwise, it is null.
     * @return The inflated view for the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_results, container, false);
    }

    /**
     * Called after the fragment's view has been created. Sets up the necessary views and
     * defines behavior for the return button.
     *
     * @param view               The view returned by {@link #onCreateView}.
     * @param savedInstanceState If the fragment is being re-initialized, this contains the data
     *                           most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     *                           Otherwise, it is null.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scoresTextView = view.findViewById(R.id.scores_text_view);
        Button returnButton = view.findViewById(R.id.return_button);

        displayQuizResults();

        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }

    /**
     * Displays the past quiz results. If there are scores available, they are shown;
     * otherwise, a message indicating no past scores is displayed.
     */
    private void displayQuizResults() {
        if (!al.isEmpty()) {
            StringBuilder scoresText = new StringBuilder("Past Scores:\n");
            for (String s : al) {
                scoresText.append(s).append("\n");
            }
            scoresTextView.setText(scoresText.toString());
        } else {
            scoresTextView.setText("No past scores available.");
        }
    }
}
