/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.util.OthelloGameAPI;
import chaumette.othello.util.board.OthelloBoard;

public abstract class OthelloUI {
	protected OthelloGameAPI gameAPI;

	public abstract void initUI();

	public abstract void displayBoardState(OthelloBoard board);

	public abstract void displayMessage(String s);
}
