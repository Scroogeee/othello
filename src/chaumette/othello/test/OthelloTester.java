/**
 * @author scrooge
 */

package chaumette.othello.test;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.board.OthelloTwoDimArrayBoard;
import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static chaumette.othello.util.PlayerColor.*;

public class OthelloTester implements Runnable {
	private final OthelloBoard board = new OthelloTwoDimArrayBoard();
	private final Scanner scanner = new Scanner(System.in);

	private final Random random = new Random();

	public static void main(String[] args) {
		new OthelloTester().run();
	}

	@Override
	public void run() {
		PlayerColor currentPlayer = BLACK;
		board.init();
		while (!board.isGameOver()) {
			System.out.println("It's player " + currentPlayer.ordinal() + "'s turn!");
			System.out.println(board);
			printValidMoves(currentPlayer);
			if (board.canDoValidMove(currentPlayer)) {
				//makePlayerMove(currentPlayer);
				makeAIMove(currentPlayer);
			} else {
				System.out.println("No valid move possible, passing turn!");
			}
			currentPlayer = nextPlayer(currentPlayer);
		}
		System.out.println("Game is over");
		System.out.println("Winner is " + board.getWinner().ordinal());
	}

	private void makeAIMove(PlayerColor currentPlayer) {
		List<Move> validMoves = new ArrayList<>(board.getValidMoves(currentPlayer));
		Move toMake = validMoves.get(random.nextInt(validMoves.size()));
		System.out.println("AI chooses " + moveToString(toMake) + ".");
		board.doMove(toMake, currentPlayer);
	}

	private void makePlayerMove(PlayerColor currentPlayer) {
		boolean isValidMove = false;
		while (!isValidMove) {
			String input = scanner.nextLine();
			String[] parts = input.split("\s");
			if (parts.length >= 2) {
				int i = Integer.parseInt(parts[0]);
				int j = Integer.parseInt(parts[1]);
				Move move = new Move(i, j);
				isValidMove = board.isValidMove(move, currentPlayer);
				if (isValidMove) {
					board.doMove(move, currentPlayer);
				} else {
					System.out.println("Invalid move, please try again!");
				}
			} else {
				System.out.println("Invalid move, please try again!");
			}
		}
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

	private String moveToString(Move m) {
		return "[x:" + m.x + ", y:" + m.y + "]";
	}

	private void printValidMoves(PlayerColor c) {
		StringBuilder validMoveString = new StringBuilder();
		for (Move validMove : board.getValidMoves(c)) {
			validMoveString.append(moveToString(validMove)).append("\t");
		}
		System.out.println("Valid moves are: " + validMoveString);
	}
}
