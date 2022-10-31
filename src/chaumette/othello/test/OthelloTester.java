/**
 * @author scrooge
 */

package chaumette.othello.test;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.board.OthelloTwoDimArrayBoard;
import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;

public class OthelloTester implements Runnable {
	OthelloBoard board = new OthelloTwoDimArrayBoard();

	public static void main(String[] args) {
		new OthelloTester().run();
	}

	@Override
	public void run() {
		board.init();
		System.out.println(board);
		boolean valid = board.isValidMove(new Move(3, 2), PlayerColor.BLACK);
		System.out.println(valid);
		board.doMove(new Move(3, 2), PlayerColor.BLACK);
		System.out.println(board);
	}
}
