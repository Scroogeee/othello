package chaumette.othello.util;

/**
 * Used to send requests to the main othello logic handling unit (Othello Main)
 */
public interface OthelloGameAPI {

	/**
	 * Sends a request to quit the game
	 */
	void onQuit();
}
