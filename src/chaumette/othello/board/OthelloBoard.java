/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.PlayerColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static chaumette.othello.util.Constants.NUM_DIRECTIONS;

/**
 * Represents an othello board as a data structure
 */
public abstract class OthelloBoard {

	/**
	 * @return the xSize
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * @return the ySize
	 */
	public int getYSize() {
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
	 * Tries to do a given move. (Doesn't check if it's player's turn!!)
	 * Throws an InvalidMoveException if the move is invalid.
	 */
	public final void doMove(Move m, PlayerColor c) {
		Set<Move> toFlip = getSideEffects(m, c);
		if (!toFlip.isEmpty()) {
			flipCellColor(toFlip);
		} else {
			throw new InvalidMoveException();
		}
	}

	/**
	 * Move is considered valid if the cell in question is empty,
	 * and placing the given color would cause at least one cell to flip.
	 *
	 * @return if the given move given the current player color would be valid
	 */
	public boolean isValidMove(Move m, PlayerColor c) {
		return getCellColor(m) == PlayerColor.EMPTY && !getSideEffects(m, c).isEmpty();
	}

	/**
	 * @return if the given player has any valid move
	 */
	public boolean canDoValidMove(PlayerColor c) {
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				if (isValidMove(new Move(i, j), c)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * If both players don't have any valid moves
	 */
	public boolean isGameOver() {
		return !canDoValidMove(PlayerColor.WHITE) && !canDoValidMove(PlayerColor.BLACK);
	}

	/**
	 * Returns the board as a two-dimensional array
	 */
	public abstract PlayerColor[][] getBoardAsTwoDimArray();

	/**
	 * Flips all cell colors at the given locations
	 */
	protected void flipCellColor(Set<Move> sideEffects) {
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
	protected final Move[] getProjection(Move from, Move projectionVector) {
		List<Move> toReturn = new ArrayList<>();
		int currentX = from.x;
		int currentY = from.y;
		while ((0 <= currentX && currentX < xSize) && (0 <= currentY && currentY < ySize)) {
			toReturn.add(new Move(currentX, currentY));
			currentX += projectionVector.x;
			currentY += projectionVector.y;
		}
		return toReturn.toArray(new Move[0]);
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
	protected final Set<Move> getSideEffects(Move m, PlayerColor currentPlayer) {
		Set<Move> sideEffects = new HashSet<>();
		Move[][] projections = new Move[NUM_DIRECTIONS][];
		int counter = 0;
		for (int xStep = -1; xStep < 1; xStep++) {
			for (int yStep = -1; yStep < 1; yStep++) {
				projections[counter] = getProjection(m, new Move(xStep, yStep));
			}
		}
		for (Move[] projection : projections) {
			List<Move> potentialSideEffects = new ArrayList<>();
			for (Move move : projection) {
				if (getCellColor(move) == PlayerColor.EMPTY) {
					potentialSideEffects.clear();
					break;
				} else if (getCellColor(move) == currentPlayer) {
					break;
				}
				potentialSideEffects.add(move);
			}
			sideEffects.addAll(potentialSideEffects);
		}
		return sideEffects;
	}

}
