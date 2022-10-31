/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.PlayerColor;

import java.util.ArrayList;
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
		List<Move> toFlip = getSideEffects(m, c);
		if (!toFlip.isEmpty()) {
			flipCellColor(toFlip);
			//TODO then check for winning condition
		} else {
			throw new InvalidMoveException();
		}
	}

	/**
	 * Flips all cell colors at the given locations
	 */
	protected void flipCellColor(List<Move> sideEffects) {
		for (Move move : sideEffects) {
			switch (getCellColor(move)) {
				case BLACK -> setCell(move, PlayerColor.WHITE);
				case WHITE -> setCell(move, PlayerColor.BLACK);
			}
		}

	}

	/**
	 * Returns all board cells from the given point in the given direction
	 */
	protected final PlayerColor[] getProjection(Move from, Move projectionVector) {
		List<PlayerColor> toReturn = new ArrayList<>();
		int currentX = from.x;
		int currentY = from.y;
		while ((0 <= currentX && currentX < xSize) && (0 <= currentY && currentY < ySize)) {
			toReturn.add(getCellColor(currentX, currentY));
			currentX += projectionVector.x;
			currentY += projectionVector.y;
		}
		return toReturn.toArray(new PlayerColor[0]);
	}

	/**
	 * Sets the value of the given board cell to the given Color without checks!!
	 */
	protected abstract void setCell(Move m, PlayerColor c);

	/**
	 * Returns the color of a given cell
	 */
	protected final PlayerColor getCellColor(Move m) {
		return getCellColor(m.x, m.y);
	}

	/**
	 * Returns the color of a given cell
	 */
	protected abstract PlayerColor getCellColor(int x, int y);

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
