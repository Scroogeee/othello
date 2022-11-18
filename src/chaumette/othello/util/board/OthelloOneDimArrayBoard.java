/**
 * @author scrooge
 */

package chaumette.othello.util.board;

import chaumette.othello.util.PlayerColor;
import szte.mi.Move;

import java.util.Arrays;

import static chaumette.othello.util.Constants.BOARD_SIZE;

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
	 * in the one-dimensional array implementation
	 */
	public OthelloOneDimArrayBoard() {
		super();
		data = new PlayerColor[BOARD_SIZE * BOARD_SIZE];
	}

	/**
	 * Constructs a new OthelloBoard with the given array as data.
	 * in the one-dimensional array implementation
	 */
	public OthelloOneDimArrayBoard(PlayerColor[] data) {
		super();
		this.data = new PlayerColor[data.length];
		System.arraycopy(data, 0, this.data, 0, data.length);
	}

	@Override
	public void resetAndInit() {
		Arrays.fill(data, PlayerColor.EMPTY);
		data[(BOARD_SIZE / 2 - 1) * BOARD_SIZE + BOARD_SIZE / 2 - 1] = PlayerColor.WHITE;
		data[(BOARD_SIZE / 2) * BOARD_SIZE + (BOARD_SIZE / 2)] = PlayerColor.WHITE;
		data[(BOARD_SIZE / 2) * BOARD_SIZE + BOARD_SIZE / 2 - 1] = PlayerColor.BLACK;
		data[(BOARD_SIZE / 2 - 1) * BOARD_SIZE + (BOARD_SIZE / 2)] = PlayerColor.BLACK;
	}

	@Override
	protected void setCell(Move m, PlayerColor c) {
		data[m.x * BOARD_SIZE + m.y] = c;
	}

	@Override
	public PlayerColor getCellColor(int x, int y) {
		return data[x * BOARD_SIZE + y];
	}

}
