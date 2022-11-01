/**
 * @author scrooge
 */

package chaumette.othello.util.players.ai;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;

import java.util.ArrayList;
import java.util.Random;

public class RandomAI implements Player {
	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();
	private PlayerColor myPlayerColor;
	private PlayerColor opponentPlayerColor;

	private Random theRandom;

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
		theRandom = rnd;
		mentalBoardModel.init();
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) {
			mentalBoardModel.doMove(prevMove, opponentPlayerColor);
		}
		ArrayList<Move> possibleMoves = new ArrayList<>(mentalBoardModel.getValidMoves(myPlayerColor));
		Move toMake = possibleMoves.get(theRandom.nextInt(possibleMoves.size()));
		mentalBoardModel.doMove(toMake, myPlayerColor);
		return toMake;
	}
}
