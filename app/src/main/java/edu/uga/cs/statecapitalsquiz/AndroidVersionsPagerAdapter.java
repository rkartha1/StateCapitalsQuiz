package edu.uga.cs.statecapitalsquiz;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A {@link FragmentStateAdapter} that provides fragments for a quiz application.
 * It dynamically loads quiz fragments for the first 6 pages, followed by a results fragment
 * showing the score and finish time for the quiz.
 */
public class AndroidVersionsPagerAdapter extends FragmentStateAdapter {

    // The count of correct answers in the quiz
    private int correctAnswersCount = GameState.getWinCount();

    /**
     * Creates an instance of the AndroidVersionsPagerAdapter.
     *
     * @param fragmentManager The FragmentManager that will manage the fragments.
     * @param lifecycle       The lifecycle of the fragment.
     * @param context         The context to be used for fragment creation.
     */
    public AndroidVersionsPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
    }

    /**
     * Sets the number of correct answers in the quiz.
     *
     * @param correctAnswersCount The number of correct answers to be set.
     */
    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    /**
     * Returns the number of correct answers in the quiz.
     *
     * @return The number of correct answers.
     */
    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    /**
     * Creates a new fragment based on the position.
     *
     * @param position The position in the pager.
     * @return A new {@link Fragment} representing the quiz page or results page.
     */
    @Override
    public Fragment createFragment(int position) {
        if (position < 6) {
            return QuizFragment.newInstance(position);
        } else {
            String finishTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            return ResultsFragment.newInstance(correctAnswersCount, 6, finishTime); // 6 is the number of questions
        }
    }

    /**
     * Returns the total number of pages in the pager (6 quiz pages and 1 results page).
     *
     * @return The number of pages in the pager.
     */
    @Override
    public int getItemCount() {
        return 7;
    }
}
