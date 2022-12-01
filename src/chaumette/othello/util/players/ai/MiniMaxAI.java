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
 * This AI always tries to maximise the score for the current player's turn,
 * simulating a few turns into the future
 */
public class MiniMaxAI implements Player {

	private OthelloBoardTreeNode rootNode;
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;
	private Random theRandom;
	private boolean isFirstTurn = true;
	public static final int initalSearchDepth = 2;
	private final int searchDepth = initalSearchDepth;

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
		this.theRandom = rnd;
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
			//if we are BLACK (starting) and it's the first turn, ignore
			//else, simulate opponent passing the turn in our game tree
			rootNode = rootNode.getNextState(null);
			turnCounter++;
		}

		//simulate what I can do
		rootNode.populateMoveMap(searchDepth + 1);

		ImprovedMove toMake = miniMaxDecision();

		if (rootNode.getNextState(toMake) == null) {
			throw new RuntimeException("Next state null on move " + toMake + "\n" +
					rootNode.getBoard() + "\n" + " with player color " + myPlayerColor);
		}

		rootNode = rootNode.getNextState(toMake);
		turnCounter++;

		if (isFirstTurn) { // if we are in first turn, set marker to false
			isFirstTurn = false;
		}

		//simulate what my opponent can do
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

		ImprovedMove toMake = miniMaxDecision();

		if (rootNode.getNextState(toMake) == null) {
			throw new RuntimeException("Next state null on move " + toMake + "\n" +
					rootNode.getBoard() + "\n" + " with player color " + myPlayerColor);
		}

		rootNode = rootNode.getNextState(toMake);
		return toMake;
	}

	private ImprovedMove miniMaxDecision() {
		ImprovedOthelloBoard currentBoard = rootNode.getBoard();
		ImprovedMove toMake = null;
		int score;
		int bestScore = Integer.MIN_VALUE;
		HashSet<ImprovedMove> validMoves = currentBoard.getValidMoves(myPlayerColor);
		if (validMoves.isEmpty()) {
			System.out.println("Empty");
		}
		for (ImprovedMove m : validMoves) {
			OthelloBoardTreeNode newNode = rootNode.getNextState(m);
			score = scoreByMiniMax(newNode, searchDepth);
			if (score > bestScore) {
				toMake = m;
				bestScore = score;
			}
		}
		if (toMake == null) {
			System.out.println("To make is null");
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
		if (currentPlayerColor == myPlayerColor) {
			return boardState.getValidMoves(currentPlayerColor).size();
		} else {
			return -boardState.getValidMoves(currentPlayerColor).size();
		}
	}

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
}
