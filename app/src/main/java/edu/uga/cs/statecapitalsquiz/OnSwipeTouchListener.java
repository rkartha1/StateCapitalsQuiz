package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * {@link OnSwipeTouchListener} is a custom touch listener that detects swipe gestures
 * and compares the user's answer with the correct answer. It tracks the win count and
 * performs actions based on swipe gestures.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private String userAnswer;
    private final String correctAnswer;
    private int winCount;

    /**
     * Constructs a new {@link OnSwipeTouchListener} with the provided parameters.
     *
     * @param context The context of the application.
     * @param userAnswer The user's answer.
     * @param correctAnswer The correct answer to compare against.
     * @param winCount The current win count.
     */
    public OnSwipeTouchListener(Context context, String userAnswer, String correctAnswer, int winCount) {
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.winCount = winCount;
        gestureDetector = new GestureDetector(context, new GestureListener());
        Log.d("OnSwipeTouchListener", "Initialized with userAnswer: " + userAnswer + ", correctAnswer: " + correctAnswer + ", winCount: " + winCount);
    }

    /**
     * Handles touch events and delegates swipe gesture detection to {@link GestureDetector}.
     * If the user answer is correct, the win count is incremented.
     *
     * @param view The view that received the touch event.
     * @param motionEvent The motion event containing touch data.
     * @return true if the gesture was detected, false otherwise.
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (userAnswer.equals(correctAnswer)) {
            Log.d("OnSwipeTouchListener", "Correct answer.");
            GameState.incrementWinCount();
            winCount = GameState.getWinCount();
        } else {
            winCount = GameState.getWinCount();
        }
        Log.d("OnSwipeTouchListener", "WINNNNNN==" + winCount);
        Log.d("OnSwipeTouchListener", "Touch event received: " + motionEvent.getAction() +
                " | X: " + motionEvent.getX() + ", Y: " + motionEvent.getY());
        boolean result = gestureDetector.onTouchEvent(motionEvent);
        Log.d("OnSwipeTouchListener", "Gesture detected: " + result);
        return result;
    }

    /**
     * Updates the user's answer.
     *
     * @param userAnswer The new answer provided by the user.
     */
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
        Log.d("OnSwipeTouchListener", "User answer updated to: " + userAnswer);
    }

    /**
     * Gets the current win count.
     *
     * @return The current win count.
     */
    public int getWin() {
        return winCount;
    }

    /**
     * {@link GestureListener} is a custom listener for detecting swipe gestures.
     * It checks whether the swipe is horizontal and detects left or right swipes.
     */
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 30;
        private static final int SWIPE_VELOCITY_THRESHOLD = 30;

        /**
         * Detects a fling gesture and determines whether it's a valid swipe.
         * If a valid horizontal swipe is detected, the appropriate swipe action is triggered.
         *
         * @param e1 The initial MotionEvent.
         * @param e2 The final MotionEvent.
         * @param velocityX The velocity of the swipe in the X direction.
         * @param velocityY The velocity of the swipe in the Y direction.
         * @return true if the swipe gesture is detected, false otherwise.
         */
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
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) { // Check if swipe is valid
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

        /**
         * Handles a left swipe gesture. Checks the user's answer and updates the win count if correct.
         */
        private void onSwipeLeft() {
            Log.d("OnSwipeTouchListener", "Swipe left detected");
            Log.d("OnSwipeTouchListener", "Correct answer: " + correctAnswer);
            Log.d("OnSwipeTouchListener", "User answer: " + userAnswer);
            if (userAnswer != null) {
                if (userAnswer.equals(correctAnswer)) {
                    Log.d("OnSwipeTouchListener", "Correct answer.");
                    winCount++; // Increment win count for correct answer
                } else {
                    Log.d("OnSwipeTouchListener", "Incorrect answer.");
                }
            } else {
                Log.d("OnSwipeTouchListener", "No answer provided.");
            }
        }

        /**
         * Handles a right swipe gesture. Currently, no action is taken for right swipes.
         */
        private void onSwipeRight() {
            Log.d("OnSwipeTouchListener", "Swipe right detected");
        }
    }
}

