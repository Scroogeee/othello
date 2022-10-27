package chaumette.othello;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.external.Move;
import chaumette.othello.gui.OthelloCmdLineGUI;
import chaumette.othello.util.OthelloGameAPI;
import chaumette.othello.util.PlayerColor;
import javafx.application.Application;
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
	private OthelloCmdLineGUI theGUI;

	public static void main(String[] args) {
		System.out.println("Hello othello!");
		OthelloMain.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		theGUI = new OthelloCmdLineGUI(primaryStage, this);
		theGUI.initUI();
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
}