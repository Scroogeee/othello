/**
 * @author scrooge
 */

package chaumette.othello.test;

import chaumette.othello.board.OthelloBoard;
import chaumette.othello.board.OthelloOneDimArrayBoard;
import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;

import java.util.*;

import static chaumette.othello.util.PlayerColor.*;

public class OthelloTester implements Runnable {
	private final OthelloBoard board = new OthelloOneDimArrayBoard();
	private final Scanner scanner = new Scanner(System.in);
	private final Random random = new Random();
	private final Map<PlayerColor, PlayerType> playerColorToPlayerType;
	private TestMode testMode;

	public OthelloTester(String[] args) {
		playerColorToPlayerType = new HashMap<>();
		if (args.length == 1) {
			switch (args[0].toUpperCase()) {
				case "HVH" -> {
					testMode = TestMode.HVH;
					playerColorToPlayerType.put(BLACK, PlayerType.HUMAN);
					playerColorToPlayerType.put(WHITE, PlayerType.HUMAN);
				}
				case "HVC" -> {
					testMode = TestMode.HVC;
					playerColorToPlayerType.put(BLACK, PlayerType.HUMAN);
					playerColorToPlayerType.put(WHITE, PlayerType.COMPUTER);
				}
				case "CVH" -> {
					testMode = TestMode.CVH;
					playerColorToPlayerType.put(BLACK, PlayerType.COMPUTER);
					playerColorToPlayerType.put(WHITE, PlayerType.HUMAN);
				}
				case "CVC" -> {
					testMode = TestMode.CVC;
					playerColorToPlayerType.put(BLACK, PlayerType.COMPUTER);
					playerColorToPlayerType.put(WHITE, PlayerType.COMPUTER);
				}
			}
		} else {
			//default to human versus computer
			testMode = TestMode.HVC;
			playerColorToPlayerType.put(BLACK, PlayerType.HUMAN);
			playerColorToPlayerType.put(WHITE, PlayerType.COMPUTER);
		}
	}

	public static void main(String[] args) {
		new OthelloTester(args).run();
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
				switch (playerColorToPlayerType.get(currentPlayer)) {
					case HUMAN -> makePlayerMove(currentPlayer);
					case COMPUTER -> makeAIMove(currentPlayer);
				}
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
				try {
					int i = Integer.parseInt(parts[0]);
					int j = Integer.parseInt(parts[1]);
					Move move = new Move(i, j);
					isValidMove = board.isValidMove(move, currentPlayer);
					if (isValidMove) {
						board.doMove(move, currentPlayer);
					} else {
						System.out.println("Invalid move, please try again!");
					}
				} catch (NumberFormatException nfex) {
					isValidMove = false;
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
