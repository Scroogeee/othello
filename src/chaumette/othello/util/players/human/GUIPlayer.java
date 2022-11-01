/**
 * @author scrooge
 */

package chaumette.othello.util.players.human;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Random;

public class GUIPlayer extends Application implements Player {

	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();

	@Override
	public void init(int order, long t, Random rnd) {

	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		return null;
	}

	@Override
	public void start(Stage primaryStage) {

	}
}
