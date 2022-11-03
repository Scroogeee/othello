/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.external.Move;
import chaumette.othello.util.board.OthelloBoard;

public abstract class OthelloUI {

	public abstract void initUI();

	public abstract void displayBoardState(OthelloBoard board);

	public abstract void displayMessage(String s);

	/**
	 * Asks the user for a move and returns it (without checks)
	 */
	public abstract Move askUserForMove();
}
