package chaumette.othello.util;

import chaumette.othello.external.Move;

/**
 * Used to send requests to the main othello logic handling unit (Othello Main)
 */
public interface OthelloGameAPI {

	/**
	 * Sends a request to do a certain move
	 * Returns if the request was successful
	 */
	boolean requestMove(Move move, PlayerColor c);

	/**
	 * Sends a request to restart the game
	 */
	void onRestart();

	/**
	 * Sends a request to return the current player as a CellStatus
	 */
	PlayerColor getCurrentPlayer();

	/**
	 * Sends a request to quit the game
	 */
	void onQuit();
}
