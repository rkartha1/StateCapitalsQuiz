package edu.uga.cs.statecapitalsquiz;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AndroidVersionsPagerAdapter extends FragmentStateAdapter {
    private int correctAnswersCount = GameState.getWinCount();
    private QuizzesData quizzesData;

    public AndroidVersionsPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
        quizzesData = new QuizzesData(context);
    }

    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    @Override
    public Fragment createFragment(int position) {
        if (position < 6) {
            // Passing the current correctAnswersCount to QuizFragment
            return QuizFragment.newInstance(position);
        } else {
            String finishTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            int score = (int) (Math.ceil(GameState.getWinCount() / 6.0) * 100); // Ensure correct rounding
            Quizzes newQuiz = new Quizzes(finishTime, score, 1); // Example of passing some data for the quiz
            quizzesData.open();
            quizzesData.storeQuizzes(newQuiz);
            quizzesData.close();

            return ResultsFragment.newInstance(correctAnswersCount, 6, finishTime); // 6 is the number of questions
        }
    }

    @Override
    public int getItemCount() {
        return 7; // 6 Quiz questions + 1 Results screen
    }
}