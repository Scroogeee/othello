/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.util.OthelloGameAPI;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class OthelloUI {
	protected final OthelloGameAPI gameAPI;

	protected final Stage theStage;

	protected Scene primaryScene;

	public OthelloUI(Stage primaryStage, OthelloGameAPI gameReference) {
		theStage = primaryStage;
		gameAPI = gameReference;
	}

	public abstract void initUI();

	public abstract void displayBoardState(OthelloBoard board);
}
