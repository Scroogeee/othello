/**
 * @author scrooge
 */

package chaumette.othello.util;

import chaumette.othello.external.Move;

/**
 * Constants and utility methods
 */
public class Constants {
	/**
	 * The size of the board
	 */
	public static final int BOARD_SIZE = 8;

	/**
	 * Number of directions to check for flips
	 */
	public static final int NUM_DIRECTIONS = 8;

	/**
	 * Default stroke size for drawing symbols
	 */
	public static final int DEFAULT_STROKE_SIZE = 3;

	/**
	 * Delay used for AIs to simulate thinking
	 */
	public static final long MOVE_DELAY = 1000;

	/**
	 * Converts data of a Move into a human-readable String representation
	 *
	 * @param m the Move to convert to a String representation
	 * @return a String representation of the given Move
	 */
	public static String moveToString(Move m) {
		return "[x:" + m.x + ", y:" + m.y + "]";
	}
}
