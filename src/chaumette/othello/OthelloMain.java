package chaumette.othello;

import chaumette.othello.gui.OthelloGUI;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;
import chaumette.othello.util.players.ai.GreedyLimitMoveAI;
import chaumette.othello.util.players.ai.RandomAI;
import chaumette.othello.util.players.human.GUIPlayer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import szte.mi.Move;
import szte.mi.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static chaumette.othello.util.PlayerColor.BLACK;
import static chaumette.othello.util.PlayerColor.WHITE;

/**
 * The main class which brings together an OthelloBoard, some OthelloUI and Players to
 * play a game of Othello
 */
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

	/**
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("Hello othello!");
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		theBoard.resetAndInit();

		theGUI = new OthelloGUI(primaryStage);
		theGUI.initUI();
		theGUI.displayBoardState(theBoard);

		playerWhite = new RandomAI();
		playerBlack = new GreedyLimitMoveAI();
		//playerWhite = new GUIPlayer(theGUI);

		colorToPlayer.put(BLACK, playerBlack);
		colorToPlayer.put(WHITE, playerWhite);

		// Black starts
		playerBlack.init(0, timeBlack, random);
		playerWhite.init(1, timeWhite, random);

		startedWorkers = new ArrayList<>();

		Task<PlayerColor> gameLoop = new Task<>() {
			@Override
			protected PlayerColor call() {
				int turnCount = 0;
				theGUI.displayMessage("Welcome to Othello!");
				while (!theBoard.isGameOver()) {
					theGUI.displayBoardState(theBoard);
					theGUI.displayValidMoves(theBoard.getValidMoves(currentPlayerColor), currentPlayerColor);
					if (!(colorToPlayer.get(currentPlayerColor) instanceof GUIPlayer)) {
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


					turnCount++;

					if (theBoard.canDoValidMove(currentPlayerColor)) {
						prevMove = colorToPlayer.get(currentPlayerColor).nextMove(prevMove, opponentTime, selfTime);
						if (prevMove != null) {
							theBoard.doMove(prevMove, currentPlayerColor);
						}
					} else {
						prevMove = colorToPlayer.get(currentPlayerColor).nextMove(prevMove, opponentTime, selfTime);
						//expecting null move
					}
					nextPlayer();
				}
				theGUI.displayBoardState(theBoard);
				System.out.println(theBoard);
				PlayerColor winner = theBoard.getWinner();
				theGUI.displayMessage("Winner is " + winner);
				return theBoard.getWinner();
			}
		};
		Thread gameLoopThread = new Thread(gameLoop);
		gameLoopThread.start();
		startedWorkers.add(gameLoop);
		gameLoop.setOnSucceeded(event -> {
			System.out.println("Succeded");
		});
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


	/**
	 * Switches to the next player (based on the current one)
	 */
	public void nextPlayer() {
		currentPlayerColor = PlayerColor.invert(currentPlayerColor);
	}
}