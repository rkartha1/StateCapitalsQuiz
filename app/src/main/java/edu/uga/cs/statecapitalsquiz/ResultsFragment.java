package edu.uga.cs.statecapitalsquiz;
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
    public static ResultsFragment newInstance(int correctAnswers, int totalQuestions) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CORRECT_ANSWERS, correctAnswers);
        args.putInt(ARG_TOTAL_QUESTIONS, totalQuestions);
        fragment.setArguments(args);
        return fragment;
    }
    //int win = QuizFragment.getWin();
    //int win = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }
    int win = 4;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView resultTextView = view.findViewById(R.id.result_text);
        if (getArguments() != null) {
            int correctAnswers = getArguments().getInt(ARG_CORRECT_ANSWERS);
            int totalQuestions = getArguments().getInt(ARG_TOTAL_QUESTIONS);
            resultTextView.setText("You answered " + win + " out of " + totalQuestions + " correct!");
        }
    }
}

