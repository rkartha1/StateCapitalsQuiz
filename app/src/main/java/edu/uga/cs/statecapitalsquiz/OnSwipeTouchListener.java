package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private String userAnswer;
    private final String correctAnswer;
    private int winCount;

    public OnSwipeTouchListener(Context context, String userAnswer, String correctAnswer, int winCount) {
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.winCount = winCount;
        gestureDetector = new GestureDetector(context, new GestureListener());
        Log.d("OnSwipeTouchListener", "Initialized with userAnswer: " + userAnswer + ", correctAnswer: " + correctAnswer + ", winCount: " + winCount);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("OnSwipeTouchListener", "Touch event received");
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
        Log.d("OnSwipeTouchListener", "User answer updated to: " + userAnswer);
    }

    public int getWin() {
        return winCount;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    return true;
                }
            }
            return false;
        }

        private void onSwipeLeft() {
            Log.d("OnSwipeTouchListener", "Swipe left detected");
            Log.d("OnSwipeTouchListener", "Correct answer: " + correctAnswer);
            Log.d("OnSwipeTouchListener", "User answer: " + userAnswer);
            if (userAnswer != null) {
                if (userAnswer.equals(correctAnswer)) {
                    Log.d("OnSwipeTouchListener", "Correct answer.");
                    winCount++;
                } else {
                    Log.d("OnSwipeTouchListener", "Incorrect answer.");
                }
            } else {
                Log.d("OnSwipeTouchListener", "No answer provided.");
            }
        }

        private void onSwipeRight() {
            Log.d("OnSwipeTouchListener", "Swipe right detected");

        }
    }}