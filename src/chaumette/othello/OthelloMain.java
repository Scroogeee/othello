package chaumette.othello;

import chaumette.othello.datastructures.CellStatus;
import chaumette.othello.datastructures.OthelloBoard;
import chaumette.othello.external.Move;
import chaumette.othello.gui.OthelloGUI;
import chaumette.othello.util.OthelloGameAPI;
import javafx.application.Application;
import javafx.stage.Stage;

public class OthelloMain extends Application implements OthelloGameAPI {

	/**
	 * Stores the current plyer (starting with black)
	 */
	private final CellStatus currentPlayer = CellStatus.BLACK;

	/**
	 * Contains the game's data
	 */
	private OthelloBoard theBoard;

	/**
	 * Contains all code for graphics
	 */
	private OthelloGUI theGUI;

	public static void main(String[] args) {
		System.out.println("Hello othello!");
		OthelloMain.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

	}

	@Override
	public boolean requestMove(Move move, CellStatus c) {
		return false;
	}
}