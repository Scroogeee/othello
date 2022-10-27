package chaumette.othello.util;

import chaumette.othello.datastructures.CellStatus;
import chaumette.othello.external.Move;

/**
 * Used to send requests to the main othello logic handling unit (Othello Main)
 */
public interface OthelloGameAPI {

	/**
	 * Sends a request to do a certain move
	 * Returns if the request was successful
	 */
	boolean requestMove(Move move, CellStatus c);
}
