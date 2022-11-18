/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import szte.mi.Move;

import java.util.Set;

/**
 * Specifies what any UI displaying an Othello game should be capable of
 */
public interface OthelloUI {

	/**
	 * Initializes the UI
	 */
	void initUI();

	/**
	 * Displays the state of the given OthelloBoard
	 *
	 * @param board the OthelloBoard to display
	 */
	void displayBoardState(OthelloBoard board);

	/**
	 * Displays a message to the user in some way
	 *
	 * @param s the message to display
	 */
	void displayMessage(String s);

	/**
	 * Asks the user for a move and returns it (without checks)
	 *
	 * @param playerColor the current player color
	 * @return the Move requested by the user (not verified!!)
	 */
	Move askUserForMove(PlayerColor playerColor);

	/**
	 * Displays the valid moves for the given PlayerColor
	 *
	 * @param valid a set of all valid moves
	 * @param c     the color of the current player
	 */
	void displayValidMoves(Set<Move> valid, PlayerColor c);
}
