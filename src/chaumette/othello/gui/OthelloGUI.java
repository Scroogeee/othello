/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.external.Move;
import chaumette.othello.util.OthelloGameAPI;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static chaumette.othello.util.Constants.BOARD_SIZE;

public class OthelloGUI extends OthelloUI {

	/**
	 * The text used to display helpful information to the player
	 */
	private Label helpText;

	/**
	 * A reference to the restart Button
	 */
	private Button restartButton;

	/**
	 * Contains the cells of the board
	 * Cell [x, y] is at array position [x * xSize + y]
	 */
	private Button[] buttonsGrid;

	public OthelloGUI(Stage primaryStage, OthelloGameAPI gameReference) {
		super(primaryStage, gameReference);
	}


	public void initUI() {
		BorderPane mainPane = new BorderPane();

		//build info row
		GridPane topGrid = createTopGrid();
		mainPane.setTop(topGrid);

		//build button grid (for the board cells)
		GridPane buttonsGrid = createGameGrid();
		mainPane.setCenter(buttonsGrid);

		theStage.setTitle("Othello");
		//theStage.setResizable(false);
		// put main scene into stage
		primaryScene = new Scene(mainPane);
		theStage.setScene(primaryScene);
		theStage.show();
	}

	@Override
	public void displayBoardState(OthelloBoard board) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				buttonsGrid[i * BOARD_SIZE + j].setText(board.getCellColor(i, j).toString());
			}
		}
	}

	@Override
	public void displayMessage(String s) {
		helpText.setText(s);
	}

	private GridPane createTopGrid() {
		GridPane topGrid = new GridPane();
		restartButton = new Button("Restart");
		restartButton.addEventHandler(ActionEvent.ACTION, event -> gameAPI.onRestart());
		helpText = new Label("Some help text");
		topGrid.add(restartButton, 0, 0);
		topGrid.add(helpText, 1, 0);
		return topGrid;
	}

	private GridPane createGameGrid() {
		GridPane gameGrid = new GridPane();
		buttonsGrid = new Button[BOARD_SIZE * BOARD_SIZE];
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				Move m = new Move(x, y);
				GameGridButton btn = new GameGridButton();
				btn.setPrefSize(100, 100);
				btn.addEventHandler(ActionEvent.ACTION, event -> gameAPI.requestMove(m, gameAPI.getCurrentPlayer()));
				gameGrid.add(btn, x, y);
				buttonsGrid[x * BOARD_SIZE + y] = btn;
			}
		}

		return gameGrid;
	}
}
