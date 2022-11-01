/**
 * @author scrooge
 */

package chaumette.othello.util.players.human;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.gui.OthelloGUI;
import chaumette.othello.gui.OthelloUI;
import chaumette.othello.util.OthelloGameAPI;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class GUIPlayer extends Application implements Player, OthelloGameAPI {

	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;
	private Random theRandom;

	private OthelloUI othelloGUI;

	@Override
	public void start(Stage primaryStage) {
		othelloGUI = new OthelloGUI(primaryStage, this);
		othelloGUI.initUI();
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
		theRandom = rnd;
		mentalBoardModel.init();
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) {
			mentalBoardModel.doMove(prevMove, opponentPlayerColor);
		}

		ArrayList<Move> possibleMoves = new ArrayList<>(mentalBoardModel.getValidMoves(myPlayerColor));
		if (possibleMoves.size() == 0) {
			//TODO set text to pass the turn
			return null;
		}
		Move toMake = new Move(0, 0);
		//TODO actually get input from the user
		mentalBoardModel.doMove(toMake, myPlayerColor);
		return toMake;
	}


	@Override
	public void onQuit() {
		Platform.exit();
	}
}
