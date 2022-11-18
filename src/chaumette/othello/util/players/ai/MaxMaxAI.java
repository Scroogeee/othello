/**
 * @author scrooge
 */

package chaumette.othello.util.players.ai;


import szte.mi.Move;
import szte.mi.Player;
import chaumette.othello.util.Constants;
import chaumette.othello.util.ImprovedMove;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.improved.ImprovedOthelloBoard;
import chaumette.othello.util.board.improved.ImprovedOthelloOneDimArrayBoard;
import chaumette.othello.util.board.tree.OthelloBoardTreeNode;

import java.util.Random;

/**
 * This AI always tries to maximise the score for the current player's turn,
 * simulating a few turns into the future
 */
public class MaxMaxAI implements Player {

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
		rootNode.populateMoveMap(MaxMaxAI.search_depth);
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
		rootNode.populateMoveMap(search_depth);

		try {
			//Simulate AI thinking
			Thread.sleep(Constants.MOVE_DELAY);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		//TODO apply maxmax on the tree
		//TODO simulate the move

		if (isFirstTurn) { // if we are in first turn, set marker to false
			isFirstTurn = false;
		}
		//TODO return the move
	}

	private int scoreState(ImprovedOthelloBoard boardState, PlayerColor currentPlayerColor) {
		//TODO implement other scoring function
		if (boardState != null) {
			return boardState.getValidMoves(currentPlayerColor).size();
		} else {
			return 0;
		}
	}

	private int scoreByMaxMax(OthelloBoardTreeNode gameState) {
		PlayerColor winner = gameState.getBoard().getWinner();
		if (winner != null) {
			if (winner == myPlayerColor) {
				return Integer.MAX_VALUE; //winning is good
			} else if (winner == opponentPlayerColor) {
				return Integer.MIN_VALUE; //losing is bad
			}
		}
		for (ImprovedMove m : gameState.getBoard().getValidMoves(gameState.getCurrentPlayerColor())) {
			//TODO actually score by MaxMax
			//if scoreState of the state after the move is better
			//save the new score for returning
		}

	}
}
