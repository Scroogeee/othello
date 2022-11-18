/**
 * @author scrooge
 */

package chaumette.othello.util.players.ai;


import chaumette.othello.util.Constants;
import chaumette.othello.util.ImprovedMove;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.improved.ImprovedOthelloBoard;
import chaumette.othello.util.board.improved.ImprovedOthelloOneDimArrayBoard;
import chaumette.othello.util.board.tree.OthelloBoardTreeNode;
import szte.mi.Move;
import szte.mi.Player;

import java.util.Random;

/**
 * This AI always tries to maximise the score for the current player's turn,
 * simulating a few turns into the future
 */
public class MiniMaxAI implements Player {

	private OthelloBoardTreeNode rootNode;
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;
	private Random theRandom;
	private boolean isFirstTurn = true;
	public static final int search_depth = 2;

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
		initTree();
		isFirstTurn = true;
	}

	private void initTree() {
		ImprovedOthelloOneDimArrayBoard mentalBoardModel = new ImprovedOthelloOneDimArrayBoard();
		mentalBoardModel.resetAndInit();
		rootNode = new OthelloBoardTreeNode(mentalBoardModel, PlayerColor.BLACK);
		rootNode.populateMoveMap(MiniMaxAI.search_depth);
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		rootNode.populateMoveMap(1);

		if (prevMove != null) { //simulate Opponent Move
			ImprovedMove opponentMove = new ImprovedMove(prevMove, opponentPlayerColor);
			rootNode = rootNode.getNextState(opponentMove);
		} else if (myPlayerColor == PlayerColor.WHITE || !isFirstTurn) {
			//if we are BLACK (starting) and it's the first turn, ignore
			//else, simulate opponent passing the turn in our game tree
			rootNode = rootNode.getNextState(null);
		}

		rootNode.populateMoveMap(search_depth + 1);

		try {
			//Simulate AI thinking
			Thread.sleep(Constants.MOVE_DELAY);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		ImprovedMove toMake = miniMaxDecision();
		rootNode = rootNode.getNextState(toMake);

		if (isFirstTurn) { // if we are in first turn, set marker to false
			isFirstTurn = false;
		}

		return toMake;
	}

	private ImprovedMove miniMaxDecision() {
		ImprovedOthelloBoard currentBoard = rootNode.getBoard();
		ImprovedMove toMake = null;
		int score;
		int bestScore = Integer.MIN_VALUE;
		for (ImprovedMove m : currentBoard.getValidMoves(myPlayerColor)) {
			score = scoreByMiniMax(rootNode.getNextState(m), search_depth);
			if (score > bestScore) {
				toMake = m;
				bestScore = score;
			}
		}
		return toMake;
	}

	private int scoreState(ImprovedOthelloBoard boardState, PlayerColor currentPlayerColor) {
		//TODO implement other scoring function
		if (boardState != null) {
			return boardState.getValidMoves(currentPlayerColor).size();
		} else {
			return 0;
		}
	}

	private int scoreByMiniMax(OthelloBoardTreeNode gameState, int depth) {
		PlayerColor winner = gameState.getBoard().getWinner();
		if (winner != null) { //Terminal test (winning)
			if (winner == myPlayerColor) {
				return Integer.MAX_VALUE; //winning is good
			} else if (winner == opponentPlayerColor) {
				return Integer.MIN_VALUE; //losing is bad
			}
		}
		PlayerColor current = gameState.getCurrentPlayerColor();
		if (depth == 0) { //Terminal test (search depth)
			return scoreState(gameState.getBoard(), current);
		}
		if (depth % 2 != 0) {
			int bestScore = Integer.MIN_VALUE;
			for (ImprovedMove m : gameState.getBoard().getValidMoves(current)) {
				bestScore = Math.max(scoreByMiniMax(gameState.getNextState(m), depth - 1), bestScore);
			}
			return bestScore;
		} else {
			int worstScore = Integer.MAX_VALUE;
			for (ImprovedMove m : gameState.getBoard().getValidMoves(current)) {
				worstScore = Math.min(scoreByMiniMax(gameState.getNextState(m), depth - 1), worstScore);
			}
			return worstScore;
		}
	}
}
