/**
 * @author scrooge
 */

package chaumette.othello.datastructures;

import chaumette.othello.external.Move;
import chaumette.othello.util.InvalidMoveException;

/**
 * The implementation of an othello board via a one-dimensional array
 */
public class OthelloOneDimArrayBoard extends OthelloBoard {

	/**
	 * Contains the data of the board in a one-dimensional array
	 * Cell [x, y] is at array position [x * xSize + y]
	 * x and y are counted from 0
	 */
	private final CellStatus[] data;

	/**
	 * Constructs a new OthelloBoard with specified x and y sizes
	 * onm the one-dimensional array implementation
	 */
	public OthelloOneDimArrayBoard(int xSize, int ySize) {
		super(xSize, ySize);
		data = new CellStatus[xSize * ySize];
	}

	@Override
	public void init() {

	}

	@Override
	public void doMove(Move m, CellStatus c) {
		//TODO then check for winning condition
		if (isValidMove(m, c)) {
			data[m.x * xSize + m.y] = c;
			computeSideEffects(m, c);
		} else {
			throw new InvalidMoveException();
		}
	}

	@Override
	protected boolean isValidMove(Move m, CellStatus c) {
		//TODO check for move validity
		return false;
	}

	@Override
	protected void computeSideEffects(Move m, CellStatus c) {
		//TODO compute the side effects
	}

	@Override
	public CellStatus[][] getBoardAsTwoDimArray() {
		CellStatus[][] toReturn = new CellStatus[xSize][ySize];
		for (int i = 0; i < xSize; i++) {
			if (ySize >= 0) {
				System.arraycopy(data, i * xSize, toReturn[i], 0, ySize);
			}
		}
		return toReturn;
	}
}
