/**
 * @author scrooge
 */

package chaumette.othello.util.players.ai;


import chaumette.othello.util.ImprovedMove;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.improved.ImprovedOthelloBoard;
import chaumette.othello.util.board.improved.ImprovedOthelloOneDimArrayBoard;
import chaumette.othello.util.board.tree.OthelloBoardTreeNode;
import szte.mi.Move;
import szte.mi.Player;

import java.util.HashSet;
import java.util.Random;

/**
 * This AI always makes the move which leads to the least
 * possible moves by the opponent in its next turn
 */
public class GreedyLimitMoveAI implements Player {

	private OthelloBoardTreeNode rootNode;
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;
	private Random theRandom;
	private boolean isFirstTurn;

	@Override
	public void init(int order, long t, Random rnd) {
		switch (order) {
			case 0 -> {
				myPlayerColor = PlayerColor.BLACK;
				opponentPlayerColor = PlayerColor.WHITE;
			}
			case 1 -> {
				myPlayerColor = PlayerColor.WHITE;
				opponentPlayerColor = PlayerColor.BLACK;
			}
		}
		this.theRandom = rnd;
		isFirstTurn = true;
		ImprovedOthelloOneDimArrayBoard mentalBoardModel = new ImprovedOthelloOneDimArrayBoard();
		mentalBoardModel.resetAndInit();
		rootNode = new OthelloBoardTreeNode(mentalBoardModel, PlayerColor.BLACK);
		rootNode.populateMoveMap(1);
		//TODO grow initial tree (depth specified by constant)
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) { //simulate Opponent Move
			ImprovedMove opponentMove = new ImprovedMove(prevMove, opponentPlayerColor);
			rootNode = rootNode.getNextState(opponentMove);
		} else if (myPlayerColor == PlayerColor.WHITE || !isFirstTurn) {
			//if we are BLACK (starting) and it's the first turn, ignore
			//else, simulate opponent passing the turn in our game tree
			rootNode = rootNode.getNextState(null);
		}
		//calculate what we can do
		rootNode.populateMoveMap(1);


		ImprovedMove toMake = makeGreedyDecision();

		//update our mental model
		rootNode = rootNode.getNextState(toMake);

		if (isFirstTurn) { // if we are in first turn, set marker to false
			isFirstTurn = false;
		}
		//calculate what the opponent can do
		rootNode.populateMoveMap(1);

		return toMake;


	}

	private ImprovedMove makeGreedyDecision() {
		HashSet<ImprovedMove> validMoves = rootNode.getBoard().getValidMoves(myPlayerColor);

		ImprovedMove toMake = null;
		int tempScore;
		int bestScore = Integer.MIN_VALUE;
		for (ImprovedMove move : validMoves) {
			tempScore = scoreMyState(rootNode.getNextState(move).getBoard());
			if (tempScore > bestScore) {
				toMake = move;
				bestScore = tempScore;
			}
		}
		return toMake;
	}

	private int scoreMyState(ImprovedOthelloBoard boardState) {
		//TODO implement better scoring function
		if (boardState != null) {
			return boardState.getValidMoves(myPlayerColor).size();
		} else {
			return 0;
		}
	}
}
