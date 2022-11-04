package chaumette.othello;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.gui.OthelloGUI;
import chaumette.othello.util.Constants;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;
import chaumette.othello.util.players.ai.RandomAI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.util.Random;

import static chaumette.othello.util.PlayerColor.*;

public class OthelloMain extends Application {

	private final long timeBlack = 8000;
	private final long timeWhite = 8000;
	private final OthelloBoard theBoard = new OthelloOneDimArrayBoard();
	private final Random random = new Random();
	/**
	 * Stores the current player (starting with black)
	 */
	private PlayerColor currentPlayer = PlayerColor.BLACK;
	private Player playerBlack;
	private Player playerWhite;
	private Move prevMove;

	private OthelloGUI theGUI;

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

		playerBlack = new RandomAI(false);
		playerWhite = new RandomAI(false);
		// Black starts
		playerBlack.init(0, timeBlack, random);
		playerWhite.init(1, timeWhite, random);

		Task<PlayerColor> gameLoop = new Task<>() {
			@Override
			protected PlayerColor call() {
				theGUI.displayMessage("Welcome to Othello!");
				while (!theBoard.isGameOver()) {
					theGUI.displayBoardState(theBoard);
					theGUI.displayValidMoves(theBoard.getValidMoves(currentPlayer), currentPlayer);
					try {
						//Busy waiting -- sorry (example code)
						Thread.sleep(Constants.MOVE_DELAY);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					if (theBoard.canDoValidMove(currentPlayer)) {
						switch (currentPlayer) {
							case WHITE -> {
								prevMove = playerWhite.nextMove(prevMove, timeBlack, timeWhite);
								if (prevMove != null) {
									theBoard.doMove(prevMove, WHITE);
								} else {
									Platform.exit();
									System.err.println("Invalid move");
								}
							}
							case BLACK -> {
								prevMove = playerBlack.nextMove(prevMove, timeWhite, timeBlack);
								if (prevMove != null) {
									theBoard.doMove(prevMove, BLACK);
								} else {
									Platform.exit();
									System.err.println("Invalid move");
								}
							}
						}
					} else {
						switch (currentPlayer) {
							case WHITE -> prevMove = playerWhite.nextMove(prevMove, timeBlack, timeWhite);
							case BLACK -> prevMove = playerBlack.nextMove(prevMove, timeWhite, timeBlack);
						}
					}
					currentPlayer = nextPlayer(currentPlayer);
				}
				theGUI.displayBoardState(theBoard);
				PlayerColor winner = theBoard.getWinner();
				theGUI.displayMessage("Winner is " + winner);
				return theBoard.getWinner();
			}
		};
		Thread gameLoopThread = new Thread(gameLoop);
		gameLoopThread.start();
	}


	public PlayerColor nextPlayer(PlayerColor current) {
		switch (current) {
			case BLACK -> {
				return WHITE;
			}
			case WHITE -> {
				return BLACK;
			}
		}
		return EMPTY;
	}
}