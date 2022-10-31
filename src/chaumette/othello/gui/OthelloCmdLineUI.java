/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.external.Move;
import chaumette.othello.util.OthelloGameAPI;
import chaumette.othello.util.PlayerColor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OthelloCmdLineUI extends OthelloUI {

	private TextArea logArea;

	public OthelloCmdLineUI(Stage primaryStage, OthelloGameAPI gameReference) {
		super(primaryStage, gameReference);
	}

	@Override
	public void initUI() {
		BorderPane mainPane = new BorderPane();

		//Build console output
		ScrollPane logPane = new ScrollPane();
		logArea = new TextArea();
		logArea.setEditable(false);
		logPane.setContent(logArea);
		mainPane.setCenter(logPane);

		TextField userInputField = new TextField();
		userInputField.setPromptText("Enter command");
		userInputField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				String command = userInputField.getText();
				userInputField.setText("");
				interpretCommand(command);
			}
		});
		mainPane.setBottom(userInputField);

		theStage.setTitle("Othello-CMD-Line");
		//theStage.setResizable(false);
		// put main scene into stage
		primaryScene = new Scene(mainPane);
		theStage.setScene(primaryScene);
		theStage.show();
	}

	@Override
	public void displayBoardState(OthelloBoard board) {
		PlayerColor[][] cells = board.getBoardAsTwoDimArray();
		displayMessage("Board state:");
		StringBuilder message;
		for (PlayerColor[] cell : cells) {
			message = new StringBuilder();
			for (int j = 0; j < cells.length; j++) {
				message.append(cell[j].ordinal());
			}
			displayMessage(message.toString());
		}

	}

	@Override
	public void displayMessage(String s) {
		logArea.appendText(s + "\n");
	}

	private void interpretCommand(String s) {
		//TODO change to allow playing against AI
		String[] parts = s.split("\s");
		switch (parts[0].toUpperCase()) {
			case "QUIT", "STOP", "CLOSE", "EXIT" -> gameAPI.onQuit();
			case "PLAY", "MOVE" -> {
				if (parts.length >= 3) {
					int i = Integer.parseInt(parts[1]);
					int j = Integer.parseInt(parts[2]);
					Move m = new Move(i, j);
					gameAPI.requestMove(m, gameAPI.getCurrentPlayer());
				}
			}
		}
	}
}
