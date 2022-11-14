/**
 * @author scrooge
 */

package chaumette.othello.util.board.tree;

import chaumette.othello.util.ImprovedMove;
import chaumette.othello.util.board.improved.ImprovedOthelloBoard;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Contains an Othello Game tree
 */
public class OthelloTreeNode {
	private final ImprovedOthelloBoard currentState;
	private final TreeMap<ImprovedMove, OthelloTreeNode> possibleNextStates =
			new TreeMap<>(Comparator.nullsFirst(Comparator.naturalOrder()));

	public OthelloTreeNode(ImprovedOthelloBoard currentState) {
		this.currentState = currentState;
	}

	/**
	 * @return the currentState
	 */
	public ImprovedOthelloBoard getCurrentState() {
		return currentState;
	}

	/**
	 * @return the possibleNextStates
	 */
	public TreeMap<ImprovedMove, OthelloTreeNode> getPossibleNextStates() {
		return possibleNextStates;
	}

	@Override
	public String toString() {
		return currentState.toString();
	}
}
