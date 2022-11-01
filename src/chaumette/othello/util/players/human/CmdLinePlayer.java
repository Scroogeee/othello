/**
 * @author scrooge
 */

package chaumette.othello.util.players.human;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.util.Constants;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;

import java.util.Random;
import java.util.Scanner;

public class CmdLinePlayer implements Player {

	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();
	private final Scanner scanner = new Scanner(System.in);
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;

	@Override
	public void init(int order, long t, Random rnd) {
		switch (order) {
			case 0 -> {
				myPlayerColor = PlayerColor.BLACK;
				opponentPlayerColor = PlayerColor.WHITE;
			}
			case 1 -> {
				myPlayerColor = PlayerColor.WHITE;
				opponentPlayerColor = PlayerColor.BLACK;
			}
		}
		mentalBoardModel.init();
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) {
			System.out.println("Player " + opponentPlayerColor.ordinal() + " makes move: " + Constants.moveToString(prevMove));
			mentalBoardModel.doMove(prevMove, opponentPlayerColor);
		}
		System.out.println("Current board state:");
		System.out.println(mentalBoardModel);
		Move move = null;
		boolean isValidMove = false;
		while (!isValidMove) {
			System.out.println("Player " + myPlayerColor.ordinal() + ", please make a move!");
			String input = scanner.nextLine();
			String[] parts = input.split("\s");
			if (parts.length >= 2) {
				try {
					int i = Integer.parseInt(parts[0]);
					int j = Integer.parseInt(parts[1]);
					move = new Move(i, j);
					isValidMove = mentalBoardModel.isValidMove(move, myPlayerColor);
					if (!isValidMove) {
						System.out.println("Invalid move, please try again!");
						Constants.printValidMoves(mentalBoardModel, myPlayerColor);
					}
				} catch (NumberFormatException nfex) {
					System.out.println("Invalid move, please try again!");
				}
			} else {
				System.out.println("Invalid move, please try again!");
			}
		}
		mentalBoardModel.doMove(move, myPlayerColor);
		System.out.println(mentalBoardModel);
		return move;
	}

}
