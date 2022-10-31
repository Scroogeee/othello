/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.PlayerColor;

import java.util.List;

/**
 * Represents an othello board as a data structure
 */
public abstract class OthelloBoard {

	/**
	 * @return the xSize
	 */
	public int getxSize() {
		return xSize;
	}

	/**
	 * @return the ySize
	 */
	public int getySize() {
		return ySize;
	}

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
	 */
	public final void doMove(Move m, PlayerColor c) {
		List<Move> sideEffects = getSideEffects(m, c);
		if (!sideEffects.isEmpty()) {
			//TODO then check for winning condition
		} else {
			throw new InvalidMoveException();
		}
	}

	/**
	 * Sets the value of the given board cell to the given Color without checks!!
	 */
	protected abstract void setCell(Move m, PlayerColor c);

	/**
	 * @return a List of all the side effect (aka flipping stones)
	 * a Move would have. If its size is 0, the move is invalid.
	 */
	protected abstract List<Move> getSideEffects(Move m, PlayerColor c);

	/**
	 * Returns the board as a two-dimensional array
	 */
	public abstract PlayerColor[][] getBoardAsTwoDimArray();
}
