/**
 * @author scrooge
 */

package chaumette.othello.util.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;

import java.util.Arrays;

import static chaumette.othello.util.Constants.BOARD_SIZE;

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

	public OthelloTwoDimArrayBoard() {
		super();
		data = new PlayerColor[BOARD_SIZE][BOARD_SIZE];
	}

	@Override
	public void init() {
		for (PlayerColor[] datum : data) {
			Arrays.fill(datum, PlayerColor.EMPTY);
		}
		data[BOARD_SIZE / 2 - 1][BOARD_SIZE / 2 - 1] = PlayerColor.WHITE;
		data[BOARD_SIZE / 2][BOARD_SIZE / 2] = PlayerColor.WHITE;
		data[BOARD_SIZE / 2][BOARD_SIZE / 2 - 1] = PlayerColor.BLACK;
		data[BOARD_SIZE / 2 - 1][BOARD_SIZE / 2] = PlayerColor.BLACK;
	}

	@Override
	protected void setCell(Move m, PlayerColor c) {
		data[m.x][m.y] = c;
	}

	@Override
	public PlayerColor getCellColor(int x, int y) {
		return data[x][y];
	}

}
