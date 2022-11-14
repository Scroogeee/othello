/**
 * @author scrooge
 */

package chaumette.othello.util.players.ai;


import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.util.Constants;
import chaumette.othello.util.ImprovedMove;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.improved.ImprovedOthelloBoard;
import chaumette.othello.util.board.improved.ImprovedOthelloOneDimArrayBoard;
import chaumette.othello.util.board.tree.OthelloTreeNode;

import java.util.Random;
import java.util.SortedSet;

/**
 * This AI always makes the move which leads to the least
 * possible moves by the opponent in its next turn
 */
public class GreedyLimitMoveAI implements Player {

	private OthelloTreeNode rootNode;
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;
	private Random theRandom;
	private boolean isFirstTurn = true;

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
		ImprovedOthelloOneDimArrayBoard mentalBoardModel = new ImprovedOthelloOneDimArrayBoard();
		mentalBoardModel.resetAndInit();
		rootNode = new OthelloTreeNode(mentalBoardModel);
		//build the boardModels for each possible move (of black)
		SortedSet<ImprovedMove> validMoves = rootNode.getCurrentState().getValidMoves(PlayerColor.BLACK);
		if (validMoves.isEmpty()) {
			validMoves.add(null);
		}
		for (ImprovedMove move : validMoves) {
			rootNode.getPossibleNextStates().put(move, new OthelloTreeNode(mentalBoardModel.simulate(move)));
		}
		//TODO grow initial tree (depth specified by constant)
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) { //simulate Opponent Move
			ImprovedMove opponentMove = new ImprovedMove(prevMove, opponentPlayerColor);
			rootNode = rootNode.getPossibleNextStates().get(opponentMove);
		} else if (myPlayerColor == PlayerColor.WHITE || !isFirstTurn) {
			//if we are BLACK (starting) and it's the first turn, ignore
			//else, simulate opponent passing the turn in our game tree
			rootNode = rootNode.getPossibleNextStates().get(null);
		}

		//calculate what we can do
		growTree(myPlayerColor);
		try {
			//Simulate AI thinking
			Thread.sleep(Constants.MOVE_DELAY);
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception");
			throw new RuntimeException(e);
		}

		ImprovedMove toMake = rootNode.getPossibleNextStates().keySet().iterator().next();
		int bestScore = Integer.MIN_VALUE;
		for (ImprovedMove move : rootNode.getPossibleNextStates().keySet()) {
			int tempScore = scoreState(rootNode.getPossibleNextStates().get(move).getCurrentState());
			if (tempScore > bestScore) {
				toMake = move;
				bestScore = tempScore;
			}
		}

		//update our mental model
		rootNode = rootNode.getPossibleNextStates().get(toMake);

		if (isFirstTurn) { // if we are in first turn, set marker to false
			isFirstTurn = false;
		}

		//calculate what the opponent can do
		growTree(opponentPlayerColor);
		return toMake;
	}

	private void growTree(PlayerColor color) {
		//TODO growTree by one layer!!
		SortedSet<ImprovedMove> validMoves = rootNode.getCurrentState().getValidMoves(color);
		if (validMoves.isEmpty()) {
			OthelloTreeNode nextState = new OthelloTreeNode(rootNode.getCurrentState());
			rootNode.getPossibleNextStates().put(null, nextState);
		} else {
			for (ImprovedMove move : validMoves) {
				ImprovedOthelloBoard stateAfterMove = rootNode.getCurrentState().simulate(move);
				OthelloTreeNode nextState = new OthelloTreeNode(stateAfterMove);
				rootNode.getPossibleNextStates().put(move, nextState);
			}
		}

	}

	private int scoreState(ImprovedOthelloBoard boardState) {
		//TODO implement better scoring function
		return -boardState.getValidMoves(opponentPlayerColor).size();
	}
}
