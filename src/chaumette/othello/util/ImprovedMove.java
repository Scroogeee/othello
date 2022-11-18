/**
 * @author scrooge
 */

package chaumette.othello.util;

import szte.mi.Move;

import java.util.Objects;

public class ImprovedMove extends Move {

	private final PlayerColor madeBy;

	/**
	 * Constructs the move. It does not perform any checks on the values.
	 *
	 * @param x X coordinate on the board. It is indexed from 0, that is,
	 *          its possible vales are 0, 1, etc.
	 * @param y Y coordinate on the board. It is indexed from 0, that is,
	 *          its possible vales are 0, 1, etc.
	 */
	public ImprovedMove(int x, int y, PlayerColor c) {
		super(x, y);
		madeBy = c;
	}

	public ImprovedMove(Move prevMove, PlayerColor c) {
		super(prevMove.x, prevMove.y);
		madeBy = c;
	}

	/**
	 * @return the madeBy
	 */
	public PlayerColor getMadeBy() {
		return madeBy;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Move) {
			Move m = (Move) obj;
			return m.x == x && m.y == y;
		}
		return false;
	}

	@Override
	public String toString() {
		return "[x:" + x + ", y:" + y + ", p:" + madeBy + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, madeBy);
	}
}
