package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AndroidVersionsPagerAdapter extends FragmentStateAdapter {
    private int correctAnswersCount = 0;

    public AndroidVersionsPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    @Override
    public Fragment createFragment(int position) {
        if (position < 6) {
            return QuizFragment.newInstance(position);
        } else {
            return ResultsFragment.newInstance(correctAnswersCount, 6); // Pass the correct answers
        }
    }

    @Override
    public int getItemCount() {
        return 7; // 6 Quiz questions + 1 Results screen
    }
}
