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

import static chaumette.othello.util.Constants.BOARD_SIZE;
import static chaumette.othello.util.Constants.NUM_DIRECTIONS;

/**
 * Represents an othello board as a data structure
 */
public abstract class OthelloBoard {

	/**
	 * The current score of the white player
	 */
	protected int whiteScore = 2;

	/**
	 * The current score of the black player
	 */
	protected int blackScore = 2;

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
		if (isValidMove(m, c)) {
			setCell(m, c);
			switch (c) {
				case BLACK -> blackScore++;
				case WHITE -> whiteScore++;
			}
			flipColorOfCells(toFlip);
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
		return !getValidMoves(c).isEmpty();
	}

	/**
	 * @return all valid moves for the given player
	 */
	public Set<Move> getValidMoves(PlayerColor c) {
		Set<Move> validMoves = new HashSet<>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Move move = new Move(i, j);
				if (isValidMove(move, c)) {
					validMoves.add(move);
				}
			}
		}
		return validMoves;
	}

	/**
	 * @return if both players don't have any valid moves to do
	 */
	public boolean isGameOver() {
		return !canDoValidMove(PlayerColor.WHITE) && !canDoValidMove(PlayerColor.BLACK);
	}

	/**
	 * @return the winning player color, or EMPTY if it is a tie, or null, if the game is still running
	 */
	public PlayerColor getWinner() {
		if (isGameOver()) {
			if (whiteScore > blackScore) {
				return PlayerColor.WHITE;
			} else if (blackScore > whiteScore) {
				return PlayerColor.BLACK;
			} else {
				return PlayerColor.EMPTY;
			}
		} else {
			return null;
		}
	}

	/**
	 * Flips all cell colors at the given locations
	 */
	protected void flipColorOfCells(Set<Move> sideEffects) {
		for (Move move : sideEffects) {
			switch (getCellColor(move)) {
				case BLACK -> {
					setCell(move, PlayerColor.WHITE);
					blackScore--;
					whiteScore++;
				}
				case WHITE -> {
					setCell(move, PlayerColor.BLACK);
					whiteScore--;
					blackScore++;
				}
			}
		}

	}

	/**
	 * Returns all board cells from the given point outwards in the given direction
	 */
	public final Move[] getProjection(Move from, Move projectionVector) {
		List<Move> toReturn = new ArrayList<>();
		int currentX = from.x + projectionVector.x;
		int currentY = from.y + projectionVector.y;
		while ((0 <= currentX && currentX < BOARD_SIZE) && (0 <= currentY && currentY < BOARD_SIZE)) {
			toReturn.add(new Move(currentX, currentY));
			currentX += projectionVector.x;
			currentY += projectionVector.y;
		}
		return toReturn.toArray(new Move[0]);
	}

	/**
	 * Sets the value of the given board cell to the given Color (without checks)!
	 */
	protected abstract void setCell(Move m, PlayerColor c);

	/**
	 * Returns the color of a given cell
	 */
	public final PlayerColor getCellColor(Move m) {
		return getCellColor(m.x, m.y);
	}

	/**
	 * Returns the color of a given cell
	 */
	public abstract PlayerColor getCellColor(int x, int y);

	/**
	 * @return a List of all the side effect (aka flipping stones)
	 * a Move would have. If its size is 0, the move is invalid.
	 */
	protected final Set<Move> getSideEffects(Move m, PlayerColor currentPlayer) {
		Set<Move> sideEffects = new HashSet<>();
		Move[][] projections = new Move[NUM_DIRECTIONS][];
		int counter = 0;
		for (int xStep = -1; xStep <= 1; xStep++) {
			for (int yStep = -1; yStep <= 1; yStep++) {
				if (xStep == 0 && yStep == 0) {
					continue;
				}
				projections[counter] = getProjection(m, new Move(xStep, yStep));
				counter++;
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

	@Override
	public String toString() {
		StringBuilder representation = new StringBuilder();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				representation.append(getCellColor(i, j)).append("\t");
			}
			representation.append("\n");
		}
		return representation.toString();
	}
}
