/**
 * @author scrooge
 */

package chaumette.othello.test;

import chaumette.othello.external.Move;
import chaumette.othello.util.Constants;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;

import java.util.*;

import static chaumette.othello.util.Constants.moveToString;
import static chaumette.othello.util.PlayerColor.*;

/**
 * An OthelloTester is a dummy class used to test the capabilities of an OthelloBoard
 */
public class OthelloTester implements Runnable {
	private final OthelloBoard board = new OthelloOneDimArrayBoard();
	private final Scanner scanner = new Scanner(System.in);
	private final Random random = new Random();
	private final Map<PlayerColor, PlayerType> playerColorToPlayerType;
	private TestMode testMode;

	/**
	 * Creates a new OthelloTester with the given arguments
	 *
	 * @param args command line arguments
	 */
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

	/**
	 * Runs a new OthelloTester
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		new OthelloTester(args).run();
	}

	@Override
	public void run() {
		PlayerColor currentPlayer = BLACK;
		board.resetAndInit();
		while (!board.isGameOver()) {
			System.out.println("It's player " + currentPlayer.ordinal() + "'s turn!");
			System.out.println(board);
			printValidMoves(board, currentPlayer);
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

	private void printValidMoves(OthelloBoard board, PlayerColor currentPlayer) {
		StringBuilder validMoveString = new StringBuilder();
		for (Move validMove : board.getValidMoves(currentPlayer)) {
			validMoveString.append(moveToString(validMove)).append("\t");
		}
		System.out.println("Valid moves are: " + validMoveString);
	}

	private void makeAIMove(PlayerColor currentPlayer) {
		List<Move> validMoves = new ArrayList<>(board.getValidMoves(currentPlayer));
		Move toMake = validMoves.get(random.nextInt(validMoves.size()));
		System.out.println("AI chooses " + Constants.moveToString(toMake) + ".");
		board.doMove(toMake, currentPlayer);
	}

	private void makePlayerMove(PlayerColor currentPlayer) {
		boolean isValidMove = false;
		while (!isValidMove) {
			String input = scanner.nextLine();
			String[] parts = input.split(" ");
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

	/**
	 * Computes the next player
	 *
	 * @param current the current player
	 * @return the next player
	 */
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
