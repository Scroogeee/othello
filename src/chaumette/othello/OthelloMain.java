package chaumette.othello;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.util.OthelloGameAPI;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;
import chaumette.othello.util.players.ai.RandomAI;
import chaumette.othello.util.players.human.GUIPlayer;
import javafx.application.Platform;

import java.util.Random;

import static chaumette.othello.util.PlayerColor.*;

public class OthelloMain implements OthelloGameAPI, Runnable {

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

	public static void main(String[] args) {
		System.out.println("Hello othello!");
		OthelloMain main = new OthelloMain();
		main.run();
	}

	@Override
	public void onQuit() {
		Platform.exit();
	}

	@Override
	public void run() {
		//TODO config who should play
		playerBlack = new RandomAI(false);
		playerWhite = new GUIPlayer();

		theBoard.init();
		// Black starts
		playerBlack.init(0, timeBlack, random);
		playerWhite.init(1, timeWhite, random);

		while (!theBoard.isGameOver()) {
			if (theBoard.canDoValidMove(currentPlayer)) {
				switch (currentPlayer) {
					case WHITE -> {
						prevMove = playerWhite.nextMove(prevMove, timeBlack, timeWhite);
						theBoard.doMove(prevMove, WHITE);
					}
					case BLACK -> {
						prevMove = playerBlack.nextMove(prevMove, timeWhite, timeBlack);
						theBoard.doMove(prevMove, BLACK);
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
		System.out.println("Game is over");
		System.out.println("Winner is " + theBoard.getWinner().ordinal());

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