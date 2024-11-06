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
        Log.d("OnSwipeTouchListener", "Touch event received: " + motionEvent.getAction() +
                " | X: " + motionEvent.getX() + ", Y: " + motionEvent.getY());
        boolean result = gestureDetector.onTouchEvent(motionEvent);
        Log.d("OnSwipeTouchListener", "Gesture detected: " + result);
        return result;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
        Log.d("OnSwipeTouchListener", "User answer updated to: " + userAnswer);
    }

    public int getWin() {
        return winCount;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 30; // Reduced for easier detection
        private static final int SWIPE_VELOCITY_THRESHOLD = 30; // Reduced for easier detection

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 == null || e2 == null) {
                Log.d("OnSwipeTouchListener", "onFling: Null MotionEvent detected.");
                return false;
            }

            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();
            Log.d("OnSwipeTouchListener", "onFling detected with diffX: " + diffX + ", diffY: " + diffY + ", velocityX: " + velocityX + ", velocityY: " + velocityY);

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    return true;
                } else {
                    Log.d("OnSwipeTouchListener", "Swipe not detected due to insufficient threshold/velocity. diffX: " + diffX + ", velocityX: " + velocityX);
                }
            } else {
                Log.d("OnSwipeTouchListener", "Horizontal swipe not detected. diffX: " + diffX + ", diffY: " + diffY);
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
            // Handle right swipe if needed
        }
    }
}
