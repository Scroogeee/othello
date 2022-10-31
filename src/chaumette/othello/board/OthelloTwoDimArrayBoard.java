/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;

import java.util.Arrays;

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
		for (PlayerColor[] datum : data) {
			Arrays.fill(datum, PlayerColor.EMPTY);
		}
		data[xSize / 2][ySize / 2] = PlayerColor.WHITE;
		data[xSize / 2 + 1][ySize / 2 + 1] = PlayerColor.WHITE;
		data[xSize / 2 + 1][ySize / 2] = PlayerColor.BLACK;
		data[xSize / 2][ySize / 2 + 1] = PlayerColor.BLACK;
	}

	@Override
	protected void setCell(Move m, PlayerColor c) {
		data[m.x][m.y] = c;
	}

	@Override
	protected PlayerColor getCellColor(int x, int y) {
		return data[x][y];
	}

	@Override
	public PlayerColor[][] getBoardAsTwoDimArray() {
		return data;
	}
}
