/**
 * @author scrooge
 */

package chaumette.othello.datastructures;

import chaumette.othello.external.Move;
import chaumette.othello.util.InvalidMoveException;

/**
 * The implementation of an othello board via a two-dimensional array
 */
public class OthelloTwoDimArrayBoard extends OthelloBoard {

	/**
	 * Contains the data of the board in a two-dimensional array
	 * Cell [x, y] is at array position [x][y]
	 * x and y are counted from 0
	 */
	private final CellStatus[][] data;

	public OthelloTwoDimArrayBoard(int xSize, int ySize) {
		super(xSize, ySize);
		data = new CellStatus[xSize][ySize];
	}

	@Override
	public void init() {
		data[xSize / 2][ySize / 2] = CellStatus.WHITE;
		data[xSize / 2 + 1][ySize / 2 + 1] = CellStatus.WHITE;
		data[xSize / 2 + 1][ySize / 2] = CellStatus.BLACK;
		data[xSize / 2][ySize / 2 + 1] = CellStatus.BLACK;
	}

	@Override
	public void doMove(Move m, CellStatus c) {
		//TODO then check for winning condition
		if (isValidMove(m, c)) {
			data[m.x][m.y] = c;
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
		return data;
	}
}
