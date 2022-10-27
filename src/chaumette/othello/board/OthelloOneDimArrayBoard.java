/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.PlayerColor;

import java.util.Arrays;

/**
 * The implementation of an othello board via a one-dimensional array
 */
public class OthelloOneDimArrayBoard extends OthelloBoard {

	/**
	 * Contains the data of the board in a one-dimensional array
	 * Cell [x, y] is at array position [x * xSize + y]
	 * x and y are counted from 0
	 */
	private final PlayerColor[] data;

	/**
	 * Constructs a new OthelloBoard with specified x and y sizes
	 * onm the one-dimensional array implementation
	 */
	public OthelloOneDimArrayBoard(int xSize, int ySize) {
		super(xSize, ySize);
		data = new PlayerColor[xSize * ySize];
	}

	@Override
	public void init() {
		Arrays.fill(data, PlayerColor.EMPTY);
	}

	@Override
	public void doMove(Move m, PlayerColor c) {
		//TODO then check for winning condition
		if (isValidMove(m, c)) {
			data[m.x * xSize + m.y] = c;
			computeSideEffects(m, c);
		} else {
			throw new InvalidMoveException();
		}
	}

	@Override
	protected boolean isValidMove(Move m, PlayerColor c) {
		//TODO check for move validity
		return false;
	}

	@Override
	protected void computeSideEffects(Move m, PlayerColor c) {
		//TODO compute the side effects
	}

	@Override
	public PlayerColor[][] getBoardAsTwoDimArray() {
		PlayerColor[][] toReturn = new PlayerColor[xSize][ySize];
		for (int i = 0; i < xSize; i++) {
			if (ySize >= 0) {
				System.arraycopy(data, i * xSize, toReturn[i], 0, ySize);
			}
		}
		return toReturn;
	}
}
