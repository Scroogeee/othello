package chaumette.othello;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.gui.OthelloGUI;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;
import chaumette.othello.util.players.human.GUIPlayer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static chaumette.othello.util.PlayerColor.BLACK;
import static chaumette.othello.util.PlayerColor.WHITE;

public class OthelloMain extends Application {

	private final long timeBlack = 8000;
	private final long timeWhite = 8000;
	private final OthelloBoard theBoard = new OthelloOneDimArrayBoard();
	private final Random random = new Random();

	private final HashMap<PlayerColor, Player> colorToPlayer = new HashMap<>();
	private PlayerColor currentPlayerColor = BLACK;

	private Player playerBlack;
	private Player playerWhite;
	private Move prevMove;

	private OthelloGUI theGUI;
	private List<Task<PlayerColor>> startedWorkers;

	public static void main(String[] args) {
		System.out.println("Hello othello!");
		Application.launch();
	}

	@Override
	public void start(Stage primaryStage) {
		theBoard.init();

		theGUI = new OthelloGUI(primaryStage);
		theGUI.initUI();
		theGUI.displayBoardState(theBoard);

		playerBlack = new GUIPlayer(theGUI);
		playerWhite = new GUIPlayer(theGUI);

		colorToPlayer.put(BLACK, playerBlack);
		colorToPlayer.put(WHITE, playerWhite);

		// Black starts
		playerBlack.init(0, timeBlack, random);
		playerWhite.init(1, timeWhite, random);

		startedWorkers = new ArrayList<>();

		Task<PlayerColor> gameLoop = new Task<>() {
			@Override
			protected PlayerColor call() {
				theGUI.displayMessage("Welcome to Othello!");
				while (!theBoard.isGameOver()) {
					theGUI.displayBoardState(theBoard);
					if (colorToPlayer.get(currentPlayerColor) instanceof GUIPlayer) {
						//only display for human players
						theGUI.displayValidMoves(theBoard.getValidMoves(currentPlayerColor), currentPlayerColor);
					} else {
						theGUI.displayMessage("AI is thinking...");
					}
					long selfTime = 0, opponentTime = 0;
					switch (currentPlayerColor) {
						case BLACK -> {
							selfTime = timeBlack;
							opponentTime = timeWhite;
						}
						case WHITE -> {
							selfTime = timeWhite;
							opponentTime = timeBlack;
						}
					}

					if (theBoard.canDoValidMove(currentPlayerColor)) {
						prevMove = colorToPlayer.get(currentPlayerColor).nextMove(prevMove, opponentTime, selfTime);
						if (prevMove != null) {
							theBoard.doMove(prevMove, currentPlayerColor);
						}
					} else {
						prevMove = colorToPlayer.get(currentPlayerColor).nextMove(prevMove, opponentTime, selfTime);
					}
					nextPlayer();
				}
				theGUI.displayBoardState(theBoard);
				PlayerColor winner = theBoard.getWinner();
				theGUI.displayMessage("Winner is " + winner);
				return theBoard.getWinner();
			}
		};
		Thread gameLoopThread = new Thread(gameLoop);
		gameLoopThread.start();
		startedWorkers.add(gameLoop);
	}

	@Override
	public void stop() {
		System.out.println("Stop called");
		// otherwise a worker may still run in a thread blocking exit
		this.endWorkers();
	}

	//Example code by Markus Joppich
	private void endWorkers() {
		for (Task<PlayerColor> turnTask : startedWorkers) {
			turnTask.cancel(true);
		}
		startedWorkers.clear();
	}


	public void nextPlayer() {
		switch (currentPlayerColor) {
			case BLACK -> currentPlayerColor = WHITE;
			case WHITE -> currentPlayerColor = BLACK;
		}
	}
}