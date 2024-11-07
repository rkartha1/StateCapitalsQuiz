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

public class ResultsFragment extends Fragment {

    private static final String ARG_CORRECT_ANSWERS = "correctAnswers";
    private static final String ARG_TOTAL_QUESTIONS = "totalQuestions";
    private static final String ARG_FINISH_TIME = "finishTime";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

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

            int score = (int) Math.round(((double) win / totalQuestions) * 100);

            String resultString = "Date: " + finishTime + ", Result: " + score + "/" + totalQuestions;

            PastResultsFragment pastResultsFragment = new PastResultsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("quizResult", resultString);
            pastResultsFragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, pastResultsFragment) // Ensure you have the correct container ID
                    .addToBackStack(null)
                    .commit();

            // Store the quiz result in the database
            QuizzesData quizzesData = new QuizzesData(getActivity().getApplicationContext());
            quizzesData.open();
            Quizzes quiz = new Quizzes(finishTime, score, totalQuestions);
            quizzesData.storeQuizzes(quiz);
            quizzesData.close();

            timeTextView.setText("Quiz finished at: " + finishTime);
        }

        returnToSplashButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }
}