/**
 * @author scrooge
 */

package chaumette.othello.util.board.tree;

import chaumette.othello.util.ImprovedMove;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.improved.ImprovedOthelloBoard;

import java.util.HashMap;

/**
 * Contains an Othello Game tree node
 */
public class OthelloBoardTreeNode {

	private final PlayerColor currentPlayerColor;
	private final ImprovedOthelloBoard currentBoard;
	private final HashMap<ImprovedMove, OthelloBoardTreeNode> possibleNextStates = new HashMap<>();

	public OthelloBoardTreeNode(ImprovedOthelloBoard currentBoard, PlayerColor currentPlayerColor) {
		this.currentBoard = currentBoard;
		this.currentPlayerColor = currentPlayerColor;
	}

	/**
	 * @return Whose turn is it?
	 */
	public PlayerColor getCurrentPlayerColor() {
		return currentPlayerColor;
	}

	/**
	 * @return the currentState
	 */
	public ImprovedOthelloBoard getBoard() {
		return currentBoard;
	}

	/**
	 * @return the possibleNextStates
	 */
	public OthelloBoardTreeNode getNextState(ImprovedMove m) {
		return possibleNextStates.get(m);
	}

	@Override
	public String toString() {
		return currentBoard.toString();
	}

	public void populateMoveMap(int searchDepth) {
		if (searchDepth == 0) {
			return;
		}
		PlayerColor nextPlayerColor = PlayerColor.invert(currentPlayerColor);
		for (ImprovedMove move : currentBoard.getValidMoves(currentPlayerColor)) {
			if (!possibleNextStates.containsKey(move)) { // if we don't already know the board state
				OthelloBoardTreeNode node = new OthelloBoardTreeNode(currentBoard.simulate(move), nextPlayerColor);
				possibleNextStates.put(move, node);
				node.populateMoveMap(searchDepth - 1);
			} else {
				// if we know the board state continue there
				possibleNextStates.get(move).populateMoveMap(searchDepth - 1);
			}
		}
		if (currentBoard.getValidMoves(currentPlayerColor).isEmpty()) {
			if (!possibleNextStates.containsKey(null)) { // if we don't already know the board state
				OthelloBoardTreeNode node = new OthelloBoardTreeNode(currentBoard, nextPlayerColor);
				possibleNextStates.put(null, node);
				node.populateMoveMap(searchDepth - 1);
			} else {
				// if we know the board state continue there
				possibleNextStates.get(null).populateMoveMap(searchDepth - 1);
			}
		}
	}
}
