/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;

/**
 * Represents an othello board as a data structure
 */
public abstract class OthelloBoard {

	/**
	 * The x size of the board (vertical downwards, starts from 0)
	 */
	protected int xSize;

	/**
	 * The y size of the board (horizontal to the right, starts from 0)
	 */
	protected int ySize;

	/**
	 * Default constructor for a new OthelloBoard with specified x and y sizes
	 */
	public OthelloBoard(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
	}

	/**
	 * Initializes the board with the white and black pieces at the center
	 */
	public abstract void init();

	/**
	 * Tries to do a given move.
	 * Throws an InvalidMoveException if the move is invalid.
	 *
	 * @return if the given Move could be successfully executed
	 */
	public abstract void doMove(Move m, PlayerColor c);

	/**
	 * Used to check whether a move is valid
	 */
	protected abstract boolean isValidMove(Move m, PlayerColor c);

	/**
	 * Used to compute side effects (aka flipping of other stones)
	 */
	protected abstract void computeSideEffects(Move m, PlayerColor c);

	/**
	 * Returns the board as a two-dimensional array
	 */
	public abstract PlayerColor[][] getBoardAsTwoDimArray();
}
