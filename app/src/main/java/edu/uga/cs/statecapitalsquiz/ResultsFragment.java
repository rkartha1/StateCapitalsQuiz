package edu.uga.cs.statecapitalsquiz;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    //int win = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView resultTextView = view.findViewById(R.id.result_text);
        TextView timeTextView = view.findViewById(R.id.time_text);
        if (getArguments() != null) {
            int correctAnswers = getArguments().getInt(ARG_CORRECT_ANSWERS);
            int totalQuestions = getArguments().getInt(ARG_TOTAL_QUESTIONS);
            String finishTime = getArguments().getString(ARG_FINISH_TIME);
            resultTextView.setText("You answered " + win + " out of " + totalQuestions + " correct!");
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("quiz_scores", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String pastResults = sharedPreferences.getString("scores", "");
            pastResults += correctAnswers + " out of " + totalQuestions + " correct on " + finishTime + "\n";
            editor.putString("scores", pastResults);
            editor.apply();
            timeTextView.setText("Quiz finished at: " + finishTime);
        }
    }
}

