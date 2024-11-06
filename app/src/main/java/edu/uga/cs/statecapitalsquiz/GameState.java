package edu.uga.cs.statecapitalsquiz;

public class GameState {
    private static int winCount = 0;  // Static variable to store win count across all instances

    // Get the current win count
    public static int getWinCount() {
        return winCount;
    }

    // Increment the win count
    public static void incrementWinCount() {
        winCount++;
    }

    // Reset the win count (if necessary)
    public static void resetWinCount() {
        winCount = 0;
    }
}
