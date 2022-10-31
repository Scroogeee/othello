package chaumette.othello;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.board.OthelloOneDimArrayBoard;
import chaumette.othello.external.Move;
import chaumette.othello.gui.OthelloCmdLineUI;
import chaumette.othello.util.Constants;
import chaumette.othello.util.InvalidMoveException;
import chaumette.othello.util.OthelloGameAPI;
import chaumette.othello.util.PlayerColor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class OthelloMain extends Application implements OthelloGameAPI {

	/**
	 * Stores the current plyer (starting with black)
	 */
	private final PlayerColor currentPlayer = PlayerColor.BLACK;

	/**
	 * Contains the game's data
	 */
	private OthelloBoard theBoard;

	/**
	 * Contains all code for graphics
	 */
	private OthelloCmdLineUI theGUI;

	/**
	 * The stage of this application
	 */
	private Stage theStage;

	public static void main(String[] args) {
		System.out.println("Hello othello!");
		OthelloMain.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		theStage = primaryStage;
		//TODO can be changed to real UI
		theGUI = new OthelloCmdLineUI(theStage, this);
		theGUI.initUI();
		//TODO can be changed to other board implementation
		theBoard = new OthelloOneDimArrayBoard(Constants.BOARD_SIZE, Constants.BOARD_SIZE);
		theBoard.init();
		theGUI.displayBoardState(theBoard);
	}

	@Override
	public boolean requestMove(Move move, PlayerColor c) {
		//TODO add checks if it's player's turn
		try {
			theBoard.doMove(move, c);
		} catch (InvalidMoveException invalidMoveException) {
			theGUI.displayMessage("Invalid Move: " + move + " with color " + c);
			return false;
		}
		theGUI.displayBoardState(theBoard);
		return true;
	}

	@Override
	public void onRestart() {
		//TODO implement restart
	}

	@Override
	public PlayerColor getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public void onQuit() {
		Platform.exit();
	}
}