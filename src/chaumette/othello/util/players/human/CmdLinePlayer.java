/**
 * @author scrooge
 */

package chaumette.othello.util.players.human;

import chaumette.othello.gui.OthelloCmdLineUI;
import chaumette.othello.util.Constants;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;
import szte.mi.Move;
import szte.mi.Player;

import java.util.Random;

/**
 * Implementation of a Player for a human using the command line
 */
public class CmdLinePlayer implements Player {

	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;

	private final OthelloCmdLineUI cmdLineUI = new OthelloCmdLineUI();

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
		mentalBoardModel.resetAndInit();
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) {
			cmdLineUI.displayMessage("Player " + opponentPlayerColor.ordinal() + " makes move: " + Constants.moveToString(prevMove));
			mentalBoardModel.doMove(prevMove, opponentPlayerColor);
		}
		cmdLineUI.displayMessage("Current board state:");
		cmdLineUI.displayBoardState(mentalBoardModel);
		Move move = null;
		boolean isValidMove = false;
		while (!isValidMove) {
			cmdLineUI.displayMessage("Player " + myPlayerColor.ordinal() + ", please make a move!");
			move = cmdLineUI.askUserForMove(myPlayerColor);
			if (move != null) {
				isValidMove = mentalBoardModel.isValidMove(move, myPlayerColor);
			}
			if (!isValidMove) {
				cmdLineUI.displayMessage("Invalid move, please try again!");
				cmdLineUI.displayValidMoves(mentalBoardModel.getValidMoves(myPlayerColor), myPlayerColor);
			}
		}
		mentalBoardModel.doMove(move, myPlayerColor);
		cmdLineUI.displayBoardState(mentalBoardModel);
		return move;
	}

}
