package chaumette.othello;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.board.OthelloOneDimArrayBoard;
import chaumette.othello.external.Move;
import chaumette.othello.gui.OthelloCmdLineUI;
import chaumette.othello.util.Constants;
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
		theGUI = new OthelloCmdLineUI(theStage, this);
		theGUI.initUI();
		theBoard = new OthelloOneDimArrayBoard(Constants.BOARD_SIZE, Constants.BOARD_SIZE);
		theBoard.init();
		theGUI.displayBoardState(theBoard);
	}

	@Override
	public boolean requestMove(Move move, PlayerColor c) {
		//TODO implement
		return false;
	}

	@Override
	public void onRestart() {
		//TODO implement restart

	}

	@Override
	public PlayerColor getCurrentPlayer() {
		//TODO implement
		return PlayerColor.EMPTY;
	}

	@Override
	public void onQuit() {
		Platform.exit();
	}
}