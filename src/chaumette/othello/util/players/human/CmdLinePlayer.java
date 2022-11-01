/**
 * @author scrooge
 */

package chaumette.othello.util.players.human;

import chaumette.othello.external.Move;
import chaumette.othello.external.Player;
import chaumette.othello.util.board.OthelloBoard;
import chaumette.othello.util.board.OthelloOneDimArrayBoard;

import java.util.Random;

public class CmdLinePlayer implements Player {

	private final OthelloBoard mentalBoardModel = new OthelloOneDimArrayBoard();

	@Override
	public void init(int order, long t, Random rnd) {

	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		return null;
	}

}
