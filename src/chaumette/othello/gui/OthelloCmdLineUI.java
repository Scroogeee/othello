/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.util.OthelloGameAPI;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OthelloCmdLineUI extends OthelloUI {

	public OthelloCmdLineUI(Stage primaryStage, OthelloGameAPI gameReference) {
		super(primaryStage, gameReference);
	}

	@Override
	public void initUI() {
		BorderPane mainPane = new BorderPane();

		//Build console output
		ScrollPane logPane = new ScrollPane();
		TextArea logArea = new TextArea();
		logArea.setEditable(false);
		logPane.setContent(logArea);
		mainPane.setCenter(logPane);

		TextField userInputField = new TextField();
		userInputField.setPromptText("Enter command");
		userInputField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				String command = userInputField.getText();
				userInputField.setText("");
				logArea.appendText(command + "\n");
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

	private void interpretCommand(String s) {
		switch (s.toUpperCase()) {
			case "QUIT", "STOP", "CLOSE" -> gameAPI.onQuit();
		}
		//TODO send command
	}
}
