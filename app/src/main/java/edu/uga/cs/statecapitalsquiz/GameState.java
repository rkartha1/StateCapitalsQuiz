package edu.uga.cs.statecapitalsquiz;

/**
 * The {@link GameState} class manages the win count for the game.
 * It provides static methods to get, increment, and reset the win count.
 * This class is designed to track the number of wins across all instances.
 */
public class GameState {

    private static int winCount = 0;

    /**
     * Retrieves the current win count.
     *
     * @return The current win count.
     */
    public static int getWinCount() {
        return winCount;
    }

    /**
     * Increments the win count by 1.
     * This method is used when the user successfully answers a question.
     */
    public static void incrementWinCount() {
        winCount++;
    }

    /**
     * Resets the win count to 0.
     * This method can be used to start a new game or reset the win count for any other reason.
     */
    public static void resetWinCount() {
        winCount = 0;
    }
}

