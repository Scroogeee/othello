/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.PlayerColor;

/**
 * The implementation of an othello board via a two-dimensional array
 */
public class OthelloTwoDimArrayBoard extends OthelloBoard {

	/**
	 * Contains the data of the board in a two-dimensional array
	 * Cell [x, y] is at array position [x][y]
	 * x and y are counted from 0
	 */
	private final PlayerColor[][] data;

	public OthelloTwoDimArrayBoard(int xSize, int ySize) {
		super(xSize, ySize);
		data = new PlayerColor[xSize][ySize];
	}

	@Override
	public void init() {
		data[xSize / 2][ySize / 2] = PlayerColor.WHITE;
		data[xSize / 2 + 1][ySize / 2 + 1] = PlayerColor.WHITE;
		data[xSize / 2 + 1][ySize / 2] = PlayerColor.BLACK;
		data[xSize / 2][ySize / 2 + 1] = PlayerColor.BLACK;
	}

	@Override
	public void doMove(Move m, PlayerColor c) {
		//TODO then check for winning condition
		if (isValidMove(m, c)) {
			data[m.x][m.y] = c;
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
		return data;
	}
}
