/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import szte.mi.Move;

import java.util.Set;

import static chaumette.othello.util.Constants.BOARD_SIZE;

/**
 * Represents a OthelloUI with a graphical user interface
 */
public class OthelloGUI implements OthelloUI {

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

	private Move moveRequest;
	private boolean allowMoveRequest;

	/**
	 * Creates a new OthelloGUI with the given Stage as the primary stage
	 *
	 * @param primaryStage the stage to use as primary stage
	 */
	public OthelloGUI(Stage primaryStage) {
		super();
		theStage = primaryStage;
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
		theStage.setOnCloseRequest(event -> Platform.exit());
		theStage.setResizable(false);
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
		Platform.runLater(() -> helpText.setText(s));
	}

	@Override
	public Move askUserForMove(PlayerColor playerColor) {
		displayMessage("Player " + playerColor + ", please make a move!!");
		allowMoveRequest = true;
		while (moveRequest == null && !Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		Move toReturn = moveRequest;
		moveRequest = null;
		return toReturn;
	}

	@Override
	public void displayValidMoves(Set<Move> valid, PlayerColor c) {
		PlayerColor toDisplay = PlayerColor.EMPTY;
		switch (c) {
			case WHITE -> toDisplay = PlayerColor.POSSIBLE_WHITE;
			case BLACK -> toDisplay = PlayerColor.POSSIBLE_BLACK;
		}
		for (Move move : valid) {
			buttonsGrid[move.x * BOARD_SIZE + move.y].setDrawing(toDisplay);
		}
	}

	private GridPane createTopGrid() {
		GridPane topGrid = new GridPane();
		helpText = new Label("Some help text");
		helpText.setFont(new Font("Arial", 28));
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
				btn.setBorder(Border.stroke(Color.BLACK));
				btn.setPrefSize(100, 100);
				btn.addEventHandler(ActionEvent.ACTION, event -> {
					if (allowMoveRequest) {
						moveRequest = m;
						allowMoveRequest = false;
					}
				});
				gameGrid.add(btn, x, y);
				buttonsGrid[x * BOARD_SIZE + y] = btn;
			}
		}

		return gameGrid;
	}
}
