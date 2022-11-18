/**
 * @author scrooge
 */

package chaumette.othello.util.board.improved;

import chaumette.othello.util.ImprovedMove;
import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.PlayerColor;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import static chaumette.othello.util.Constants.BOARD_SIZE;
import static chaumette.othello.util.Constants.NUM_DIRECTIONS;
import static chaumette.othello.util.PlayerColor.BLACK;
import static chaumette.othello.util.PlayerColor.WHITE;

/**
 * Represents an othello board as a data structure
 */
public abstract class ImprovedOthelloBoard {

	/**
	 * The current score of the white player
	 */
	protected int whiteScore = 2;
	/**
	 * The current score of the black player
	 */
	protected int blackScore = 2;

	/**
	 * @return the whiteScore
	 */
	public final int getWhiteScore() {
		return whiteScore;
	}

	/**
	 * @return the blackScore
	 */
	public final int getBlackScore() {
		return blackScore;
	}

	/**
	 * Initializes the board with the white and black pieces at the center
	 */
	public abstract void resetAndInit();

	/**
	 * Tries to do a given move. (Doesn't check if it's player's turn!!)
	 * Throws an InvalidMoveException if the move is invalid.
	 *
	 * @param m the Move to do
	 */
	public final void doMove(ImprovedMove m) {
		SortedSet<ImprovedMove> toFlip = getSideEffects(m);
		if (isValidMove(m)) {
			setCell(m);
			switch (m.getMadeBy()) {
				case BLACK -> blackScore++;
				case WHITE -> whiteScore++;
			}
			flipColorOfCells(toFlip);
		} else {
			System.out.println("Move " + m.x + "/" + m.y + " for Player " + m.getMadeBy().ordinal() +
					" is invalid." + "\n" + "Board state is \n" + this);
			System.out.println("(Improved OthelloBoard)");
			throw new InvalidMoveException("Invalid move");
		}
	}

	/**
	 * Move is considered valid if the cell in question is empty,
	 * and placing the given color would cause at least one cell to flip.
	 *
	 * @param m the Move to check
	 * @return if the given move given the current player color would be valid
	 */
	public final boolean isValidMove(ImprovedMove m) {
		return getCellColor(m) == PlayerColor.EMPTY && !getSideEffects(m).isEmpty();
	}

	/**
	 * @param c the player color to check
	 * @return if the given player has any valid move
	 */
	public final boolean canDoValidMove(PlayerColor c) {
		return !getValidMoves(c).isEmpty();
	}

	/**
	 * @param c the current player color
	 * @return all valid moves for the given player
	 */
	public final SortedSet<ImprovedMove> getValidMoves(PlayerColor c) {
		SortedSet<ImprovedMove> validMoves = new TreeSet<>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				ImprovedMove move = new ImprovedMove(i, j, c);
				if (isValidMove(move)) {
					validMoves.add(move);
				}
			}
		}
		return validMoves;
	}

	/**
	 * @return if both players don't have any valid moves to do
	 */
	public final boolean isGameOver() {
		return !canDoValidMove(WHITE) && !canDoValidMove(BLACK);
	}

	/**
	 * @return the winning player color, or EMPTY if it is a tie, or null, if the game is still running
	 */
	public final PlayerColor getWinner() {
		if (isGameOver()) {
			if (whiteScore > blackScore) {
				return WHITE;
			} else if (blackScore > whiteScore) {
				return BLACK;
			} else {
				return PlayerColor.EMPTY;
			}
		} else {
			return null;
		}
	}

	/**
	 * Flips all cell colors at the given locations
	 *
	 * @param sideEffects a set of all cells which should be flipped
	 */
	protected final void flipColorOfCells(SortedSet<ImprovedMove> sideEffects) {
		for (ImprovedMove move : sideEffects) {
			switch (getCellColor(move)) {
				case BLACK -> {
					setCell(move);
					blackScore--;
					whiteScore++;
				}
				case WHITE -> {
					setCell(move);
					whiteScore--;
					blackScore++;
				}
			}
		}

	}

	/**
	 * @param from             starting point for the projections
	 * @param projectionVector direction in which to project
	 * @return all board cells from the given point (inclusive) outwards in the given direction
	 */
	public final ImprovedMove[] getProjection(ImprovedMove from, ImprovedMove projectionVector) {
		ArrayList<ImprovedMove> toReturn = new ArrayList<>();
		int currentX = from.x;
		int currentY = from.y;
		while ((0 <= currentX && currentX < BOARD_SIZE) && (0 <= currentY && currentY < BOARD_SIZE)) {
			toReturn.add(new ImprovedMove(currentX, currentY, projectionVector.getMadeBy()));
			currentX += projectionVector.x;
			currentY += projectionVector.y;
		}
		return toReturn.toArray(new ImprovedMove[0]);
	}

	/**
	 * Returns the color of a given cell
	 *
	 * @param m a Move object containing the coordinates of the cell
	 * @return the cell color of the given cell
	 */
	public final PlayerColor getCellColor(ImprovedMove m) {
		return getCellColor(m.x, m.y);
	}

	/**
	 * @param m the cell to set to the given color
	 */
	protected abstract void setCell(ImprovedMove m);

	/**
	 * Returns the color of a given cell
	 *
	 * @param x the x value of the cell (horizontal to the right counting from 0)
	 * @param y the y value of the cell (vertical downwards counting from 0)
	 * @return the cell color of the given cell
	 */
	public abstract PlayerColor getCellColor(int x, int y);

	/**
	 * Computes and returns a Set of all the side effect (aka flipping stones)
	 * a given Move would have for the given PlayerColor.
	 * If the size of this Set is 0, the move is invalid.
	 *
	 * @param m the move to check for side effects
	 * @return a Set of all the side effect (aka flipping stones)
	 * a Move would have. If its size is 0, the move is invalid.
	 */
	protected SortedSet<ImprovedMove> getSideEffects(ImprovedMove m) {
		SortedSet<ImprovedMove> sideEffects = new TreeSet<>();
		ImprovedMove[][] projections = new ImprovedMove[NUM_DIRECTIONS][];
		int counter = 0;
		for (int xStep = -1; xStep <= 1; xStep++) {
			for (int yStep = -1; yStep <= 1; yStep++) {
				if (xStep == 0 && yStep == 0) {
					continue;
				}
				projections[counter] = getProjection(m, new ImprovedMove(xStep, yStep, m.getMadeBy()));
				counter++;
			}
		}
		for (ImprovedMove[] projection : projections) {
			SortedSet<ImprovedMove> potentialSideEffects = new TreeSet<>();
			boolean isValidProjectionEnd = false;
			for (int i = 1; i < projection.length; i++) {
				if (getCellColor(projection[i]) == PlayerColor.EMPTY) {
					break;
				} else if (getCellColor(projection[i]) == m.getMadeBy()) {
					isValidProjectionEnd = true;
					break;
				}
				potentialSideEffects.add(projection[i]);
			}
			if (isValidProjectionEnd) {
				sideEffects.addAll(potentialSideEffects);
			}
		}
		return sideEffects;
	}

	@Override
	public String toString() {
		StringBuilder representation = new StringBuilder();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				representation.append(getCellColor(j, i).ordinal()).append("\t");
			}
			representation.append("\n");
		}
		representation.deleteCharAt(representation.lastIndexOf("\n"));
		return representation.toString();
	}

	/**
	 * Returns a copy of this board with the simulated move played, or null if move is invalid
	 * passing null returns the same board
	 */
	public abstract ImprovedOthelloBoard simulate(ImprovedMove move);
}
