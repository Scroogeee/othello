/**
 * @author scrooge
 */

package chaumette.othello.util.board;

import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.PlayerColor;
import szte.mi.Move;

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
	 * @return the whiteScore
	 */
	public final int getWhiteScore() {
		return whiteScore;
	}

	/**
	 * The current score of the white player
	 */
	protected int whiteScore = 2;

	/**
	 * @return the blackScore
	 */
	public final int getBlackScore() {
		return blackScore;
	}

	/**
	 * The current score of the black player
	 */
	protected int blackScore = 2;

	/**
	 * Initializes the board with the white and black pieces at the center
	 */
	public abstract void resetAndInit();

	/**
	 * Tries to do a given move. (Doesn't check if it's player's turn!!)
	 * Throws an InvalidMoveException if the move is invalid.
	 *
	 * @param m the Move to do
	 * @param c the color of the current player
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
			System.out.println("Move " + m.x + "/" + m.y + " for Player " + c.ordinal() + " is invalid." + "\n" + "Board state is \n" + this);
			System.out.println("(Normal OthelloBoard)");
			throw new InvalidMoveException("Invalid move");
		}
	}

	/**
	 * Move is considered valid if the cell in question is empty,
	 * and placing the given color would cause at least one cell to flip.
	 *
	 * @param m the Move to check
	 * @param c the color of the current player
	 * @return if the given move given the current player color would be valid
	 */
	public final boolean isValidMove(Move m, PlayerColor c) {
		return getCellColor(m) == PlayerColor.EMPTY && !getSideEffects(m, c).isEmpty();
	}

	/**
	 * @param c the player color to check
	 * @return if the given player has any valid move
	 */
	public final boolean canDoValidMove(PlayerColor c) {
		return !getValidMoves(c).isEmpty();
	}

	/**
	 * If no moves are valid, returns an empty Set
	 *
	 * @param c the current player color
	 * @return all valid moves for the given player
	 */
	public final Set<Move> getValidMoves(PlayerColor c) {
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
	public final boolean isGameOver() {
		return !canDoValidMove(PlayerColor.WHITE) && !canDoValidMove(PlayerColor.BLACK);
	}

	/**
	 * @return the winning player color, or EMPTY if it is a tie, or null, if the game is still running
	 */
	public final PlayerColor getWinner() {
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
	 *
	 * @param sideEffects a set of all cells which should be flipped
	 */
	protected final void flipColorOfCells(Set<Move> sideEffects) {
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
	 * @param from             starting point for the projections
	 * @param projectionVector direction in which to project
	 * @return all board cells from the given point (inclusive) outwards in the given direction
	 */
	public final Move[] getProjection(Move from, Move projectionVector) {
		List<Move> toReturn = new ArrayList<>();
		int currentX = from.x;
		int currentY = from.y;
		while ((0 <= currentX && currentX < BOARD_SIZE) && (0 <= currentY && currentY < BOARD_SIZE)) {
			toReturn.add(new Move(currentX, currentY));
			currentX += projectionVector.x;
			currentY += projectionVector.y;
		}
		return toReturn.toArray(new Move[0]);
	}

	/**
	 * @param m the cell to set to the given color
	 * @param c the color to set the given cell to
	 *          <p>
	 *          Sets the value of the given board cell to the given Color (without checks)!
	 */
	protected abstract void setCell(Move m, PlayerColor c);

	/**
	 * Returns the color of a given cell
	 *
	 * @param m a Move object containing the coordinates of the cell
	 * @return the cell color of the given cell
	 */
	public final PlayerColor getCellColor(Move m) {
		return getCellColor(m.x, m.y);
	}

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
	 * @param currentPlayer the color of the current player
	 * @param m             the move to check for side effects
	 * @return a Set of all the side effect (aka flipping stones)
	 * a Move would have. If its size is 0, the move is invalid.
	 */
	protected Set<Move> getSideEffects(Move m, PlayerColor currentPlayer) {
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
			boolean validProjectionEnd = false;
			for (int i = 1; i < projection.length; i++) {
				if (getCellColor(projection[i]) == PlayerColor.EMPTY) {
					break;
				} else if (getCellColor(projection[i]) == currentPlayer) {
					validProjectionEnd = true;
					break;
				}
				potentialSideEffects.add(projection[i]);
			}
			if (validProjectionEnd) {
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
}
