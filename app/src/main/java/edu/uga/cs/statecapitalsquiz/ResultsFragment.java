package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A fragment that displays the results of a quiz, including the number of correct answers,
 * the total number of questions, and the time when the quiz was finished.
 * It also allows the user to return to the splash screen by clicking a button.
 */
public class ResultsFragment extends Fragment {

    private static final String ARG_CORRECT_ANSWERS = "correctAnswers";
    private static final String ARG_TOTAL_QUESTIONS = "totalQuestions";
    private static final String ARG_FINISH_TIME = "finishTime";

    private String s;

    /**
     * Creates a new instance of the ResultsFragment with the specified data.
     *
     * @param correctAnswers the number of correct answers in the quiz
     * @param totalQuestions the total number of questions in the quiz
     * @param finishTime the time when the quiz was finished
     * @return a new instance of ResultsFragment
     */
    public static ResultsFragment newInstance(int correctAnswers, int totalQuestions, String finishTime) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CORRECT_ANSWERS, correctAnswers);
        args.putInt(ARG_TOTAL_QUESTIONS, totalQuestions);
        args.putString(ARG_FINISH_TIME, finishTime);
        fragment.setArguments(args);
        return fragment;
    }

    int win = GameState.getWinCount();

    /**
     * Inflates the fragment's layout and returns the root view.
     *
     * @param inflater the LayoutInflater to inflate the fragment's layout
     * @param container the container that holds the fragment
     * @param savedInstanceState any saved state from a previous instance of the fragment
     * @return the root view of the fragment's layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    /**
     * Called after the fragment's view has been created. This method initializes the
     * views and sets up the data passed to the fragment, including the result text
     * and the finish time. It also sets up the "Return to Splash" button.
     *
     * @param view the root view of the fragment's layout
     * @param savedInstanceState any saved state from a previous instance of the fragment
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView resultTextView = view.findViewById(R.id.result_text);
        TextView timeTextView = view.findViewById(R.id.time_text);
        Button returnToSplashButton = view.findViewById(R.id.return_to_splash_button);

        if (getArguments() != null) {
            int correctAnswers = getArguments().getInt(ARG_CORRECT_ANSWERS);
            int totalQuestions = getArguments().getInt(ARG_TOTAL_QUESTIONS);
            String finishTime = getArguments().getString(ARG_FINISH_TIME);

            resultTextView.setText("You answered " + win + " out of " + totalQuestions + " correct!");
            timeTextView.setText("Quiz finished at: " + finishTime);

            s = "Score: " + win + " at time: " + finishTime;
            PastResultsFragment.setString(s);
        }

        returnToSplashButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }
}


