/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.external.Move;
import chaumette.othello.util.OthelloGameAPI;
import chaumette.othello.util.board.OthelloBoard;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static chaumette.othello.util.Constants.BOARD_SIZE;

public class OthelloGUI extends OthelloUI {

	private final Stage theStage;

	private Scene primaryScene;
	/**
	 * Contains the cells of the board
	 * Cell [x, y] is at array position [x * xSize + y]
	 */
	private GameGridButton[] buttonsGrid;

	/**
	 * The text used to display helpful information to the player
	 */
	private Label helpText;

	public OthelloGUI(Stage primaryStage, OthelloGameAPI gameReference) {
		super();
		theStage = primaryStage;
		gameAPI = gameReference;
	}

	@Override
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
				buttonsGrid[i * BOARD_SIZE + j].setDrawing(board.getCellColor(i, j));
			}
		}
	}

	@Override
	public void displayMessage(String s) {
		helpText.setText(s);
	}

	private GridPane createTopGrid() {
		GridPane topGrid = new GridPane();
		helpText = new Label("Some help text");
		topGrid.add(helpText, 0, 0);
		return topGrid;
	}

	private GridPane createGameGrid() {
		GridPane gameGrid = new GridPane();
		buttonsGrid = new GameGridButton[BOARD_SIZE * BOARD_SIZE];
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				Move m = new Move(x, y);
				GameGridButton btn = new GameGridButton();
				btn.setPrefSize(100, 100);
				btn.addEventHandler(ActionEvent.ACTION, event -> {
					//TODO save the click event as last requested move
					System.out.println("TODO: save the click event as last requested move");
				});
				gameGrid.add(btn, x, y);
				buttonsGrid[x * BOARD_SIZE + y] = btn;
			}
		}

		return gameGrid;
	}
}
