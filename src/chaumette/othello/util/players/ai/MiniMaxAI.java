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
import java.util.List;
import java.util.Random;

/**
 * This AI always tries to maximise the score for the current player's turn,
 * simulating a few turns into the future
 */
public class MiniMaxAI implements Player {
	public static final int cornerScoreModifier = 50;
	private static final HashSet<ImprovedMove> cornerPositions = new HashSet<>(
			List.of(new ImprovedMove(0, 0, PlayerColor.EMPTY),
					new ImprovedMove(0, 7, PlayerColor.EMPTY),
					new ImprovedMove(7, 0, PlayerColor.EMPTY),
					new ImprovedMove(7, 7, PlayerColor.EMPTY))
	);

	private OthelloBoardTreeNode rootNode;
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;
	private boolean isFirstTurn = true;
	public static final int initalSearchDepth = 2;
	private int searchDepth = initalSearchDepth;

	private int turnCounter = 0;

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
		isFirstTurn = true;
		initTree();
	}

	private void initTree() {
		ImprovedOthelloOneDimArrayBoard mentalBoardModel = new ImprovedOthelloOneDimArrayBoard();
		mentalBoardModel.resetAndInit();
		rootNode = new OthelloBoardTreeNode(mentalBoardModel, PlayerColor.BLACK);
		rootNode.populateMoveMap(searchDepth);
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) { //simulate Opponent Move
			ImprovedMove opponentMove = new ImprovedMove(prevMove, opponentPlayerColor);
			rootNode = rootNode.getNextState(opponentMove);
			turnCounter++;
		} else if (myPlayerColor == PlayerColor.WHITE || !isFirstTurn) {
			// if we are BLACK (starting) and it's the first turn, ignore
			// else, simulate opponent passing the turn in our game tree
			rootNode = rootNode.getNextState(null);
			turnCounter++;
		}
		ImprovedMove toMake;
		if (t < tOpponent && t < 500) { //not enough time limits search depth
			searchDepth = 1;
		}
		rootNode.populateMoveMap(searchDepth + 1);
		toMake = miniMaxDecision(searchDepth);
		// simulate what I can do

		rootNode = rootNode.getNextState(toMake);
		turnCounter++;

		if (isFirstTurn) { // if we are in first turn, set marker to false
			isFirstTurn = false;
		}

		// simulate what my opponent can do
		rootNode.populateMoveMap(1);
		return toMake;
	}

	/**
	 * Test method for debugging
	 */
	public Move debugNextMove(ImprovedOthelloBoard previousBoard, PlayerColor myPlayerColor) {
		this.myPlayerColor = myPlayerColor;
		this.opponentPlayerColor = PlayerColor.invert(myPlayerColor);
		rootNode = new OthelloBoardTreeNode(previousBoard, myPlayerColor);

		rootNode.populateMoveMap(searchDepth);
		ImprovedMove toMake = miniMaxDecision(searchDepth);

		if (rootNode.getNextState(toMake) == null) {
			throw new RuntimeException("Next state null on move " + toMake + "\n" +
					rootNode.getBoard() + "\n" + " with player color " + myPlayerColor);
		}

		rootNode = rootNode.getNextState(toMake);
		return toMake;
	}

	private ImprovedMove miniMaxDecision(int initalSearchDepth) {
		ImprovedOthelloBoard currentBoard = rootNode.getBoard();
		ImprovedMove toMake = null;
		int score;
		int bestScore = Integer.MIN_VALUE;
		HashSet<ImprovedMove> validMoves = currentBoard.getValidMoves(myPlayerColor);
		for (ImprovedMove m : validMoves) {
			OthelloBoardTreeNode newNode = rootNode.getNextState(m);
			score = scoreByMiniMax(newNode, initalSearchDepth);
			if (cornerPositions.contains(new ImprovedMove(m, PlayerColor.EMPTY))) {
				score += cornerScoreModifier;
			}
			if (score > bestScore) {
				toMake = m;
				bestScore = score;
			}
		}
		return toMake;
	}

	/**
	 * Scoring Function
	 * reflects how good a board state is for my player color
	 */
	private int scoreState(ImprovedOthelloBoard boardState, PlayerColor currentPlayerColor) {
		PlayerColor winner = boardState.getWinner();
		//Terminal test (winning)
		if (winner == myPlayerColor) {
			return Integer.MAX_VALUE; //winning is good
		} else if (winner == opponentPlayerColor) {
			return Integer.MIN_VALUE + 2; //losing is bad (+1 because not moving is worse)
		}
		//scoring
		int score;
		if (currentPlayerColor == myPlayerColor) {
			score = boardState.getValidMoves(currentPlayerColor).size();
		} else {
			score = -boardState.getValidMoves(currentPlayerColor).size();
		}
		return score;
	}

	/**
	 * Recursively scores the current (possibly simulated) gameState for my player color
	 */
	private int scoreByMiniMax(OthelloBoardTreeNode gameState, int depth) {
		ImprovedOthelloBoard board = gameState.getBoard();
		PlayerColor current = gameState.getCurrentPlayerColor();
		HashSet<ImprovedMove> validMoves = board.getValidMoves(current);
		if (depth == 0) { //Terminal test (search depth)
			return scoreState(board, current);
		}
		if (gameState.getCurrentPlayerColor() == myPlayerColor) {
			int bestScore = Integer.MIN_VALUE + 1;
			for (ImprovedMove m : validMoves) {
				bestScore = Math.max(scoreByMiniMax(gameState.getNextState(m), depth - 1), bestScore);
			}
			return bestScore;
		} else {
			int worstScore = Integer.MAX_VALUE;
			for (ImprovedMove m : validMoves) {
				worstScore = Math.min(scoreByMiniMax(gameState.getNextState(m), depth - 1), worstScore);
			}
			return worstScore;
		}
	}

	/**
	 * Should return how good the corner situation is for my player color
	 */
	private int computeFieldScoreModifier(ImprovedOthelloBoard board) {
		int toReturn = 0;
		PlayerColor c;
		//TODO implement scoring of fields next to corners
		for (ImprovedMove m : cornerPositions) {
			c = board.getCellColor(m);
			if (c == myPlayerColor) {
				toReturn += 1;
			} else if (c == opponentPlayerColor) {
				toReturn -= 1;
			}
		}
		return toReturn;
	}
}
