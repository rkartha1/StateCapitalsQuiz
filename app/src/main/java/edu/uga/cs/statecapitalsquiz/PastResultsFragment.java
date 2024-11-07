package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;

public class PastResultsFragment extends Fragment {

    private QuizzesData quizzesData;
    private TextView scoresTextView;

    public PastResultsFragment() {
        // Default empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scoresTextView = view.findViewById(R.id.scores_text_view);

        // Initialize QuizzesData to retrieve stored results
        quizzesData = new QuizzesData(requireContext());
        quizzesData.open();

        // Retrieve quiz results and display them
        displayQuizResults();

        quizzesData.close();
    }

    private void displayQuizResults() {
        List<Quizzes> previousQuizzes = quizzesData.retrieveAllQuizzes();

        if (previousQuizzes != null && !previousQuizzes.isEmpty()) {
            StringBuilder scoresText = new StringBuilder("Past Scores:\n");
            for (Quizzes quiz : previousQuizzes) {
                scoresText.append("Date: ").append(quiz.getDate())
                        .append(", Result: ").append(quiz.getResult())
                        .append("/").append(quiz.getAnswered()).append("\n");
            }
            scoresTextView.setText(scoresText.toString());
        } else {
            scoresTextView.setText("No past scores available.");
        }
    }
}


