/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.external.Move;
import chaumette.othello.util.board.OthelloBoard;

import java.util.Set;

public interface OthelloUI {

	void initUI();

	void displayBoardState(OthelloBoard board);

	void displayMessage(String s);

	/**
	 * Asks the user for a move and returns it (without checks)
	 */
	Move askUserForMove();

	void displayValidMoves(Set<Move> valid);
}
