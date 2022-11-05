/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.external.Move;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;

import java.util.Scanner;
import java.util.Set;

import static chaumette.othello.util.Constants.moveToString;

public class OthelloCmdLineUI implements OthelloUI {

	private final Scanner scanner = new Scanner(System.in);

	@Override
	public void initUI() {

	}

	@Override
	public void displayBoardState(OthelloBoard board) {
		System.out.println(board);
	}

	@Override
	public void displayMessage(String s) {
		System.out.println(s);
	}

	@Override
	public Move askUserForMove(PlayerColor color) {
		Move move = null;
		String input = scanner.nextLine();
		String[] parts = input.split(" ");
		if (parts.length >= 2) {
			try {
				int i = Integer.parseInt(parts[0]);
				int j = Integer.parseInt(parts[1]);
				move = new Move(i, j);
			} catch (NumberFormatException ex) {
				displayMessage("Invalid syntax, please try again!");
			} catch (IndexOutOfBoundsException ex) {
				displayMessage("Move Index out of bounds, please try again!");
			}
		} else {
			displayMessage("Invalid syntax, please try again!");
		}
		return move;
	}


	@Override
	public void displayValidMoves(Set<Move> valid, PlayerColor c) {
		StringBuilder validMoveString = new StringBuilder();
		for (Move validMove : valid) {
			validMoveString.append(moveToString(validMove)).append("\t");
		}
		System.out.println("Valid moves are: " + validMoveString);
	}
}
