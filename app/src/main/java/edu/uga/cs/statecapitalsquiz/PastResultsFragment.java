package edu.uga.cs.statecapitalsquiz;

import android.os.AsyncTask;
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

        // Load past quiz results asynchronously
        new LoadQuizResultsTask().execute();
    }

    // AsyncTask to load quiz results from the database
    private class LoadQuizResultsTask extends AsyncTask<Void, Void, List<Quizzes>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Quizzes> doInBackground(Void... voids) {
            QuizzesData quizzesData = new QuizzesData(getContext());
            quizzesData.open();
            List<Quizzes> quizList = quizzesData.retrieveAllQuizzes();  // Retrieve all quizzes
            quizzesData.close();
            return quizList;
        }

        @Override
        protected void onPostExecute(List<Quizzes> previousQuizzes) {
            if (previousQuizzes != null && !previousQuizzes.isEmpty()) {
                StringBuilder scoresText = new StringBuilder("Past Scores:\n");

                // Build a string for each quiz result
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
}
