/**
 * @author scrooge
 */

package chaumette.othello.util;

import chaumette.othello.external.Move;
import chaumette.othello.util.board.OthelloBoard;

public class Constants {
	public static final int BOARD_SIZE = 8;
	public static final int NUM_DIRECTIONS = 8;
	public static final int DEFAULT_STROKE_SIZE = 3;

	public static String moveToString(Move m) {
		return "[x:" + m.x + ", y:" + m.y + "]";
	}

	public static void printValidMoves(OthelloBoard b, PlayerColor c) {
		StringBuilder validMoveString = new StringBuilder();
		for (Move validMove : b.getValidMoves(c)) {
			validMoveString.append(moveToString(validMove)).append("\t");
		}
		System.out.println("Valid moves are: " + validMoveString);
	}
}
