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

	/**
	 * Which player's turn it is with the given board state
	 */
	private final PlayerColor currentPlayerColor;
	private final ImprovedOthelloBoard currentBoard;
	private final HashMap<ImprovedMove, OthelloBoardTreeNode> possibleNextStates = new HashMap<>();

	/**
	 * @param currentPlayerColor Which player's turn it is with the given board state
	 */
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
			//terminal case
			return;
		}
		PlayerColor nextPlayerColor = PlayerColor.invert(currentPlayerColor);

		//for each possible valid move
		for (ImprovedMove move : currentBoard.getValidMoves(currentPlayerColor)) {
			if (!possibleNextStates.containsKey(move)) { // if we don't already know the board state after that move
				// create a new given board state after that move
				OthelloBoardTreeNode node = new OthelloBoardTreeNode(currentBoard.simulate(move), nextPlayerColor);
				// and add the mapping to possibleNextStates
				possibleNextStates.put(move, node);
				//populate this node
				node.populateMoveMap(searchDepth - 1);
			} else {
				// if we know the board state continue there
				possibleNextStates.get(move).populateMoveMap(searchDepth - 1);
			}
		}

		if (possibleNextStates.isEmpty()) {
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
