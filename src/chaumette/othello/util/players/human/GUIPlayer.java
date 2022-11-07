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

/**
 * Implementation of a Player for a human via a graphical user interface using JavaFX
 */
public class GUIPlayer implements Player {

	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();
	private final OthelloGUI othelloGUI;
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;

	/**
	 * Creates a new GUIPlayer storing a reference to the given GUI
	 *
	 * @param theGUI the OthelloGUI to use for this GUIPlayer
	 */
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
		if (!mentalBoardModel.getValidMoves(myPlayerColor).isEmpty()) {
			while (toReturn == null && !Thread.currentThread().isInterrupted()) {
				toReturn = othelloGUI.askUserForMove(myPlayerColor);
				if (!mentalBoardModel.isValidMove(toReturn, myPlayerColor)) {
					toReturn = null;
				}
			}
			mentalBoardModel.doMove(toReturn, myPlayerColor);
		} else {
			othelloGUI.displayMessage("No possible move, skipping!");
		}
		return toReturn;
	}
}
