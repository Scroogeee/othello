/**
 * @author scrooge
 */

package chaumette.othello.board;

import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;

import java.util.Arrays;
import java.util.List;

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
		data[(xSize / 2 - 1) * xSize + ySize / 2 - 1] = PlayerColor.WHITE;
		data[(xSize / 2) * xSize + (ySize / 2)] = PlayerColor.WHITE;
		data[(xSize / 2) * xSize + ySize / 2 - 1] = PlayerColor.BLACK;
		data[(xSize / 2 - 1) * xSize + (ySize / 2)] = PlayerColor.BLACK;
	}

	@Override
	protected List<Move> getSideEffects(Move m, PlayerColor c) {
		//TODO compute the side effects
		return null;
	}

	@Override
	protected void setCell(Move m, PlayerColor c) {
		data[m.x * xSize + m.y] = c;
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
