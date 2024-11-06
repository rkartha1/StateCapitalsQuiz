package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;

public class PastResultsFragment extends Fragment{

    private List<String> previousScores;

    public PastResultsFragment() {
        //default empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_past_results, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView scoresTextView = view.findViewById(R.id.scores_text_view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("quiz_scores", Context.MODE_PRIVATE);
        String scores = sharedPreferences.getString("scores", "No previous scores available.");
        scoresTextView.setText(scores);

        // Display scores. Here we're simply joining them into a single string, but use a RecyclerView for more complex displays.
        if (previousScores != null && !previousScores.isEmpty()) {
            StringBuilder scoresText = new StringBuilder("Past Scores:\n");
            for (String score : previousScores) {
                scoresText.append(score).append("\n");
            }
            scoresTextView.setText(scoresText.toString());
        } else {
            scoresTextView.setText("No past scores available.");
        }
    }
}

