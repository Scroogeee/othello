/**
 * @author scrooge
 */

package chaumette.othello.util;

/**
 * Exception used to indicate an invalid move in a game of Othello
 */
public class InvalidMoveException extends RuntimeException {

	/**
	 * Creates a new InvalidMoveException with the given String as the message
	 *
	 * @param s the message for the exception
	 */
	public InvalidMoveException(String s) {
		super(s);
	}
}
