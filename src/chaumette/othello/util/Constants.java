/**
 * @author scrooge
 */

package chaumette.othello.util;

import chaumette.othello.external.Move;

public class Constants {
	public static final int BOARD_SIZE = 8;
	public static final int NUM_DIRECTIONS = 8;
	public static final int DEFAULT_STROKE_SIZE = 3;

	//TODO change delay
	public static final long MOVE_DELAY = 1000;

	public static String moveToString(Move m) {
		return "[x:" + m.x + ", y:" + m.y + "]";
	}
}
