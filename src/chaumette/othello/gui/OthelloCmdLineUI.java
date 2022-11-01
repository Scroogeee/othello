/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.util.board.OthelloBoard;

public class OthelloCmdLineUI extends OthelloUI {

	@Override
	public void initUI() {

	}

	@Override
	public void displayBoardState(OthelloBoard board) {
		displayMessage(board.toString());
	}

	@Override
	public void displayMessage(String s) {
		System.out.println(s);
	}
}
