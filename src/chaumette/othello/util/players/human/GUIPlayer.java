/**
 * @author scrooge
 */

package chaumette.othello.util.players.human;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.gui.OthelloGUI;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;

import java.util.Random;

public class GUIPlayer implements Player {

	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();
	private final OthelloGUI othelloGUI;
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;

	public GUIPlayer(OthelloGUI theGUI) {
		this.othelloGUI = theGUI;
	}

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
		mentalBoardModel.init();
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) {
			mentalBoardModel.doMove(prevMove, opponentPlayerColor);
		}
		Move toReturn = null;
		while (toReturn == null) {
			toReturn = othelloGUI.askUserForMove();
			if (!mentalBoardModel.isValidMove(toReturn, myPlayerColor)) {
				toReturn = null;
			}
		}
		return toReturn;
	}
}
